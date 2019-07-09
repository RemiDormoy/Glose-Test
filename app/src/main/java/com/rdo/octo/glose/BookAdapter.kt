package com.rdo.octo.glose

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rdo.octo.glose.entities.Book
import kotlinx.android.synthetic.main.cell_book.view.*

class BookAdapter(private val list: List<Book>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.cell_book,
            parent,
            false
        )
        return object : RecyclerView.ViewHolder(
            view
        ) {}
    }

    override fun getItemCount() = list?.size ?: 3

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list != null) {
            with(list[position]) {
                imageUrl?.let {
                    Glide.with(holder.itemView)
                        .load(it)
                        .into(holder.itemView.bookImageView)
                }
                holder.itemView.bookTitleTextView.text = name
                holder.itemView.authorTextView.text = authorName
                price?.let { holder.itemView.priceTextView.text = "$it €" }
                holder.itemView.bookProgresssBar.visibility = GONE
            }
        } else {
            holder.itemView.bookImageView.setImageResource(R.drawable.ic_books)
            holder.itemView.bookProgresssBar.visibility = VISIBLE
        }
    }
}