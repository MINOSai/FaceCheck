package com.minosai.facecheck.ui.main.fragments.student.enroll

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.minosai.facecheck.R
import com.minosai.facecheck.adapter.EnrollListAdapter
import com.minosai.facecheck.api.WebService
import com.minosai.facecheck.models.Class
import com.minosai.facecheck.utils.Constants
import com.minosai.facecheck.utils.PreferenceHelper
import com.minosai.facecheck.utils.PreferenceHelper.get
import kotlinx.android.synthetic.main.activity_enroll.*
import org.jetbrains.anko.toast
import retrofit2.Call
import java.io.File
import retrofit2.Callback
import retrofit2.Response

class EnrollActivity : AppCompatActivity() {

    private lateinit var webService: WebService
    private lateinit var prefs: SharedPreferences
    private lateinit var courseList: List<Class>
    private lateinit var adapter: EnrollListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enroll)

        actionBar.setDisplayHomeAsUpEnabled(true)

        webService = WebService.create()
        prefs = PreferenceHelper.defaultPrefs(this)

        adapter = EnrollListAdapter(this){}
        rv_course_enroll.adapter = adapter
        rv_course_enroll.layoutManager = LinearLayoutManager(this)

        getAllCourses()
    }

    private fun getAllCourses() {
        val token: String? = prefs[Constants.PREF_TOKEN]
        var call = webService.getAllCourses("jwt $token")
        call.enqueue(object : Callback<List<Class>> {
            override fun onFailure(call: Call<List<Class>>?, t: Throwable?) {
                toast("An error occurred")
            }

            override fun onResponse(call: Call<List<Class>>?, response: Response<List<Class>>?) {
                response?.let {
                    if(response.isSuccessful) {
                        courseList = response.body()!!
                        adapter.replaceItems(courseList)
                    }
                }
            }

        })
    }
}
