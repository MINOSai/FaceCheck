package com.minosai.facecheck.ui.main.fragments.student.photos

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.minosai.facecheck.R

class StudentPhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_photo)

        actionBar.setDisplayHomeAsUpEnabled(true)
    }
}
