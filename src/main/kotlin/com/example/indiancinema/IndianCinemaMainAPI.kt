package com.example.indiancinema

import com.example.indiancinema.model.CinemaMovie
import com.example.indiancinema.viewmodel.IndianCinemaViewModel
import com.lagradost.cloudstream3.HomePageList
import com.lagradost.cloudstream3.HomePageResponse
import com.lagradost.cloudstream3.LoadResponse
import com.lagradost.cloudstream3.MainAPI
import com.lagradost.cloudstream3.MainPageRequest
import com.lagradost.cloudstream3.SearchResponse
import com.lagradost.cloudstream3.TvType
import com.lagradost.cloudstream3.newHomePageResponse
import com.lagradost.cloudstream3.newMovieLoadResponse
import com.lagradost.cloudstream3.newMovieSearchResponse
import com.lagradost.cloudstream3.mainPageOf
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.SubtitleFile

internal class IndianCinemaMainAPI(
    repository: TmdbRepository,
) : MainAPI() {
    private val viewModel = IndianCinemaViewModel(repository)

    override var mainUrl = TMDB_WEB_BASE
    override var name = "Indian Cinema (TMDB)"
    override var lang = "en"
    override val hasMainPage = true
    override val hasDownloadSupport = false
    override val supportedTypes = setOf(TvType.Movie)
    override val mainPage = mainPageOf(
        "te" to "Telugu",
        "ta" to "Tamil",
        "hi" to "Hindi",
        "ml" to "Malayalam",
    )

    override suspend fun getMainPage(
        page: Int,
        request: MainPageRequest,
    ): HomePageResponse {
        val response = viewModel.popular(request.data, page)
        val movies = response.movies.map { it.toSearchResponse() }
        return newHomePageResponse(
            list = HomePageList(name = request.name, list = movies),
            hasNext = response.hasNext,
        )
    }

    override suspend fun search(query: String): List<SearchResponse> =
        viewModel.search(query).map { it.toSearchResponse() }

    override suspend fun load(url: String): LoadResponse {
        val id = MOVIE_ID_REGEX.find(url)?.groupValues?.getOrNull(1)?.toIntOrNull()
            ?: throw IllegalArgumentException("Invalid TMDB movie URL: $url")
        val movie = viewModel.movie(id)

        return newMovieLoadResponse(movie.title, url, TvType.Movie, id.toString()) {
            posterUrl = movie.posterUrl
            backgroundPosterUrl = movie.backdropUrl
            plot = movie.overview
            year = movie.year
            tags = movie.genres
        }
    }

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit,
    ): Boolean = false

    private fun CinemaMovie.toSearchResponse(): SearchResponse =
        newMovieSearchResponse(
            name = year?.let { "$title ($it)" } ?: title,
            url = "$TMDB_WEB_BASE/movie/$id",
            type = TvType.Movie,
        ) {
            posterUrl = this@toSearchResponse.posterUrl
        }

    private companion object {
        val MOVIE_ID_REGEX = Regex("""/movie/(\d+)""")
    }
}

private const val TMDB_WEB_BASE = "https://www.themoviedb.org"
