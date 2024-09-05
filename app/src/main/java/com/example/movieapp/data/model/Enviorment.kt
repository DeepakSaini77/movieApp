
package com.example.movieapp.data.model

import android.content.Context
import java.io.IOException
import java.util.*

object Environment {

    private var properties: Properties = Properties()

    fun initialize(context: Context, envType: String = "development") {
        val fileName = if (envType == "development") "env.development" else "env.production"
        try {
            context.assets.open(fileName).use { inputStream ->
                properties.load(inputStream)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            throw RuntimeException("Error loading $fileName file from assets folder")
        }
    }

    val apiUrl: String
        get() = properties.getProperty("TMDB_BASE_URL")
            ?: throw RuntimeException("TMDB_BASE_URL not found in environment file")

    val apiKey: String
        get() = properties.getProperty("TMDB_API_KEY")
            ?: throw RuntimeException("TMDB_API_KEY not found in environment file")

    val imageBaseUrl: String
        get() = properties.getProperty("TMDB_IMAGE_BASE_URL")
            ?: throw RuntimeException("TMDB_IMAGE_BASE_URL not found in environment file")
}
