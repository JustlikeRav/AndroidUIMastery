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

/**
 * ViewModel for managing tasks and filtering in the TODO list demo.
 */
class TaskViewModel : ViewModel() {
    private val _filter = MutableStateFlow<Boolean>(false)
    /** Flow indicating whether to filter completed tasks. */
    val filter = _filter.asStateFlow()

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    /** Flow of tasks, filtered by completion status if enabled. */
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

    /**
     * Toggles the completion status of a task by ID.
     * @param id The ID of the task to update.
     */
    fun taskCompleted(id: Int) {
        _tasks.update {
            _tasks.value.map { task ->
                if (task.id == id) task.copy(isCompleted = task.isCompleted.not())
                else task
            }
        }
    }

    /**
     * Deletes a task by ID.
     * @param id The ID of the task to delete.
     */
    fun deleteTask(id: Int) {
        _tasks.update {
            _tasks.value.mapNotNull { task ->
                if (task.id == id) null
                else task
            }
        }
    }

    /**
     * Adds a new task with a random description.
     */
    fun addRandomTask() {
        _tasks.update {
            val newList = _tasks.value
            _tasks.value + Task(id = newList.size, Random.nextLong().toString())
        }
    }

    /**
     * Toggles the filter to show/hide completed tasks.
     */
    fun toggleFilter() {
        _filter.update {
            !_filter.value
        }
    }
}