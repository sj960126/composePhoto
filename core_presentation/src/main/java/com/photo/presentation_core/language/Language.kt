package com.photo.presentation_core.language

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import java.util.*

fun changeLanguage(context: Context, languageCode: String) {
    val locale = Locale(languageCode).apply { Locale.setDefault(this) }
    val config = Configuration(context.resources.configuration).apply { setLocale(locale) }
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
    (context as Activity).recreate()
}

enum class LanguageDefine(val title : String,val code :String){
    KOREA("한국어","ko"),
    ENGLISH("English","en"),
    JAPANESE("日本語", "ja")
}