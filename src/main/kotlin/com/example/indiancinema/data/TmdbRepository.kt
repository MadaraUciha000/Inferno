package com.example.indiancinema.data

import android.net.Uri
import com.example.indiancinema.TmdbSettings
import com.lagradost.cloudstream3.app
import com.lagradost.cloudstream3.utils.parsedSafe

internal class TmdbRepository(
    private val settings: TmdbSettings,
) {
    suspend fun getPopular(language: String, page: Int): TmdbMoviePage =
        get(
            endpoint = "discover/movie",
            parameters = mapOf(
                "include_adult" to "false",
                "language" to "en-US",
                "page" to page.toString(),
                "region" to "IN",
                "sort_by" to "popularity.desc",
                "with_original_language" to language,
            ),
        )

    suspend fun search(query: String): TmdbMoviePage =
        get(
            endpoint = "search/movie",
            parameters = mapOf(
                "include_adult" to "false",
                "language" to "en-US",
                "page" to "1",
                "query" to query,
                "region" to "IN",
            ),
        )

    suspend fun getMovie(id: Int): TmdbMovie =
        get(
            endpoint = "movie/$id",
            parameters = mapOf("language" to "en-US"),
        )

    private suspend inline fun <reified T : Any> get(
        endpoint: String,
        parameters: Map<String, String>,
    ): T {
        val apiKey = settings.apiKey()
        val url = Uri.parse("$API_BASE/$endpoint")
            .buildUpon()
            .appendQueryParameter("api_key", apiKey)
            .also { builder ->
                parameters.forEach { (key, value) ->
                    builder.appendQueryParameter(key, value)
                }
            }
            .build()
            .toString()

        return app.get(url).parsedSafe<T>()
            ?: throw IllegalStateException("TMDB returned an invalid response for $endpoint.")
    }

    private companion object {
        const val API_BASE = "https://api.themoviedb.org/3"
    }
}
