package com.example.mvi_android.view


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mvi_android.model.Animal
import com.example.mvi_android.network.RetrofitService
import com.example.mvi_android.ui.theme.Mvi_androidTheme
import com.example.mvi_android.viewmodel.AnimalViewModel
import com.example.mvi_android.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch


class MainActivity : androidx.fragment.app.FragmentActivity() {
    private lateinit var animalViewModel: AnimalViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        animalViewModel = ViewModelProvider(
            this,
            ViewModelFactory(RetrofitService.api)
        )[AnimalViewModel::class.java]
        val onButtonClick: () -> Unit = {
            lifecycleScope.launch {
                animalViewModel.userIntent.send(Intent.fetchAnimals)
            }
        }

        setContent {
            Mvi_androidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(animalViewModel, onButtonClick)
                }
            }
        }
    }

    @Composable
    fun IdleScreen() {
    }

    @Composable
    fun LoadingScreen() {
    }

    @Composable
    fun AnimalsScreen(animals: List<Animal>) {

    }

    @Composable
    fun ErrorScreen() {
    }

    @Composable
    fun MainScreen(vm: AnimalViewModel, btnClick: () -> Unit) {
        val state = vm.state.value
        when (state) {
            is State.Idle -> IdleScreen()
            is State.Error -> ErrorScreen()
            is State.Animals -> AnimalsScreen(state.animals)
            is State.Loading -> LoadingScreen()
        }
    }
}
