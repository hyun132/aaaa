package com.example.locationfinder.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*

/**
 * McLocationDao
 */
@Dao
interface McLocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveLocation(mcItemEntity: McItemEntity): Long

    @Delete
    fun deleteLocation(mcItemEntity: McItemEntity) :Int

    @Query("select * from item_db")
    fun getSavedLocation(): DataSource.Factory<Int, McItemEntity>
//    fun getSavedLocation(): LiveData<List<McItemEntity>>

    @Query("Delete from item_db")
    fun deleteAllSavedLocation():Int

    @Update
    fun updateLocation(mcItemEntity: McItemEntity):Int
}