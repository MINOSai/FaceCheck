package com.minosai.facecheck.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.minosai.facecheck.R
import com.minosai.facecheck.models.Student
import com.minosai.facecheck.utils.inflate
import kotlinx.android.synthetic.main.row_student_list.view.*

/**
 * Created by minos.ai on 30/03/18.
 */
class StudentListAdapter : RecyclerView.Adapter<StudentListAdapter.StudentHolder>() {

    private var studentList: List<Student> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = StudentListAdapter.StudentHolder(parent!!.inflate(R.layout.row_student_list))

    override fun getItemCount(): Int = studentList.size

    override fun onBindViewHolder(holder: StudentHolder?, position: Int)= holder!!.bind(studentList[position])

    class StudentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(student: Student) = with(itemView) {
            text_student_name.text = student.studentUser.firstName
            text_student_regno.text = student.studentUser.lastName
        }
    }

    fun replaceItems(items: List<Student>) {
        studentList = items
        notifyDataSetChanged()
    }
}