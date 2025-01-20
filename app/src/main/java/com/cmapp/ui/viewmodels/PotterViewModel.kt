package com.cmapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.cmapp.model.data.DataBaseHelper.addPotion
import com.cmapp.model.data.DataBaseHelper.addSpell
import com.cmapp.model.data.getRandomMovements
import com.cmapp.model.data.getRandomPotionColor
import com.cmapp.model.data.getRandomSpellColor
import com.cmapp.model.data.getRandomTime
import com.cmapp.model.domain.database.Potion
import com.cmapp.model.domain.database.Spell
import com.cmapp.model.domain.potterDB.PotterData
import com.cmapp.model.domain.potterDB.PotterPotion
import com.cmapp.model.domain.potterDB.PotterSpell
import com.cmapp.network.PotterDBApi
import com.cmapp.ui.states.PotterUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.future.await
import kotlinx.coroutines.launch
import java.io.IOException

class PotterViewModel: ViewModel() {

    // bookId: "6751e7f7-a8b7-488b-bde7-8606822d2338"
    // chapterId: "60dc2d57-45d9-4fb6-9b20-4987def53e4d"
    // characterId: "8b7e8ccb-f2ef-42b0-a4cd-fb3c2572e619"
    // movieId: "bb71cc0d-32b7-4a05-876b-4774064a5cec"
    // potionId: "0dbbb97f-e1c2-453d-8105-13618e8e8bf9"
    // spellId: "4a7478e4-307a-428b-9af2-c502f996a05c"

    var uiState: PotterUiState by mutableStateOf(PotterUiState.Loading)
        private set

    private val _potions = MutableStateFlow<PotterData<List<PotterPotion>>?>(null)
    val potions: MutableStateFlow<PotterData<List<PotterPotion>>?> = _potions

    private val _spells = MutableStateFlow<PotterData<List<PotterSpell>>?>(null)
    val spells: MutableStateFlow<PotterData<List<PotterSpell>>?> = _spells

    init {
        fetchPotions()
        //fetchSpells()
    }

    private fun fetchPotions() {
        viewModelScope.launch(Dispatchers.IO) {
            val potions = PotterDBApi.retrofitService.getPotions()
            _potions.value = potions
            storePotions(potions)
        }
    }

    private suspend fun storePotions(potions: PotterData<List<PotterPotion>>?) {

        potions!!.data.forEach { potion ->

            val attributes = potion.attributes
            val color = getRandomPotionColor()
            val name = attributes.name
            val description = attributes.effect
            val ingredients = attributes.ingredients

            if(description != null && ingredients != null)
                addPotion(Potion(color = color, name = name, description = description, ingredients = ingredients)).await()
        }
    }

    private fun fetchSpells() {
        viewModelScope.launch(Dispatchers.IO) {
            val spells = PotterDBApi.retrofitService.getSpells()
            _spells.value = spells
            storeSpells(spells)
        }
    }

    private suspend fun storeSpells(spells: PotterData<List<PotterSpell>>?) {

        spells!!.data.forEach { spell ->

            val color = getRandomSpellColor()
            val name = spell.attributes.name
            val description = spell.attributes.effect
            val movements = getRandomMovements()
            val time = getRandomTime()

            addSpell(Spell(color = color, name = name, description = description, movements = movements, time = time)).await()
        }
    }

    private fun getBooks(){
        callback {
            val listResult = PotterDBApi.retrofitService.getBooks()
            PotterUiState.Success("Success: $listResult")
        }
    }

    private fun getBook(id: String) {
        callback {
            val book = PotterDBApi.retrofitService.getBook(id)
            PotterUiState.Success("Success $book")
        }
    }

    private fun getChapters(bookId: String) {
        callback {
            val chaptersResult = PotterDBApi.retrofitService.getBookChapters(bookId)
            PotterUiState.Success("Success: $chaptersResult")
        }
    }

    private fun getChapter(bookId: String, chapterId: String) {
        callback {
            val chapter = PotterDBApi.retrofitService.getBookChapter(bookId, chapterId)
            PotterUiState.Success("Success: $chapter")
        }
    }

    private fun getCharacters() {
        callback {
            val charsList = PotterDBApi.retrofitService.getCharacters()
            PotterUiState.Success("Success: $charsList")
        }
    }

    private fun getCharacter(id: String) {
        callback {
            val char = PotterDBApi.retrofitService.getCharacter(id)
            PotterUiState.Success("Success: $char")
        }
    }

    private fun getMovies() {
        callback {
            val moviesResult = PotterDBApi.retrofitService.getMovies()
            PotterUiState.Success("Success: $moviesResult")
        }
    }

    private fun getMovie(id: String) {
        callback {
            val movie = PotterDBApi.retrofitService.getMovie(id)
            PotterUiState.Success("Success: $movie")
        }
    }

    private fun getPotion(id: String) {
        callback {
            val potion = PotterDBApi.retrofitService.getPotion(id)
            PotterUiState.Success("Success: $potion")
        }
    }

    private fun getSpell(id: String) {
        callback {
            val spell = PotterDBApi.retrofitService.getSpell(id)
            PotterUiState.Success("Success: $spell")
        }
    }

    private fun callback(func: suspend () -> PotterUiState.Success){
        viewModelScope.launch {
            uiState = PotterUiState.Loading
            uiState = try {
                func()
            } catch (e: HttpException) {
                PotterUiState.Error
            } catch (e: IOException) {
                PotterUiState.Error
            }
        }
    }
}