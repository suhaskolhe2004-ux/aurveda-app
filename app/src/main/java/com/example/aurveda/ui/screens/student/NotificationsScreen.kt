package com.example.aurveda.ui.screens.student

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.clickable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aurveda.ui.theme.EmptyState
import com.example.aurveda.ui.theme.IosCard
import com.example.aurveda.ui.theme.HeartbeatLoader
import com.example.aurveda.ui.viewmodels.AdminNotificationsViewModel

data class NotificationMock(val id: String, val title: String, val message: String, val timestamp: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    onNavigateToNotificationDetail: (String) -> Unit,
    viewModel: AdminNotificationsViewModel = viewModel() // Reusing the same VM since we just need the read flow
) {
    val notifications by viewModel.notifications.collectAsState()
    val loading by viewModel.loading.collectAsState()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Notifications", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(16.dp))

            if (loading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    HeartbeatLoader()
                }
            } else if (notifications.isEmpty()) {
                EmptyState("No recent announcements.")
            } else {
                LazyColumn {
                    items(notifications) { notification ->
                        IosCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clickable { onNavigateToNotificationDetail(notification.id) }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(notification.title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(notification.message, style = MaterialTheme.typography.bodyMedium)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(notification.timestamp, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }
    }
}