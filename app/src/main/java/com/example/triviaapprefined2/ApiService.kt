package com.example.triviaapprefined2

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


private val retrofit = Retrofit.Builder().baseUrl("https://api.api-ninjas.com/v1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val recipeService = retrofit.create(TriviaApiService::class.java)

interface TriviaApiService {
    @GET("trivia")
    suspend fun getTrivia(
        @Header("X-Api-Key") apiKey: String,
        @Query("category") category: String
    ): List<TriviaItem>
}