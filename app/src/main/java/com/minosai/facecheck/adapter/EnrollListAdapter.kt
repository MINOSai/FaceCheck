package com.minosai.facecheck.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.minosai.facecheck.R
import com.minosai.facecheck.api.WebService
import com.minosai.facecheck.models.Class
import com.minosai.facecheck.models.User
import com.minosai.facecheck.models.api.CourseId
import com.minosai.facecheck.utils.Constants
import com.minosai.facecheck.utils.PreferenceHelper
import com.minosai.facecheck.utils.PreferenceHelper.get
import com.minosai.facecheck.utils.inflate
import kotlinx.android.synthetic.main.row_enroll_list.view.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by minos.ai on 30/03/18.
 */
class EnrollListAdapter(
        val context: Context,
        val listener: (Class) -> Unit
) : RecyclerView.Adapter<EnrollListAdapter.EnrollHolder>(){

    private var courseList: List<Class> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = EnrollHolder(parent!!.inflate(R.layout.row_class_list))

    override fun getItemCount() = courseList.size

    override fun onBindViewHolder(holder: EnrollHolder?, position: Int) = holder!!.bind(courseList[position], listener, context)

    class EnrollHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(course: Class, listener: (Class) -> Unit, context: Context) = with(itemView) {
            text_course_name.text = course.title
            text_course_code.text = course.code
            text_course_slot.text = course.slot
            text_course_venue.text = course.venue + course.room
            text_course_teacher.text = course.teacher

            txt_btn_enroll.setOnClickListener {
                val webService = WebService.create()
                val prefs = PreferenceHelper.defaultPrefs(context)
                val token: String? = prefs[Constants.PREF_TOKEN]
                var call= webService.enrollStudent("jwt $token", CourseId(course.id))
                call.enqueue(object : Callback<Class> {
                    override fun onFailure(call: Call<Class>?, t: Throwable?) {
                        context.toast("Error occurred")
                    }

                    override fun onResponse(call: Call<Class>?, response: Response<Class>?) {
                        response?.let {
                            if(response.isSuccessful) {
                                txt_btn_enroll.visibility = View.GONE
                                text_enroll_complete.visibility = View.VISIBLE
                            } else {
                                context.toast("Not able to register")
                            }
                        }
                    }
                })
            }
        }
    }

    fun replaceItems(items: List<Class>) {
        courseList = items
        notifyDataSetChanged()
    }
}