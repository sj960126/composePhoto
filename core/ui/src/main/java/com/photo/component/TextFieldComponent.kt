package com.photo.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.photo.design_system.LocalColors
import com.photo.design_system.LocalTypography

@Composable
fun SearchBarLayout(
    modifier: Modifier,
    labelTitle: String,
    text: String,
    hint : String? = null,
    onTextChange: (String) -> Unit,
    keyboardType: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
) {
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        placeholder = {
           if(hint != null) Text(text = hint, style = LocalTypography.current.body4)
        },
        label = { Text(labelTitle, style = LocalTypography.current.caption2) },
        modifier = modifier.padding(end = 8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = LocalColors.current.transparent,
            focusedBorderColor = LocalColors.current.primary,
            unfocusedBorderColor = LocalColors.current.primary,
            cursorColor = LocalColors.current.primary
        ),
        textStyle = LocalTypography.current.caption2,
        keyboardOptions= keyboardType
    )
}