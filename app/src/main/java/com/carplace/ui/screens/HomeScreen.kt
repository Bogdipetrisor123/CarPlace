package com.carplace.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.carplace.R
import com.carplace.ui.viewmodel.HomeUiState
import com.carplace.ui.viewmodel.HomeViewModel

@Composable
internal fun HomeRoute(
    onCarClick: (id: Int) -> Unit,
    onSearchBarClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    HomeScreen(
        uiState = uiState,
        onCarClick = onCarClick,
        onSearchBarClick = onSearchBarClick
    )
}

@Composable
fun HomeScreen(uiState: HomeUiState, onCarClick: (id: Int) -> Unit, onSearchBarClick: () -> Unit) {
    var showChatGptDialog by remember { mutableStateOf(false) }
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
                    contentDescription = "navigationIconContentDescription",
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
    onCarClick: (id: Int) -> Unit
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
            painter = painterResource(id = car.imagesRes.first()),
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
        )
        Text(modifier = modifier, text = car.title, style = MaterialTheme.typography.titleLarge)
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

data class Car(
    val id: Int = 0,
    val title: String = "",
    val price: Int = 0,
    val km: Int = 0,
    val location: String = "",
    val imagesRes: List<Int> = emptyList(),
    val carDetails: CarDetails = CarDetails(),
    val carFeatures: CarFeatures = CarFeatures()
)

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

data class CarFeatures(
    val abs: String = "",
    val adaptiveCruiseControl: String = "",
    val ambientLighting: String = "",
    val appleCarPlay: String = "",
    val adaptiveCorneringLights: String = "",
    val alloyWheels: String = "",
    val androidAuto: String = "",
    val armRest: String = "",
    val bluetooth: String = "",
    val biXenonHeadLights: String = "",
) {
    fun getFeatures() = listOf(
        Pair("Abs: ", abs),
        Pair("Adaptive Cruise Control: ", adaptiveCruiseControl),
        Pair("Ambient Lighting: ", ambientLighting),
        Pair("Apple Car Play: ", appleCarPlay),
        Pair("Adaptive Cornering Lights: ", adaptiveCorneringLights),
        Pair("Alloy Wheels: ", alloyWheels),
        Pair("Android Auto: ", androidAuto),
        Pair("Arm rest: ", armRest),
        Pair("Bluetooth: ", bluetooth),
        Pair("Bi-xenon headlights: ", biXenonHeadLights),
    )
}