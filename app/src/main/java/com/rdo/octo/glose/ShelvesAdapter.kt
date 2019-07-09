package com.rdo.octo.glose

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.cell_shelve.view.*

class ShelvesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: List<ShelveViewModel> = emptyList()

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
            Glide.with(holder.itemView).load(bookShelve.authorPictureUrl)
                .into(holder.itemView.imageView)
            holder.itemView.authorTextView.text = bookShelve.authorName
            holder.itemView.shelvaNameTextView.text = bookShelve.description
            val layoutManager = LinearLayoutManager(holder.itemView.context)
            layoutManager.orientation = HORIZONTAL
            holder.itemView.booksRecyclerView.layoutManager = layoutManager
            holder.itemView.booksRecyclerView.adapter = BookAdapter(books)
        }
    }

    fun setShelves(bookShelves: List<ShelveViewModel>) {
        list = bookShelves
        notifyDataSetChanged()
    }
}

