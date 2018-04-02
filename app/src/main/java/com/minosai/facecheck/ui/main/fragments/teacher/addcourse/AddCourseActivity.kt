package com.minosai.facecheck.ui.main.fragments.teacher.addcourse

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.minosai.facecheck.R
import android.content.DialogInterface
import android.content.SharedPreferences
import android.support.v7.app.AlertDialog
import android.view.MenuItem
import com.minosai.facecheck.api.WebService
import com.minosai.facecheck.models.Class
import com.minosai.facecheck.models.api.AddCourse
import com.minosai.facecheck.ui.main.MainActivity
import com.minosai.facecheck.utils.Constants
import com.minosai.facecheck.utils.PreferenceHelper
import com.minosai.facecheck.utils.PreferenceHelper.get
import kotlinx.android.synthetic.main.activity_add_course.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.jetbrains.anko.toolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddCourseActivity : AppCompatActivity() {

    private lateinit var webService: WebService
    private lateinit var prefs: SharedPreferences
    private val venueList: Array<CharSequence> = arrayOf("sjt", "tt", "gdn", "smv")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        webService = WebService.create()
        prefs = PreferenceHelper.defaultPrefs(this)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Make your selection")
        builder.setItems(venueList, DialogInterface.OnClickListener { dialog, item ->
            input_new_course_venue.text = venueList[item]
        })
        val alert = builder.create()

        input_new_course_venue.setOnClickListener { alert.show() }

        fab_add_new_course.setOnClickListener {
            addCourse(
                    input_new_course_code.text.toString(),
                    input_new_course_name.text.toString(),
                    input_new_course_slot.text.toString(),
                    input_new_course_venue.text.toString(),
                    input_new_course_room.text.toString()
            )
        }
    }

    private fun addCourse(courseCode: String, courseName: String, courseSlot: String, courseVenue: String, courseRoom: String) {
        val token: String? = prefs[Constants.PREF_TOKEN]
        var call = webService.addCourse("jwt $token", AddCourse(
                code = courseCode,
                title = courseName,
                slot =  courseSlot,
                venue = courseVenue,
                room = courseRoom
        ))
        call.enqueue(object : Callback<Class> {
            override fun onFailure(call: Call<Class>?, t: Throwable?) { toast("An error occurred") }

            override fun onResponse(call: Call<Class>?, response: Response<Class>?) {
                response?.let {
                    if(response.isSuccessful) {
                        toast("Course added successfully")
                        startActivity<MainActivity>()
                    } else {

                        toast("Could not add course")
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
