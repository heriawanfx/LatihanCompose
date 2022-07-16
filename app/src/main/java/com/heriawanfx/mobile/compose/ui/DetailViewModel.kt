package com.heriawanfx.mobile.compose.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.heriawanfx.mobile.compose.data.Note
import com.heriawanfx.mobile.compose.data.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    noteRepository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val selectedNoteId = savedStateHandle.get<Int>(KEY_NOTE_ID)

    private val _notes = noteRepository.getSampleNotes()

    fun getNote(): Note? {
        return _notes.find { it.id == selectedNoteId }
    }

    var title: String? by mutableStateOf(getNote()?.title)
    var description: String? by mutableStateOf(getNote()?.description)


}