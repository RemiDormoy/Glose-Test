package com.rdo.octo.glose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.rdo.octo.glose.actions.LoadBookshelvesAction
import com.rdo.octo.glose.state.GloseState
import com.rdo.octo.glose.store.GloseStore
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import me.tatarka.redux.android.lifecycle.StoreViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var store: GloseStore
    private val adapter: ShelvesAdapter by lazy {
        ShelvesAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shelvesRecyclerView.layoutManager = LinearLayoutManager(this)
        shelvesRecyclerView.adapter = adapter
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        store = viewModel.store
        store.subscribeTo { displayShelves(it) }
        store.dispatch(LoadBookshelvesAction)
    }

    private fun displayShelves(it: GloseState) {
        adapter.setShelves(it.bookShelves)
    }
}

class MainViewModel : StoreViewModel<GloseState, GloseStore>(GloseStore(GloseState.INSTANCE))

fun <S> Flowable<S>.subscribeOnMainThread(action: (S) -> Unit) {
    this.subscribe {
        GlobalScope.launch(Dispatchers.Main) {
            action(it)
        }
    }
}

