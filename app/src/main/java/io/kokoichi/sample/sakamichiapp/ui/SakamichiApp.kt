package io.kokoichi.sample.sakamichiapp.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import io.kokoichi.sample.sakamichiapp.ui.home.MainView
import io.kokoichi.sample.sakamichiapp.ui.components.MemberProps
import io.kokoichi.sample.sakamichiapp.TAG
import io.kokoichi.sample.sakamichiapp.ui.detailedPage.DetailedView
import io.kokoichi.sample.sakamichiapp.ui.detailedPage.WebViewWidget
import io.kokoichi.sample.sakamichiapp.ui.home.HomeViewModel

@Composable
fun SakamichiApp(viewModel: HomeViewModel) {

    val navController = rememberNavController()

    NavHost(navController, startDestination = "main") {

        composable("main") {
            MainView(
                navController = navController,
                viewModel = viewModel
            )
        }

        // userData は Member クラスを Json オブジェクトにして渡してあげる
        composable(
            route = "detailed/userData={userData}",
            arguments = listOf(navArgument("userData") { type = NavType.StringType })
        ) { backStackEntry ->
            // 受け取った時の処理を記述、
            // Json が渡ってくるので、それをオブジェクトに変換する
            val userJson = backStackEntry.arguments?.getString("userData")

            Log.d(TAG, userJson.toString())
            val memberProps = Gson().fromJson<MemberProps>(userJson, MemberProps::class.java)
            DetailedView(memberProps, navController)
        }

        composable(
            route = "webView/url={url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->

            var url = backStackEntry.arguments?.getString("url")
            if (url == null) {
                url = "https://blog.nogizaka46.com/"
            }
            Log.d("TAG", "The passed content URL is $url")
            WebViewWidget(url)
        }
    }
}