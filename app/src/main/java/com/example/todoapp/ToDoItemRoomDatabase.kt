package com.example.todoapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = arrayOf(ToDoItem::class, ListItem::class), version = 1, exportSchema = false)
abstract class ToDoItemRoomDatabase : RoomDatabase() {

    abstract fun todoDao(): ToDoItemDao

    abstract fun listDao(): ListItemDao

    private class ToDoDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var todoDao = database.todoDao()
                    var listDao = database.listDao()
                    // Delete all content here.
                    todoDao.deleteAll()
                    listDao.deleteAll()


                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ToDoItemRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ToDoItemRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoItemRoomDatabase::class.java,
                    "todo_database"
                )
                    .addCallback(ToDoDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

