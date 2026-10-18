package com.example.indiancinema

import android.content.Context
import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.Plugin

@CloudstreamPlugin
class IndianCinemaPlugin : Plugin() {
    override fun load(context: Context) {
        val settings = TmdbSettings(context.applicationContext)
        registerMainAPI(IndianCinemaMainAPI(TmdbRepository(settings)))
        openSettings = settings::showDialog
    }
}
