package com.df.dicodingevent.data.retrofit

import com.df.dicodingevent.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("events")
    fun getEvents(
        @Query("active") active: Int = -1,
        @Query("q") query: String? = null,
        @Query("limit") limit: Int? = null
    ): Call<EventResponse>
}