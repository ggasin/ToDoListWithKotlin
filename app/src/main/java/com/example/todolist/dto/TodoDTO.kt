package com.example.todolist.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "todoTable")
class TodoDTO (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id : Long = 0,
    @ColumnInfo(name = "title")  var title : String,
    @ColumnInfo(name = "content")  var content : String,
    @ColumnInfo(name = "time") var time : String,
    @ColumnInfo(name = "isChecked")  var isChecked : Boolean,
) : Serializable {

}