package com.example.indiancinema.viewmodel

import com.example.indiancinema.data.TmdbRepository
import com.example.indiancinema.model.CinemaMovie
import com.example.indiancinema.model.CinemaMoviePage
import com.example.indiancinema.model.toCinemaMovie

internal class IndianCinemaViewModel(
    private val repository: TmdbRepository,
) {
    suspend fun popular(language: String, page: Int): CinemaMoviePage {
        val response = repository.getPopular(language, page)
        return CinemaMoviePage(
            movies = response.results.mapNotNull { it.toCinemaMovie() },
            hasNext = response.page < response.totalPages,
        )
    }

    suspend fun search(query: String): List<CinemaMovie> =
        repository.search(query).results
            .asSequence()
            .filter { it.originalLanguage?.let(SUPPORTED_LANGUAGES::contains) == true }
            .mapNotNull { it.toCinemaMovie() }
            .distinctBy(CinemaMovie::id)
            .toList()

    suspend fun movie(id: Int): CinemaMovie =
        repository.getMovie(id).toCinemaMovie()
            ?: throw IllegalStateException("TMDB movie $id did not include a title.")

    private companion object {
        val SUPPORTED_LANGUAGES = setOf("te", "ta", "hi", "ml")
    }
}
