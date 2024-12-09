package com.cmapp.ui.states

interface UnsplashUiState {
    data class Success(val photos: String) : UnsplashUiState
    object Error : UnsplashUiState
    object Loading : UnsplashUiState
}