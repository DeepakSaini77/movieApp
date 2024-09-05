package com.example.movieapp.ui

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.movieapp.data.model.Environment
import com.example.movieapp.data.model.Movie
import com.example.movieapp.viewmodel.MovieViewModel
import com.example.movieapp.ui.utils.noResultFound
import com.example.movieapp.ui.utils.searchMovieTxt
import com.example.movieapp.ui.utils.searchTxt
import com.example.movieapp.ui.utils.trendingMovies

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieListScreen(navController: NavController, viewModel: MovieViewModel) {
    val movies by viewModel.movies.collectAsState()
    var query by remember { mutableStateOf("") }

    val searchMovies by viewModel.searchMovies.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState().value

    val toShow = if (query.isEmpty()) movies else searchMovies



    Scaffold(
        backgroundColor = Color.White,
        topBar = {
            Spacer(modifier = Modifier.height(8.dp))
        }
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            OutlinedTextField(
                value = query,
                onValueChange = { newText ->
                    query = newText
                    viewModel.searchMovies(query)
                },
                placeholder = { Row {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = searchTxt,
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(searchMovieTxt, color = Color.Gray)
                }  },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFFFFF), shape = MaterialTheme.shapes.large),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color.Transparent,
                    textColor = Color.Black,
                ),
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.body1.fontSize,
                )

            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (query.isEmpty()) trendingMovies else "Result for $query:",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (isLoading) {
                    CustomLoadingIndicator()
            }
            else {
                if (toShow.isEmpty()) {
                    Spacer(modifier = Modifier.height(18.dp))
                    Text(
                        text = noResultFound,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(toShow.chunked(2)) { rowMovies ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                rowMovies.forEach { movie ->
                                    MovieItem(movie) {
                                        navController.navigate("movie_detail/${movie.id}")
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun MovieItem(movie: Movie, onClick: () -> Unit) {
    val tmdbImageBaseUrl = Environment.imageBaseUrl
    Column(
        modifier = Modifier
            .width(160.dp)
            .clickable(onClick = onClick)
    ) {

        Image(
            painter = rememberImagePainter("$tmdbImageBaseUrl${movie.poster_path}"),
            contentDescription = movie.title,
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 4.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = movie.title,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview
@Composable
fun PreviewMovieItem() {
    val movie = Movie(1, "Movie", "This is a sample movie description.", null)
    MovieItem(movie, {})
}



@Composable
fun CustomLoadingIndicator() {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        CircularProgressIndicator(
            color = Color.Gray,
            strokeWidth = 4.dp,
            modifier = Modifier.rotate(rotation)
        )
    }
}