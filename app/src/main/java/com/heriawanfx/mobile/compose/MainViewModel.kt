package com.heriawanfx.mobile.compose

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val _numbers: SnapshotStateList<String>
        get() = (1..300).toList().map { "$it" }.toMutableStateList()
    val numbers: List<String>
        get() = _numbers

}