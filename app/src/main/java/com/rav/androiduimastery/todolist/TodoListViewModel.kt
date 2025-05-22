package com.rav.androiduimastery.todolist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskViewModel : ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks = _tasks.asStateFlow()

    init {
        _tasks.value = listOf(
            Task(1, "Buy groceries"),
            Task(2, "Call mom"),
            Task(3, "Finish report")
        )
    }

}