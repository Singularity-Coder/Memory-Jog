package com.singularitycoder.memoryjog

data class Question(
    val id: Long = 0,
    val question: String,
    val hint: String,
    val answer: String,
    var isHintVisible: Boolean = true,
    var isAnswerVisible: Boolean = true,
)
