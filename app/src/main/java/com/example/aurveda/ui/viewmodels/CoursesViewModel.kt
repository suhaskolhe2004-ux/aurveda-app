package com.example.aurveda.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurveda.data.models.Course
import com.example.aurveda.data.repositories.CourseRepository
import com.example.aurveda.data.repositories.MockCourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CoursesViewModel(
    private val courseRepository: CourseRepository = MockCourseRepository()
) : ViewModel() {
    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    private val _trendingCourses = MutableStateFlow<List<Course>>(emptyList())
    val trendingCourses: StateFlow<List<Course>> = _trendingCourses

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        loadCourses()
        loadTrendingCourses()
    }

    fun loadCourses(subject: String? = null) {
        viewModelScope.launch {
            _loading.value = true
            _courses.value = courseRepository.getCourses(subject)
            _loading.value = false
        }
    }

    fun loadTrendingCourses() {
        viewModelScope.launch {
            _trendingCourses.value = courseRepository.getTrendingCourses()
        }
    }
}