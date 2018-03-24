package com.minosai.facecheck.db.teacher

import android.arch.persistence.room.Room
import android.content.Context

/**
 * Created by minos.ai on 16/03/18.
 */
object DatabaseCreator {

    fun database(context: Context): ClassDatabase {
        return Room.databaseBuilder(context, ClassDatabase::class.java, "class-db").build()
    }

}