package com.minosai.facecheck.utils

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import retrofit2.Call
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.security.Timestamp


/**
 * Created by minos.ai on 22/03/18.
 */

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.commit()
}

//inline fun <T, U> Call.unwrapCall(f: T.() -> U): U = execute().body().f()

//fun isAccessTokenExpired(accessToken: String): Boolean? {
//
//    val accessTokenPart = accessToken.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//    val header = accessTokenPart[0]
//    var payload = accessTokenPart[1]
//    val signature = accessTokenPart[2]
//
//    try {
//
//        val decodedPayload = Base64.decode(payload, Base64.DEFAULT)
//        payload = String(decodedPayload, "UTF-8")
//    } catch (e: UnsupportedEncodingException) {
//        e.printStackTrace()
//    }
//
//
//    try {
//        val obj = JSONObject(payload)
//        val expireDate = obj.getInt("exp")
//        val timestampExpireDate = Timestamp(expireDate)
//        val time = System.currentTimeMillis()
//        val timestamp = Timestamp(time)
//
//        return timestamp.after(timestampExpireDate)
//
//    } catch (e: JSONException) {
//        e.printStackTrace()
//        return true
//    }
//
//}