package com.heriawanfx.mobile.compose.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteLocalDataSource @Inject constructor() {
    fun getSampleNotes(): List<Note>{
        return List(3) {
            val id = it + 1
            Note(id, "Title #$id", "Description #$id")
        }
    }
}