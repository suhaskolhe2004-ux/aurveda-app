package com.example.aurveda.data.repositories

import com.example.aurveda.data.models.Notification
import kotlinx.coroutines.delay
import java.util.UUID

interface NotificationRepository {
    suspend fun getNotifications(): List<Notification>
    suspend fun addNotification(title: String, message: String, authorId: String): Notification
    suspend fun deleteNotification(id: String)
}

class MockNotificationRepository : NotificationRepository {
    private val mockNotifications = mutableListOf(
        Notification("1", "New Course Available", "Anatomy 101 is now live!", "2h ago", "admin_1"),
        Notification("2", "Notes Update", "Physiology flashcards have been updated.", "1d ago", "admin_1")
    )

    override suspend fun getNotifications(): List<Notification> {
        delay(800)
        return mockNotifications.toList()
    }

    override suspend fun addNotification(title: String, message: String, authorId: String): Notification {
        delay(500)
        val newNotification = Notification(
            id = UUID.randomUUID().toString(),
            title = title,
            message = message,
            timestamp = "Just now",
            authorId = authorId
        )
        mockNotifications.add(0, newNotification)
        return newNotification
    }

    override suspend fun deleteNotification(id: String) {
        delay(500)
        mockNotifications.removeAll { it.id == id }
    }
}

class FirebaseNotificationRepository : NotificationRepository {
    override suspend fun getNotifications(): List<Notification> { TODO("Not yet implemented") }
    override suspend fun addNotification(title: String, message: String, authorId: String): Notification { TODO("Not yet implemented") }
    override suspend fun deleteNotification(id: String) { TODO("Not yet implemented") }
}