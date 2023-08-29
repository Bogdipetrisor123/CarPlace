package com.carplace.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.carplace.R
import com.carplace.ui.viewmodel.ChatGptUiState
import com.carplace.ui.viewmodel.ChatGptViewModel

@Composable
fun ChatGptDialog(
    viewModel: ChatGptViewModel = hiltViewModel(),
    closeDialog: () -> Unit
) {
    val uiState = viewModel.uiState
    ChatGptDialogUi(
        uiState = uiState,
        closeDialog = closeDialog,
        onTextChange = viewModel::setText,
        onSendButtonPressed = viewModel::sendMessageToApi
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatGptDialogUi(
    uiState: ChatGptUiState,
    closeDialog: () -> Unit,
    onTextChange: (String) -> Unit,
    onSendButtonPressed: () -> Unit
) {
    Dialog(onDismissRequest = closeDialog) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .background(Color.White)
                .padding(16.dp)
        ) {
            Text(
                text = "Chat GPT",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            LazyColumn(modifier = Modifier.weight(1f)) {
                item {
                    if (uiState.isLoading) {
                        LoadingIndicator()
                    } else {
                        if (uiState.chatResponse.isNotEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxWidth()
                                    .background(
                                        color = MaterialTheme.colorScheme.primary.copy(
                                            alpha = 0.2f
                                        ),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                            ) {
                                Text(text = uiState.chatResponse, modifier = Modifier.padding(8.dp))
                            }
//                            Card(
//                                shape = RoundedCornerShape(16.dp),
//                                colors = CardDefaults.cardColors(
//                                    containerColor = MaterialTheme.colorScheme.primary.copy(
//                                        alpha = 0.2f
//                                    )
//                                )
//                            ) {
//                                Text(text = uiState.chatResponse, modifier = Modifier.padding(8.dp))
//                            }
                        }
                    }
                }
            }
            OutlinedTextField(
                modifier = Modifier.padding(vertical = 8.dp),
                value = uiState.userText,
                onValueChange = onTextChange,
                trailingIcon = {
                    IconButton(
                        onClick = onSendButtonPressed
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_search),
                            contentDescription = "navigationIconContentDescription",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                })
        }
    }
}