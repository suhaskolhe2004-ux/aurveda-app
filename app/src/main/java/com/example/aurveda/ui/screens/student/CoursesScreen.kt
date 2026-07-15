package com.example.aurveda.ui.screens.student

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aurveda.ui.viewmodels.CoursesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoursesScreen(
    viewModel: CoursesViewModel = viewModel()
) {
    val courses by viewModel.courses.collectAsState()
    val loading by viewModel.loading.collectAsState()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Courses", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            if (loading) {
                 CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                LazyColumn {
                    items(courses) { course ->
                        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(course.title, style = MaterialTheme.typography.titleMedium)
                                Text(course.description, style = MaterialTheme.typography.bodyMedium)
                                Text("Lessons: ${course.lessons.size}", style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }
                }
            }
        }
    }
}