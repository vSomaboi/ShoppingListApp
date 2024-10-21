package hu.bme.aut.android.shoppinglist.feature.createshoppinglist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.SolidColor
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
import hu.bme.aut.android.shoppinglist.ui.common.ShoppingListLoadingScreen
import hu.bme.aut.android.shoppinglist.ui.theme.ShoppingListTheme
import hu.bme.aut.android.shoppinglist.ui.util.UiEvent
import kotlinx.coroutines.launch

@Composable
fun CreateShoppingListScreen(
    viewModel: CreateShoppingListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val hostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect{uiEvent ->
            when(uiEvent){
                is UiEvent.Success -> {

                }
                is UiEvent.Failure -> {
                    scope.launch {
                        hostState.showSnackbar(uiEvent.message.asString(context))
                    }
                }
            }
        }
    }

    Scaffold(
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
                    val (tfName, rowAboveList, lItems) = createRefs()

                    val glTopOfContent = createGuidelineFromTop(0.2f)
                    val glBottomOfContent = createGuidelineFromBottom(0.2f)
                    val glStartOfContent = createGuidelineFromStart(0.1f)
                    val glEndOfContent = createGuidelineFromEnd(0.1f)

                    val margin = dimensionResource(id = R.dimen.margin_xl)

                    OutlinedTextField(
                        modifier = Modifier
                            .constrainAs(tfName) {
                                start.linkTo(glStartOfContent)
                                end.linkTo(glEndOfContent)
                                bottom.linkTo(
                                    anchor = glTopOfContent,
                                    margin = margin
                                )
                                width = Dimension.fillToConstraints
                            }
                            .shadow(
                                elevation = dimensionResource(id = R.dimen.shadow_elevation_large),
                                shape = OutlinedTextFieldDefaults.shape,
                                ambientColor = MaterialTheme.colorScheme.background
                            ),
                        value = state.listName,
                        onValueChange = { viewModel.onEvent(CreateShoppingListEvent.NameChanged(it))},
                        singleLine = true,
                        label = {
                                Text(text = stringResource(id = R.string.list_name_supporting_text))
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            focusedTextColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                            focusedBorderColor = MaterialTheme.colorScheme.onTertiary
                        )
                    )

                    Row(
                        modifier = Modifier
                            .constrainAs(rowAboveList){
                                start.linkTo(glStartOfContent)
                                end.linkTo(glEndOfContent)
                                top.linkTo(glTopOfContent)
                                width = Dimension.fillToConstraints
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(id = R.string.text_above_list),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        IconButton(
                            onClick = { viewModel.onEvent(CreateShoppingListEvent.AddButtonClicked) }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add),
                                contentDescription = stringResource(id = R.string.add_item_content_description)
                            )
                        }
                    }
                    LazyColumn(
                        modifier = Modifier
                            .constrainAs(lItems) {
                                start.linkTo(glStartOfContent)
                                end.linkTo(glEndOfContent)
                                top.linkTo(rowAboveList.bottom)
                                bottom.linkTo(glBottomOfContent)
                                width = Dimension.fillToConstraints
                                height = Dimension.fillToConstraints
                            }
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_rounding))
                            )
                            .border(
                                width = dimensionResource(id = R.dimen.border_extra_thin),
                                brush = SolidColor(value = MaterialTheme.colorScheme.onSurface),
                                shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_rounding))
                            )
                            .padding(dimensionResource(id = R.dimen.padding_m))
                    ) {
                        items(state.items, key = { product -> product.id}){product ->
                            ListItem(
                                headlineContent = {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = product.name,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                        IconButton(
                                            onClick = { viewModel.onEvent(CreateShoppingListEvent.DeleteListItem(product)) }
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_delete),
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

@Composable
@Preview
fun CreateShoppingListPreview(){
    val viewModel = CreateShoppingListViewModel()
    ShoppingListTheme {
        CreateShoppingListScreen(viewModel)
    }
}