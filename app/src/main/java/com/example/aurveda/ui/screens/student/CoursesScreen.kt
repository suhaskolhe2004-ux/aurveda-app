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
import com.example.aurveda.ui.viewmodels.CoursesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoursesScreen(
    onNavigateToCourseDetail: (String) -> Unit,
    viewModel: CoursesViewModel = viewModel()
) {
    val courses by viewModel.courses.collectAsState()
    val loading by viewModel.loading.collectAsState()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Courses", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(16.dp))

            if (loading) {
                 Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                     HeartbeatLoader()
                 }
            } else if (courses.isEmpty()) {
                EmptyState("No courses found in this subject yet — check back soon.")
            } else {
                LazyColumn {
                    items(courses) { course ->
                        ListRow(
                            title = course.title,
                            subtitle = "${course.lessons.size} lessons · ${course.subject}",
                            onClick = { onNavigateToCourseDetail(course.id) }
                        )
                    }
                }
            }
        }
    }
}