package com.minosai.facecheck.activities.main.fragments.teacher

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

/**
 * Created by minos.ai on 16/03/18.
 */

@Dao
public interface ClassDao {

    @Insert(onConflict = REPLACE)
    fun save(vararg mClass: Class)

    @get:Query("SELECT * FROM class")
    val all: List<Class>

    @get:Query("SELECT COUNT(*) FROM class")
    val count: Int
}