package com.hilmisatrio.core.data

import com.hilmisatrio.core.data.source.local.LocalDataSource
import com.hilmisatrio.core.data.source.remote.RemoteDataSource
import com.hilmisatrio.core.data.source.remote.network.ApiSourceResponse
import com.hilmisatrio.core.data.source.remote.response.DetailMovieResponse
import com.hilmisatrio.core.data.source.remote.response.ResultListMovie
import com.hilmisatrio.core.domain.model.DetailMovie
import com.hilmisatrio.core.domain.model.Movie
import com.hilmisatrio.core.domain.repository.IMovieRepository
import com.hilmisatrio.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IMovieRepository {
    override fun getPopularMovies(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<ResultListMovie>>() {
            override fun loadFromNetwork(data: List<ResultListMovie>): Flow<List<Movie>> =
                DataMapper.mapResponseToDomain(data)

            override suspend fun createCall(): Flow<ApiSourceResponse<List<ResultListMovie>>> =
                remoteDataSource.getPopularMovies()
        }.asFlow()

    override fun getTrendingMovies(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<ResultListMovie>>() {
            override fun loadFromNetwork(data: List<ResultListMovie>): Flow<List<Movie>> =
                DataMapper.mapResponseToDomain(data)

            override suspend fun createCall(): Flow<ApiSourceResponse<List<ResultListMovie>>> =
                remoteDataSource.getTrendingMovies()

        }.asFlow()

    override fun getTopRateMovies(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<ResultListMovie>>() {
            override fun loadFromNetwork(data: List<ResultListMovie>): Flow<List<Movie>> =
                DataMapper.mapResponseToDomain(data)

            override suspend fun createCall(): Flow<ApiSourceResponse<List<ResultListMovie>>> =
                remoteDataSource.getTopRateMovies()

        }.asFlow()

    override fun getDetailMovie(id: Int): Flow<Resource<DetailMovie>> =
        object : NetworkBoundResource<DetailMovie, DetailMovieResponse>() {
            override fun loadFromNetwork(data: DetailMovieResponse): Flow<DetailMovie> =
                DataMapper.mapDetailResponseToDomain(data)

            override suspend fun createCall(): Flow<ApiSourceResponse<DetailMovieResponse>> =
                remoteDataSource.getDetailMovie(id)

        }.asFlow()

    override fun getFavoriteMovie(): Flow<List<Movie>> =
        localDataSource.getMoviesFavorite().map {
            DataMapper.mapListEntityToDomain(it)
        }

    override fun getFavoriteMovieById(id: Int): Flow<Boolean> =
        localDataSource.getMovieFavoriteById(id)

    override suspend fun insertMovie(movie: Movie) =
        localDataSource.insertMovie(DataMapper.mapDomainToEntity(movie))

    override suspend fun deleteMovie(movie: Movie) =
        localDataSource.deleteMovie(DataMapper.mapDomainToEntity(movie))


}