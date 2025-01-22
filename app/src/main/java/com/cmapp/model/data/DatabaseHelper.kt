package com.cmapp.model.data

import android.net.Uri
import android.util.Log
import com.cmapp.R
import com.cmapp.model.data.DataBaseHelper.database
import com.cmapp.model.domain.database.Potion
import com.cmapp.model.domain.database.Profile
import com.cmapp.model.domain.database.Spell
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.mindrot.jbcrypt.BCrypt
import java.util.concurrent.CompletableFuture

object DataBaseHelper {

    val persistance =
        FirebaseDatabase.getInstance("https://hogwarts-apprentice-default-rtdb.europe-west1.firebasedatabase.app/")
            .setPersistenceEnabled(false)
    val database =
        FirebaseDatabase.getInstance("https://hogwarts-apprentice-default-rtdb.europe-west1.firebasedatabase.app/")

    val imageStorage = FirebaseStorage.getInstance("gs://hogwarts-apprentice.firebasestorage.app")

    fun addNotificationListener(onNotificationReceived: (String, Spell) -> Unit) {
        val myRef = database.getReference("notifications")

        myRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                // A new notification has been added
                val username = snapshot.key // The username is the child key
                val spell = snapshot.getValue(Spell::class.java) // The spellKey is the value

                if (username != null && spell != null) {
                    onNotificationReceived(username, spell)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {
                Log.e("RealtimeDB", "Database error: ${error.message}")
            }
        })
    }

    fun registerUser(
        username: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if(!isValidUsernameLength(username)){
            onError("Username must have 3 to 20 characters")
            return
        }
        if(!isValidUsername(username)){
            onError("Username cannot have special characters")
            return
        }
        if(!isValidPasswordLength(password)){
            onError("Password must have 8 to 30 characters")
            return
        }
        if(!isValidPassword(password)){
            onError("Password must include lowercase, uppercase, numbers, and special characters")
            return
        }

        val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())

        val userData = mapOf("password" to hashedPassword)

        val usernameRef = database.getReference("accounts")

        usernameRef.child(username).get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()){
                    onError("Username already taken!")
                    Log.d("register", "user with this username already exists")
                } else {
                    addUser(usernameRef, username, userData, onSuccess, onError)
                }
            }
            .addOnFailureListener { exception ->
                onError("Something went wrong on register")
                Log.d("register", exception.localizedMessage ?: "error registering the user")
            }
    }

    fun loginUser(
        username: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val usernameRef = database.getReference("accounts")

        usernameRef.child(username).get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()){
                    val storedPassword = snapshot.child("password").getValue(String::class.java)
                    if (BCrypt.checkpw(password, storedPassword)) {
                        onSuccess()
                        Log.d("login", "User logged in successfully")
                    } else {
                        onError("Incorrect password")
                        Log.d("login", "password mismatch")
                    }
                } else {
                    onError("User does not exist")
                    Log.d("login", "Non-existing user")
                }
            }
    }

    private fun addUser(
        usernameRef: DatabaseReference,
        username: String,
        userData: Map<String, String>,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        usernameRef.child(username).setValue(userData)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    addProfile(username).thenAccept { sucess ->
                        if(sucess){
                            onSuccess()
                        }
                    }
                    onSuccess()
                    Log.d("register", "User registered successfully")
                } else {
                    onError("Registration failed")
                    Log.d("register", task.exception?.localizedMessage ?: "error registering the user")
                }
            }
    }

    fun addProfile(username: String): CompletableFuture<Boolean> {

        val profile = Profile(username, defaultPhoto, wandsFrontUrls[0], wandsSideUrls[0])
        val completableFuture = CompletableFuture<Boolean>()

        database.getReference("profiles").child(username).setValue(profile).addOnCompleteListener { task ->

            if (task.isSuccessful) { completableFuture.complete(true) }
            else { completableFuture.complete(false) }
        }

        return completableFuture
    }

    fun getProfile(username: String, onResult: (Profile) -> Unit){

        database.getReference("profiles").child(username).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val profile = dataSnapshot.getValue(Profile::class.java)
                profile?.username = dataSnapshot.key.toString()
                onResult(profile!!)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    fun getProfiles(onResult: (List<Profile>) -> Unit) {

        database.getReference("profiles").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val profileList = mutableListOf<Profile>()

                for(snapshot in dataSnapshot.children){
                    val profile = snapshot.getValue(Profile::class.java)
                    profile?.username = snapshot.key.toString()
                    profile?.let { profileList.add(it) }
                }

                onResult(profileList)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error retrieving data: ${error.message}")
            }
        })
    }

    fun updateProfilePhoto(username: String, photo: String){

        uploadImage(photo){ sucess, url ->
            if(sucess){
                val update = hashMapOf<String, Any>("photo" to url)
                database.getReference("profiles").child(username).updateChildren(update)
            }
        }
    }

    fun updateWand(username: String, wand: String, wandSide: String){

        val update = hashMapOf<String, Any>("wandFront" to wand, "wandSide" to wandSide)
        database.getReference("profiles").child(username).updateChildren(update)
    }

    fun addFriendRequest(username: String, friendUsername: String): CompletableFuture<Boolean> {

        val completableFuture = CompletableFuture<Boolean>()

        database.getReference("usersInfo").child(friendUsername).child("requests").setValue(username).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                completableFuture.complete(true)
            } else {
                completableFuture.complete(false)
            }
        }

        return completableFuture
    }

    fun getFriendRequests(username: String, onResult: (List<Profile>) -> Unit) {

        CoroutineScope(Dispatchers.IO).launch {

            val requestsRef = database.getReference("usersInfo").child(username).child("requests")
            val dataSnapshot = requestsRef.get().await()

            val requestsProfiles = mutableListOf<Profile>()
            val deferredList = mutableListOf<CompletableDeferred<Profile?>>()

            for (snapshot in dataSnapshot.children) {
                val profileUsername = snapshot.key.toString()

                val potionDeferred = CompletableDeferred<Profile?>()
                launch {
                    val profile = getProfileAsync(profileUsername)
                    potionDeferred.complete(profile)
                }
                deferredList.add(potionDeferred)
            }

            deferredList.forEach{
                it.await()
                requestsProfiles.add(it.getCompleted()!!)
            }
            onResult(requestsProfiles)
        }
    }

    fun getFriends(username: String, onResult: (List<Profile>) -> Unit) {

        CoroutineScope(Dispatchers.IO).launch {

            val friendsRef = database.getReference("usersInfo").child(username).child("friends")
            val dataSnapshot = friendsRef.get().await()

            val friendsProfiles = mutableListOf<Profile>()
            val deferredList = mutableListOf<CompletableDeferred<Profile?>>()

            for (snapshot in dataSnapshot.children) {
                val profileUsername = snapshot.key.toString()

                val potionDeferred = CompletableDeferred<Profile?>()
                launch {
                    val profile = getProfileAsync(profileUsername)
                    potionDeferred.complete(profile)
                }
                deferredList.add(potionDeferred)
            }

            deferredList.forEach{
                it.await()
                friendsProfiles.add(it.getCompleted()!!)
            }
            onResult(friendsProfiles)
        }
    }

    suspend fun getProfileAsync(username: String): Profile? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<Profile?>()
            getProfile(username) { profile ->
                deferred.complete(profile)
            }
            deferred.await()
        }
    }

    fun getPotions(onResult: (List<Potion>) -> Unit) {

        database.getReference("potions").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val potionList = mutableListOf<Potion>()

                for(snapshot in dataSnapshot.children){
                    val potion = snapshot.getValue(Potion::class.java)
                    potion?.key = snapshot.key.toString()
                    potion?.let { potionList.add(it) }
                }

                onResult(potionList)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error retrieving data: ${error.message}")
            }
        })
    }

    fun addPotion(potion: Potion): CompletableFuture<Boolean> {

        val completableFuture = CompletableFuture<Boolean>()

        database.getReference("potions").push().setValue(potion).addOnCompleteListener { task ->

            if (task.isSuccessful) { completableFuture.complete(true) }
            else { completableFuture.complete(false) }
        }

        return completableFuture
    }

    fun getPotion(potionKey:String, onResult: (Potion) -> Unit){

        database.getReference("potions").child(potionKey).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val potion = dataSnapshot.getValue(Potion::class.java)
                potion?.key = dataSnapshot.key.toString()
                onResult(potion!!)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    suspend fun getPotionAsync(potionKey: String): Potion? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<Potion?>()
            getPotion(potionKey) { potion ->
                deferred.complete(potion)
            }
            deferred.await()
        }
    }

    fun  deleteFriendRequest(sender: String, receiver: String): CompletableFuture<Boolean> {

        val completableFuture = CompletableFuture<Boolean>()

        database.getReference("usersInfo").child(receiver).child("requests").child(sender).removeValue()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    completableFuture.complete(true)
                } else {
                    completableFuture.complete(false)
                }
            }

        return completableFuture
    }

    fun addFriends(username: String, friendUsername: String): CompletableFuture<Boolean> {

        val completableFuture = CompletableFuture<Boolean>()

        database.getReference("usersInfo").child(username).child("friends").child(friendUsername)
            .setValue("")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    database.getReference("usersInfo").child(friendUsername).child("friends").child(username)
                        .setValue("")
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                if (task.isSuccessful) {
                                    deleteFriendRequest(friendUsername, username)
                                    completableFuture.complete(true)
                                }
                            } else {
                                completableFuture.complete(false)
                            }
                        }
                } else {
                    completableFuture.complete(false)
                }
            }

        return completableFuture
    }

    fun deleteFriends(username: String, friendUsername: String): CompletableFuture<Boolean> {

        val completableFuture = CompletableFuture<Boolean>()

        database.getReference("usersInfo").child(username).child("friends").child(friendUsername).removeValue()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    database.getReference("usersInfo").child(friendUsername).child("friends").child(username).removeValue()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                if (task.isSuccessful) {
                                    completableFuture.complete(true)
                                }
                            } else {
                                completableFuture.complete(false)
                            }
                        }
                } else {
                    completableFuture.complete(false)
                }
            }

        return completableFuture
    }

    fun addLearnedPotion(username: String, potionkey: String): CompletableFuture<Boolean> {

        val completableFuture = CompletableFuture<Boolean>()

        database.getReference("usersInfo").child(username).child("potions").child(potionkey).setValue("").addOnCompleteListener { task ->

            if (task.isSuccessful) { completableFuture.complete(true) }
            else { completableFuture.complete(false) }
        }

        return completableFuture
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getLearnedPotions(username: String, onResult: (List<Potion>) -> Unit) {

        CoroutineScope(Dispatchers.IO).launch {

            val learnedPotionsRef = database.getReference("usersInfo").child(username).child("potions")
            val dataSnapshot = learnedPotionsRef.get().await()

            val potions = mutableListOf<Potion>()
            val deferredList = mutableListOf<CompletableDeferred<Potion?>>()

            for (snapshot in dataSnapshot.children) {
                val potionKey = snapshot.key.toString()

                val potionDeferred = CompletableDeferred<Potion?>()
                launch {
                    val potion = getPotionAsync(potionKey)
                    potionDeferred.complete(potion)
                }
                deferredList.add(potionDeferred)
            }

            deferredList.forEach{
                it.await()
                potions.add(it.getCompleted()!!)
            }
            onResult(potions)
        }
    }

    fun uploadImage(image: String, onComplete: (Boolean, String) -> Unit) {

        val fileStorageReference: StorageReference = imageStorage.getReference(image)

        val imageUri = Uri.parse(image)
        fileStorageReference.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                fileStorageReference.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    onComplete(true, imageUrl)
                }.addOnFailureListener { exception ->
                    onComplete(false, "")
                }
            }
            .addOnFailureListener { exception ->
                onComplete(false, "")
            }
    }
}


fun getLocationsByIngredient(ingredient: String, onResult: (List<LatLng>) -> Unit) {

    database.getReference("ingredients").child(ingredient).child("positions").addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {

            if (snapshot.exists()) {
                val locations = snapshot.children.mapNotNull {
                    val lat = it.child("lat").getValue(Double::class.java)
                    val lng = it.child("lng").getValue(Double::class.java)
                    if (lat != null && lng != null) LatLng(lat, lng) else null
                }
                onResult(locations)
            } else {
                onResult(emptyList()) // No locations found
            }
        }
        override fun onCancelled(error: DatabaseError) {}
    })
}

fun addLocationByIngredient( ingredient: String,
                             location: LatLng): CompletableFuture<Boolean> {

    val completableFuture = CompletableFuture<Boolean>()

    val newLocation = mapOf("lat" to location.latitude, "lng" to location.longitude)


    database.getReference("ingredients").child(ingredient).child("positions").push().setValue(newLocation).addOnCompleteListener { task ->

        if (task.isSuccessful) { completableFuture.complete(true) }
        else { completableFuture.complete(false) }
    }

    return completableFuture
}