package com.example.aurveda.data.repositories

import com.example.aurveda.data.models.Course
import com.example.aurveda.data.models.Lesson
import kotlinx.coroutines.delay

interface CourseRepository {
    suspend fun getCourses(subject: String? = null): List<Course>
    suspend fun getTrendingCourses(): List<Course>
    suspend fun getCourse(id: String): Course?
    suspend fun addCourse(course: Course): Course
    suspend fun updateCourse(course: Course)
    suspend fun deleteCourse(id: String)
}

class MockCourseRepository : CourseRepository {
    private val mockCourses = mutableListOf(
        Course(
            id = "c1",
            title = "Anatomy 101",
            subject = "Anatomy",
            description = "Basic human anatomy.",
            thumbnailUrl = "",
            lessons = listOf(Lesson("l1", "Introduction to Anatomy", "dQw4w9WgXcQ", 15)),
            trending = true
        ),
        Course(
            id = "c2",
            title = "Physiology Basics",
            subject = "Physiology",
            description = "Introduction to human physiology.",
            thumbnailUrl = "",
            lessons = listOf(Lesson("l2", "Cell Structure", "dQw4w9WgXcQ", 20)),
            trending = false
        )
    )

    override suspend fun getCourses(subject: String?): List<Course> {
        delay(800)
        return if (subject == null) mockCourses else mockCourses.filter { it.subject == subject }
    }

    override suspend fun getTrendingCourses(): List<Course> {
        delay(500)
        return mockCourses.filter { it.trending }
    }

    override suspend fun getCourse(id: String): Course? {
        delay(300)
        return mockCourses.find { it.id == id }
    }

    override suspend fun addCourse(course: Course): Course {
        delay(500)
        val newCourse = course.copy(id = java.util.UUID.randomUUID().toString())
        mockCourses.add(newCourse)
        return newCourse
    }

    override suspend fun updateCourse(course: Course) {
        delay(500)
        val index = mockCourses.indexOfFirst { it.id == course.id }
        if (index != -1) {
            mockCourses[index] = course
        }
    }

    override suspend fun deleteCourse(id: String) {
        delay(500)
        mockCourses.removeAll { it.id == id }
    }
}

class FirebaseCourseRepository : CourseRepository {
    override suspend fun getCourses(subject: String?): List<Course> { TODO("Not yet implemented") }
    override suspend fun getTrendingCourses(): List<Course> { TODO("Not yet implemented") }
    override suspend fun getCourse(id: String): Course? { TODO("Not yet implemented") }
    override suspend fun addCourse(course: Course): Course { TODO("Not yet implemented") }
    override suspend fun updateCourse(course: Course) { TODO("Not yet implemented") }
    override suspend fun deleteCourse(id: String) { TODO("Not yet implemented") }
}