package com.example.aurveda.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurveda.data.models.Note
import com.example.aurveda.data.repositories.MockNoteRepository
import com.example.aurveda.data.repositories.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdminNotesViewModel(
    private val noteRepository: NoteRepository = MockNoteRepository()
) : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        loadNotes()
    }

    fun loadNotes() {
        viewModelScope.launch {
            _loading.value = true
            _notes.value = noteRepository.getNotes()
            _loading.value = false
        }
    }

    fun deleteNote(id: String) {
        viewModelScope.launch {
            noteRepository.deleteNote(id)
            loadNotes()
        }
    }

    fun addMockNote() {
        viewModelScope.launch {
            val dummyNote = Note(
                id = "",
                title = "New Mock Note",
                subject = "General",
                price = 0.0,
                previewPageCount = 1,
                thumbnailUrl = "",
                fileUrl = ""
            )
            noteRepository.addNote(dummyNote)
            loadNotes()
        }
    }
}