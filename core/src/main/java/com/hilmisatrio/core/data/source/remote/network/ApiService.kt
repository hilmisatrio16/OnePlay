package com.hilmisatrio.core.data.source.remote.network

import com.hilmisatrio.core.data.source.remote.response.DetailMovieResponse
import com.hilmisatrio.core.data.source.remote.response.ListMovieResponse
import com.hilmisatrio.core.utils.ConstantValue.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/popular?language=en-US&page=1&api_key=$API_KEY")
    suspend fun getListPopular(
    ): ListMovieResponse

    @GET("movie/top_rated?language=en-US&page=1&api_key=$API_KEY")
    suspend fun getListTopRate(
    ): ListMovieResponse

    @GET("trending/movie/day?language=en-US&api_key=$API_KEY")
    suspend fun getListTrending(
    ): ListMovieResponse

    @GET("movie/{id}?language=en-US&api_key=$API_KEY")
    suspend fun getDetailMovie(
        @Path("id") id: Int
    ): DetailMovieResponse
}