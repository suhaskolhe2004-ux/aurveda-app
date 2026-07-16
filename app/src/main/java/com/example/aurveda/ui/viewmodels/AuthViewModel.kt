package com.example.aurveda.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurveda.data.models.User
import com.example.aurveda.data.models.Role
import com.example.aurveda.data.repositories.AuthRepository
import com.example.aurveda.data.repositories.MockAuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository = MockAuthRepository() // Defaulting to mock for now
) : ViewModel() {
    private val _userState = MutableStateFlow<User?>(null)
    val userState: StateFlow<User?> = _userState

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun login(mobileNumber: String, password: String, onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            val result = authRepository.login(mobileNumber, password)
            if (result.isSuccess) {
                val user = result.getOrNull()
                _userState.value = user
                onSuccess(user?.role == Role.PLATFORM_ADMIN || user?.role == Role.CONTENT_ADMIN)
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Login failed"
            }
            _loading.value = false
        }
    }

    fun signup(mobileNumber: String, otp: String, name: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            // Dummy user object
            val user = User(id = "", name = name, mobileNumber = mobileNumber, email = "", passoutYear = "", course = "", role = Role.STUDENT)
            val result = authRepository.verifyOtpAndSignup(mobileNumber, otp, password, user)

            if (result.isSuccess) {
                _userState.value = result.getOrNull()
                onSuccess()
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Signup failed"
            }
            _loading.value = false
        }
    }

    fun sendOtp(mobileNumber: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
             _loading.value = true
             _error.value = null
             val success = authRepository.sendOtp(mobileNumber)
             if (success) {
                 onSuccess()
             } else {
                 _error.value = "Failed to send OTP"
             }
             _loading.value = false
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _userState.value = null
        }
    }
}