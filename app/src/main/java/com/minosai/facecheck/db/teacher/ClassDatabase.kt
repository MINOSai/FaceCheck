package com.minosai.facecheck.db.teacher

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.minosai.facecheck.models.Class

/**
 * Created by minos.ai on 16/03/18.
 */

@Database(entities = arrayOf(Class::class), version = 1)
abstract class ClassDatabase : RoomDatabase() {
    abstract fun classDao(): ClassDao
}