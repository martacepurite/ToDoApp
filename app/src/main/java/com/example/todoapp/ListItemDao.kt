package com.example.todoapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ListItemDao {

    @Query("SELECT * FROM list_table ORDER BY uid ASC")
    fun getOrderedLists(): Flow<List<ListItem>>

    @Query("SELECT * from list_table WHERE uid = :uid")
    fun getItem(uid: Int): ListItem

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(list: ListItem)

    @Query("DELETE FROM list_table")
    suspend fun deleteAll()

    @Query("UPDATE list_table SET list_name=:newtext WHERE uid = :uid")
    suspend fun update(newtext: String , uid: Int)

    @Delete
    suspend fun delete(list: ListItem)

    @Query("DELETE from list_table WHERE uid = :uid")
    suspend fun update(uid: Int)

    @Update
    suspend fun update(list: ListItem)

}