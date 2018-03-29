package com.minosai.facecheck.ui.main.fragments.teacher

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.minosai.facecheck.R
import com.minosai.facecheck.api.WebService
import com.minosai.facecheck.utils.PreferenceHelper
import java.io.File

/**
 * Created by minos.ai on 30/03/18.
 */
class TeacherFragment : Fragment() {
    private lateinit var webService: WebService
    private lateinit var prefs: SharedPreferences
    private lateinit var bitmap: Bitmap
    private lateinit var imgFile: File

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) = inflater!!.inflate(R.layout.fragment_teacher, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        webService = WebService.create()
        prefs = PreferenceHelper.defaultPrefs(context)


    }
}