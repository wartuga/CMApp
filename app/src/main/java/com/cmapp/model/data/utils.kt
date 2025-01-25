package com.cmapp.model.data

import com.cmapp.model.domain.database.Profile
import java.util.Locale

const val MIN_USERNAME_LEN = 3
const val MAX_USERNAME_LEN = 20
const val MIN_PASSWORD_LEN = 8
const val MAX_PASSWORD_LEN = 30

val spellsUrls = listOf(
    "https://firebasestorage.googleapis.com/v0/b/hogwarts-apprentice.firebasestorage.app/o/spells%2Fblue_spell.png?alt=media&token=277d395b-bc70-46f2-97e6-7731e4cb9cf3",
    "https://firebasestorage.googleapis.com/v0/b/hogwarts-apprentice.firebasestorage.app/o/spells%2Fcyan_spell.png?alt=media&token=1b780ff2-6b34-41c4-8e48-db2759b7e58a",
    "https://firebasestorage.googleapis.com/v0/b/hogwarts-apprentice.firebasestorage.app/o/spells%2Fgreen_spell.png?alt=media&token=4c996092-a920-4120-bcb5-6612cdab8e8b",
    "https://firebasestorage.googleapis.com/v0/b/hogwarts-apprentice.firebasestorage.app/o/spells%2Fpink_spell.png?alt=media&token=314899c9-ef07-4845-a39d-27017ea861f0",
    "https://firebasestorage.googleapis.com/v0/b/hogwarts-apprentice.firebasestorage.app/o/spells%2Fred_spell.png?alt=media&token=08e59d9f-667b-4200-bb3a-d14892ed5082",
    "https://firebasestorage.googleapis.com/v0/b/hogwarts-apprentice.firebasestorage.app/o/spells%2Fyellow_spell.png?alt=media&token=eac130b4-91d4-46b3-b6d7-2139c1e86252"
)

val potionsMapColorUrls = mapOf(
    "Blue" to "https://firebasestorage.googleapis.com/v0/b/hogwarts-apprentice.firebasestorage.app/o/potions%2Fblue_potion.png?alt=media&token=af369256-edd6-4d5c-a4c5-a7e458bf4330",
    "Cyan" to "https://firebasestorage.googleapis.com/v0/b/hogwarts-apprentice.firebasestorage.app/o/potions%2Fcyan_potion.png?alt=media&token=d823e180-d828-4150-9ff6-671e1168958a",
    "Green" to "https://firebasestorage.googleapis.com/v0/b/hogwarts-apprentice.firebasestorage.app/o/potions%2Fgreen_potion.png?alt=media&token=e7d78e6e-0ab2-4709-a5d3-5f20c0641a71",
    "Magenta" to "https://firebasestorage.googleapis.com/v0/b/hogwarts-apprentice.firebasestorage.app/o/potions%2Fpink_potion.png?alt=media&token=9243707d-fa3a-42e4-bc05-201de0cf391d",
    "Red" to "https://firebasestorage.googleapis.com/v0/b/hogwarts-apprentice.firebasestorage.app/o/potions%2Fred_potion.png?alt=media&token=d6bd2488-cdae-4457-9046-52a2c8c84d03",
    "Yellow" to "https://firebasestorage.googleapis.com/v0/b/hogwarts-apprentice.firebasestorage.app/o/potions%2Fyellow_potion.png?alt=media&token=132226e9-9aed-4e36-a7c8-9e4cf1b9a33e"
)

val wandsFrontUrls = listOf(
    "https://firebasestorage.googleapis.com/v0/b/hogwarts-apprentice.firebasestorage.app/o/wands%2Fharry_wand_front.png?alt=media&token=f60ca14e-b282-4599-a174-1def5e1447a9",
    "https://firebasestorage.googleapis.com/v0/b/hogwarts-apprentice.firebasestorage.app/o/wands%2Fdumbledore_wand_front.png?alt=media&token=926f28e0-e7a8-4031-a11f-fcd78e9025da",
    "https://firebasestorage.googleapis.com/v0/b/hogwarts-apprentice.firebasestorage.app/o/wands%2Fhermione_wand_front.png?alt=media&token=57a1c407-a9d2-4678-9c56-e684fc02da2a",
    "https://firebasestorage.googleapis.com/v0/b/hogwarts-apprentice.firebasestorage.app/o/wands%2Fsnape_wand_front.png?alt=media&token=19a79a68-edb4-413f-b062-eb2fff6ebd74"
)

val wandsSideUrls = listOf(
    "https://firebasestorage.googleapis.com/v0/b/hogwarts-apprentice.firebasestorage.app/o/wands%2Fharry_wand_side.png?alt=media&token=7dd4ac45-633d-43e6-8e22-286ab64e26cf",
    "https://firebasestorage.googleapis.com/v0/b/hogwarts-apprentice.firebasestorage.app/o/wands%2Fdumbledore_wand_side.png?alt=media&token=f47329e3-87b4-4c45-a276-90dc6d7c62a6",
    "https://firebasestorage.googleapis.com/v0/b/hogwarts-apprentice.firebasestorage.app/o/wands%2Fhermione_wand_side.png?alt=media&token=465c11ba-9d37-47be-b85e-cfe8f485fbb9",
    "https://firebasestorage.googleapis.com/v0/b/hogwarts-apprentice.firebasestorage.app/o/wands%2Fsnape_wand_side.png?alt=media&token=a284ed32-b438-4ed1-b8f8-d744e60cbd4c"
)

val defaultPhoto = "https://firebasestorage.googleapis.com/v0/b/hogwarts-apprentice.firebasestorage.app/o/hogwarts.jpg?alt=media&token=e0ab9627-7434-4180-a1df-f0bfdcafee4e"

fun isValidUsername(username: String) =
    username.matches(Regex("^(?=.*[a-zA-Z])[a-zA-Z0-9]*\$"))

fun isValidUsernameLength(username: String) =
    username.length in MIN_USERNAME_LEN..MAX_USERNAME_LEN

fun isValidPassword(password: String) =
    password.matches(Regex("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%^&*()\\-__+.]).*$"))

fun isValidPasswordLength(password: String) =
    password.length in MIN_PASSWORD_LEN..MAX_PASSWORD_LEN

fun toUpperCase(text: String): String{
    return text.uppercase(Locale.ROOT)
}

fun getRandomPotionColorImage(): Pair<String, String> {
    val randomPotionPair = potionsMapColorUrls.entries.random()
    return randomPotionPair.key to randomPotionPair.value
}

fun getRandomSpellImage(): String {
    return spellsUrls.random()
}

fun getWandsFront(): List<String> {
    return wandsFrontUrls
}

fun getWandsSide(): List<String> {
    return wandsSideUrls
}

fun getRandomMovements(): List<String> {
    val movList = listOf("right","up-right","up","up-left","left")
    return List((10..20).random()) { _ -> movList.random() }
}

fun getSuggestions(username: String, profiles: List<Profile>, searchText: String): List<Profile> {

    return profiles.filter {
    it.username?.contains(searchText, ignoreCase = true) == true &&
            searchText.isNotBlank() &&
            it.username != username
    }
}