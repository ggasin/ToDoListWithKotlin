package com.example.todolist.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todolist.dto.TodoDTO

@Dao
interface TodoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE) //대이터 삽입 시 충돌이 발생하는 경우 해당 데이터를 대체하도록
    fun insert(dto: TodoDTO)

    @Query("select * from todoTable")
    fun list(): LiveData<MutableList<TodoDTO>>

    @Query("select * from todoTable where id = (:id)")
    fun selectOne(id:Long): TodoDTO

    @Update
    suspend fun update(dto: TodoDTO)

    @Delete
    fun delete(dto : TodoDTO)
}