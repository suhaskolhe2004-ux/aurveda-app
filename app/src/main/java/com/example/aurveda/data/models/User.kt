package com.example.aurveda.data.models

data class User(
    val id: String,
    val name: String,
    val mobileNumber: String,
    val email: String,
    val passoutYear: String,
    val course: String,
    val role: Role = Role.STUDENT
)

enum class Role {
    STUDENT, PLATFORM_ADMIN, CONTENT_ADMIN, COMMUNITY_ADMIN, FINANCE_ADMIN
}