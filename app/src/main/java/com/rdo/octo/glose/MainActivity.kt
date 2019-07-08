package com.rdo.octo.glose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.rdo.octo.glose.actions.LoadBookshelvesAction
import com.rdo.octo.glose.state.GloseState
import com.rdo.octo.glose.store.GloseStore
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import me.tatarka.redux.android.lifecycle.StoreViewModel
import me.tatarka.redux.rx2.FlowableAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var store: GloseStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        store = viewModel.store
        FlowableAdapter.flowable(store).subscribe {
            GlobalScope.launch(Dispatchers.Main) {
                val text = it.bookShelves.map {
                    "${it.name} : ${it.description}"
                }.joinToString("\n")
                totoTextView.text = text
            }
        }
        /*viewModel.state.observe(this, Observer<GloseState> {
            val text = it.bookShelves.map {
                "${it.name} : ${it.description}"
            }.joinToString("\n")
            totoTextView.text = text
        })*/
        store.dispatch(LoadBookshelvesAction)
    }
}

class MainViewModel : StoreViewModel<GloseState, GloseStore>(GloseStore(GloseState.INSTANCE))