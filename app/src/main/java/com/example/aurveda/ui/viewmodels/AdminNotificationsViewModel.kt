package com.example.aurveda.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurveda.data.models.Notification
import com.example.aurveda.data.repositories.MockNotificationRepository
import com.example.aurveda.data.repositories.NotificationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdminNotificationsViewModel(
    private val notificationRepository: NotificationRepository = MockNotificationRepository()
) : ViewModel() {
    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> = _notifications

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        loadNotifications()
    }

    fun loadNotifications() {
        viewModelScope.launch {
            _loading.value = true
            _notifications.value = notificationRepository.getNotifications()
            _loading.value = false
        }
    }

    fun deleteNotification(id: String) {
        viewModelScope.launch {
            notificationRepository.deleteNotification(id)
            loadNotifications()
        }
    }

    fun addNotification(title: String, message: String) {
        viewModelScope.launch {
            notificationRepository.addNotification(title, message, "admin_user_id")
            loadNotifications()
        }
    }
}