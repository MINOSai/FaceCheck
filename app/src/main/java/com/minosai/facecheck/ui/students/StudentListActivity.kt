package com.minosai.facecheck.ui.students

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.minosai.facecheck.R
import com.minosai.facecheck.utils.Constants

class StudentListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_list)

        actionBar.setDisplayHomeAsUpEnabled(true)

        intent.getStringExtra(Constants.COURSE_ID)
    }
}
