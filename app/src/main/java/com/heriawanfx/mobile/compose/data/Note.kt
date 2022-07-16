package com.heriawanfx.mobile.compose.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class Note(
    val id: Int,
    val title: String,
    val description: String,
    private val initialExpanded: Boolean = false,
    private val initialChecked: Boolean = false,
) {
    var isExpanded: Boolean by mutableStateOf(initialExpanded)
    var isChecked: Boolean by mutableStateOf(initialChecked)
}
