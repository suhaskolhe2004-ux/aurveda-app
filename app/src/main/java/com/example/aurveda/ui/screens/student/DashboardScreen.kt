package com.example.aurveda.ui.screens.student

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aurveda.ui.theme.HeartbeatDivider
import com.example.aurveda.ui.theme.IosCard
import com.example.aurveda.ui.viewmodels.CoursesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    coursesViewModel: CoursesViewModel = viewModel()
) {
    val trendingCourses by coursesViewModel.trendingCourses.collectAsState()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text("Dashboard", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(24.dp))

            IosCard(modifier = Modifier.fillMaxWidth().height(180.dp)) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Banner Carousel Placeholder", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            HeartbeatDivider()
            Spacer(modifier = Modifier.height(24.dp))

            Text("Trending Courses", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold), modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(12.dp))

            if (trendingCourses.isEmpty()) {
                Text("No trending courses right now.", color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.align(Alignment.Start))
            } else {
                LazyRow {
                    items(trendingCourses) { course ->
                        IosCard(modifier = Modifier.width(220.dp).height(120.dp).padding(end = 12.dp)) {
                             Column(modifier = Modifier.padding(16.dp)) {
                                 Text(course.title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))
                                 Spacer(modifier = Modifier.height(4.dp))
                                 Text(course.subject, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                             }
                        }
                    }
                }
            }
        }
    }
}