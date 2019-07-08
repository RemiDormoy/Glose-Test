package com.rdo.octo.glose.reducers

import com.rdo.octo.glose.actions.LoadBookshelvesAction
import com.rdo.octo.glose.repositories.LoadBookshelvesRepository
import com.rdo.octo.glose.state.GloseState
import me.tatarka.redux.Reducer

class LoadBookshelvesReducer(private val repository: LoadBookshelvesRepository) :
    Reducer<LoadBookshelvesAction, GloseState> {

    companion object {
        fun getInstance() = LoadBookshelvesReducer(LoadBookshelvesRepository.getInstance())
    }

    override fun reduce(action: LoadBookshelvesAction, state: GloseState): GloseState {
        return state.copy(bookShelves = repository.getBookshelves())
    }

}