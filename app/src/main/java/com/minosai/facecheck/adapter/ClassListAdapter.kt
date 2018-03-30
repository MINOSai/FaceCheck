package com.minosai.facecheck.adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.minosai.facecheck.R
import com.minosai.facecheck.models.Class
import com.minosai.facecheck.ui.main.MainActivity
import com.minosai.facecheck.ui.students.StudentListActivity
import com.minosai.facecheck.utils.Constants
import com.minosai.facecheck.utils.inflate
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.android.synthetic.main.row_class_list.view.*

/**
 * Created by minos.ai on 24/03/18.
 */
class ClassListAdapter(
        val context: Context,
        val listener: (Class) -> Unit
) : RecyclerView.Adapter<ClassListAdapter.ClassHolder>() {

    private var classList: List<Class> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = ClassHolder(parent!!.inflate(R.layout.row_class_list))

    override fun getItemCount() = classList.size

    override fun onBindViewHolder(holder: ClassHolder?, position: Int) = holder!!.bind(classList[position], listener, context)

    class ClassHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(mClass: Class, listener: (Class) -> Unit, context: Context) = with(itemView) {
            text_class_name.text = mClass.title
            text_class_code.text = mClass.code
            text_class_slot.text = mClass.slot
            text_class_venue.text = mClass.venue + mClass.room

            txt_btn_photo.setOnClickListener {
                listener(mClass)
            }

            linear_content_holder.setOnClickListener {
                val intent = Intent(context, StudentListActivity::class.java)
                intent.putExtra(Constants.COURSE_ID, id)
                context.startActivity(intent)
            }
        }
    }

    fun replaceItems(items: List<Class>) {
        classList = items
        notifyDataSetChanged()
    }
}