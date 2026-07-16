package com.example.aurveda.data.repositories

import com.example.aurveda.data.models.User
import kotlinx.coroutines.delay

interface AuthRepository {
    suspend fun sendOtp(mobileNumber: String): Boolean
    suspend fun verifyOtpAndSignup(mobileNumber: String, otp: String, password: String, user: User): Result<User>
    suspend fun login(mobileNumber: String, password: String): Result<User>
    suspend fun logout()
    fun getCurrentUser(): User?
}

class MockAuthRepository : AuthRepository {
    private var currentUser: User? = null

    // Simulate database
    private val users = mutableListOf<User>(
        User("admin_1", "Platform Admin", "1234567890", "admin@app.internal", "N/A", "N/A", com.example.aurveda.data.models.Role.PLATFORM_ADMIN)
    )

    override suspend fun sendOtp(mobileNumber: String): Boolean {
        delay(1000)
        return true
    }

    override suspend fun verifyOtpAndSignup(
        mobileNumber: String,
        otp: String,
        password: String,
        user: User
    ): Result<User> {
        delay(1500)
        if (otp == "123456") { // Dummy OTP
            val newUser = user.copy(id = "user_${users.size + 1}", mobileNumber = mobileNumber)
            users.add(newUser)
            currentUser = newUser
            return Result.success(newUser)
        }
        return Result.failure(Exception("Invalid OTP"))
    }

    override suspend fun login(mobileNumber: String, password: String): Result<User> {
        delay(1500)
        // Dummy login - accepting password "password" for mock
        if (password == "password") {
            val user = users.find { it.mobileNumber == mobileNumber }
            if (user != null) {
                currentUser = user
                return Result.success(user)
            } else if (mobileNumber == "1234567890") { // Admin bypass
                 currentUser = users.first()
                 return Result.success(users.first())
            }
        }
        return Result.failure(Exception("Invalid credentials or user not found"))
    }

    override suspend fun logout() {
        delay(500)
        currentUser = null
    }

    override fun getCurrentUser(): User? = currentUser
}