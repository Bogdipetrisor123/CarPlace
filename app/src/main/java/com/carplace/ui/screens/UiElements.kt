package com.carplace.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.carplace.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarPlaceTopAppBar(@StringRes titleRes: Int, doSignOut: () -> Unit) {
    TopAppBar(title = {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.titleLarge
        )
    },
        actions = {
            IconButton(onClick = { doSignOut.invoke() }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_account),
                    contentDescription = "navigationIconContentDescription",
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        })
}

@Composable
fun LoadingIndicator() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}