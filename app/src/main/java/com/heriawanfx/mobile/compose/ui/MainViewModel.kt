package com.heriawanfx.mobile.compose.ui

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.heriawanfx.mobile.compose.data.Note
import com.heriawanfx.mobile.compose.data.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {
    /**
     * Jangan mengubah tipe ini menjadi SnapshotStateList
     * menyebabkan state menjadi tidak berfungsi
     */
    private val _notes = noteRepository.getSampleNotes().toMutableStateList()
    val notes: List<Note>
        get() = _notes

    fun toggleExpandNote(item: Note){
        _notes.find { it.id == item.id }?.let {
            it.isExpanded = !it.isExpanded
        }
    }

    fun checkNote(item: Note, isChecked: Boolean){
        _notes.find { it.id == item.id }?.let {
            it.isChecked = isChecked
        }
    }

    fun removeNote(item: Note){
        _notes.remove(item)
    }


    fun saveNote(note: Note){
        val selectedIndex = _notes.indexOf(note)
        _notes[selectedIndex] = note
    }

    fun addNewNote(note: Note){
        _notes.add(note)
    }

}