package com.example.indiancinema.model

import com.example.indiancinema.data.TmdbMovie

internal data class CinemaMovie(
    val id: Int,
    val title: String,
    val overview: String?,
    val posterUrl: String?,
    val backdropUrl: String?,
    val year: Int?,
    val originalLanguage: String?,
    val genres: List<String>,
)

internal data class CinemaMoviePage(
    val movies: List<CinemaMovie>,
    val hasNext: Boolean,
)

internal fun TmdbMovie.toCinemaMovie(): CinemaMovie? {
    val displayTitle = title?.takeIf(String::isNotBlank)
        ?: originalTitle?.takeIf(String::isNotBlank)
        ?: return null

    return CinemaMovie(
        id = id,
        title = displayTitle,
        overview = overview?.takeIf(String::isNotBlank),
        posterUrl = posterPath?.let { "$IMAGE_BASE/w500$it" },
        backdropUrl = backdropPath?.let { "$IMAGE_BASE/w1280$it" },
        year = releaseDate?.take(4)?.toIntOrNull(),
        originalLanguage = originalLanguage,
        genres = genres.map(TmdbGenre::name),
    )
}

private const val IMAGE_BASE = "https://image.tmdb.org/t/p"
