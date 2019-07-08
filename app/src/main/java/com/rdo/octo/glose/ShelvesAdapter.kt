package com.rdo.octo.glose

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rdo.octo.glose.reducers.BookShelve
import kotlinx.android.synthetic.main.cell_shelve.view.*

class ShelvesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: List<BookShelve> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.cell_shelve,
            parent,
            false
        )
        return object : RecyclerView.ViewHolder(
            view
        ) {}
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(list[position]) {
            Glide.with(holder.itemView).load(authorPictureUrl).into(holder.itemView.imageView)
            holder.itemView.authorTextView.text = authorName
            holder.itemView.shelvaNameTextView.text = description
        }
    }

    fun setShelves(bookShelves: List<BookShelve>) {
        list = bookShelves
        notifyDataSetChanged()
    }
}