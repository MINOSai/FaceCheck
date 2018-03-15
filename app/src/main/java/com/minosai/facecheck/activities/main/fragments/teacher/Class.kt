package com.minosai.facecheck.activities.main.fragments.teacher

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by minos.ai on 15/03/18.
 */

@Entity
data class Class (
        @PrimaryKey
        var code: String,
        var title: String,
        var venue: String,
        var slot: String
)