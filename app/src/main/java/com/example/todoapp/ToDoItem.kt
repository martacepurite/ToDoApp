package com.example.todoapp

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.time.LocalDate
import java.util.Date

@Entity(tableName = "todo_table")
data class ToDoItem(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "todotext") val todotext: String?,
    @ColumnInfo(name = "date_created") val date_cr: String?,
    @ColumnInfo(name = "list_id") val lid: Int
)

