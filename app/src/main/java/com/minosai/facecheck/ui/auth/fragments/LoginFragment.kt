package com.minosai.facecheck.ui.auth.fragments

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.minosai.facecheck.R
import com.minosai.facecheck.api.WebService
import com.minosai.facecheck.models.api.Token
import com.minosai.facecheck.models.api.UserLogin
import com.minosai.facecheck.ui.main.MainActivity
import com.minosai.facecheck.utils.Constants
import com.minosai.facecheck.utils.PreferenceHelper
import com.minosai.facecheck.utils.PreferenceHelper.get
import com.minosai.facecheck.utils.PreferenceHelper.set
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by minos.ai on 24/03/18.
 */
class LoginFragment : Fragment() {

    var webService = WebService.create()
    var prefs: SharedPreferences? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) = inflater!!.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        prefs = PreferenceHelper.defaultPrefs(context)

        button_login.setOnClickListener {
            var call: Call<Token> = webService.loginUser(UserLogin(
                    input_username.text.toString(),
                    input_password.text.toString()
            ))
//            val result = call.execute()
//            AnkoLogger("NETWORK RESULT").info(result.toString())
            call.enqueue(object : Callback<Token> {
                override fun onResponse(call: Call<Token>?, response: Response<Token>?) {
                    if(response != null && response.isSuccessful) {
                        storeToken(input_username.text.toString(), input_password.text.toString(), response.body()?.token)
                    } else {
                        toast("An error occurred")
                    }
                }

                override fun onFailure(call: Call<Token>?, t: Throwable?) {
                    toast("Couldn't connect to server")
                }
            })
        }
    }

    private fun storeToken(userName: String, password: String?, token: String?) {
        prefs?.set(Constants.PREF_TOKEN, token)
        AnkoLogger("PREF TOKEN").info(token)
        startActivity<MainActivity>()
    }
}