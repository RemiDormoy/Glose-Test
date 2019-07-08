package com.rdo.octo.glose.reducers

import com.rdo.octo.glose.actions.GloseAction
import com.rdo.octo.glose.actions.LoadBookshelvesAction
import com.rdo.octo.glose.repositories.LoadBookshelvesRepository
import com.rdo.octo.glose.state.GloseState
import me.tatarka.redux.Reducer

class MainReducer : Reducer<GloseAction, GloseState> {

    private val loadBookshelvesReducer =
        LoadBookshelvesReducer(LoadBookshelvesRepository.getInstance())

    override fun reduce(action: GloseAction, state: GloseState): GloseState {
        return when (action) {
            is LoadBookshelvesAction -> loadBookshelvesReducer.reduce(action, state)
            else -> TODO()
        }
    }
}

class LoadBookshelvesReducer(private val repository: LoadBookshelvesRepository) :
    Reducer<LoadBookshelvesAction, GloseState> {
    override fun reduce(action: LoadBookshelvesAction, state: GloseState): GloseState {
        return state.copy(bookShelves = repository.getBookshelves())
    }

}

data class BookShelve(
    val name: String,
    val description: String,
    val authorName: String,
    val authorUserName: String,
    val authorPictureUrl: String
)
