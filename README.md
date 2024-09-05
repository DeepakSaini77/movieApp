# Movie App


## Features

*   Displays a list of trending movies.
*   Supports searching for movies.
*   Shows detailed information about each movie.
*   Implements Compose Navigation for navigation.
*   Handles loading, empty, and error states.

## Architecture

*   **Repository Pattern:**  Used for data access and abstraction.
*   **Jetpack Compose:**  Declarative UI framework for building modern Android interfaces.
*   **Compose Navigation:**  Handles navigation between screens.
*   **Retrofit:**  Used for making network requests to the TMDB API.

## Environment Variables

*   **TMDB\_API\_KEY:** Your TMDB API key is stored in a `.env` file and accessed using the `dotenv` library.

## Code Structure


## App Demo

## APK

## Getting Started

1.  Clone the repository.
2.  Create a `.env` file in the root directory and add your TMDB API key: `TMDB_API_KEY=your_api_key_here`
3.  Build and run the app.
