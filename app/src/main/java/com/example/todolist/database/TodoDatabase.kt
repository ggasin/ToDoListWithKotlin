package com.example.todolist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todolist.dao.TodoDAO
import com.example.todolist.dto.TodoDTO


@Database(entities = arrayOf(TodoDTO::class), version = 1)
abstract class TodoDatabase : RoomDatabase(){
    abstract fun todoDao() : TodoDAO
}