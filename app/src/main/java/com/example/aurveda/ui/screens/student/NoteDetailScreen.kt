package com.example.aurveda.ui.screens.student

import android.app.Activity
import android.view.WindowManager
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aurveda.ui.viewmodels.NotesViewModel
import com.razorpay.Checkout
import org.json.JSONObject
import android.widget.Toast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    noteId: String,
    onNavigateBack: () -> Unit,
    viewModel: NotesViewModel = viewModel()
) {
    val context = LocalContext.current

    DisposableEffect(Unit) {
        val window = (context as? Activity)?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        onDispose {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }

    val notes by viewModel.notes.collectAsState()
    val note = notes.find { it.id == noteId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(note?.title ?: "Note Detail") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (note == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Note not found")
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(modifier = Modifier.fillMaxWidth().weight(1f)) {
                 Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                     Text("PDF Viewer Mock (FLAG_SECURE Active)")

                     // Watermark Overlay
                     Text(
                         text = "User: Current Student\nMobile: 1234567890",
                         color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                         style = MaterialTheme.typography.headlineMedium,
                         modifier = Modifier
                             .align(Alignment.Center)
                     )
                 }
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (!note.isFree) {
                Button(
                    onClick = {
                        // Simulate Razorpay Checkout launch
                        val activity = context as? Activity
                        if (activity != null) {
                            try {
                                val checkout = Checkout()
                                checkout.setKeyID("rzp_test_mockkey") // Mock Key
                                val options = JSONObject()
                                options.put("name", "Aurveda App")
                                options.put("description", note.title)
                                options.put("currency", "INR")
                                options.put("amount", (note.price * 100).toInt()) // Amount in paise
                                checkout.open(activity, options)
                            } catch (e: Exception) {
                                Toast.makeText(context, "Error launching Razorpay: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Purchase for ₹${note.price}")
                }
            } else {
                Button(onClick = { /* Download */ }, modifier = Modifier.fillMaxWidth()) {
                    Text("Download Free Note")
                }
            }
        }
    }
}