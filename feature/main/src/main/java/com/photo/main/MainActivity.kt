package com.photo.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.photo.design_system.MainTheme
import com.photo.language.changeLanguage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme {
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
