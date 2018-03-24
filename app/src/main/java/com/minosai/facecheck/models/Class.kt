package com.minosai.facecheck.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by minos.ai on 15/03/18.
 */

@Entity
data class Class (
        @PrimaryKey
        @SerializedName("course_code")
        var code: String,
        @SerializedName("course_title")
        var title: String,
        @SerializedName("class_venue")
        var venue: String,
        @SerializedName("class_slot")
        var slot: String
)