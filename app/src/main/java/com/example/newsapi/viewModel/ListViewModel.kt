package com.example.newsapi.viewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapi.data.api.ApiConfig
import com.example.newsapi.data.response.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListViewModel : ViewModel() {

    private val _newsData = MutableLiveData<NewsResponse>()
    val data: LiveData<NewsResponse> get() = _newsData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getNewsData(query: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getNews(query)
        client.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _newsData.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: Gagal")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

}