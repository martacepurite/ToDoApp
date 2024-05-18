package com.example.todoapp

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow


class ToDoItemRepository(private val todoDao: ToDoItemDao){

    val allTodos: Flow<List<ToDoItem>> = todoDao.getOrderedTodos()

    fun getAllItemsStream(): Flow<List<ToDoItem>> = todoDao.getOrderedTodos()

    fun getItemsInList(lid: Int): Flow<List<ToDoItem>> = todoDao.getItemsInList(lid)

    fun searchDatabase(query: String) : Flow<List<ToDoItem>> = todoDao.searchDatabase(query)

    fun getItemStream(id: Int): ToDoItem = todoDao.getItem(id)

    suspend fun insertItem(item: ToDoItem) = todoDao.insert(item)

    suspend fun deleteItem(id: Int) = todoDao.update(id)

    suspend fun updateItem(item: ToDoItem) = todoDao.update(item)

    suspend fun updateItem(newtext: String , uid: Int) = todoDao.update(newtext, uid)

    suspend fun deleteAllInList(lid: Int) = todoDao.deleteAllInList(lid)

}
