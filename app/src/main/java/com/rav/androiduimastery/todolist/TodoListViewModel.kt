package com.rav.androiduimastery.todolist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

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

    fun taskCompleted(id: Int) {
        _tasks.update {
            _tasks.value.map { task ->
                if (task.id == id) task.copy(isCompleted = task.isCompleted.not())
                else task
            }
        }
    }

    fun deleteTask(id: Int) {
        _tasks.update {
            _tasks.value.mapNotNull { task ->
                if (task.id == id) null
                else task
            }
        }
    }

    fun addRandomTask() {
        _tasks.update {
            val newList = _tasks.value
            _tasks.value + Task(id = newList.size, Random.nextLong().toString())
        }
    }
}