# Indian Cinema TMDB for Cloudstream

A Cloudstream 3 metadata plugin that shows popular Indian movies from TMDB in
separate Telugu, Tamil, Hindi, and Malayalam home-page lists. It supports
searching those languages and displays TMDB posters, backdrops, plot summaries,
release years, and genres.

## TMDB API key

TMDB requires an API key. Install the plugin, open its settings from Cloudstream,
and enter your own TMDB v3 API key. The key is stored in the app's private
preferences on the device and is not included in the plugin source.

## Build

Build the extension with Gradle installed in an Android/Cloudstream plugin
development environment:

```powershell
gradle build
```

The GitHub Actions workflow builds the Cloudstream package and publishes the
package plus `plugins.json` to the `builds` branch on pushes to `main`. In the
repository settings, allow GitHub Actions to write repository contents for the
deployment step. Replace the fallback repository name in `build.gradle.kts`
when building outside GitHub Actions.

The extension provides TMDB metadata only; it does not resolve or play video
streams. This product uses the TMDB API but is not endorsed or certified by
TMDB.
