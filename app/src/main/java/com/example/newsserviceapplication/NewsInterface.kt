package com.example.newsserviceapplication

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

//https://newsapi.org/v2/top-headlines?country=us&apiKey=5b8baac87032430ca53b9b13772d8367


const val BASE_URL ="https://newsapi.org/v2/"
interface NewsInterface {

    @GET("top-headlines?apiKey=5b8baac87032430ca53b9b13772d8367")
    fun getHeadlines(
        @Query("country") country: String,
        @Query("page") page: Int
    ): Call<News>
}

object newsService {
    val newsInstance: NewsInterface
    init {
        val retrofit= Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build()
        newsInstance= retrofit.create(NewsInterface::class.java)
    }
}