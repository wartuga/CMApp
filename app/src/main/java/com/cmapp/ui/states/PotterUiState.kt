package com.cmapp.ui.states

sealed interface PotterUiState {
    data class Success(val potions: String) : PotterUiState
    object Error : PotterUiState
    object Loading : PotterUiState
}