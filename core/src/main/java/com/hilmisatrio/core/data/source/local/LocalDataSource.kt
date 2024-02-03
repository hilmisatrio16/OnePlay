package com.hilmisatrio.core.data.source.local

import com.hilmisatrio.core.data.source.local.entity.MovieEntity
import com.hilmisatrio.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val movieDao: MovieDao) {
    fun getMoviesFavorite(): Flow<List<MovieEntity>> = movieDao.getMovies()

    fun getMovieFavoriteById(id: Int): Flow<Boolean> = movieDao.getMoviesById(id)

    suspend fun insertMovie(movie: MovieEntity) = movieDao.insertMovieFavorite(movie)

    suspend fun deleteMovie(movie: MovieEntity) = movieDao.deleteMovieFavorite(movie)
}