package com.heriawanfx.mobile.compose

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _notes: SnapshotStateList<Note>
        get() = getNotesList().toMutableStateList()
    val notes: List<Note>
        get() = _notes

    private fun getNotesList(): List<Note> {
        return List(5) {
            val id = it + 1
            Note(id, "Title #$id", "Description #$id")
        }

    }

    fun toggleExpandNote(item: Note){
        notes.find { it.id == item.id }?.let {
            it.isExpanded = !it.isExpanded
        }
    }

    fun checkNote(item: Note, isChecked: Boolean){
        notes.find { it.id == item.id }?.let {
            it.isChecked = isChecked
        }
    }

    fun removeNote(item: Note){
        _notes.remove(item)
    }

    fun addNewNote(){
        val id = getNotesList().lastIndex + 1
        _notes.add(Note(id, "Title #$id", "Description #$id"))
    }

}