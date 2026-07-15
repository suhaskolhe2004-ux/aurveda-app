package com.example.aurveda.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurveda.data.models.Course
import com.example.aurveda.data.repositories.CourseRepository
import com.example.aurveda.data.repositories.MockCourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdminCoursesViewModel(
    private val courseRepository: CourseRepository = MockCourseRepository()
) : ViewModel() {
    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        loadCourses()
    }

    fun loadCourses() {
        viewModelScope.launch {
            _loading.value = true
            _courses.value = courseRepository.getCourses()
            _loading.value = false
        }
    }

    fun deleteCourse(id: String) {
        viewModelScope.launch {
            courseRepository.deleteCourse(id)
            loadCourses()
        }
    }

    fun addMockCourse() {
        viewModelScope.launch {
            val dummyCourse = Course(
                id = "",
                title = "New Mock Course",
                subject = "General",
                description = "Added by Admin",
                thumbnailUrl = "",
                lessons = emptyList()
            )
            courseRepository.addCourse(dummyCourse)
            loadCourses()
        }
    }
}