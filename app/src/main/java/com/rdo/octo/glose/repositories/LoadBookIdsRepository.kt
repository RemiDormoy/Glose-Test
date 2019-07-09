package com.rdo.octo.glose.repositories

import com.rdo.octo.glose.GloseApplication
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class LoadBookIdsRepository(private val service: BookIdsService) {

    companion object {

        fun getInstance() =
            LoadBookIdsRepository(
                getRetrofit().create(
                    BookIdsService::class.java
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

    interface BookIdsService {
        @GET("/shelves/{id}/forms")
        fun getBookIds(@Path("id") id: String): Call<List<String>>
    }

    fun getBookIds(shelveId: String) = service.getBookIds(shelveId).execute().body()
        ?: throw   IllegalStateException("No book for this id : $shelveId")
}