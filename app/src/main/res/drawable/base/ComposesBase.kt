package com.example.whetherapp.presentation.base

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BaseImage(
    modifier: Modifier = Modifier.size(20.dp),
    @DrawableRes imageId: Int,
    contentDescription: String = "Base image",
    contentScale: ContentScale = ContentScale.Crop
) {
    Image(
        modifier = modifier,
        painter = painterResource(id = imageId),
        contentDescription = contentDescription,
        contentScale = contentScale
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseTextField(
    modifier: Modifier = Modifier
        .padding(horizontal = 26.dp)
        .clip(RoundedCornerShape(10.dp))
        .shadow(elevation = 20.dp, ambientColor = Color.Black, spotColor = Color.Black)
        .background(TransparentLightGray, RoundedCornerShape(10.dp)),
    labelText: String,
    @DrawableRes imageId: Int

): String {
    var text by remember {
        mutableStateOf("")
    }
    TextField(
        modifier = modifier,
        value = text,
        onValueChange = { text = it },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            containerColor = TransparentLightGray,
            focusedLabelColor = Color.LightGray,
            unfocusedLabelColor = Color.LightGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        label = {
            Text(text = labelText, fontSize = 16.sp)
        },
        leadingIcon = {
            com.example.mywhether.presentation.screens.base.BaseImage(imageId = imageId)
        })
    return text
}