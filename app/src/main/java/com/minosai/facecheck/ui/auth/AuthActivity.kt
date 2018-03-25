package com.minosai.facecheck.ui.auth

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.minosai.facecheck.R
import com.minosai.facecheck.ui.auth.fragments.LoginFragment
import com.minosai.facecheck.utils.inTransaction
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        supportFragmentManager.inTransaction {
            replace(R.id.auth_famelayout, LoginFragment())
        }
    }
}
