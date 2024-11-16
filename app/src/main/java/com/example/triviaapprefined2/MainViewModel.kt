package com.example.triviaapprefined2

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var text by mutableStateOf("")


    private val _triviasState = mutableStateOf(TriviaState())
    val apiKey : String = "l5iWQsFP4f3h7FuJuFuOXg==IeiZ7QgKR52NI0pO"

    val triviaState : State<TriviaState> = _triviasState



    fun fetchTrivia(category: String){

        viewModelScope.launch {

            try {
                val response = recipeService.getTrivia(apiKey,category)
                _triviasState.value = _triviasState.value.copy(
                    loading = false,
                    trivia = response.get(0),
                    error = null
                )
            }catch (e: Exception){
                _triviasState.value = _triviasState.value.copy(

                    loading = false,
                    error = "error fetching data"
                )
            }
        }

    }


    data class TriviaState(
        val loading: Boolean = true,
        val trivia: TriviaItem = TriviaItem("", "", ""),
        val error: String? = null
    )
}
