package com.example.aurveda.data.models

data class Notification(
    val id: String,
    val title: String,
    val message: String,
    val timestamp: String,
    val authorId: String
)