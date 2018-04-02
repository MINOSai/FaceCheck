package com.minosai.facecheck.ui.main

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.minosai.facecheck.utils.*
import com.minosai.facecheck.R
import com.minosai.facecheck.models.User
import com.minosai.facecheck.ui.auth.AuthActivity
import com.minosai.facecheck.ui.main.fragments.student.StudentFragment
import com.minosai.facecheck.ui.main.fragments.student.photos.StudentPhotoActivity
import com.minosai.facecheck.ui.main.fragments.teacher.TeacherFragment
import com.minosai.facecheck.utils.PreferenceHelper.get
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkSavePic()

        prefs = PreferenceHelper.defaultPrefs(this)
        var userJson = prefs.get(Constants.KEY_USER, "")
        if(userJson == "")  {
            startActivity<AuthActivity>()
        } else {
            var gson = Gson()
            user = gson.fromJson(userJson, User::class.java)
            if(user.isTeacher) {
                supportFragmentManager.inTransaction {
                    replace(R.id.main_framelayout, TeacherFragment())
                }
            } else {
                supportFragmentManager.inTransaction {
                    replace(R.id.main_framelayout, StudentFragment())
                }
            }
        }
    }

    private fun checkSavePic() {
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1234)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1234 -> {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) { }
            }
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        if(user.isTeacher) {
//            menuInflater.inflate(R.menu.menu_teacher, menu)
//        } else {
//            menuInflater.inflate(R.menu.menu_student, menu)
//        }
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        when(item?.itemId) {
//            R.id.action_logout -> {
//                with(prefs.edit()){
//                    remove(Constants.KEY_USER)
//                    remove(Constants.PREF_TOKEN)
//                    commit()
//                }
//                startActivity<AuthActivity>()
//            }
//            R.id.action_photo -> startActivity<StudentPhotoActivity>()
//        }
//        return super.onOptionsItemSelected(item)
//    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}
