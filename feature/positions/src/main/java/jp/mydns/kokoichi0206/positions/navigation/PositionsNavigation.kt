package jp.mydns.kokoichi0206.positions.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import jp.mydns.kokoichi0206.positions.PositionsScreen


const val positionsRoute = "positions_route"

fun NavGraphBuilder.positionsScreen() {
    composable(route = positionsRoute) {
        PositionsScreen()
    }
}
