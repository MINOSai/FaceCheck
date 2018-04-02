package com.minosai.facecheck.ui.main.students

import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.minosai.facecheck.R
import com.minosai.facecheck.adapter.StudentListAdapter
import com.minosai.facecheck.api.WebService
import com.minosai.facecheck.models.Student
import com.minosai.facecheck.models.api.CourseId
import com.minosai.facecheck.utils.Constants
import com.minosai.facecheck.utils.PreferenceHelper
import com.minosai.facecheck.utils.PreferenceHelper.get
import kotlinx.android.synthetic.main.activity_student_list.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentListActivity : AppCompatActivity() {

    private lateinit var webService: WebService
    private lateinit var prefs: SharedPreferences
    private lateinit var studentList: List<Student>
    private lateinit var adapter: StudentListAdapter
    private var courseId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_list)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        webService = WebService.create()
        prefs = PreferenceHelper.defaultPrefs(this)

        courseId = intent.getIntExtra(Constants.COURSE_ID, -1)
        if(courseId != -1) {
            adapter = StudentListAdapter()
            rv_student_list.adapter = adapter
            rv_student_list.layoutManager = LinearLayoutManager(this)
            getStudentList(courseId)
        }
    }

    private fun getStudentList(courseId: Int) {
        val token: String? = prefs[Constants.PREF_TOKEN]
        val call = webService.getStudentList("jwt $token", CourseId(courseId))
        call.enqueue(object : Callback<List<Student>> {
            override fun onFailure(call: Call<List<Student>>?, t: Throwable?) { toast("Error occurred") }

            override fun onResponse(call: Call<List<Student>>?, response: Response<List<Student>>?) {
                response?.let {
                    if(response.isSuccessful) {
                        studentList = response.body()!!
                        adapter.replaceItems(studentList)
                    }
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
