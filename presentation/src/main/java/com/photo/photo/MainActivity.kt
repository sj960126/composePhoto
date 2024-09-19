package com.photo.photo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.photo.main.MainScreen
import com.photo.presentation_core.design_system.MainTheme
import com.photo.presentation_core.language.changeLanguage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme{
                MainScreen(onLanguageChange = {
                    changeLanguage(
                        this@MainActivity,
                        it
                    )
                })
            }
        }
    }

}
