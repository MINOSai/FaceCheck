package com.minosai.facecheck.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.minosai.facecheck.R
import com.minosai.facecheck.models.Class
import com.minosai.facecheck.utils.inflate

/**
 * Created by minos.ai on 24/03/18.
 */
class ClassListAdapter(
        private val listener: (Class) -> Unit
) : RecyclerView.Adapter<ClassListAdapter.ClassHolder>() {

    private var classList: List<Class> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = ClassHolder(parent!!.inflate(R.layout.row_class_list))

    override fun getItemCount() = classList.size

    override fun onBindViewHolder(holder: ClassHolder?, position: Int) = holder!!.bind(classList[position], listener)

    class ClassHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(mClass: Class, listener: (Class) -> Unit) = with(itemView) {
            //TODO: bind data to views
        }
    }

    fun replaceItems(items: List<Class>) {
        classList = items
        notifyDataSetChanged()
    }
}