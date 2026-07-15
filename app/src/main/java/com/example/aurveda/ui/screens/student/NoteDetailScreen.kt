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
                 }
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (!note.isFree) {
                Button(onClick = { /* Launch Razorpay */ }, modifier = Modifier.fillMaxWidth()) {
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