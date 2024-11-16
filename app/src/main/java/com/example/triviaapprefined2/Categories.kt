package com.example.triviaapprefined2

import com.google.gson.annotations.SerializedName

data class TriviaItem(
    @SerializedName("category") val category: String,
    @SerializedName("question") val question: String,
    @SerializedName("answer") val answer: String

)


//data class TriviaResponse(val Trivias :List<TriviaItem>)