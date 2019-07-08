package com.rdo.octo.glose.repositories

import com.rdo.octo.glose.reducers.BookShelve
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


class LoadBookshelvesRepository (private val service: Service) {

    interface Service {
        @GET("/users/5a8411b53ed02c04187ff02a/shelves")
        fun getShelves(): Call<List<ResponseObject>>
    }

    data class ResponseObject(
        val slug: String,
        val title: String
    )

    companion object {
        private fun getRetrofit() = Retrofit.Builder()
            .baseUrl("https://api.glose.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun getInstance() = LoadBookshelvesRepository(getRetrofit().create(Service::class.java))

    }

    fun getBookshelves(): List<BookShelve> {
        return service.getShelves().execute().body()?.map {
            BookShelve(it.slug, it.title)
        } ?: throw IllegalStateException("shelves list empty")
    }
}