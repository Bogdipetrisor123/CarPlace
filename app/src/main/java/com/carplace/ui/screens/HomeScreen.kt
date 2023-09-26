package com.carplace.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.carplace.R
import com.carplace.ui.viewmodel.Car
import com.carplace.ui.viewmodel.HomeUiState
import com.carplace.ui.viewmodel.HomeViewModel

@Composable
internal fun HomeRoute(
    onCarClick: (id: Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    HomeScreen(
        uiState = uiState,
        onCarClick = onCarClick,
        loadData = viewModel::loadData
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(uiState: HomeUiState, onCarClick: (id: Int) -> Unit, loadData: () -> Unit) {
    var showChatGptDialog by remember { mutableStateOf(false) }
    val pullRefreshState =
        rememberPullRefreshState(refreshing = uiState.isRefreshing, onRefresh = loadData)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        if (uiState.isInitComplete) {
            if (showChatGptDialog) {
                ChatGptDialog { showChatGptDialog = false }
            }
            LazyColumn {
                item {
                    SearchBar(onSearchBarClick = { showChatGptDialog = true })
                    ShowCars(
                        title = "Recent added cars",
                        cars = uiState.recentAddedCars,
                        onCarClick = onCarClick
                    )
                    ShowCars(
                        title = "Popular cars",
                        cars = uiState.popularCars,
                        onCarClick = onCarClick
                    )
                    ShowCars(
                        title = "Cars nears you",
                        cars = uiState.nearsCars,
                        onCarClick = onCarClick
                    )
                }
            }
        } else {
            LoadingIndicator()
        }
        PullRefreshIndicator(
            refreshing = uiState.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(onSearchBarClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        onClick = onSearchBarClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_search),
                    contentDescription = "ChatGPTSearchBar",
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
            Text(text = "Chat GPT")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarItem(
    modifier: Modifier,
    car: Car,
    navigateFromMyListings: Boolean = false,
    onDeleteClick: (Int) -> Unit = {},
    onCarClick: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(
                alpha = 0.2f
            )
        ),
        onClick = {
            onCarClick(car.id)
        }
    ) {
        Image(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            painter = rememberAsyncImagePainter(car.images.first()),
            contentDescription = "CarPagerItem",
            contentScale = ContentScale.FillWidth,
        )
        if (navigateFromMyListings) {
            Row {
                Text(
                    modifier = modifier.weight(1f),
                    text = car.title,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    modifier = modifier.clickable { onDeleteClick(car.id) },
                    text = "Delete",
                    color = Color.Red,
                )
            }
        } else {
            Text(
                modifier = modifier,
                text = car.title,
                style = MaterialTheme.typography.titleLarge
            )
        }
        Row(modifier = Modifier.padding(bottom = 4.dp)) {
            Text(
                modifier = modifier.weight(1f),
                text = "${car.price} EUR",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(modifier = modifier, text = "${car.km} km")
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun ShowCars(
    title: String,
    cars: List<Car>,
    onCarClick: (id: Int) -> Unit
) {
    Text(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        text = title,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
    )
    HorizontalPager(pageCount = cars.size, pageSize = PageSize.Fixed(330.dp)) { page ->
        CarItem(
            modifier = Modifier.padding(horizontal = 8.dp),
            car = cars[page],
            onCarClick = onCarClick,
        )
    }
}

data class CarDetails(
    val make: String = "",
    val model: String = "",
    val category: String = "",
    val cubicCapacity: String = "",
    val horsePower: String = "",
    val fuelType: String = "",
    val numberOfSeats: String = "",
    val gearBox: String = "",
    val firstRegistration: String = "",
    val colour: String = "",
    val condition: String = "",
) {
    fun getDetails() = listOf(
        Pair("Make: ", make),
        Pair("Category: ", category),
        Pair("Cubic Capacity: ", cubicCapacity),
        Pair("Horse Power: ", horsePower),
        Pair("Fuel Type: ", fuelType),
        Pair("Number of seats: ", numberOfSeats),
        Pair("Gear box: ", gearBox),
        Pair("First Registration: ", firstRegistration),
        Pair("Colour: ", colour),
        Pair("Condition: ", condition),
    )
}