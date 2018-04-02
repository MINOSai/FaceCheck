package com.minosai.facecheck.models.api

import com.google.gson.annotations.SerializedName

/**
 * Created by minos.ai on 02/04/18.
 */
data class AddCourse (
        @SerializedName("course_code") var code: String,
        @SerializedName("course_name") var title: String,
        @SerializedName("slot") var slot: String,
        @SerializedName("venue") var venue: String,
        @SerializedName("room") var room: String
)