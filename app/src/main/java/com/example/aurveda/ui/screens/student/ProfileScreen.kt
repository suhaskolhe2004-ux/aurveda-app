package com.example.aurveda.ui.screens.student

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aurveda.ui.viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    val user by viewModel.userState.collectAsState()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Profile", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            user?.let {
                Text("Name: ${it.name}", style = MaterialTheme.typography.bodyLarge)
                Text("Mobile: ${it.mobileNumber}", style = MaterialTheme.typography.bodyLarge)
                Text("Role: ${it.role.name}", style = MaterialTheme.typography.bodyLarge)
            } ?: run {
                Text("User data not found.", style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    viewModel.logout()
                    onLogout()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout")
            }
        }
    }
}