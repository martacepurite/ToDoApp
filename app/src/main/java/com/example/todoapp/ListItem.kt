package com.example.todoapp

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.time.LocalDate
import java.util.Date

@Entity(tableName = "list_table")
data class ListItem(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "list_name") val listname: String?

)