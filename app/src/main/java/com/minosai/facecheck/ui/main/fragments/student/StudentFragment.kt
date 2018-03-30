package com.minosai.facecheck.ui.main.fragments.student

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.minosai.facecheck.R
import com.minosai.facecheck.api.WebService
import com.minosai.facecheck.models.api.UploadResponse
import com.minosai.facecheck.utils.Constants
import com.minosai.facecheck.utils.PreferenceHelper
import com.minosai.facecheck.utils.PreferenceHelper.get
import kotlinx.android.synthetic.main.fragment_student.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

/**
 * Created by minos.ai on 25/03/18.
 */
class StudentFragment : Fragment() {
    private lateinit var webService: WebService
    private lateinit var prefs: SharedPreferences
    private lateinit var bitmap: Bitmap
    private lateinit var imgFile: File

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) = inflater!!.inflate(R.layout.fragment_student, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        webService = WebService.create()
        prefs = PreferenceHelper.defaultPrefs(context)

        floatingActionButton.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, Constants.PICK_IMAGE_ID)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK) {
            when(requestCode) {
                Constants.PICK_IMAGE_ID -> {
                    bitmap = data?.extras?.get("data") as Bitmap
                    checkSavePic()
                }
            }
        }
    }

    private fun checkSavePic() {
        val permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), Constants.RC_SOTRAGE_PERMISSION)
            }
        } else {
            savePic()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.RC_SOTRAGE_PERMISSION-> {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    savePic()
                }
            }
        }
    }

    private fun savePic() {
        val root = Environment.getExternalStorageDirectory().toString()
        val myDir = File("$root/FaceCheck")
        myDir.mkdirs()
        val fname = "ImageUpload.jpg"
        val file = File(myDir, fname)
//        Log.i("IMAGE-SAVE", "" + file)
        if (file.exists())
            file.delete()
        try {
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        imgFile = File("$root/FaceCheck/$fname")
        uploadImage()
    }

    private fun uploadImage() {
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imgFile)
        val body = MultipartBody.Part.createFormData("img", imgFile.name, requestFile)
        val token: String? = prefs[Constants.PREF_TOKEN]
        val call = webService.studentUploadPhoto("jwt $token", body)
        call.enqueue(object : Callback<UploadResponse> {
            override fun onResponse(call: Call<UploadResponse>?, response: Response<UploadResponse>?) {
                response?.let {
                    if(it.isSuccessful) {
                        toast("Image uploaded")
                        AnkoLogger("API_RESPONSE_SUCCESS").info(response.body()?.imgUrl)
                    } else {
                        toast("Error occurred")
                        AnkoLogger("API_RESPONSE").info(response.toString())
                    }
                }
            }
            override fun onFailure(call: Call<UploadResponse>?, t: Throwable?) {
                toast("Failed to upload")
            }
        })
    }
}