package com.example.mywhether.presentation.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mywhether.R
import com.example.mywhether.data.remote.models.WhetherModel
import com.example.mywhether.presentation.screens.base.BaseImage
import com.example.mywhether.presentation.screens.base.BaseTextField
import com.example.mywhether.presentation.states.UiState
import com.example.mywhether.ui.theme.TransparentLightGray
import com.google.gson.Gson

@SuppressLint("MutableCollectionMutableState")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val currentWhetherListState = homeViewModel.currentWhetherListState.collectAsState()

    when (currentWhetherListState.value) {
        is UiState.Error -> {
            Log.e("error", "HomeScreen: ")
        }

        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center)
                )
            }
        }

        is UiState.Success -> {

            var isCityAdd by remember {
                mutableStateOf(false)
            }

            var chooseElement by remember {
                mutableStateOf(false)
            }

            var deleteCurrentWhethers by remember {
                mutableStateOf(mutableListOf<WhetherModel>())
            }

            if (isCityAdd) {
                AddCity(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    isCityAdd = false
                    homeViewModel.setCity(it)
                }
            } else {
                Column(
                    modifier = modifier
                        .padding(20.dp),
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        BaseImage(imageId = R.drawable.menu)
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "Add city",
                            color = Color.White
                        )
                        BaseImage(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .clickable {
                                    if (chooseElement) {
                                        for (i in deleteCurrentWhethers) {
                                            homeViewModel.deleteCity(i.location?.name!!)
                                        }
                                    } else {
                                        isCityAdd = true
                                    }
                                },
                            imageId = R.drawable.plus
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    LazyVerticalGrid(columns = GridCells.Adaptive(150.dp)) {
                        items((currentWhetherListState.value as UiState.Success<MutableList<WhetherModel>>).data) {
                            chooseElement = CityItem(
                                rootDto = it,
                                navController = navController
                            ) { deleteRootDto, isDelete ->
                                if (!isDelete) {
                                    deleteCurrentWhethers.add(deleteRootDto)
                                } else {
                                    deleteCurrentWhethers.remove(deleteRootDto)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddCity(modifier: Modifier = Modifier, addCity: (city: String) -> Unit) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val text =
                BaseTextField(
                    modifier = Modifier.fillMaxWidth(),
                    labelText = "Input your city...",
                    imageId = R.drawable.person_auth
                )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = TransparentLightGray),
                onClick = {
                    addCity(text)
                }) {
                Text(text = "Add city", color = Color.White)
            }
        }
    }
}

@Composable
fun CityItem(
    modifier: Modifier = Modifier,
    rootDto: WhetherModel,
    navController: NavController,
    elementCount: (rootDto: WhetherModel, isDelete: Boolean) -> Unit
): Boolean {
    var chooseElement by remember {
        mutableStateOf(false)
    }

    var elementPaddingDifference by remember {
        mutableIntStateOf(150)
    }
    elementPaddingDifference = if (chooseElement) {
        10
    } else {
        0
    }
    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        chooseElement = !chooseElement
                        if (chooseElement) {
                            elementCount(rootDto, false)
                        } else {
                            elementCount(rootDto, true)
                        }
                    },
                    onTap = {
                        val gson = Gson()
                        val rootJson = gson.toJson(rootDto)
                        navController.navigate("detail/${rootJson.replace("/", ".")}")
                    }
                )
            }
            .padding(5.dp + elementPaddingDifference.dp)
            .size(150.dp - elementPaddingDifference.dp)
            .clip(RoundedCornerShape(26.dp))
            .background(TransparentLightGray)
            .padding(20.dp)
    ) {
        Box(
            modifier = Modifier
                .width(150.dp)
                .align(Alignment.TopCenter)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = "${rootDto.current?.tempC}°c",
                color = Color.White,
                fontSize = 16.sp
            )
            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterEnd),
                model = "https:${rootDto.current?.condition?.icon}",
                contentDescription = "Icon",
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
        ) {
            rootDto.location?.name?.let { Text(text = it, color = Color.White, fontSize = 18.sp) }
            Spacer(modifier = Modifier.height(6.dp))
            rootDto.location?.country?.let {
                Text(
                    text = it,
                    color = Color.LightGray,
                    fontSize = 10.sp,
                    lineHeight = 8.sp
                )
            }
        }
    }
    return chooseElement
}
