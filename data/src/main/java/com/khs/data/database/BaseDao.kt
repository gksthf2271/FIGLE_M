package com.khs.data.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDAO<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: T)

    @Insert
    fun insertList(obj: List<T>)

    @Delete
    fun delete(obj :T)

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(obj: T)
}
