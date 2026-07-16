package com.example.aurveda.ui.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aurveda.ui.theme.EmptyState
import com.example.aurveda.ui.theme.IosCard

data class AuditLogEntry(val id: String, val action: String, val adminName: String, val timestamp: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAuditLogScreen(onNavigateBack: () -> Unit) {
    val logs = remember {
        listOf(
            AuditLogEntry("1", "Published 'Anatomy 101' Course", "Platform Admin", "2 mins ago"),
            AuditLogEntry("2", "Deleted Note 'Old Physiology'", "Content Admin", "1 hour ago"),
            AuditLogEntry("3", "Broadcasted Notification", "Community Admin", "1 day ago")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Audit Log", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            if (logs.isEmpty()) {
                EmptyState("No actions logged yet.")
            } else {
                LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                    items(logs) { log ->
                        IosCard(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(log.action, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("By: ${log.adminName}", style = MaterialTheme.typography.bodyMedium)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(log.timestamp, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }
    }
}