package com.example.todoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope

class ItemsViewModel(private val repository: ToDoItemRepository) : ViewModel() {

    val allTodos: LiveData<List<ToDoItem>> = repository.getAllItemsStream().asLiveData()

//    val todosInList : LiveData<List<ToDoItem>> = repository.getItemsInList(lid).asLiveData()

    fun todosInList(lid: Int): LiveData<List<ToDoItem>> {
        return repository.getItemsInList(lid).asLiveData()
    }

    fun insert(todoitem: ToDoItem) = viewModelScope.launch {
        repository.insertItem(todoitem)
    }

    fun update(todoitem: ToDoItem) = viewModelScope.launch {
        repository.updateItem(todoitem)
    }

    fun update(newtext: String , uid: Int) = viewModelScope.launch {
        repository.updateItem(newtext, uid)
    }

    fun update(uid: Int) = viewModelScope.launch {
        repository.deleteItem(uid)
    }

    fun delete(lid: Int) = viewModelScope.launch {
        repository.deleteAllInList(lid)
    }

    fun searchDatabase(searchQuery: String): LiveData<List<ToDoItem>> {
        return repository.searchDatabase(searchQuery).asLiveData()
    }

}

class ItemsViewModelFactory(private val repository: ToDoItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ItemsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

