package com.rav.androiduimastery.todolist

data class Task(
    val id: Int,
    val title: String,
    val isCompleted: Boolean = false,
    val priority: Int = 0 // For sorting later
)