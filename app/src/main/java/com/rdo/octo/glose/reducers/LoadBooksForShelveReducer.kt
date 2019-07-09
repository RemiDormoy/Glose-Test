package com.rdo.octo.glose.reducers

import com.rdo.octo.glose.actions.LoadBooksForShelveAction
import com.rdo.octo.glose.repositories.LoadBookDetailRepository
import com.rdo.octo.glose.repositories.LoadBookIdsRepository
import com.rdo.octo.glose.state.GloseState
import me.tatarka.redux.Reducer

class LoadBooksForShelveReducer(
    private val loadBookIdsRepository: LoadBookIdsRepository,
    private val loadBookDetailRepository: LoadBookDetailRepository
) : Reducer<LoadBooksForShelveAction, GloseState> {

    companion object {
        fun getInstance() = LoadBooksForShelveReducer(
            LoadBookIdsRepository.getInstance(),
            LoadBookDetailRepository.getInstance()
        )
    }

    override fun reduce(action: LoadBooksForShelveAction, state: GloseState): GloseState {
        val ids = loadBookIdsRepository.getBookIds(action.id)
        val books = ids.map {
            loadBookDetailRepository.getBookDetail(it)
        }
        return state.copy(shelvesBookIdMap = state.shelvesBookIdMap.plus(action.id to books))
    }
}
