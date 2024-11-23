package hu.bme.aut.android.shoppinglist.feature.main

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.ui.common.AddProductDialog
import hu.bme.aut.android.shoppinglist.ui.common.ShoppingListLoadingScreen
import hu.bme.aut.android.shoppinglist.ui.theme.ShoppingListTheme
import hu.bme.aut.android.shoppinglist.ui.util.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    newContactButtonClicked: () -> Unit = {},
    notificationsButtonClicked: () -> Unit = {},
    createButtonClicked: () -> Unit = {}
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

    if(state.isDialogOpen){
        AddProductDialog(
            onDismissRequest = { viewModel.onEvent(MainEvent.DialogDismissed) },
            dataProvider = viewModel
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(
                        onClick = { newContactButtonClicked() }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add_contact),
                            contentDescription = stringResource(id = R.string.new_contact_button_content_description)
                        )
                    }
                    IconButton(
                        onClick = { notificationsButtonClicked() }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_notifications),
                            contentDescription = stringResource(id = R.string.notifications_button_content_description)
                        )
                    }
                    IconButton(
                        onClick = { viewModel.onEvent(MainEvent.DialogOpened) }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = stringResource(id = R.string.add_product_button_content_description)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    actionIconContentColor = MaterialTheme.colorScheme.onTertiary
                )
            )
        },
        content = {paddingValues ->
            if(state.isLoading){
                ShoppingListLoadingScreen()
            }
            else{
                ConstraintLayout(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background)
                ) {
                    val (tOwnLists, ownList, btnCreateList, tSharedLists, sharedList) = createRefs()

                    val glBottomOfContent = createGuidelineFromBottom(0.1f)
                    val glStartOfContent = createGuidelineFromStart(0.1f)
                    val glEndOfContent = createGuidelineFromEnd(0.1f)
                    val glVerticalCenter = createGuidelineFromTop(0.5f)

                    val margin = dimensionResource(id = R.dimen.margin_xl)
                    val smallMargin = dimensionResource(id = R.dimen.margin_s)

                    Text(
                        modifier = Modifier
                            .constrainAs(tOwnLists){
                                start.linkTo(glStartOfContent)
                                top.linkTo(
                                    anchor = parent.top,
                                    margin = margin
                                )
                            },
                        text = stringResource(id = R.string.own_lists_text),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    LazyColumn(
                        modifier = Modifier
                            .constrainAs(ownList) {
                                start.linkTo(glStartOfContent)
                                end.linkTo(glEndOfContent)
                                top.linkTo(tOwnLists.bottom)
                                bottom.linkTo(
                                    anchor = btnCreateList.top,
                                    margin = smallMargin
                                )
                                width = Dimension.fillToConstraints
                                height = Dimension.fillToConstraints
                            }
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(size = dimensionResource(id = R.dimen.corner_rounding))
                            )
                            .padding(dimensionResource(id = R.dimen.padding_m))
                    ) {
                        items(state.ownLists, key = { list -> list.firebaseId }){ list ->
                            ListItem(
                                modifier = Modifier
                                    .clickable {
                                           viewModel.onEvent(MainEvent.ListClicked(list))
                                    },
                                headlineContent = {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = list.name,
                                            style = MaterialTheme.typography.titleLarge,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        IconButton(
                                            onClick = { viewModel.onEvent(MainEvent.ModifyOwnClicked(list)) }
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_edit),
                                                contentDescription = stringResource(id = R.string.edit_button_content_description),
                                                tint = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    }
                                },
                                supportingContent = {
                                    HorizontalDivider(
                                        thickness = dimensionResource(id = R.dimen.border_thin),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            )
                        }
                    }

                    Button(
                        modifier = Modifier
                            .constrainAs(btnCreateList){
                                end.linkTo(glEndOfContent)
                                top.linkTo(glVerticalCenter)
                                bottom.linkTo(glVerticalCenter)

                            },
                        onClick = { createButtonClicked() },
                        border = BorderStroke(
                            width = dimensionResource(id = R.dimen.border_thin),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.create_new_list_button_text),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    Text(
                        modifier = Modifier
                            .constrainAs(tSharedLists){
                                start.linkTo(glStartOfContent)
                                top.linkTo(btnCreateList.bottom)
                            },
                        text = stringResource(id = R.string.shared_lists_text),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    LazyColumn(
                        modifier = Modifier
                            .constrainAs(sharedList) {
                                start.linkTo(glStartOfContent)
                                end.linkTo(glEndOfContent)
                                top.linkTo(tSharedLists.bottom)
                                bottom.linkTo(glBottomOfContent)
                                width = Dimension.fillToConstraints
                                height = Dimension.fillToConstraints
                            }
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(size = dimensionResource(id = R.dimen.corner_rounding))
                            )
                            .padding(dimensionResource(id = R.dimen.padding_m))
                    ) {
                        items(state.sharedLists, key = { list -> list.firebaseId }){ list ->
                            ListItem(
                                modifier = Modifier
                                    .clickable {
                                        viewModel.onEvent(MainEvent.ListClicked(list))
                                    },
                                headlineContent = {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = list.name,
                                            style = MaterialTheme.typography.titleLarge,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        IconButton(
                                            onClick = { viewModel.onEvent(MainEvent.ModifySharedClicked(list)) }
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_edit),
                                                contentDescription = stringResource(id = R.string.edit_button_content_description),
                                                tint = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    }
                                },
                                supportingContent = {
                                    HorizontalDivider(
                                        thickness = dimensionResource(id = R.dimen.border_thin),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            )

                        }
                    }
                }
            }
        }
    )
}

/*
@Composable
@Preview
fun MainPreview(){
    val viewModel = MainViewModel()
    ShoppingListTheme {
        MainScreen(viewModel)
    }
}*/
