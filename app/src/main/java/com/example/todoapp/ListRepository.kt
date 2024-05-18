package com.example.todoapp

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow


class ListRepository(private val listDao: ListItemDao){

    val allLists: Flow<List<ListItem>> = listDao.getOrderedLists()

    fun getAllItemsStream(): Flow<List<ListItem>> = listDao.getOrderedLists()

    fun getItemStream(id: Int): ListItem = listDao.getItem(id)

    suspend fun insertItem(item: ListItem) = listDao.insert(item)

    suspend fun deleteItem(id: Int) = listDao.update(id)

    suspend fun updateItem(item: ListItem) = listDao.update(item)

    suspend fun updateItem(newtext: String , uid: Int) = listDao.update(newtext, uid)


}