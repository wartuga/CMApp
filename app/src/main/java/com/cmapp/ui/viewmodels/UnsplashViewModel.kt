package com.cmapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.cmapp.network.UnsplashApi
import com.cmapp.ui.states.UnsplashUiState
import kotlinx.coroutines.launch
import java.io.IOException

class UnsplashViewModel: ViewModel() {

    var uiState: UnsplashUiState by mutableStateOf(UnsplashUiState.Loading)
        private set

    init {
        getProfileImages()
    }

    private fun getProfileImages(){
        viewModelScope.launch {
            uiState = UnsplashUiState.Loading
            uiState = try {
                val listResult = UnsplashApi.retrofitService.getProfileImages()
                UnsplashUiState.Success("Success: $listResult")
            } catch (e: HttpException) {
                UnsplashUiState.Error
            } catch (e: IOException) {
                UnsplashUiState.Error
            }
        }
    }
}