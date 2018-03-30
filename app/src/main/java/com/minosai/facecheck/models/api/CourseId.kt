package com.minosai.facecheck.models.api

import com.google.gson.annotations.SerializedName

/**
 * Created by minos.ai on 30/03/18.
 */
data class CourseId(
        @SerializedName("course_id") val courseId: Int
)