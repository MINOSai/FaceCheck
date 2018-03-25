package com.minosai.facecheck.models

import com.google.gson.annotations.SerializedName

/**
 * Created by minos.ai on 24/03/18.
 */
data class User(
        @SerializedName("registration_number") var registrationNumber: String,
        @SerializedName("is_teacher") var isTeacher: Boolean,
        @SerializedName("email") var email: String,
        @SerializedName("first_name") var firstName: String,
        @SerializedName("last_name") var lastName: String
)