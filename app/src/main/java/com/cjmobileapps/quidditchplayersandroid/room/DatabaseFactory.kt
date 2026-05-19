package com.cjmobileapps.quidditchplayersandroid.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.PlayerEntity

@Database(entities = [House::class, PlayerEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class QuidditchPlayersDatabase : RoomDatabase() {
    abstract fun quidditchPlayersDao(): QuidditchPlayersDao
}

class DatabaseFactory {
    companion object {
        fun getDB(context: Context): QuidditchPlayersDatabase =
            Room
                .databaseBuilder(
                    context,
                    QuidditchPlayersDatabase::class.java,
                    "quidditch-players-database",
                ).build()
    }
}
