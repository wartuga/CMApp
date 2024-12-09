package com.cmapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.cmapp.network.PotterDBApi
import com.cmapp.ui.states.PotterUiState
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

    init {
        getPotions()
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

    private fun getPotions() {
        callback {
            val potions = PotterDBApi.retrofitService.getPotions()
            PotterUiState.Success("Success: $potions")
        }
    }

    private fun getPotion(id: String) {
        callback {
            val potion = PotterDBApi.retrofitService.getPotion(id)
            PotterUiState.Success("Success: $potion")
        }
    }

    private fun getSpells() {
        callback {
            val spells = PotterDBApi.retrofitService.getSpells()
            PotterUiState.Success("Success: $spells")
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