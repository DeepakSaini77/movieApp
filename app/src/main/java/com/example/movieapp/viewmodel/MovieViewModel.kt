package com.example.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.network.RetrofitClient.apiService
import com.example.movieapp.ui.utils.searchMovieFetchingError
import com.example.movieapp.ui.utils.trendingMovieFetchingError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    private val _selectedMovie = MutableStateFlow<List<Movie>>(emptyList())
    val searchMovies: StateFlow<List<Movie>> = _selectedMovie


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchTrendingMovies()
    }

    private fun fetchTrendingMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.getTrendingMovies()
                _movies.value = response.results
            } catch (e: Exception) {
                error(message = trendingMovieFetchingError)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            _isLoading.value=true
            try{
                val response = apiService.searchMovies(query)
                _selectedMovie.value = response.results
            } catch (e:Exception){
                error(message = "$searchMovieFetchingError$query")
            } finally {
                _isLoading.value=false;
            }


        }
    }

    fun getMovieById(movieId: Int): Movie? {
        val movieInMovies = movies.value.find { it.id == movieId }

        return movieInMovies ?: searchMovies.value.find { it.id == movieId }

    }
}
