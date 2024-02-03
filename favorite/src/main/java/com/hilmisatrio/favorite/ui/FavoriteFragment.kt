package com.hilmisatrio.favorite.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hilmisatrio.core.domain.model.Movie
import com.hilmisatrio.core.ui.ListMovieLandscapeAdapter
import com.hilmisatrio.core.utils.ConstantValue.ID_MOVIE
import com.hilmisatrio.favorite.databinding.FragmentFavoriteBinding
import com.hilmisatrio.favorite.di.DaggerFavoriteComponent
import com.hilmisatrio.oneplay.di.FavoriteModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        factory
    }
    private lateinit var listFavoriteAdapter: ListMovieLandscapeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireContext(),
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        observerData()

        setLoading(binding.progressCircularFavorite, binding.favoriteList, true)

        binding.toolBar.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observerData() {
        favoriteViewModel.getMovie.observe(viewLifecycleOwner) { movies ->
            if (movies != null) {
                listFavoriteAdapter.submitData(movies as ArrayList<Movie>)
                setLoading(binding.progressCircularFavorite, binding.favoriteList, false)
                Log.i("HASIL DB", movies.toString())
            }
        }
    }

    private fun setAdapter() {
        listFavoriteAdapter = ListMovieLandscapeAdapter {
            moveToDetail(it)
        }

        with(binding.favoriteList) {
            layoutManager = LinearLayoutManager(
                context
            )
            adapter = listFavoriteAdapter
        }
    }

    private fun moveToDetail(dataMovie: Movie) {
        val idBundle = Bundle().apply {
            putInt(ID_MOVIE, dataMovie.id)
        }

        findNavController().navigate(
            com.hilmisatrio.oneplay.R.id.action_favoriteFragment_to_detailFragment,
            idBundle
        )
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