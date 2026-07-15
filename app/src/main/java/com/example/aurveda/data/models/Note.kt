package com.example.aurveda.data.models

data class Note(
    val id: String,
    val title: String,
    val subject: String,
    val price: Double,
    val previewPageCount: Int,
    val thumbnailUrl: String,
    val fileUrl: String,
    val trending: Boolean = false,
    val downloads: Int = 0
) {
    val isFree: Boolean get() = price <= 0.0
}