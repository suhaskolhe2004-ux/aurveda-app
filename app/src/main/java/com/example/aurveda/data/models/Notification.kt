package com.example.aurveda.data.models

data class Notification(
    val id: String,
    val title: String,
    val message: String,
    val timestamp: String,
    val authorId: String,
    val comments: List<Comment> = emptyList()
)

data class Comment(
    val id: String,
    val authorName: String,
    val text: String,
    val timestamp: String
)