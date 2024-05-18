package com.example.todoapp

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


// https://developer.android.com/codelabs/android-room-with-a-view-kotlin#0
class TodoApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { ToDoItemRoomDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { ToDoItemRepository(database.todoDao()) }
    val repository2 by lazy { ListRepository(database.listDao()) }
}
