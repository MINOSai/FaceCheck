package com.minosai.facecheck.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by minos.ai on 15/03/18.
 */

data class Class (
        @SerializedName("course_code") var code: String,
        @SerializedName("course_name") var title: String,
        @SerializedName("course_slot") var slot: String,
        @SerializedName("course_venue") var venue: String,
        @SerializedName("course_room") var room: String,
        @SerializedName("teacher") var teacher: String,
        @SerializedName("id") var id: Int
)