package com.example.aurveda.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurveda.data.models.Note
import com.example.aurveda.data.repositories.MockNoteRepository
import com.example.aurveda.data.repositories.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotesViewModel(
    private val noteRepository: NoteRepository = MockNoteRepository()
) : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    private val _trendingNotes = MutableStateFlow<List<Note>>(emptyList())
    val trendingNotes: StateFlow<List<Note>> = _trendingNotes

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        loadNotes()
    }

    fun loadNotes(subject: String? = null, freeOnly: Boolean = false) {
        viewModelScope.launch {
            _loading.value = true
            _notes.value = noteRepository.getNotes(subject, freeOnly)
            _loading.value = false
        }
    }
}