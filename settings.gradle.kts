pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
    // TODO can this be replaced using the version catalog?
    val quarkusPluginId = providers.gradleProperty("quarkusPluginId").get()
    val quarkusPluginVersion = providers.gradleProperty("quarkusPluginVersion").get()

    val foojayResolverPlugin = providers.gradleProperty("foojayResolverPlugin").get()
    val foojayResolverPluginVersion = providers.gradleProperty("foojayResolverPluginVersion").get()

    plugins {
        id(quarkusPluginId) version quarkusPluginVersion
        // Apply the foojay-resolver plugin to allow automatic download of JDKs
        id(foojayResolverPlugin) version foojayResolverPluginVersion
    }
}

rootProject.name = "cold-frontier"
include("backend", "frontend", "lib")
