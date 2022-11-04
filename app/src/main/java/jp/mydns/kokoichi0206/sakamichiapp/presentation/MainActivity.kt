package jp.mydns.kokoichi0206.sakamichiapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import jp.mydns.kokoichi0206.common.datamanager.DataStoreManager
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.Navigation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        generateUUID()
        setContent {
            Navigation()
        }
    }

    /**
     * Generate and save UUID if not found.
     */
    private fun generateUUID() {
        lifecycleScope.launch(Dispatchers.IO) {
            if (DataStoreManager.readString(applicationContext, DataStoreManager.KEY_USER_ID).isBlank()) {
                val uuidString = UUID.randomUUID().toString()
                DataStoreManager.writeString(applicationContext, DataStoreManager.KEY_USER_ID, uuidString)
            }
        }
    }
}
