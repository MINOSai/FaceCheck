package com.minosai.facecheck.models.api

import com.google.gson.annotations.SerializedName

/**
 * Created by minos.ai on 24/03/18.
 */
data class Token(
        @SerializedName("token")
        var token: String
)