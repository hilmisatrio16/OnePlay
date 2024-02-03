package com.hilmisatrio.oneplay.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hilmisatrio.core.data.Resource
import com.hilmisatrio.core.domain.model.Movie
import com.hilmisatrio.core.ui.ListMovieBannerAdapter
import com.hilmisatrio.core.ui.ListMovieLandscapeAdapter
import com.hilmisatrio.core.ui.ListMoviePortraitAdapter
import com.hilmisatrio.core.utils.ConstantValue.ID_MOVIE
import com.hilmisatrio.oneplay.R
import com.hilmisatrio.oneplay.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var listMoviePopularAdapter: ListMovieLandscapeAdapter
    private lateinit var listMovieTrendingAdapter: ListMoviePortraitAdapter
    private lateinit var listMovieTopRateAdapter: ListMovieBannerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        observerData()

        binding.btnFavorite.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
        }
    }

    private fun setAdapter() {
        listMovieTrendingAdapter = ListMoviePortraitAdapter {
            moveToDetailFragment(it)
        }

        with(binding.trendingList) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = listMovieTrendingAdapter
        }

        listMoviePopularAdapter = ListMovieLandscapeAdapter {
            moveToDetailFragment(it)
        }

        with(binding.populerList) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = listMoviePopularAdapter
        }

        listMovieTopRateAdapter = ListMovieBannerAdapter {
            moveToDetailFragment(it)
        }
        with(binding.topRateList) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = listMovieTopRateAdapter
        }

    }

    private fun moveToDetailFragment(movie: Movie) {
        val idBundle = Bundle().apply {
            putInt(ID_MOVIE, movie.id)
        }

        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, idBundle)
    }

    private fun observerData() {

        homeViewModel.moviePopular.observe(viewLifecycleOwner) { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> {
                        setLoading(binding.progressCircularPopular, binding.populerList, true)
                    }

                    is Resource.Success -> {
                        Log.i("HASIL", movies.data.toString())
                        setLoading(binding.progressCircularPopular, binding.populerList, false)
                        listMoviePopularAdapter.submitData(movies.data as ArrayList<Movie>)
                    }

                    is Resource.Error -> {
                        setLoading(binding.progressCircularPopular, binding.populerList, true)
                        Toast.makeText(context, movies.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                        Log.i("HASIL", movies.message.toString())
                    }
                }
            }
        }

        homeViewModel.movieTrending.observe(viewLifecycleOwner) { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> {
                        setLoading(binding.progressCircularTrending, binding.trendingList, true)
                    }

                    is Resource.Success -> {
                        setLoading(binding.progressCircularTrending, binding.trendingList, false)
                        listMovieTrendingAdapter.submitData(movies.data as ArrayList<Movie>)
                    }

                    is Resource.Error -> {
                        setLoading(binding.progressCircularTrending, binding.trendingList, true)
                        Toast.makeText(context, movies.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                        Log.i("HASIL", movies.message.toString())
                    }

                }
            }
        }

        homeViewModel.movieTopRate.observe(viewLifecycleOwner) { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> {
                        setLoading(binding.progressCircularToprate, binding.topRateList, true)
                    }

                    is Resource.Success -> {
                        setLoading(binding.progressCircularToprate, binding.topRateList, false)
                        listMovieTopRateAdapter.submitData(movies.data as ArrayList<Movie>)
                    }

                    is Resource.Error -> {
                        setLoading(binding.progressCircularToprate, binding.topRateList, true)
                        Toast.makeText(context, movies.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                        Log.i("HASIL", movies.message.toString())
                    }
                }
            }

        }
    }

    private fun setLoading(progress: ProgressBar, recycleview: RecyclerView, isLoad: Boolean) {
        if (isLoad) {
            progress.visibility = View.VISIBLE
            recycleview.visibility = View.GONE
        } else {
            progress.visibility = View.GONE
            recycleview.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}