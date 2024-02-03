package com.hilmisatrio.oneplay.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.hilmisatrio.core.data.Resource
import com.hilmisatrio.core.domain.model.DetailMovie
import com.hilmisatrio.core.domain.model.GenreMovie
import com.hilmisatrio.core.domain.model.Movie
import com.hilmisatrio.core.ui.ListGenreAdapter
import com.hilmisatrio.core.utils.ConstantValue.ID_MOVIE
import com.hilmisatrio.core.utils.DataMapper
import com.hilmisatrio.oneplay.R
import com.hilmisatrio.oneplay.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel: DetailViewModel by viewModels()
    private var dataMovie: Movie? = null
    private var isFavoriteMovie by Delegates.notNull<Boolean>()
    private lateinit var listGenreAdapter: ListGenreAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getIdMovie = arguments?.getInt(ID_MOVIE)
        if (getIdMovie != null) {
            observerData(getIdMovie)
        }

        setMenuFavorite(getIdMovie)
        setAdapter()

        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.buttonFavorite.setOnClickListener {
            setFavorite()
        }

    }

    private fun setAdapter() {
        listGenreAdapter = ListGenreAdapter()

        with(binding.listGenre){
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = listGenreAdapter
        }
    }

    private fun setMenuFavorite(idMovie: Int?) {
        if (idMovie != null) {
            detailViewModel.getMoviesFavoriteById(idMovie).observe(viewLifecycleOwner) {
                if (it != null) {
                    isFavoriteMovie = it
                    setImageButtonFavorite(it)
                }
            }
        }
    }

    private fun setImageButtonFavorite(isFavorite: Boolean) {
        with(binding.buttonFavorite) {
            if (isFavorite) {
                setImageResource(R.drawable.ic_favorite_fill)
            } else {
                setImageResource(R.drawable.ic_favorite)
            }
        }
    }

    private fun setFavorite() {
        isFavoriteMovie = if (!isFavoriteMovie) {
            dataMovie?.let { detailViewModel.insertMovieFavorite(movie = it) }
            setImageButtonFavorite(false)
            false
        } else {
            dataMovie?.let { detailViewModel.deleteMovie(it) }
            setImageButtonFavorite(true)
            true
        }
    }

    private fun observerData(id: Int) {
        detailViewModel.detailMovie(id).observe(viewLifecycleOwner) { movie ->
            if (movie != null) {
                when (movie) {
                    is Resource.Loading -> {
                        setLoading(binding.progressCircularDetail, binding.contentLayout, true)
                    }

                    is Resource.Success -> {
                        movie.data?.let { setValueOnView(it) }
                        listGenreAdapter.submitData(movie.data?.genres as ArrayList<GenreMovie>)
                        dataMovie = movie.data?.let { DataMapper.mapDetailDomainToMovie(it) }
                        setLoading(binding.progressCircularDetail, binding.contentLayout, false)
                    }

                    is Resource.Error -> {
                        setLoading(binding.progressCircularDetail, binding.contentLayout, true)
                        Toast.makeText(context, movie.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                        Log.i("HASIL", movie.message.toString())
                    }
                }
            }
        }
    }

    private fun setValueOnView(movie: DetailMovie) {
        with(binding) {
            Glide.with(requireContext())
                .load(movie.posterPath)
                .into(imageFilm)
            tvTitleFilm.text = movie.title
            tvDateRelease.text = movie.releaseDate
            popularity.text = movie.popularity.toString()
            tvRateFilm.text = String.format("%.1f", movie.voteAverage).toDouble().toString()
            vote.text = movie.voteCount.toString()
            tvOverview.text = movie.overview

        }
    }

    private fun setLoading(progress: ProgressBar, layoutScrollView: ScrollView, isLoad: Boolean) {
        if (isLoad) {
            progress.visibility = View.VISIBLE
            layoutScrollView.visibility = View.GONE
        } else {
            progress.visibility = View.GONE
            layoutScrollView.visibility = View.VISIBLE
        }
    }

}