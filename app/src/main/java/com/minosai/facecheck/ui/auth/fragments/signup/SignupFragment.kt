package com.minosai.facecheck.ui.auth.fragments.signup

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.minosai.facecheck.R
import com.minosai.facecheck.api.WebService
import com.minosai.facecheck.models.User
import com.minosai.facecheck.models.api.Token
import com.minosai.facecheck.models.api.UserCreate
import com.minosai.facecheck.models.api.UserLogin
import com.minosai.facecheck.ui.auth.fragments.login.LoginFragment
import com.minosai.facecheck.ui.main.MainActivity
import com.minosai.facecheck.utils.Constants
import com.minosai.facecheck.utils.PreferenceHelper
import com.minosai.facecheck.utils.PreferenceHelper.set
import com.minosai.facecheck.utils.inTransaction
import kotlinx.android.synthetic.main.fragment_signup.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by minos.ai on 24/03/18.
 */
class SignupFragment : Fragment() {
    var webService = WebService.create()
    lateinit var prefs: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) = inflater!!.inflate(R.layout.fragment_signup, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        prefs = PreferenceHelper.defaultPrefs(context)

        button_signup.setOnClickListener {
            signup()
        }

        text_login.setOnClickListener {
            fragmentManager.inTransaction {
                replace(R.id.auth_famelayout, LoginFragment())
            }
        }
    }

    private fun signup() {
        var call = webService.createUser(UserCreate(
                input_regno.text.toString(),
                input_password.text.toString(),
                radio_student.isChecked,
                input_email.text.toString(),
                input_first_name.text.toString(),
                input_last_name.text.toString()
        ))
        call.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>?, t: Throwable?) {
                toast("Signup failed")
            }

            override fun onResponse(call: Call<User>?, response: Response<User>?) {
                response?.let {
                    if (it.isSuccessful) {
                        var gson = Gson()
                        response.body()?.let {
                            prefs.set(
                                    Constants.KEY_USER,
                                    gson.toJson(it)
                            )
                            saveToken()
                        }
                    }
                }
            }

        })
    }

    private fun saveToken() {
        var call = webService.loginUser(UserLogin(
                input_regno.text.toString(),
                input_password.text.toString()
        ))
        call.enqueue(object : Callback<Token> {
            override fun onFailure(call: Call<Token>?, t: Throwable?) {
                toast("An error occurred")
            }

            override fun onResponse(call: Call<Token>?, response: Response<Token>?) {
                response?.let {
                    if (response.isSuccessful) {
                        prefs.set(Constants.PREF_TOKEN, response.body()?.token)
                        val intent = Intent(context, MainActivity::class.java)
                        intent.putExtra(Constants.KEY_ISTEACHER, radio_teacher.isChecked)
                        startActivity(intent)
                    }
                }
            }

        })
    }
}