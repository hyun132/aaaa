package com.example.locationfinder.api

import com.example.locationfinder.models.McKakaoSearchResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * McApi
 */
interface McApi {
    @GET("/v2/local/search/keyword.json")
    suspend fun searchLocation(
        @Query("query")
        query: String,

        @Query("page")
        display: Int = 1,

        @Query("size")
        size: Int = 15,

        @Query("y")
        posY: Double,

        @Query("x")
        posX: Double,

        @Query("radius")
        radius: Int = 1000,
    ): Response<McKakaoSearchResponseDto>
}