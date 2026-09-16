package com.df.dicodingevent.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.df.dicodingevent.data.response.EventItem
import com.df.dicodingevent.data.response.EventResponse
import com.df.dicodingevent.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _upcomingEvents = MutableLiveData<List<EventItem>>()
    val upcomingEvents: LiveData<List<EventItem>> = _upcomingEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        findUpcomingEvents()
    }

    private fun findUpcomingEvents() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvents(active = 1, limit = 10)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                Log.d("HomeViewModel", "Response code: ${response.code()}")
                if (response.isSuccessful) {
                    Log.d("HomeViewModel", "Data: ${response.body()?.listEvents?.size} events")
                    _upcomingEvents.value = response.body()?.listEvents
                } else {
                    Log.e("HomeViewModel", "Error body: ${response.errorBody()?.string()}")
                    _errorMessage.value = "Gagal memuat data: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("HomeViewModel", "Failure: ${t.message}", t)
                _errorMessage.value = "Terjadi kesalahan: ${t.message}"
            }
        })
    }
}