package com.minosai.facecheck.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.minosai.facecheck.R
import com.minosai.facecheck.utils.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_image_student.view.*

/**
 * Created by minos.ai on 30/03/18.
 */
class PhotoListAdapter : RecyclerView.Adapter<PhotoListAdapter.PhotoHolder>() {

    private var photoUrlList: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = PhotoListAdapter.PhotoHolder(parent!!.inflate(R.layout.row_image_student))

    override fun getItemCount() = photoUrlList.size

    override fun onBindViewHolder(holder: PhotoHolder?, position: Int) = holder!!.bind(photoUrlList[position])

    class PhotoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(photoUrl: String) = with(itemView) {
            Picasso.get().load(photoUrl).into(image_student_upload)
        }
    }

    fun replaceItems(photoUrlList: MutableList<String>){
        this.photoUrlList = photoUrlList
        notifyDataSetChanged()
    }
}