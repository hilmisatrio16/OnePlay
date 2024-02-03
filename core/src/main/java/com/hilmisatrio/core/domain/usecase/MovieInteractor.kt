package com.hilmisatrio.core.domain.usecase

import com.hilmisatrio.core.data.Resource
import com.hilmisatrio.core.domain.model.DetailMovie
import com.hilmisatrio.core.domain.model.Movie
import com.hilmisatrio.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: IMovieRepository) :
    MovieUseCase {
    override fun getMoviePopular(): Flow<Resource<List<Movie>>> {
        return movieRepository.getPopularMovies()
    }

    override fun getMovieTrending(): Flow<Resource<List<Movie>>> {
        return movieRepository.getTrendingMovies()
    }

    override fun getMovieTopRate(): Flow<Resource<List<Movie>>> {
        return movieRepository.getTopRateMovies()
    }

    override fun getDetailMovie(id: Int): Flow<Resource<DetailMovie>> {
        return movieRepository.getDetailMovie(id)
    }

    override fun getMoviesFavorite(): Flow<List<Movie>> {
        return movieRepository.getFavoriteMovie()
    }

    override fun getMoviesFavoriteById(id: Int): Flow<Boolean> {
        return movieRepository.getFavoriteMovieById(id)
    }

    override suspend fun insertMovie(movie: Movie) {
        movieRepository.insertMovie(movie)
    }

    override suspend fun deleteMovie(movie: Movie) {
        movieRepository.deleteMovie(movie)
    }
}