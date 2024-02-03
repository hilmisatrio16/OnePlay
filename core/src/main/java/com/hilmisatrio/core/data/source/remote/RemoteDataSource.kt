package com.hilmisatrio.core.data.source.remote

import android.util.Log
import com.hilmisatrio.core.data.source.remote.network.ApiService
import com.hilmisatrio.core.data.source.remote.network.ApiSourceResponse
import com.hilmisatrio.core.data.source.remote.response.DetailMovieResponse
import com.hilmisatrio.core.data.source.remote.response.ResultListMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getPopularMovies(): Flow<ApiSourceResponse<List<ResultListMovie>>> {
        return flow {
            try {
                val response = apiService.getListPopular()
                val resultMovies = response.results

                if (resultMovies.isNotEmpty()) {
                    emit(ApiSourceResponse.Success(response.results))
                } else {
                    emit(ApiSourceResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiSourceResponse.Error(e.toString()))
                Log.e("REMOTE_DATA_SOURCE", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTopRateMovies(): Flow<ApiSourceResponse<List<ResultListMovie>>> {
        return flow {
            try {
                val response = apiService.getListTopRate()
                val resultMovies = response.results

                if (resultMovies.isNotEmpty()) {
                    emit(ApiSourceResponse.Success(response.results))
                } else {
                    emit(ApiSourceResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiSourceResponse.Error(e.toString()))
                Log.e("REMOTE_DATA_SOURCE", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTrendingMovies(): Flow<ApiSourceResponse<List<ResultListMovie>>> {
        return flow {
            try {
                val response = apiService.getListTrending()
                val resultMovies = response.results

                if (resultMovies.isNotEmpty()) {
                    emit(ApiSourceResponse.Success(response.results))
                } else {
                    emit(ApiSourceResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiSourceResponse.Error(e.toString()))
                Log.e("REMOTE_DATA_SOURCE", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailMovie(id: Int): Flow<ApiSourceResponse<DetailMovieResponse>> {
        return flow {
            try {
                val response = apiService.getDetailMovie(id)
                emit(ApiSourceResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiSourceResponse.Error(e.toString()))
                Log.e("REMOTE_DATA_SOURCE", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}