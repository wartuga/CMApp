package com.cmapp.model.data

import com.cmapp.model.data.DataBaseHelper.database
import com.cmapp.model.domain.database.Spell
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.concurrent.CompletableFuture

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
                val spell = Spell(
                    key = snapshot.key.toString(),
                    color = snapshot.child("color").getValue(String::class.java),
                    description = snapshot.child("description").getValue(String::class.java),
                    movements = snapshot.child("movements").children.map { it.getValue(String::class.java) ?: "" },
                    name = snapshot.child("name").getValue(String::class.java),
                    time = snapshot.child("time").getValue(Int::class.java)
                )
                spellList.add(spell)
            }

            onResult(spellList)
        }

        override fun onCancelled(error: DatabaseError) {
            println("Error retrieving data: ${error.message}")
        }
    })
}

fun getSpell(spellKey:String, onResult: (Spell) -> Unit){

    database.getReference("spells").child(spellKey).addListenerForSingleValueEvent(object :
        ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val spell = Spell(
                key = dataSnapshot.key.toString(),
                color = dataSnapshot.child("color").getValue(String::class.java),
                description = dataSnapshot.child("description").getValue(String::class.java),
                movements = dataSnapshot.child("movements").children.map { it.getValue(String::class.java) ?: "" },
                name = dataSnapshot.child("name").getValue(String::class.java),
                time = dataSnapshot.child("time").getValue(Int::class.java)
            )
            onResult(spell)
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

fun addLearnedSpell(username: String, spellKey: String): Boolean {

    val completableFuture1 = CompletableFuture<Boolean>()
    val completableFuture2 = CompletableFuture<Boolean>()
    var spell = Spell()

    database.getReference("usersInfo").child(username).child("spells").setValue(spellKey)
        .addOnCompleteListener { task ->

        if (task.isSuccessful) { completableFuture1.complete(true) }
        else { completableFuture1.complete(false) }
    }

    getSpell(spellKey) { spell = it }

    database.getReference("notifications").child(username).setValue(spell)
        .addOnCompleteListener { task ->

        if (task.isSuccessful) { completableFuture2.complete(true) }
        else { completableFuture2.complete(false) }
    }

    val completableFuture = completableFuture1.get() && completableFuture2.get()

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