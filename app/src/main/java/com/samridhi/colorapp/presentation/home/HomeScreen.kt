package com.samridhi.colorapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.samridhi.colorapp.R
import com.samridhi.colorapp.presentation.common.ErrorMessage
import com.samridhi.colorapp.presentation.home.viewmodel.HomeScreenUIEvent
import com.samridhi.colorapp.presentation.home.viewmodel.HomeScreenUiState
import com.samridhi.colorapp.presentation.home.viewmodel.HomeScreenViewModel
import com.samridhi.colorapp.presentation.home.viewmodel.ScreenState
import com.samridhi.colorapp.presentation.util.AppDrawable
import com.samridhi.colorapp.presentation.util.AppString
import com.samridhi.colorapp.presentation.util.TimeUtil
import com.samridhi.colorapp.ui.theme.lightPurple
import com.samridhi.colorapp.ui.theme.purple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.color_app),
                        color = Color.White,
                    )
                },
                actions = {
                    if (viewModel.uiState.unSyncedCount != 0) {
                        Row(
                            modifier = Modifier
                                .wrapContentSize()
                                .background(shape = RoundedCornerShape(20.dp), color = lightPurple)
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                                .clickable {
                                    viewModel.onEvent(HomeScreenUIEvent.OnSync)
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = viewModel.uiState.unSyncedCount.toString(),
                                fontSize = 20.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Icon(
                                modifier = Modifier.size(24.dp),
                                tint = purple,
                                painter = painterResource(id = AppDrawable.group_2111),
                                contentDescription = ""
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = purple
                )
            )
        },
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .background(shape = RoundedCornerShape(20.dp), color = lightPurple)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .clickable {
                        viewModel.onEvent(HomeScreenUIEvent.OnAddColor)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(AppString.add_color),
                    fontSize = 18.sp,
                    color = purple
                )
                Spacer(modifier = Modifier.size(4.dp))
                Icon(
                    modifier = Modifier.size(24.dp),
                    tint = purple,
                    painter = painterResource(id = AppDrawable.plus_icon),
                    contentDescription = ""
                )
            }
        }
    ) { innerPadding ->
        HomeScreenContent(
            modifier = Modifier.padding(innerPadding),
            uiState = viewModel.uiState,
        )
    }
}

@Composable
fun HomeScreenContent(
    modifier: Modifier,
    uiState: HomeScreenUiState
) {

    when (uiState.screenState) {
        ScreenState.EMPTY -> {
            ErrorMessage(errorMessage = stringResource(id = AppString.no_data_available))
        }

        ScreenState.ERROR -> {
            ErrorMessage(
                errorMessage = stringResource(id = AppString.something_went_wrong),
                error = true
            )
        }

        else -> {
            Column(
                modifier = modifier
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2)
                ) {
                    items(uiState.list.size) {
                        ColorBox(
                            hexCode = uiState.list[it].colorCode,
                            createdAt = uiState.list[it].timeStamp
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ColorBox(hexCode: String, createdAt: String) {
    Box(
        modifier = Modifier
            .size(150.dp)
            .padding(8.dp)
            .background(color = getColor(hexCode), shape = RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Text(
                text = hexCode,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Divider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(0.5f)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.align(Alignment.End),
                text = stringResource(
                    AppString.created_at,
                    TimeUtil.convertMillisToDate(createdAt)
                ),
                color = Color.White,
                fontSize = 14.sp,
            )
        }
    }
}

fun getColor(color: String): Color {
    val hexColor = color.trimStart('#').padStart(8, 'F')
    return Color(hexColor.toLong(16))
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Color App", color = Color.White,
                        modifier = Modifier
                    )
                },
                actions = {
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .background(shape = RoundedCornerShape(20.dp), color = lightPurple)
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .clickable {
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "9",
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Icon(
                            modifier = Modifier.size(24.dp),
                            tint = purple,
                            painter = painterResource(id = AppDrawable.group_2111),
                            contentDescription = ""
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = purple
                )
            )
        },
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .background(shape = RoundedCornerShape(20.dp), color = lightPurple)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(AppString.add_color),
                    fontSize = 18.sp,
                    color = purple
                )
                Spacer(modifier = Modifier.size(4.dp))
                Icon(
                    modifier = Modifier.size(24.dp),
                    tint = purple,
                    painter = painterResource(id = AppDrawable.plus_icon),
                    contentDescription = ""
                )
            }
        }
    ) { innerPadding ->
        val mockState = HomeScreenUiState()

        HomeScreenContent(
            uiState = mockState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}