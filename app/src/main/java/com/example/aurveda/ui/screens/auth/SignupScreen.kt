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
fun SignupScreen(
    onSignupSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    var step by remember { mutableStateOf(1) }

    var mobileNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

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
            Text("Sign Up", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(32.dp))

            if (error != null) {
                Text(error!!, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(16.dp))
            }

            when (step) {
                1 -> {
                    OutlinedTextField(
                        value = mobileNumber,
                        onValueChange = { mobileNumber = it },
                        label = { Text("Mobile Number") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = {
                            viewModel.sendOtp(mobileNumber) {
                                step = 2
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !loading
                    ) {
                        Text("Send OTP")
                    }
                }
                2 -> {
                    OutlinedTextField(
                        value = otp,
                        onValueChange = { otp = it },
                        label = { Text("OTP (Enter '123456')") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = { if (otp == "123456") step = 3 },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !loading
                    ) {
                        Text("Verify OTP")
                    }
                }
                3 -> {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Full Name") },
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
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = {
                            viewModel.signup(mobileNumber, otp, name, password, onSignupSuccess)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !loading
                    ) {
                        Text("Complete Sign Up")
                    }
                }
            }

            if (step == 1) {
                Spacer(modifier = Modifier.height(16.dp))
                TextButton(onClick = onNavigateToLogin) {
                    Text("Already have an account? Login")
                }
            }
        }
    }
}