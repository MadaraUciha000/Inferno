package com.example.indiancinema.data

import com.fasterxml.jackson.annotation.JsonProperty

data class TmdbMoviePage(
    @JsonProperty("page") val page: Int = 1,
    @JsonProperty("total_pages") val totalPages: Int = 1,
    @JsonProperty("results") val results: List<TmdbMovie> = emptyList(),
)

data class TmdbMovie(
    @JsonProperty("id") val id: Int,
    @JsonProperty("title") val title: String? = null,
    @JsonProperty("original_title") val originalTitle: String? = null,
    @JsonProperty("overview") val overview: String? = null,
    @JsonProperty("poster_path") val posterPath: String? = null,
    @JsonProperty("backdrop_path") val backdropPath: String? = null,
    @JsonProperty("release_date") val releaseDate: String? = null,
    @JsonProperty("original_language") val originalLanguage: String? = null,
    @JsonProperty("genres") val genres: List<TmdbGenre> = emptyList(),
)

data class TmdbGenre(
    @JsonProperty("name") val name: String,
)
