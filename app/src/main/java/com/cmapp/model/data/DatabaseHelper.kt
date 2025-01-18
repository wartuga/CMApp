package com.cmapp.model.data

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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

    fun addPotion(
        color: String,
        description: String?,
        ingredients: String?,
        name: String?
    ): CompletableFuture<Boolean> {

        val completableFuture = CompletableFuture<Boolean>()

        val potion = hashMapOf(
            "color" to color,
            "description" to description,
            "ingredients" to ingredients,
            "name" to name
        )

        database.getReference("potions").push().setValue(potion).addOnCompleteListener { task ->

            if (task.isSuccessful) { completableFuture.complete(true) }
            else { completableFuture.complete(false) }
        }

        return completableFuture
    }

    fun addSpell(
        color: String,
        description: String?,
        movements: String?,
        name: String?,
        time: Int?
    ): CompletableFuture<Boolean> {

        val completableFuture = CompletableFuture<Boolean>()

        val spell = hashMapOf(
            "color" to color,
            "description" to description,
            "movements" to movements,
            "name" to name,
            "time" to time
        )

        database.getReference("spells").push().setValue(spell).addOnCompleteListener { task ->

            if (task.isSuccessful) { completableFuture.complete(true) }
            else { completableFuture.complete(false) }
        }

        return completableFuture
    }

    fun getSpells(onResult: (HashMap<String, Any>) -> Unit) {

        database.getReference("spells").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val spell = snapshot.value as? HashMap<String, Any>
                if (spell != null) {
                    onResult(spell)
                } else {
                    onResult(hashMapOf())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error retrieving data: ${error.message}")
            }
        })
    }

    private fun isAuthValid(username: String, password: String) =
        isValidUsername(username) &&
        isValidUsernameLength(username) &&
        isValidPassword(password) &&
        isValidPasswordLength(password)
}