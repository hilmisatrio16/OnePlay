package com.hilmisatrio.oneplay.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hilmisatrio.core.data.Resource
import com.hilmisatrio.core.domain.model.DetailMovie
import com.hilmisatrio.core.domain.model.Movie
import com.hilmisatrio.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun detailMovie(id: Int): LiveData<Resource<DetailMovie>> =
        movieUseCase.getDetailMovie(id).asLiveData()

    fun getMoviesFavoriteById(id: Int) = movieUseCase.getMoviesFavoriteById(id).asLiveData()

    fun insertMovieFavorite(movie: Movie) = viewModelScope.launch {
        movieUseCase.insertMovie(movie)
    }

    fun deleteMovie(movie: Movie) = viewModelScope.launch {
        movieUseCase.deleteMovie(movie)
    }
}