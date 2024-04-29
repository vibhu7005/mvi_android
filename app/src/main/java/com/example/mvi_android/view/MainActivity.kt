package com.example.mvi_android.view


import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    fun IdleScreen(btnClick: () -> Unit) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Button(onClick = btnClick) {
                Text(text = "Click to fetch animals")
            }
        }
    }

    @Composable
    fun LoadingScreen() {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    @Composable
    fun AnimalsScreen(animals: List<Animal>) {
        LazyColumn {
            items(items = animals) {
                Text(text = it.name)
            }
        }
    }

    @Composable
    fun MainScreen(vm: AnimalViewModel, btnClick: () -> Unit) {
        when (val state = vm.state.value) {
            is State.Idle -> IdleScreen(btnClick)
            is State.Error -> {
                IdleScreen(btnClick)
                Toast.makeText(this, state.description, Toast.LENGTH_SHORT).show()
            }
            is State.Animals -> AnimalsScreen(state.animals)
            is State.Loading -> LoadingScreen()
        }
    }
}
