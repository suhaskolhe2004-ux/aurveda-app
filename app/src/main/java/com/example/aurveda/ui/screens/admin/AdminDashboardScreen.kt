package com.example.aurveda.ui.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(onLogout: () -> Unit) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text("Admin Dashboard", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { /* Navigate to Manage Courses */ },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Manage Courses")
            }
            Button(
                onClick = { /* Navigate to Manage Notes */ },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Manage Notes")
            }
            Button(
                onClick = { /* Navigate to Manage Notifications */ },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Manage Notifications")
            }
            Button(
                onClick = { /* Navigate to Manage Students */ },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Manage Students")
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Logout")
            }
        }
    }
}