import com.android.build.gradle.BaseExtension
import com.lagradost.cloudstream3.gradle.CloudstreamExtension

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("com.github.recloudstream:gradle:-SNAPSHOT")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

apply(plugin = "com.android.library")
apply(plugin = "kotlin-android")
apply(plugin = "com.lagradost.cloudstream3.gradle")

version = 1

configure<CloudstreamExtension> {
    val repository = System.getenv("GITHUB_REPOSITORY")
        ?: "your-name/indian-cinema-tmdb"
    setRepo("https://github.com/$repository")
    language = "en"
    description = "Popular Telugu, Tamil, Hindi, and Malayalam cinema from TMDB."
    authors = listOf("Indian Cinema TMDB")
    status = 1
    tvTypes = listOf("Movie")
}

configure<BaseExtension> {
    namespace = "com.example.indiancinema"
    compileSdkVersion(33)

    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    add("apk", "com.lagradost:cloudstream3:pre-release")
    add("implementation", kotlin("stdlib"))
    add("implementation", "com.fasterxml.jackson.module:jackson-module-kotlin:2.16.1")
}
