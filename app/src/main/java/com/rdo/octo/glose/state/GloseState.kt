package com.rdo.octo.glose.state

import com.rdo.octo.glose.reducers.BookShelve

data class GloseState(
    val bookShelves: List<BookShelve> = emptyList()
) {

    companion object {
        val INSTANCE = GloseState()
    }


}