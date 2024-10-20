package com.questionsnanswersgame

data class Question(
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String,
    val funFact: String
)
