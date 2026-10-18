package com.example.indiancinema

import android.app.AlertDialog
import android.content.Context
import android.text.InputType
import android.widget.EditText
import android.widget.Toast

internal class TmdbSettings(context: Context) {
    private val preferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun apiKey(): String = preferences.getString(API_KEY_PREFERENCE, null)
        ?.trim()
        ?.takeIf(String::isNotEmpty)
        ?: throw IllegalStateException(
            "Set your TMDB API key in this plugin's settings before browsing.",
        )

    fun showDialog(context: Context) {
        val input = EditText(context).apply {
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            setSingleLine(true)
            setText(preferences.getString(API_KEY_PREFERENCE, ""))
            hint = "TMDB API key"
        }

        AlertDialog.Builder(context)
            .setTitle("TMDB API key")
            .setMessage("Enter your TMDB v3 API key. It is stored locally on this device.")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val key = input.text.toString().trim()
                if (key.isEmpty()) {
                    Toast.makeText(context, "The API key cannot be empty.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    preferences.edit().putString(API_KEY_PREFERENCE, key).apply()
                    Toast.makeText(context, "TMDB API key saved.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private companion object {
        const val PREFERENCES_NAME = "indian_cinema_tmdb"
        const val API_KEY_PREFERENCE = "api_key"
    }
}
