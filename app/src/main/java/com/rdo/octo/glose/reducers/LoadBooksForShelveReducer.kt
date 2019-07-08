package com.rdo.octo.glose.reducers

import com.rdo.octo.glose.actions.LoadBooksForShelveAction
import com.rdo.octo.glose.entities.Book
import com.rdo.octo.glose.state.GloseState
import me.tatarka.redux.Reducer
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class LoadBooksForShelveReducer(
    private val loadBookIdsService: LoadBookService
) : Reducer<LoadBooksForShelveAction, GloseState> {

    companion object {
        fun getInstance() = LoadBooksForShelveReducer(
            getRetrofit().create(LoadBookService::class.java)
        )

        fun getRetrofit() = Retrofit.Builder()
            .baseUrl("https://api.glose.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    interface LoadBookService {

        @GET("/forms/{id}")
        fun getBook(@Path("id") id: String): Call<BookResponse>

        @GET("/shelves/{id}/forms")
        fun getBookIds(@Path("id") id: String): Call<List<String>>

        data class BookResponse(
            val id: String,
            val title: String,
            val image: String?,
            val authors: Author?
        )

        data class Author(
            val name: String
        )
    }


    override fun reduce(action: LoadBooksForShelveAction, state: GloseState): GloseState {
        val ids = loadBookIdsService.getBookIds(action.id).execute().body()
            ?: throw IllegalStateException("No ids for this shelve : ${action.id}")
        val books = ids.map {
            val bookResponse = loadBookIdsService.getBook(it).execute().body()
                ?: throw   IllegalStateException("No book for this id : $it")
            Book(
                id = bookResponse.id,
                name = bookResponse.title,
                imageUrl = bookResponse.image,
                authorName = bookResponse.authors?.name
            )
        }
        return state.copy(shelvesBookIdMap = state.shelvesBookIdMap.plus(action.id to books))
    }
}