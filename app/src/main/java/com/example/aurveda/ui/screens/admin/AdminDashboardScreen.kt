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
fun AdminDashboardScreen(onLogout: () -> Unit) {
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
                onClick = { /* Navigate */ }
            )
            ListRow(
                title = "Manage Notes",
                subtitle = "Upload and manage PDF notes",
                onClick = { /* Navigate */ }
            )
            ListRow(
                title = "Manage Notifications",
                subtitle = "Broadcast announcements",
                onClick = { /* Navigate */ }
            )
            ListRow(
                title = "Manage Students",
                subtitle = "View and manage accounts",
                onClick = { /* Navigate */ }
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