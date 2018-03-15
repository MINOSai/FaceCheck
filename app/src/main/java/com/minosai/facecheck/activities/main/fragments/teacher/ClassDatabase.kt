package com.minosai.facecheck.activities.main.fragments.teacher

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by minos.ai on 16/03/18.
 */

@Database(entities = [(Class::class)], version = 1)
abstract class ClassDatabase : RoomDatabase() {
    abstract fun classDao(): ClassDao
}