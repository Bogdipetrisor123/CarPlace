package com.carplace.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.carplace.R
import com.carplace.ui.viewmodel.Car
import com.carplace.ui.viewmodel.CarDetailsUiState
import com.carplace.ui.viewmodel.CarDetailsViewModel
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@Composable
internal fun CarDetailsRoute(
    onBackClick: () -> Unit,
    viewModel: CarDetailsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    CarDetailsScreen(onBackClick, uiState)
}

@Composable
fun CarDetailsScreen(onBackClick: () -> Unit, uiState: CarDetailsUiState) {
    if (uiState.isInitComplete) {
        LazyColumn {
            item {
                TopScreenHeader(car = uiState.car) { onBackClick.invoke() }
                CarInformation(car = uiState.car)
                CarFeatures(car = uiState.car)
            }
        }
    } else {
        LoadingIndicator()
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalGlideComposeApi::class
)
@Composable
fun TopScreenHeader(car: Car, onBackClick: () -> Unit) {
    TopAppBar(
        title = { },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        ),
        navigationIcon = {
            Icon(
                modifier = Modifier
                    .clickable { onBackClick.invoke() },
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_back),
                contentDescription = "",
            )
        },
        actions = {
            Icon(
                modifier = Modifier.clickable {  },
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_favorite),
                contentDescription = ""
            )
        }
    )
    HorizontalPager(
        modifier = Modifier.fillMaxWidth(),
        pageCount = car.images.size,
        pageSize = PageSize.Fill
    ) { page ->
        Box(modifier = Modifier.fillMaxWidth()) {
            GlideImage(
                modifier = Modifier.height(300.dp),
                model = car.images[page],
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                loading = placeholder {
                    Image(
                        painter = ColorPainter(Color.LightGray),
                        contentDescription = null,
                        modifier = Modifier.wrapContentSize()
                    )
                },
                failure = placeholder {
                    Image(
                        painter = ColorPainter(Color.Red),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                },
            )
            Text(
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 8.dp)
                    .align(Alignment.BottomEnd),
                text = "${page + 1}/${car.images.size}",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                color = Color.White
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = car.title,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "${car.price} EUR",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CarInformation(car: Car) {
    FlowRow(
        modifier = Modifier
            .padding(start = 16.dp, end = 2.dp, top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        car.carDetails.getDetails().forEach {
            FilterChip(selected = false, onClick = {}, label = {
                Text(text = "${it.first} ${it.second}")
            })
        }
        FilterChip(selected = false, onClick = {}, label = {
            Text(text = "Phone Number: ${car.phoneNumber}")
        })
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CarFeatures(car: Car) {
    Text(
        modifier = Modifier.padding(start = 16.dp),
        text = "Features",
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
    )
    Divider(thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))
    FlowRow(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        maxItemsInEachRow = 2,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        car.carFeatures.forEach {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_check),
                    contentDescription = ""
                )
                Text(text = it.option)
            }
        }
    }
}

