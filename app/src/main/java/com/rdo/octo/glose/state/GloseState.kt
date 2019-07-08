package com.rdo.octo.glose.state

import com.rdo.octo.glose.entities.Book
import com.rdo.octo.glose.entities.BookShelve

data class GloseState(
    val bookShelves: List<BookShelve> = emptyList(),
    val shelvesBookIdMap: Map<String, List<Book>> = mutableMapOf()
) {

    companion object {
        val INSTANCE = GloseState()
    }
}