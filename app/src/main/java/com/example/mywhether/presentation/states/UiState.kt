package com.example.mywhether.presentation.states

sealed class UiState<T> {

    class Loading<T> : UiState<T>()

    class Success<T>(val data: T) : UiState<T>()

    class Error<T>(val data: Int) : UiState<T>()
}
