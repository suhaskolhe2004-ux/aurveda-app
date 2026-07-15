package com.example.aurveda.ui.screens.student

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
            Text("Dashboard", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(32.dp))
            Card(modifier = Modifier.fillMaxWidth().height(150.dp)) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Banner Carousel Placeholder")
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text("Trending Courses", style = MaterialTheme.typography.titleMedium, modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(8.dp))

            LazyRow {
                items(trendingCourses) { course ->
                    Card(modifier = Modifier.width(200.dp).height(100.dp).padding(end = 8.dp)) {
                         Column(modifier = Modifier.padding(8.dp)) {
                             Text(course.title, style = MaterialTheme.typography.titleMedium)
                             Text(course.subject, style = MaterialTheme.typography.bodySmall)
                         }
                    }
                }
            }
        }
    }
}