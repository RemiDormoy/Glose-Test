package com.rdo.octo.glose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.rdo.octo.glose.actions.LoadBooksForShelveAction
import com.rdo.octo.glose.actions.LoadBookshelvesAction
import com.rdo.octo.glose.entities.Book
import com.rdo.octo.glose.entities.BookShelve
import com.rdo.octo.glose.state.GloseState
import com.rdo.octo.glose.store.GloseStore
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
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

    private fun displayShelves(state: GloseState) {
        val viewModels = state.bookShelves.map {
            val books = state.shelvesBookIdMap[it.id]
            if (books == null) store.dispatch(LoadBooksForShelveAction(it.id))
            ShelveViewModel(it, books)
        }
        adapter.setShelves(viewModels)
    }
}

data class ShelveViewModel(
    val bookShelve: BookShelve,
    val books: List<Book>?
)

class MainViewModel : StoreViewModel<GloseState, GloseStore>(GloseStore(GloseState.INSTANCE))

fun <S> Flowable<S>.subscribeOnMainThread(action: (S) -> Unit): Disposable {
    return this.subscribe {
        GlobalScope.launch(Dispatchers.Main) {
            action(it)
        }
    }
}

