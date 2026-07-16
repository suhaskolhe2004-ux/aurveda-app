package com.example.aurveda.data.repositories

import com.example.aurveda.data.models.Notification
import com.example.aurveda.data.models.Comment
import kotlinx.coroutines.delay
import java.util.UUID

interface NotificationRepository {
    suspend fun getNotifications(): List<Notification>
    suspend fun addNotification(title: String, message: String, authorId: String): Notification
    suspend fun deleteNotification(id: String)
    suspend fun addComment(notificationId: String, authorName: String, text: String): Comment
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

    override suspend fun addComment(notificationId: String, authorName: String, text: String): Comment {
        delay(500)
        val index = mockNotifications.indexOfFirst { it.id == notificationId }
        val newComment = Comment(
            id = UUID.randomUUID().toString(),
            authorName = authorName,
            text = text,
            timestamp = "Just now"
        )
        if (index != -1) {
            val notification = mockNotifications[index]
            mockNotifications[index] = notification.copy(comments = notification.comments + newComment)
        }
        return newComment
    }
}