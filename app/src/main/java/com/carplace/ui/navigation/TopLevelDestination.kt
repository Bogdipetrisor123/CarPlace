package com.carplace.ui.navigation

import com.carplace.R

enum class TopLevelDestination(
    val iconDrawable: Int,
    val iconTextId: Int,
    val titleTextId: Int
) {
    HOME(
        iconDrawable = R.drawable.ic_home,
        iconTextId = R.string.home,
        titleTextId = R.string.home,
    ),
    Sell(
        iconDrawable = R.drawable.ic_add,
        iconTextId = R.string.sell,
        titleTextId = R.string.sell,
    ),
    Search(
        iconDrawable = R.drawable.ic_search,
        iconTextId = R.string.search,
        titleTextId = R.string.search,
    ),
//    Favorites(
//        iconDrawable = R.drawable.ic_favorites,
//        iconTextId = R.string.favorites,
//        titleTextId = R.string.favorites,
//    )
}