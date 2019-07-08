package com.rdo.octo.glose.store

import com.rdo.octo.glose.actions.GloseAction
import com.rdo.octo.glose.reducers.MainReducer
import com.rdo.octo.glose.state.GloseState
import me.tatarka.redux.Dispatcher
import me.tatarka.redux.SimpleStore
import kotlin.concurrent.thread

class GloseStore(state: GloseState) : SimpleStore<GloseState>(state) {

    private val reducer = MainReducer()
    private val dispatcher = Dispatcher.forStore(this, reducer)

    fun dispatch(action: GloseAction) {
        thread {
            dispatcher.dispatch(action)
        }
    }
}