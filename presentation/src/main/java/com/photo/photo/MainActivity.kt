package com.photo.photo

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.photo.feature_main.MainScreen
import com.photo.presentation_core.design_system.MainTheme
import com.photo.presentation_core.language.updateLocale
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme{
                MainScreen( onLanguageChange = { updateLocale(this@MainActivity,it) })
            }
        }
    }


}
