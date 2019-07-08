package com.rdo.octo.glose.repositories

import com.rdo.octo.glose.entities.BookShelve
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
        val id: String,
        val slug: String,
        val title: String,
        val user: User
    )

    data class User(
        val image: String,
        val username: String,
        val name: String
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
            BookShelve(
                id = it.id,
                name = it.slug,
                description = it.title,
                authorName = it.user.name,
                authorUserName = it.user.username,
                authorPictureUrl = it.user.image
            )
        } ?: throw IllegalStateException("shelves list empty")
    }
}