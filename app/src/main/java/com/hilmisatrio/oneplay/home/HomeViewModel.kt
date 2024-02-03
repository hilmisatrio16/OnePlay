package com.hilmisatrio.oneplay.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.hilmisatrio.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(movieUseCase: MovieUseCase) : ViewModel() {
    val moviePopular = movieUseCase.getMoviePopular().asLiveData()
    val movieTrending = movieUseCase.getMovieTrending().asLiveData()
    val movieTopRate = movieUseCase.getMovieTopRate().asLiveData()
}