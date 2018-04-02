package com.minosai.facecheck.ui.main.fragments.student.photos

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import com.minosai.facecheck.R
import com.minosai.facecheck.adapter.PhotoListAdapter
import com.minosai.facecheck.api.WebService
import com.minosai.facecheck.models.api.UploadResponse
import com.minosai.facecheck.ui.main.MainActivity
import com.minosai.facecheck.utils.Constants
import com.minosai.facecheck.utils.PreferenceHelper
import com.minosai.facecheck.utils.PreferenceHelper.get
import kotlinx.android.synthetic.main.activity_student_photo.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import retrofit2.Call
import java.io.File
import retrofit2.Callback
import retrofit2.Response
import java.io.FileOutputStream

class StudentPhotoActivity : AppCompatActivity() {

    private lateinit var webService: WebService
    private lateinit var prefs: SharedPreferences
    private lateinit var bitmap: Bitmap
    private lateinit var imgFile: File
    private var urlList: MutableList<String> = mutableListOf()
    private lateinit var adapter: PhotoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_photo)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = PhotoListAdapter()
        rv_student_photo_grid.adapter = adapter
        rv_student_photo_grid.layoutManager = GridLayoutManager(this, 2)

        webService = WebService.create()
        prefs = PreferenceHelper.defaultPrefs(this)

        refreshPhotoList()

        fab_student_photo.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, Constants.PICK_IMAGE_ID)
        }
    }

    private fun refreshPhotoList() {
        val token: String? = prefs[Constants.PREF_TOKEN]
        val call = webService.getPhotoList("jwt $token")
        call.enqueue(object : Callback<List<UploadResponse>> {
            override fun onResponse(call: Call<List<UploadResponse>>?, response: Response<List<UploadResponse>>?) {

                response?.let {
//                    if(response.isSuccessful) {
//                        for (i in 0..response.body()!!.size) {
//                            var uploadResponse = response.body()
//                            urlList.add(uploadResponse!![i].imgUrl)
//                        }
//                        adapter.replaceItems(urlList)
//                    }
                }
            }

            override fun onFailure(call: Call<List<UploadResponse>>?, t: Throwable?) { }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                Constants.PICK_IMAGE_ID -> {
                    bitmap = data?.extras?.get("data") as Bitmap
                    checkSavePic()
                }
            }
        }
    }

    private fun checkSavePic() {
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), Constants.RC_SOTRAGE_PERMISSION)
            }
        } else {
            savePic()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.RC_SOTRAGE_PERMISSION-> {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
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
                        urlList.add(response.body()!!.imgUrl)
                        adapter.replaceItems(urlList)
                    } else {
                        toast("Failed to upload")
                        AnkoLogger("API_RESPONSE").info(response.toString())
                    }
                }
            }
            override fun onFailure(call: Call<UploadResponse>?, t: Throwable?) {
                toast("Error occurred")
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> startActivity<MainActivity>()
        }
        return super.onOptionsItemSelected(item)
    }
}
