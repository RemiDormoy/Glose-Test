package com.rdo.octo.glose.reducers

import com.rdo.octo.glose.actions.GloseAction
import com.rdo.octo.glose.actions.LoadBooksForShelveAction
import com.rdo.octo.glose.actions.LoadBookshelvesAction
import com.rdo.octo.glose.state.GloseState
import me.tatarka.redux.Reducer

class MainReducer : Reducer<GloseAction, GloseState> {

    private val loadBookshelvesReducer = LoadBookshelvesReducer.getInstance()
    private val loadBooksForShelveReducer = LoadBooksForShelveReducer.getInstance()

    override fun reduce(action: GloseAction, state: GloseState): GloseState {
        return when (action) {
            is LoadBookshelvesAction -> loadBookshelvesReducer.reduce(action, state)
            is LoadBooksForShelveAction -> loadBooksForShelveReducer.reduce(action, state)
            else -> TODO()
        }
    }
}

