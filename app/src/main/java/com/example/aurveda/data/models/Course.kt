package com.example.aurveda.data.models

data class Course(
    val id: String,
    val title: String,
    val subject: String,
    val description: String,
    val thumbnailUrl: String,
    val lessons: List<Lesson>,
    val trending: Boolean = false,
    val enrollments: Int = 0
)

data class Lesson(
    val id: String,
    val title: String,
    val youtubeVideoId: String,
    val durationMin: Int
)