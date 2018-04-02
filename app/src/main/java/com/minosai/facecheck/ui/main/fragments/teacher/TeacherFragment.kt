package com.minosai.facecheck.ui.main.fragments.teacher

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.minosai.facecheck.R
import com.minosai.facecheck.adapter.ClassListAdapter
import com.minosai.facecheck.api.WebService
import com.minosai.facecheck.models.Class
import com.minosai.facecheck.models.api.CourseId
import com.minosai.facecheck.models.api.UploadResponse
import com.minosai.facecheck.ui.auth.AuthActivity
import com.minosai.facecheck.ui.main.fragments.teacher.addcourse.AddCourseActivity
import com.minosai.facecheck.utils.Constants
import com.minosai.facecheck.utils.PreferenceHelper
import com.minosai.facecheck.utils.PreferenceHelper.get
import kotlinx.android.synthetic.main.fragment_teacher.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by minos.ai on 30/03/18.
 */
class TeacherFragment : Fragment() {
    private lateinit var webService: WebService
    private lateinit var prefs: SharedPreferences
    private lateinit var bitmap: Bitmap
    private lateinit var imgFile: File
    private var courseId: Int = 0
    private lateinit var adapter: ClassListAdapter
    var mCurrentPhotoPath: String? = null
    private var imageUri: Uri? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) = inflater!!.inflate(R.layout.fragment_teacher, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        webService = WebService.create()
        prefs = PreferenceHelper.defaultPrefs(context)

        adapter = ClassListAdapter(context,{
            courseId = it.id
            captureImage()
        })
        rv_teacher_course.adapter = adapter
        rv_teacher_course.layoutManager = LinearLayoutManager(context)

        getCourses()

        fab_teacher.setOnClickListener {
            startActivity<AddCourseActivity>()
        }
    }

    override fun onResume() {
        super.onResume()
        getCourses()
    }

    private fun getCourses() {
        val token: String? = prefs[Constants.PREF_TOKEN]
        var call = webService.getCourses("jwt $token")
        call.enqueue(object : Callback<List<Class>> {
            override fun onFailure(call: Call<List<Class>>?, t: Throwable?) { }

            override fun onResponse(call: Call<List<Class>>?, response: Response<List<Class>>?) {
                response?.let {
                    if(response.isSuccessful) {
                        adapter.replaceItems(response.body()!!)
                    }
                }
            }
        })
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val storageDir = context.getExternalFilesDir(Environment.getExternalStorageDirectory().toString())
        val image = File.createTempFile("ImageUpload", ".jpg", storageDir)
        mCurrentPhotoPath = image.getAbsolutePath()
        return image
    }

    fun captureImage() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        imageUri = FileProvider.getUriForFile(context,"com.minosai.facecheck.fileprovider", createImageFile())
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, Constants.PICK_IMAGE_ID)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            Constants.PICK_IMAGE_ID -> {
                if(resultCode == Activity.RESULT_OK) {
                    imgFile = File(imageUri.toString())
                    uploadImage()
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
        if (file.exists())
            file.delete()
        try {
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
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
        val cId = MultipartBody.Part.createFormData("id", courseId.toString())
        val token: String? = prefs[Constants.PREF_TOKEN]
        val call = webService.teacherUploadPhoto("jwt $token", body, cId)
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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_teacher, menu)
        super.onCreateOptionsMenu(menu,inflater);
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_logout -> {
                with(prefs.edit()){
                    remove(Constants.KEY_USER)
                    remove(Constants.PREF_TOKEN)
                    commit()
                }
                startActivity<AuthActivity>()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}