package com.example.aurveda.ui.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aurveda.ui.theme.IosCard
import com.example.aurveda.ui.theme.ListRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    onNavigateToCourses: () -> Unit,
    onNavigateToNotes: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToStudents: () -> Unit,
    onNavigateToAuditLog: () -> Unit,
    onNavigateToReviews: () -> Unit,
    onLogout: () -> Unit
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Admin Dashboard", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(32.dp))

            ListRow(
                title = "Manage Courses",
                subtitle = "Add, edit, or delete courses",
                onClick = onNavigateToCourses
            )
            ListRow(
                title = "Manage Notes",
                subtitle = "Upload and manage PDF notes",
                onClick = onNavigateToNotes
            )
            ListRow(
                title = "Manage Notifications",
                subtitle = "Broadcast announcements",
                onClick = onNavigateToNotifications
            )
            ListRow(
                title = "Manage Platform Admins",
                subtitle = "View and manage accounts",
                onClick = onNavigateToStudents
            )
            ListRow(
                title = "Audit Log",
                subtitle = "Track admin actions",
                onClick = onNavigateToAuditLog
            )
            ListRow(
                title = "Moderate Reviews",
                subtitle = "Manage course & note reviews",
                onClick = onNavigateToReviews
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Logout")
            }
        }
    }
}