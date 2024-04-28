package com.example.mvi_android.view

import com.example.mvi_android.model.Animal

sealed class State {
    object Loading : State()
    object Idle : State()
    data class Animals(val list : List<Animal>) : State()
    data class Error(val description : String) : State()
}