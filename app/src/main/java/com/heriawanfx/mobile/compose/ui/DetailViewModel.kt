package com.heriawanfx.mobile.compose.ui

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.heriawanfx.mobile.compose.data.Note
import com.heriawanfx.mobile.compose.data.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val selectedNoteId = savedStateHandle.get<Int>(KEY_NOTE_ID)

    private val _notes: MutableList<Note>
        get() = noteRepository.getSampleNotes().toMutableStateList()

    fun getNote(): Note? {
        return _notes.find { it.id == selectedNoteId }
    }


}