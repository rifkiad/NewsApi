package com.example.newsapi.data.api

import com.example.newsapi.data.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @GET("everything")
    @Headers("Authorization: token a874f38a75a9483bb99f96a22d043073")
    fun getNews(
        @Query("q") q: String
    ): Call<NewsResponse>
}
