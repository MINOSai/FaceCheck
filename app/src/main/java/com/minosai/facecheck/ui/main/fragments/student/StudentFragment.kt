package com.minosai.facecheck.ui.main.fragments.student

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.minosai.facecheck.R
import com.minosai.facecheck.adapter.CourseListAdapter
import com.minosai.facecheck.api.WebService
import com.minosai.facecheck.models.Class
import com.minosai.facecheck.models.api.UploadResponse
import com.minosai.facecheck.ui.auth.AuthActivity
import com.minosai.facecheck.ui.main.fragments.student.enroll.EnrollActivity
import com.minosai.facecheck.ui.main.fragments.student.photos.StudentPhotoActivity
import com.minosai.facecheck.utils.Constants
import com.minosai.facecheck.utils.PreferenceHelper
import com.minosai.facecheck.utils.PreferenceHelper.get
import kotlinx.android.synthetic.main.fragment_student.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

/**
 * Created by minos.ai on 25/03/18.
 */
class StudentFragment : Fragment() {
    private lateinit var webService: WebService
    private lateinit var prefs: SharedPreferences
    private lateinit var adapter: CourseListAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) = inflater!!.inflate(R.layout.fragment_student, container, false)!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        webService = WebService.create()
        prefs = PreferenceHelper.defaultPrefs(context)

        adapter = CourseListAdapter()
        rv_student_courses.adapter = adapter
        rv_student_courses.layoutManager = LinearLayoutManager(context)

        getCourses()

        fab_enroll_course.setOnClickListener {
            startActivity<EnrollActivity>()
        }
    }

    private fun getCourses() {
        val token: String? = prefs[Constants.PREF_TOKEN]
        var call = webService.getCourses("jwt $token")
        call.enqueue(object : Callback<List<Class>> {
            override fun onFailure(call: Call<List<Class>>?, t: Throwable?) { }

            override fun onResponse(call: Call<List<Class>>?, response: Response<List<Class>>?) {
                response?.let {
                    if(response.isSuccessful) {
                        adapter.replaceItems(response.body()!!)
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_student, menu)
        super.onCreateOptionsMenu(menu,inflater);
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_logout -> {
                with(prefs.edit()){
                    remove(Constants.KEY_USER)
                    remove(Constants.PREF_TOKEN)
                    commit()
                }
                startActivity<AuthActivity>()
            }
            R.id.action_photo -> startActivity<StudentPhotoActivity>()
        }
        return super.onOptionsItemSelected(item)
    }
}