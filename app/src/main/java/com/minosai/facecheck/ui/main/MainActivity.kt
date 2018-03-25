package com.minosai.facecheck.ui.main

import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.minosai.facecheck.utils.*
import com.minosai.facecheck.R
import com.minosai.facecheck.ui.auth.AuthActivity
import com.minosai.facecheck.ui.main.fragments.StudentFragment
import com.minosai.facecheck.utils.PreferenceHelper.get
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = PreferenceHelper.defaultPrefs(this)

        if(prefs.get(Constants.PREF_TOKEN, "") == "")  {
            startActivity<AuthActivity>()
        } else {
            supportFragmentManager.inTransaction {
                replace(R.id.main_framelayout, StudentFragment())
            }
        }
    }
}
