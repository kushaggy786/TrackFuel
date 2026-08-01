pluginManagement {
    repositories {
        google()
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

rootProject.name = "TrackFuel"

include(":app")
include(":core:common")
include(":core:ui")
include(":domain")
include(":data")
include(":feature:onboarding")
include(":feature:diet")
include(":feature:workout")
include(":feature:wellness")
include(":feature:results")
include(":feature:settings")
