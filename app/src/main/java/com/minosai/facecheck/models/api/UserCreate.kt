package com.minosai.facecheck.models.api

import com.google.gson.annotations.SerializedName

/**
 * Created by minos.ai on 24/03/18.
 */

data class UserCreate(
        @SerializedName("registration_number") var registrationNumber: String,
        @SerializedName("password") var password: String,
        @SerializedName("is_teacher") var isTeacher: Boolean,
        @SerializedName("email") var email: String,
        @SerializedName("first_name") var firstName: String,
        @SerializedName("last_name") var lastName: String
)