package com.example.aurveda.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aurveda.ui.viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: (Boolean) -> Unit,
    onNavigateToSignup: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    var mobileNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Login", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                value = mobileNumber,
                onValueChange = { mobileNumber = it },
                label = { Text("Mobile Number") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            if (error != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(error!!, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    viewModel.login(mobileNumber, password, onLoginSuccess)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading
            ) {
                if (loading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Text("Login")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = onNavigateToSignup) {
                Text("Don't have an account? Sign up")
            }
        }
    }
}