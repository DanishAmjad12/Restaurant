pluginManagement {
    plugins {
        id("com.android.application") version "8.8.0"
        id("com.android.library") version "8.8.0"
        id("org.jetbrains.kotlin.android") version "2.0.0"
        id("com.google.dagger.hilt.android") version "2.52"
    }

    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "restaurant"
include(":app")
