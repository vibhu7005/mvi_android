package com.example.mvi_android.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvi_android.network.AnimalRepo
import com.example.mvi_android.view.Intent
import com.example.mvi_android.view.State
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class AnimalViewModel(val repo : AnimalRepo): ViewModel() {
    var state = mutableStateOf<State>(State.Idle)
        private set

    val userIntent = Channel<Intent>(Channel.UNLIMITED)

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when(it) {
                    is Intent.fetchAnimals -> fetchAnimals()
                }
            }
        }
    }

    private fun fetchAnimals() {
        viewModelScope.launch {
            state.value = State.Loading
            try {
                state.value = State.Animals(repo.getAnimals())
            } catch (e : Exception) {
                e.message?.let {
                    state.value = State.Error(it)
                }
            }
        }
    }
}