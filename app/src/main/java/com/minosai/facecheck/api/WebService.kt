package com.minosai.facecheck.api

import com.google.gson.Gson
import com.minosai.facecheck.models.Class
import com.minosai.facecheck.models.Student
import com.minosai.facecheck.models.User
import com.minosai.facecheck.models.api.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 * Created by minos.ai on 16/03/18.
 */
interface WebService {

    @POST("user/create/")
    fun createUser(@Body createUser: UserCreate) : Call<User>

    @POST("user/login/")
    fun loginUser(@Body userLogin: UserLogin) : Call<Token>

    @GET("user/view/")
    fun getUserDetails(@Header("Authorization") token: String) : Call<User>

//    @Multipart
//    @POST("upload/")
//    fun uploadImage(@Header("Authorization") token: String, @Part image: MultipartBody.Part) : Call<UploadResponse>

    @Multipart
    @POST("photo/")
    fun teacherUploadPhoto(@Header("Authorization") token: String, @Part image: MultipartBody.Part, @Body courseId: CourseId): Call<UploadResponse>

    @Multipart
    @POST("photo/")
    fun studentUploadPhoto(@Header("Authorization") token: String, @Part image: MultipartBody.Part): Call<UploadResponse>

    @GET("course/")
    fun getCourses(@Header("Authorization") token: String): Call<List<Class>>

    @GET("course/all/")
    fun getAllCourses(@Header("Authorization") token: String): Call<List<Class>>

    @PATCH("course/")
    fun enrollStudent(@Header("Authorization") token: String, @Body courseId: CourseId): Call<Class>

    @GET("student")
    fun getStudentList(@Header("Authorization") token: String, @Body courseId: CourseId): Call<List<Student>>

    companion object {
        fun create(): WebService {
            val retrofit = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8000/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(WebService::class.java)
        }
    }
}