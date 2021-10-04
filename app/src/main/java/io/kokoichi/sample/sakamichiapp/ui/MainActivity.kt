package io.kokoichi.sample.sakamichiapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import io.kokoichi.sample.sakamichiapp.ui.SakamichiApp
import io.kokoichi.sample.sakamichiapp.ui.home.HomeViewModel
import io.kokoichi.sample.sakamichiapp.ui.theme.SakamichiAppTheme
import io.kokoichi.sample.sakamichiapp.ui.util.Member
import io.kokoichi.sample.sakamichiapp.ui.util.ShowMemberStyle


val TAG = "MainActivity"

class MainActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            SakamichiAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    SakamichiApp(viewModel)
                }
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("TAG", "onRestart() called")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d("TAG", "onBackPressed() called")
    }
}




