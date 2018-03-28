package com.minosai.facecheck.ui.main.fragments.student

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.SharedPreferences
import android.graphics.Bitmap
import com.minosai.facecheck.api.WebService
import com.minosai.facecheck.repo.StudentRepo
import com.minosai.facecheck.utils.PreferenceHelper
import java.io.File

/**
 * Created by minos.ai on 28/03/18.
 */
class StudentViewModel(application: Application) : AndroidViewModel(application) {
    private var webService: WebService
    private var prefs: SharedPreferences
    private  lateinit var studentRepo: StudentRepo
    private lateinit var bitmap: Bitmap
    private lateinit var imgFile: File

    init {
        webService = WebService.create()
        prefs = PreferenceHelper.defaultPrefs(application)
    }
}