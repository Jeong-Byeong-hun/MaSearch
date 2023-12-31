package com.example.masearch.screen

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object SearchScreen : Screen("search_screen/{searchId}") {
        fun searchCharacter(searchId: String) = "search_screen/$searchId"
    }

    object StatScreen : Screen("stat_screen")

    object LikeScreen : Screen("like_screen")

    object RecentSearchScreen : Screen("recentSearch_screen")

}
