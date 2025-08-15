package com.mahnoosh.database.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mahnoosh.database.data.dao.HeadlineDao
import com.mahnoosh.database.data.model.HeadlineEntity

@Database(entities = [HeadlineEntity::class], version = 1)
internal abstract class NewsDatabase : RoomDatabase() {
    abstract fun headlineDao(): HeadlineDao

}