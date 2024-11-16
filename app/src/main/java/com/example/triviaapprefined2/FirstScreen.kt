package com.example.triviaapprefined2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun firstScreen(navigateToSecondScreen: (String) -> Unit){

val viewState : MainViewModel = viewModel()
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){


        Text("Give us a Category:")

        Spacer(modifier = Modifier.padding(8.dp))

        OutlinedTextField(value = viewState.text, onValueChange = { viewState.text = it}, singleLine = true,
            modifier = Modifier.padding(8.dp))


        Spacer(modifier = Modifier.padding(8.dp))

        Button(onClick = {
            navigateToSecondScreen(viewState.text)
        }) {
            Text("Get a Trivia")
        }
    }

}

@Composable
fun actaualTriviaScreen(category: String){
    val viewState : MainViewModel = viewModel()


    // Fetch trivia only once per category
    LaunchedEffect(category) {
        viewState.fetchTrivia(category)
    }

    val trivia by viewState.triviaState


    Box(modifier = Modifier.fillMaxSize()){

        when{
            trivia.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            trivia.error != null -> {
                Text("Error occurred, Sorry Boss")
            }

            else -> {
                Column(modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(text = trivia.trivia.question)
                }
            }
        }

    }

}


@Composable
fun MainScreen(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "firstScreen"){
        composable("firstScreen") {
            firstScreen {category ->
                navController.navigate("secondScreen/$category")
            }
        }
            composable("secondScreen/{category}"){
                val category = it.arguments?.getString("category")  ?: "music"
                actaualTriviaScreen(category)
            }
        }
    }
