package com.hilmisatrio.core.utils


import com.hilmisatrio.core.data.source.local.entity.MovieEntity
import com.hilmisatrio.core.data.source.remote.response.DetailMovieResponse
import com.hilmisatrio.core.data.source.remote.response.ResultListMovie
import com.hilmisatrio.core.domain.model.DetailMovie
import com.hilmisatrio.core.domain.model.GenreMovie
import com.hilmisatrio.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


object DataMapper {

    fun mapResponseToDomain(movieResponse: List<ResultListMovie>): Flow<List<Movie>> {
        val listMovie = ArrayList<Movie>()
        movieResponse.map {
            val movie = Movie(
                it.adult,
                ConstantValue.PATH_URL_IMG + it.backdropPath,
                it.genreIds,
                it.id,
                it.originalLanguage,
                it.originalTitle,
                it.overview,
                it.popularity,
                ConstantValue.PATH_URL_IMG + it.posterPath,
                it.releaseDate,
                it.title,
                it.video,
                it.voteAverage,
                it.voteCount
            )
            listMovie.add(movie)
        }
        return flowOf(listMovie)
    }

    fun mapDomainToEntity(movie: Movie): MovieEntity {
        return MovieEntity(
            movie.id,
            movie.adult,
            ConstantValue.PATH_URL_IMG + movie.backdropPath,
            movie.originalLanguage,
            movie.originalTitle,
            movie.overview,
            movie.popularity,
            ConstantValue.PATH_URL_IMG + movie.posterPath,
            movie.releaseDate,
            movie.title,
            movie.video,
            movie.voteAverage,
            movie.voteCount
        )
    }

    fun mapListEntityToDomain(movie: List<MovieEntity>): List<Movie> {
        val listMovie = ArrayList<Movie>()

        movie.map {
            val movie = Movie(
                it.adult,
                ConstantValue.PATH_URL_IMG + it.backdropPath,
                listOf(),
                it.id,
                it.originalLanguage,
                it.originalTitle,
                it.overview,
                it.popularity,
                ConstantValue.PATH_URL_IMG + it.posterPath,
                it.releaseDate,
                it.title,
                it.video,
                it.voteAverage,
                it.voteCount
            )
            listMovie.add(movie)
        }

        return listMovie
    }

    fun mapDetailDomainToMovie(movie: DetailMovie): Movie {

        return Movie(
            movie.adult,
            ConstantValue.PATH_URL_IMG + movie.backdropPath,
            listOf(),
            movie.id,
            movie.originalLanguage,
            movie.originalTitle,
            movie.overview,
            movie.popularity,
            ConstantValue.PATH_URL_IMG + movie.posterPath,
            movie.releaseDate,
            movie.title,
            movie.video,
            movie.voteAverage,
            movie.voteCount
        )
    }

    fun mapDetailResponseToDomain(detailMovieResponse: DetailMovieResponse): Flow<DetailMovie> {
        return flowOf(
            DetailMovie(
                detailMovieResponse.adult,
                detailMovieResponse.backdropPath,
                detailMovieResponse.budget,
                detailMovieResponse.genres.map {
                    GenreMovie(it.id, it.name)
                },
                detailMovieResponse.homepage,
                detailMovieResponse.id,
                detailMovieResponse.imdbId,
                detailMovieResponse.originalLanguage,
                detailMovieResponse.originalTitle,
                detailMovieResponse.overview,
                detailMovieResponse.popularity,
                ConstantValue.PATH_URL_IMG + detailMovieResponse.posterPath,
                detailMovieResponse.releaseDate,
                detailMovieResponse.revenue,
                detailMovieResponse.runtime,
                detailMovieResponse.status,
                detailMovieResponse.tagline,
                detailMovieResponse.title,
                detailMovieResponse.video,
                detailMovieResponse.voteAverage,
                detailMovieResponse.voteCount
            )
        )
    }
}