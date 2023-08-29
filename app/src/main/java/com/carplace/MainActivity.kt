package com.carplace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.datastore.core.DataStore
import com.carplace.ui.screens.CarPlaceApp
import com.carplace.ui.theme.CarPlaceTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.prefs.Preferences

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarPlaceTheme {
                CarPlaceApp()
            }
        }
    }
}