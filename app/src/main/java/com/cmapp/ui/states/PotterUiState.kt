package com.cmapp.ui.states

sealed interface PotterUiState {
    data class Success(val photos: String) : PotterUiState
    object Error : PotterUiState
    object Loading : PotterUiState
}