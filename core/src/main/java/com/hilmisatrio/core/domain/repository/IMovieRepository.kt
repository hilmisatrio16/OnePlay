package com.hilmisatrio.core.domain.repository

import com.hilmisatrio.core.data.Resource
import com.hilmisatrio.core.data.source.local.entity.MovieEntity
import com.hilmisatrio.core.domain.model.DetailMovie
import com.hilmisatrio.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getPopularMovies(): Flow<Resource<List<Movie>>>
    fun getTrendingMovies(): Flow<Resource<List<Movie>>>
    fun getTopRateMovies(): Flow<Resource<List<Movie>>>
    fun getDetailMovie(id: Int): Flow<Resource<DetailMovie>>
    fun getFavoriteMovie(): Flow<List<Movie>>
    fun getFavoriteMovieById(id: Int): Flow<Boolean>
    suspend fun insertMovie(movie: Movie)
    suspend fun deleteMovie(movie: Movie)
}