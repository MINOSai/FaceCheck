package com.minosai.facecheck.models.api

import com.google.gson.annotations.SerializedName

/**
 * Created by minos.ai on 24/03/18.
 */

data class UserLogin(
        @SerializedName("registration_number") var regno: String,
        @SerializedName("password") var password: String
)