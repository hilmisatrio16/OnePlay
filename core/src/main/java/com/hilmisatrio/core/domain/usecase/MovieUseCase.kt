package com.hilmisatrio.core.domain.usecase

import com.hilmisatrio.core.data.Resource
import com.hilmisatrio.core.domain.model.DetailMovie
import com.hilmisatrio.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getMoviePopular(): Flow<Resource<List<Movie>>>
    fun getMovieTrending(): Flow<Resource<List<Movie>>>
    fun getMovieTopRate(): Flow<Resource<List<Movie>>>
    fun getDetailMovie(id: Int): Flow<Resource<DetailMovie>>
    fun getMoviesFavorite(): Flow<List<Movie>>
    fun getMoviesFavoriteById(id: Int): Flow<Boolean>
    suspend fun insertMovie(movie: Movie)
    suspend fun deleteMovie(movie: Movie)
}