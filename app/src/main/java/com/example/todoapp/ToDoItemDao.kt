package com.example.todoapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// https://www.geeksforgeeks.org/android-searchview-in-room-database/

@Dao
interface ToDoItemDao {

    @Query("SELECT * FROM todo_table ORDER BY uid ASC")
    fun getOrderedTodos(): Flow<List<ToDoItem>>

    @Query("SELECT * from todo_table WHERE list_id = :list_id")
    fun getItemsInList(list_id : Int) : Flow<List<ToDoItem>>

    @Query("SELECT * from todo_table WHERE uid = :uid")
    fun getItem(uid: Int): ToDoItem

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: ToDoItem)

    @Query("DELETE FROM todo_table WHERE list_id = :list_id")
    suspend fun deleteAllInList(list_id : Int)

    @Query("SELECT * FROM todo_table WHERE todotext LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<ToDoItem>>

    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()

    @Query("UPDATE todo_table SET todotext=:newtext WHERE uid = :uid")
    suspend fun update(newtext: String , uid: Int)

    @Delete
    suspend fun delete(todo: ToDoItem)

    @Query("DELETE from todo_table WHERE uid = :uid")
    suspend fun update(uid: Int)

    @Update
    suspend fun update(todo: ToDoItem)



}
