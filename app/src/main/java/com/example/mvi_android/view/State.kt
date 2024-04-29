package com.example.mvi_android.view

import androidx.compose.runtime.MutableState
import com.example.mvi_android.model.Animal

sealed class State {
    object Loading : State()
    object Idle : State()
    data class Animals(val animals : List<Animal>) : State()
    data class Error(val description : String) : State()
}