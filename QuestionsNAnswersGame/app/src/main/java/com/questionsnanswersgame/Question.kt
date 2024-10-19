package com.questionsnanswersgame

data class Question(
    val text: String,
    val options: List<String>,
    val correctAnswerIndex: Int
) {
    fun explanation(): String {
        return "La respuesta correcta es: ${options[correctAnswerIndex]}"
    }
}
