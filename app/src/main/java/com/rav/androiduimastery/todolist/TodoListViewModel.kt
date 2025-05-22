package com.rav.androiduimastery.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class TaskViewModel : ViewModel() {
    private val _filter = MutableStateFlow<Boolean>(false)
    val filter = _filter.asStateFlow()

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks = _tasks.combine(_filter) { list, filter ->
        if (filter) {
            list.filter { task ->
                task.isCompleted.not()
            }
        } else {
            list
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = _tasks.value
    )

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

    fun toggleFilter() {
        _filter.update {
            !_filter.value
        }
    }
}