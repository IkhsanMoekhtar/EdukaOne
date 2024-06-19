package com.example.edukaone.data


data class ChatMessage(
    val message: String,
    val type: Int
) {
    companion object {
        const val TYPE_SENT = 0
        const val TYPE_RECEIVED = 1
    }
}


