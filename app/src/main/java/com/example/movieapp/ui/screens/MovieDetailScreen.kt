package com.example.movieapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.movieapp.data.model.Environment
import com.example.movieapp.data.model.Movie
import com.example.movieapp.ui.utils.backTxt
import com.example.movieapp.viewmodel.MovieViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieDetailScreen(navController: NavController, movieId: Int, viewModel: MovieViewModel) {
    val movie = viewModel.getMovieById(movieId)
    val tmdbImageBaseUrl = Environment.imageBaseUrl
    Scaffold(
        backgroundColor = Color.White,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = backTxt,
                            tint = Color.Gray
                        )
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        }
    ) {
        movie?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = rememberImagePainter("$tmdbImageBaseUrl/${movie.poster_path}"),
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = movie.title,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    style = MaterialTheme.typography.h5
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = movie.overview,
                    color = Color.Black,
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


@Preview
@Composable
fun PreviewMovieDetailScreen() {
    val movie = Movie(1, "Movie", "This is a sample movie description.", null)
    MovieDetailScreen(navController = rememberNavController() , movie.id, MovieViewModel())
}
