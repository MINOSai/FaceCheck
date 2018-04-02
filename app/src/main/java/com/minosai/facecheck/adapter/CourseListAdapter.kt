package com.minosai.facecheck.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.minosai.facecheck.R
import com.minosai.facecheck.models.Class
import com.minosai.facecheck.utils.inflate
import kotlinx.android.synthetic.main.row_student_course.view.*

/**
 * Created by minos.ai on 01/04/18.
 */
class CourseListAdapter : RecyclerView.Adapter<CourseListAdapter.CourseHolder>() {

    private var courseList: List<Class> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = CourseHolder(parent!!.inflate(R.layout.row_student_course))

    override fun getItemCount() = courseList.size

    override fun onBindViewHolder(holder: CourseHolder?, position: Int) = holder!!.bind(courseList[position])

    class CourseHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(course: Class) = with(itemView) {
            text_student_course_code.text = course.code
            text_student_course_name.text = course.title
            text_student_course_slot.text = course.slot
            text_student_course_teacher.text = course.teacher
            text_student_course_venue.text = course.venue + course.room
        }
    }

    fun replaceItems(items: List<Class>) {
        courseList = items
        notifyDataSetChanged()
    }
}