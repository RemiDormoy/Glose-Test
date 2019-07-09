package com.rdo.octo.glose.repositories

import com.rdo.octo.glose.GloseApplication
import com.rdo.octo.glose.entities.Book
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class LoadBookDetailRepository(private val service: BookDetailService) {

    companion object {

        fun getInstance() =
            LoadBookDetailRepository(
                getRetrofit().create(
                    BookDetailService::class.java
                )
            )

        fun getRetrofit() = Retrofit.Builder()
            .baseUrl("https://api.glose.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(ChuckInterceptor(GloseApplication.CONTEXT))
                    .build()
            )
            .build()
    }

    interface BookDetailService {
        @GET("/forms/{id}")
        fun getBook(@Path("id") id: String): Call<BookResponse>

        data class BookResponse(
            val id: String,
            val title: String,
            val image: String?,
            val authors: List<Author>?
        )

        data class Author(
            val name: String
        )
    }

    fun getBookDetail(bookId: String): Book {
        val response = service.getBook(bookId).execute().body()
            ?: throw   IllegalStateException("No book for this id : $bookId")
        return Book(
            id = response.id,
            name = response.title,
            imageUrl = response.image,
            authorName = response.authors?.joinToString(", ") { author -> author.name }
        )
    }
}

