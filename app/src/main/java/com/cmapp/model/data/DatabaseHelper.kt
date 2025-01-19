package com.cmapp.model.data

import android.util.Log
import com.cmapp.model.domain.database.Potion
import com.cmapp.model.domain.database.Spell
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

    fun registerUser(
        username: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if(!isAuthValid(username, password)){
            Log.d("AuthValid", "Failed")
            return
        }
        Log.d("AuthValid", "Passed")

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
        if(!isAuthValid(username, password)){
            Log.d("AuthValid", "Failed")
            return
        }
        Log.d("AuthValid", "Passed")

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
                    onSuccess()
                    Log.d("register", "User registered successfully")
                } else {
                    onError("Registration failed")
                    Log.d("register", task.exception?.localizedMessage ?: "error registering the user")
                }
            }
    }

    fun addPotion(potion: Potion): CompletableFuture<Boolean> {

        val completableFuture = CompletableFuture<Boolean>()

        database.getReference("potions").push().setValue(potion).addOnCompleteListener { task ->

            if (task.isSuccessful) { completableFuture.complete(true) }
            else { completableFuture.complete(false) }
        }

        return completableFuture
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

    fun addSpell(spell: Spell): CompletableFuture<Boolean> {

        val completableFuture = CompletableFuture<Boolean>()

        database.getReference("spells").push().setValue(spell).addOnCompleteListener { task ->

            if (task.isSuccessful) { completableFuture.complete(true) }
            else { completableFuture.complete(false) }
        }

        return completableFuture
    }

    fun getSpells(onResult: (List<Spell>) -> Unit) {

        database.getReference("spells").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val spellList = mutableListOf<Spell>()

                for(snapshot in dataSnapshot.children){
                    val spell = snapshot.getValue(Spell::class.java)
                    spell?.key = snapshot.key.toString()
                    spell?.let { spellList.add(it) }
                }

                onResult(spellList)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error retrieving data: ${error.message}")
            }
        })
    }

    fun getSpell(spellKey:String, onResult: (Spell) -> Unit){

        database.getReference("spells").child(spellKey).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val spell = dataSnapshot.getValue(Spell::class.java)
                spell?.key = dataSnapshot.key.toString()
                onResult(spell!!)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    suspend fun getSpellAsync(spellKey: String): Spell? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<Spell?>()
            getSpell(spellKey) { spell ->
                deferred.complete(spell)
            }
            deferred.await()
        }
    }

    fun addLearnedSpell(username: String, spellKey: String): CompletableFuture<Boolean> {

        val completableFuture = CompletableFuture<Boolean>()

        database.getReference("usersInfo").child(username).child("spells").child(spellKey).setValue("").addOnCompleteListener { task ->

            if (task.isSuccessful) { completableFuture.complete(true) }
            else { completableFuture.complete(false) }
        }

        return completableFuture
    }

    fun getLearnedSpells(username: String, onResult: (List<Spell>) -> Unit) {

        CoroutineScope(Dispatchers.IO).launch {

            val learnedSpellsRef = database.getReference("usersInfo").child(username).child("spells")
            val dataSnapshot = learnedSpellsRef.get().await()

            val spells = mutableListOf<Spell>()
            val deferredList = mutableListOf<CompletableDeferred<Spell?>>()

            for (snapshot in dataSnapshot.children) {
                val spellKey = snapshot.key.toString()

                val spellDeferred = CompletableDeferred<Spell?>()
                launch {
                    val spell = getSpellAsync(spellKey)
                    spellDeferred.complete(spell)
                }
                deferredList.add(spellDeferred)
            }

            deferredList.forEach{
                it.await()
                spells.add(it.getCompleted()!!)
            }
            onResult(spells)
        }
    }

    private fun isAuthValid(username: String, password: String) =
        isValidUsername(username) &&
        isValidUsernameLength(username) &&
        isValidPassword(password) &&
        isValidPasswordLength(password)
}