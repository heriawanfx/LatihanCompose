package com.heriawanfx.mobile.compose.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(private val noteLocalDataSource: NoteLocalDataSource){
    fun getSampleNotes(): List<Note>{
        return noteLocalDataSource.getSampleNotes()
    }
}