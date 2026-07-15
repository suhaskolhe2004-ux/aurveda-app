package com.example.aurveda.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.aurveda.ui.screens.auth.LoginScreen
import com.example.aurveda.ui.screens.auth.SignupScreen
import com.example.aurveda.ui.screens.student.DashboardScreen
import com.example.aurveda.ui.screens.student.CoursesScreen
import com.example.aurveda.ui.screens.student.CourseDetailScreen
import com.example.aurveda.ui.screens.student.NotesScreen
import com.example.aurveda.ui.screens.student.NoteDetailScreen
import com.example.aurveda.ui.screens.student.NotificationsScreen
import com.example.aurveda.ui.screens.student.ProfileScreen
import com.example.aurveda.ui.screens.admin.AdminDashboardScreen
import com.example.aurveda.ui.viewmodels.AuthViewModel
import com.example.aurveda.ui.viewmodels.CoursesViewModel
import com.example.aurveda.ui.viewmodels.NotesViewModel

enum class Screen(val route: String) {
    Login("login"),
    Signup("signup"),
    Dashboard("dashboard"),
    Courses("courses"),
    CourseDetail("course_detail/{courseId}"),
    Notes("notes"),
    NoteDetail("note_detail/{noteId}"),
    Notifications("notifications"),
    Profile("profile"),
    AdminDashboard("admin_dashboard");

    fun createRoute(id: String) = route.replace(Regex("\\{.*\\}"), id)
}

@Composable
fun AppNavigation(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Shared ViewModels
    val authViewModel: AuthViewModel = viewModel()
    val coursesViewModel: CoursesViewModel = viewModel()
    val notesViewModel: NotesViewModel = viewModel()

    val bottomNavScreens = listOf(
        Screen.Dashboard,
        Screen.Courses,
        Screen.Notes,
        Screen.Notifications,
        Screen.Profile
    )
    val showBottomNav = currentRoute in bottomNavScreens.map { it.route }

    Scaffold(
        bottomBar = {
            if (showBottomNav) {
                NavigationBar {
                    bottomNavScreens.forEach { screen ->
                        NavigationBarItem(
                            icon = { Text(screen.name.first().toString()) }, // Placeholder icon
                            label = { Text(screen.name) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Login.route) {
                LoginScreen(
                    viewModel = authViewModel,
                    onLoginSuccess = { isAdmin ->
                        if (isAdmin) {
                            navController.navigate(Screen.AdminDashboard.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        } else {
                            navController.navigate(Screen.Dashboard.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                    },
                    onNavigateToSignup = {
                        navController.navigate(Screen.Signup.route)
                    }
                )
            }
            composable(Screen.Signup.route) {
                SignupScreen(
                    viewModel = authViewModel,
                    onSignupSuccess = {
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.Signup.route) { inclusive = true }
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onNavigateToLogin = {
                        navController.popBackStack()
                    }
                )
            }
            composable(Screen.Dashboard.route) {
                DashboardScreen(coursesViewModel = coursesViewModel)
            }
            composable(Screen.Courses.route) {
                CoursesScreen(
                    viewModel = coursesViewModel,
                    onNavigateToCourseDetail = { courseId ->
                        navController.navigate(Screen.CourseDetail.createRoute(courseId))
                    }
                )
            }
            composable(
                route = Screen.CourseDetail.route,
                arguments = listOf(navArgument("courseId") { type = NavType.StringType })
            ) { backStackEntry ->
                val courseId = backStackEntry.arguments?.getString("courseId") ?: return@composable
                CourseDetailScreen(
                    courseId = courseId,
                    viewModel = coursesViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable(Screen.Notes.route) {
                NotesScreen(
                    viewModel = notesViewModel,
                    onNavigateToNoteDetail = { noteId ->
                         navController.navigate(Screen.NoteDetail.createRoute(noteId))
                    }
                )
            }
            composable(
                route = Screen.NoteDetail.route,
                arguments = listOf(navArgument("noteId") { type = NavType.StringType })
            ) { backStackEntry ->
                 val noteId = backStackEntry.arguments?.getString("noteId") ?: return@composable
                 NoteDetailScreen(
                     noteId = noteId,
                     viewModel = notesViewModel,
                     onNavigateBack = { navController.popBackStack() }
                 )
            }
            composable(Screen.Notifications.route) {
                NotificationsScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    viewModel = authViewModel,
                    onLogout = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.AdminDashboard.route) {
                AdminDashboardScreen(
                    onLogout = {
                        authViewModel.logout()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}