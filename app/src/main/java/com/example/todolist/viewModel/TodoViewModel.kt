package com.example.todolist.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.dto.TodoDTO
import com.example.todolist.reposiroty.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel:ViewModel() {
    val todoList : LiveData<MutableList<TodoDTO>>
    private  val todoRepository: TodoRepository = TodoRepository.get()

    init {
        todoList = todoRepository.list()
    }

    fun getOne(id: Long) = todoRepository.getTodo(id)

    fun insert(dto: TodoDTO) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.insert(dto)
    }

    fun update(dto: TodoDTO) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.update(dto)
    }

    fun delete(dto: TodoDTO) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.delete(dto)
    }



}