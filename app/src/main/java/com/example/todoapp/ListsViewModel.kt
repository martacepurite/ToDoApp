package com.example.todoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ListsViewModel(private val repository: ListRepository) : ViewModel(){
    val allLists: LiveData<List<ListItem>> = repository.getAllItemsStream().asLiveData()

    fun insert(item: ListItem) = viewModelScope.launch {
        repository.insertItem(item)
    }

    fun update(item: ListItem) = viewModelScope.launch {
        repository.updateItem(item)
    }

    fun update(newtext: String , uid: Int) = viewModelScope.launch {
        repository.updateItem(newtext, uid)
    }

    fun update(uid: Int) = viewModelScope.launch {
        repository.deleteItem(uid)
    }
}

class ListViewModelFactory(private val repository: ListRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}