package com.example.aurveda.data.repositories

import com.example.aurveda.data.models.Note
import kotlinx.coroutines.delay

interface NoteRepository {
    suspend fun getNotes(subject: String? = null, freeOnly: Boolean = false): List<Note>
    suspend fun getTrendingNotes(): List<Note>
    suspend fun getNote(id: String): Note?
    suspend fun addNote(note: Note): Note
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(id: String)
}

class MockNoteRepository : NoteRepository {
    private val mockNotes = mutableListOf(
        Note(
            id = "n1",
            title = "Anatomy Flashcards",
            subject = "Anatomy",
            price = 0.0,
            previewPageCount = 5,
            thumbnailUrl = "",
            fileUrl = "",
            trending = true
        ),
        Note(
            id = "n2",
            title = "Physiology Summary",
            subject = "Physiology",
            price = 5.0, // Paid
            previewPageCount = 2,
            thumbnailUrl = "",
            fileUrl = "",
            trending = false
        )
    )

    override suspend fun getNotes(subject: String?, freeOnly: Boolean): List<Note> {
        delay(800)
        var notes: List<Note> = mockNotes
        if (subject != null) notes = notes.filter { it.subject == subject }
        if (freeOnly) notes = notes.filter { it.isFree }
        return notes
    }

    override suspend fun getTrendingNotes(): List<Note> {
        delay(500)
        return mockNotes.filter { it.trending }
    }

    override suspend fun getNote(id: String): Note? {
        delay(300)
        return mockNotes.find { it.id == id }
    }

    override suspend fun addNote(note: Note): Note {
        delay(500)
        val newNote = note.copy(id = java.util.UUID.randomUUID().toString())
        mockNotes.add(newNote)
        return newNote
    }

    override suspend fun updateNote(note: Note) {
        delay(500)
        val index = mockNotes.indexOfFirst { it.id == note.id }
        if (index != -1) {
            mockNotes[index] = note
        }
    }

    override suspend fun deleteNote(id: String) {
        delay(500)
        mockNotes.removeAll { it.id == id }
    }
}

class FirebaseNoteRepository : NoteRepository {
    override suspend fun getNotes(subject: String?, freeOnly: Boolean): List<Note> { TODO("Not yet implemented") }
    override suspend fun getTrendingNotes(): List<Note> { TODO("Not yet implemented") }
    override suspend fun getNote(id: String): Note? { TODO("Not yet implemented") }
    override suspend fun addNote(note: Note): Note { TODO("Not yet implemented") }
    override suspend fun updateNote(note: Note) { TODO("Not yet implemented") }
    override suspend fun deleteNote(id: String) { TODO("Not yet implemented") }
}