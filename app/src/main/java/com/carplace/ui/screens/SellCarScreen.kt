package com.carplace.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ChipDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.carplace.R
import com.carplace.ui.enums.Type
import com.carplace.ui.theme.CarPlaceTheme
import com.carplace.ui.viewmodel.FilterOption
import com.carplace.ui.viewmodel.SellCarUiState
import com.carplace.ui.viewmodel.SellCarViewModel

@Composable
internal fun SellCarRoute(
    viewModel: SellCarViewModel = hiltViewModel()
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            SellCarScreen(
                uiState = viewModel.uiState,
                onDataChange = viewModel::onOptionSelected,
                onValueChange = viewModel::onValueChange,
                onAddCarClick = viewModel::onAddCarClicked,
                setPhotosUri = viewModel::setPhotosUri,
                setPhoneNumber = viewModel::setPhoneNumber,
                setTitle = viewModel::setCarTitle
            )
        }
    }
}

@Composable
fun SellCarScreen(
    uiState: SellCarUiState,
    onDataChange: (type: Type, filterOption: FilterOption) -> Unit,
    onValueChange: (Type, Int) -> Unit,
    onAddCarClick: () -> Unit,
    setPhotosUri: (List<Uri>) -> Unit,
    setPhoneNumber: (String) -> Unit,
    setTitle: (String) -> Unit
) {
    AddCarPhotos(setPhotosUri = setPhotosUri)
    ShowCarDetails(
        uiState = uiState,
        onDataChange = onDataChange,
        onValueChange = onValueChange,
        setPhoneNumber = setPhoneNumber,
        setTitle = setTitle
    )
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        enabled = uiState.buttonEnabled,
        onClick = onAddCarClick
    ) {
        if (!uiState.isLoading) {
            Text(text = "Add car", textAlign = TextAlign.Center)
        } else {
            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowCarDetails(
    uiState: SellCarUiState,
    onDataChange: (type: Type, filterOption: FilterOption) -> Unit,
    onValueChange: (Type, Int) -> Unit,
    setPhoneNumber: (String) -> Unit,
    setTitle: (String) -> Unit
) {
    CarDetailSection(
        title = "Make",
        options = uiState.makes,
        type = Type.MAKE,
        onDataChange = onDataChange
    )
    if (uiState.makes.any { it.isSelected }) {
        CarDetailSection(
            title = "Models",
            options = uiState.models,
            type = Type.MODELS,
            onDataChange = onDataChange
        )
    }
    CarDetailSection(
        title = "Category",
        options = uiState.categories,
        type = Type.CATEGORY,
        onDataChange = onDataChange
    )
    TitleSection(text = "Phone number")
    Row(
        modifier = Modifier
            .background(color = Color.White)
            .padding(vertical = 2.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val focusManager = LocalFocusManager.current
        TextField(
            modifier = Modifier.weight(1f),
            value = uiState.phoneNumber.ifEmpty {
                ""
            },
            placeholder = { Text(text = "Enter a value") },
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.moveFocus(FocusDirection.Next) }),
            onValueChange = { setPhoneNumber(it) })
    }
    TitleSection(text = "Title")
    Row(
        modifier = Modifier
            .background(color = Color.White)
            .padding(vertical = 2.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            modifier = Modifier.weight(1f),
            value = uiState.title.ifEmpty {
                ""
            },
            placeholder = { Text(text = "Enter a value") },
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
            onValueChange = { setTitle(it) })
    }
    CarDetailSectionForNumberInput(
        title = "Price",
        value = uiState.price,
        type = Type.PRICE,
        onValueChange = onValueChange
    )
    CarDetailSection(
        title = "Fuel Type",
        options = uiState.fuelTypes,
        type = Type.FUEL,
        onDataChange = onDataChange
    )
    CarDetailSectionForNumberInput(
        title = "Mileage",
        value = uiState.mileage,
        type = Type.MILEAGE,
        onValueChange = onValueChange
    )
    CarDetailSection(
        title = "Location",
        options = uiState.locations,
        type = Type.LOCATION,
        onDataChange = onDataChange
    )
    CarDetailSectionForNumberInput(
        title = "Cubic capacity",
        value = uiState.cubicCapacity,
        type = Type.CUBIC_CAPACITY,
        onValueChange = onValueChange
    )
    CarDetailSectionForNumberInput(
        title = "Horse Power",
        value = uiState.horsePower,
        type = Type.HP,
        onValueChange = onValueChange
    )
    CarDetailSectionForNumberInput(
        title = "Seats",
        value = uiState.seatsNumber,
        type = Type.SEATS,
        onValueChange = onValueChange
    )
    CarDetailSection(
        title = "Gear Box",
        options = uiState.gearBoxTypes,
        type = Type.GEAR_BOX,
        onDataChange = onDataChange
    )
    CarDetailSectionForNumberInput(
        title = "First Registration",
        value = uiState.firstRegistration,
        type = Type.FIRST_REGISTRATION,
        onValueChange = onValueChange
    )
    CarDetailSection(
        title = "Color",
        options = uiState.colors,
        type = Type.COLOR,
        onDataChange = onDataChange
    )
    CarDetailSection(
        title = "Condition",
        options = uiState.condition,
        type = Type.CONDITION,
        onDataChange = onDataChange
    )
    CarDetailSection(
        title = "Features",
        options = uiState.features,
        type = Type.FEATURES,
        onDataChange = onDataChange
    )
}

@Composable
fun AddCarPhotos(setPhotosUri: (List<Uri>) -> Unit) {
    TitleSection(text = "Add photos with your car")

    val photosUri = remember { mutableStateListOf<Uri>() }.also { setPhotosUri(it) } // Remember the list of selected URIs

    val selectMultipleImagesLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        photosUri.clear()
        photosUri.addAll(uris)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LazyRow {
            items(photosUri) { uri ->
                Image(
                    painter = rememberAsyncImagePainter(model = uri),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .clickable { photosUri.remove(uri) }
                )
            }
        }

        IconButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { selectMultipleImagesLauncher.launch("image/*") }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_add_photo),
                contentDescription = "navigationIconContentDescription",
                tint = Color.Unspecified,
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun CarDetailSection(
    title: String,
    options: List<FilterOption>,
    type: Type,
    onDataChange: (type: Type, filterOption: FilterOption) -> Unit
) {
    val selectedOption = options.firstOrNull { it.isSelected }
    TitleSection(text = title)
    FlowRow(
        modifier = Modifier
            .padding(end = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        options.forEach {
            FilterChip(
                modifier = Modifier
                    .wrapContentWidth(),
                selected = it.isSelected,
                onClick = {
                    if (type != Type.FEATURES) {
                        if (selectedOption == null || selectedOption == it) {
                            onDataChange(type, it)
                        }
                    } else {
                        onDataChange(type, it)
                    }
                },
                colors = ChipDefaults.filterChipColors(
                    backgroundColor = if (it.isSelected) MaterialTheme.colorScheme.primary.copy(
                        alpha = 0.2f
                    ) else Color.White,
                    contentColor = Color.White
                ),
                border = if (!it.isSelected) {
                    BorderStroke(1.dp, Color.Black)
                } else null,
                trailingIcon = {}
            ) {
                Text(
                    text = it.option,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarDetailSectionForNumberInput(
    title: String,
    value: Int,
    type: Type,
    onValueChange: (Type, Int) -> Unit
) {
    TitleSection(text = title)
    Row(
        modifier = Modifier
            .background(color = Color.White)
            .padding(vertical = 2.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val focusManager = LocalFocusManager.current
        TextField(
            modifier = Modifier.weight(1f),
            value = if (value != 0) {
                value.toString()
            } else {
                ""
            },
            placeholder = { Text(text = "Enter a value") },
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.moveFocus(FocusDirection.Next) }),
            onValueChange = { onValueChange(type, it?.toIntOrNull() ?: 0) })
    }
}

//TODO: add Show/Hide button for phone number, URI-s
@Composable
fun TitleSection(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(
                color = MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.2f
                ),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Text(
            text = text, modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun SellCarScreenPreview() {
    CarPlaceTheme {
        SellCarRoute()
    }
}