package com.example.aurveda.ui.screens.student

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aurveda.ui.theme.EmptyState
import com.example.aurveda.ui.theme.HeartbeatLoader
import com.example.aurveda.ui.theme.ListRow
import com.example.aurveda.ui.viewmodels.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    onNavigateToNoteDetail: (String) -> Unit,
    viewModel: NotesViewModel = viewModel()
) {
    val notes by viewModel.notes.collectAsState()
    val loading by viewModel.loading.collectAsState()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Notes", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(16.dp))

            if (loading) {
                 Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                     HeartbeatLoader()
                 }
            } else if (notes.isEmpty()) {
                EmptyState("No notes in this subject yet — check back soon.")
            } else {
                LazyColumn {
                    items(notes) { note ->
                        val priceText = if (note.isFree) "Free" else "Paid - ₹${note.price}"
                        ListRow(
                            title = note.title,
                            subtitle = priceText,
                            onClick = { onNavigateToNoteDetail(note.id) }
                        )
                    }
                }
            }
        }
    }
}