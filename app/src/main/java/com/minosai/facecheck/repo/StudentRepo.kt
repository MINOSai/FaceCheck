package com.minosai.facecheck.repo

import com.minosai.facecheck.api.WebService
import com.minosai.facecheck.models.api.UploadResponse
import com.minosai.facecheck.utils.Constants
import com.minosai.facecheck.utils.PreferenceHelper.get
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

/**
 * Created by minos.ai on 28/03/18.
 */
class StudentRepo {

    fun uploadImage(webService: WebService, imgFile: File, token: String) {
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imgFile)
        val body = MultipartBody.Part.createFormData("filefieldname", imgFile.name, requestFile)
        val token: String? = token
        val call = webService.uploadImage("  $token", body)
        call.enqueue(object : Callback<UploadResponse> {
            override fun onResponse(call: Call<UploadResponse>?, response: Response<UploadResponse>?) {
                response?.let {
                    if(it.isSuccessful) {
                        AnkoLogger("API_RESPONSE_SUCCESS").info(response.body()?.imgUrl)
                    } else {
                        AnkoLogger("API_RESPONSE").info(response.toString())
                    }
                }
            }
            override fun onFailure(call: Call<UploadResponse>?, t: Throwable?) { }
        })
    }
}