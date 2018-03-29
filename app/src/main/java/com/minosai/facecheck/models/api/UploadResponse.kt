package com.minosai.facecheck.models.api

import com.google.gson.annotations.SerializedName

/**
 * Created by minos.ai on 25/03/18.
 */
data class UploadResponse (
        @SerializedName("student") val student: Int,
        @SerializedName("teacher") val teacher: Int,
        @SerializedName("img") val imgUrl: String
)