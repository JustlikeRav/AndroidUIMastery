package com.rav.androiduimastery.todolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun TodoListComposable(modifier: Modifier = Modifier, viewModel: TaskViewModel = viewModel()) {
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    Column(modifier = modifier) {
        Row {
            Button(onClick = {
                viewModel.addRandomTask()
            }) {
                Text("Add Random Task")
            }
        }

        tasks.forEach { task ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        viewModel.deleteTask(task.id)
                    }
            ) {
                Text(
                    text = task.title,
                    modifier = Modifier.weight(1f),
                    style = if (task.isCompleted) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle()
                )
                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = { viewModel.taskCompleted(task.id) })
            }
        }
    }
}