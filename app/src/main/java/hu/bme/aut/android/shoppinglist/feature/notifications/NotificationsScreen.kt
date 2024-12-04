package hu.bme.aut.android.shoppinglist.feature.notifications

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.ui.util.UiEvent

@Composable
fun NotificationsScreen(
    viewModel: NotificationsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect{uiEvent ->
            when(uiEvent){
                is UiEvent.Success -> {

                }
                is UiEvent.Notification -> {
                    Toast.makeText(context, uiEvent.message.asString(context), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    LaunchedEffect(key1 = null) {
        viewModel.onEvent(NotificationEvent.LoadNotificationList)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(color = MaterialTheme.colorScheme.surface),
                contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_l))
            ) {
                items(state.notificationList, key = {notification -> notification}){ notification ->
                    ListItem(
                        headlineContent = {
                            Column {
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.Bold
                                        )){
                                            append(notification)
                                        }
                                        append(" ")
                                        withStyle(style = SpanStyle(
                                            color = MaterialTheme.colorScheme.onTertiary
                                        )){
                                            append(stringResource(id = R.string.notification_explaining_text))
                                        }
                                    },
                                    modifier = Modifier
                                        .padding(bottom = dimensionResource(id = R.dimen.padding_l))
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Button(
                                        onClick = { viewModel.onEvent(NotificationEvent.AcceptClicked(notification)) },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if(isPressed){
                                                MaterialTheme.colorScheme.primary
                                            }else{
                                                MaterialTheme.colorScheme.secondary
                                            },
                                            contentColor = if(isPressed){
                                                MaterialTheme.colorScheme.onPrimary
                                            }else{
                                                MaterialTheme.colorScheme.onSecondary
                                            }
                                        ),
                                        border = BorderStroke(
                                            width = dimensionResource(id = R.dimen.border_thin),
                                            color = if(isPressed){
                                                MaterialTheme.colorScheme.onPrimary
                                            }else{
                                                MaterialTheme.colorScheme.onSecondary
                                            }
                                        )
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.accept_button_text),
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                    }
                                    Button(
                                        onClick = { viewModel.onEvent(NotificationEvent.DeclineClicked(notification)) },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if(isPressed){
                                                MaterialTheme.colorScheme.primary
                                            }else{
                                                MaterialTheme.colorScheme.secondary
                                            },
                                            contentColor = if(isPressed){
                                                MaterialTheme.colorScheme.onPrimary
                                            }else{
                                                MaterialTheme.colorScheme.onSecondary
                                            }
                                        ),
                                        border = BorderStroke(
                                            width = dimensionResource(id = R.dimen.border_thin),
                                            color = if(isPressed){
                                                MaterialTheme.colorScheme.onPrimary
                                            }else{
                                                MaterialTheme.colorScheme.onSecondary
                                            }
                                        )
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.decline_button_text),
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                    }
                                }
                            }
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.tertiary
                        ),
                        supportingContent = {
                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(top = dimensionResource(id = R.dimen.padding_m)),
                                thickness = dimensionResource(id = R.dimen.border_thin),
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                        }
                    )
                }

            }
        }
    )
}

/*
@Composable
@Preview
fun NotificationPreview(){
    ShoppingListTheme {
        NotificationsScreen(
            viewModel = NotificationsViewModel()
        )
    }
}*/
