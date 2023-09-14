package com.carplace.ui.navigation

import com.carplace.R

enum class TopLevelDestination(
    val iconDrawable: Int,
    val titleTextId: Int
) {
    HOME(
        iconDrawable = R.drawable.ic_home,
        titleTextId = R.string.home,
    ),
    SELL(
        iconDrawable = R.drawable.ic_add,
        titleTextId = R.string.sell,
    ),
    SEARCH(
        iconDrawable = R.drawable.ic_search,
        titleTextId = R.string.search,
    ),
    MY_LISTINGS(
        iconDrawable = R.drawable.ic_listings,
        titleTextId = R.string.my_listings
    )
//    Favorites(
//        iconDrawable = R.drawable.ic_favorites,
//        iconTextId = R.string.favorites,
//        titleTextId = R.string.favorites,
//    )
}