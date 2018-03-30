package com.minosai.facecheck.models

import com.google.gson.annotations.SerializedName

/**
 * Created by minos.ai on 30/03/18.
 */
data class Student (
        @SerializedName("user") var studentUser: StudentUser
)

data class StudentUser(
        @SerializedName("first_name") var firstName: String = "",
        @SerializedName("last_name") var lastNaame: String = ""
)