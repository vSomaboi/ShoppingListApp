package hu.bme.aut.android.shoppinglist.feature.info

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.ui.common.PriceInfoChart
import hu.bme.aut.android.shoppinglist.ui.common.ProvidePriceInfoDialog
import hu.bme.aut.android.shoppinglist.ui.common.ShoppingListLoadingScreen
import hu.bme.aut.android.shoppinglist.ui.util.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationScreen(
    viewModel: InformationViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

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

    if(state.isInfoDialogOpen){
        ProvidePriceInfoDialog(
            onDismissRequest = { viewModel.onEvent(InformationEvent.ProvideInfoDialogDismissed) },
            dataProvider = viewModel
        )
    }

    Scaffold(
        topBar = {
                 TopAppBar(
                     title = {
                             Text(
                                 text = stringResource(id = R.string.info_screen_title),
                                 style = MaterialTheme.typography.titleLarge,
                                 color = MaterialTheme.colorScheme.onTertiary
                             )
                     },
                     actions = {
                         IconButton(
                             onClick = { viewModel.onEvent(InformationEvent.ProvideInfoDialogOpened) }
                         ) {
                             Icon(
                                 painter = painterResource(id = R.drawable.ic_add),
                                 contentDescription = null
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
            }else{
                ConstraintLayout(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background)
                ) {
                    val (tfProductName, priceChart, shopSelectorRow, estimatedTime, contributeRow) = createRefs()

                    val glTopOfContent = createGuidelineFromTop(0.2f)
                    val glBottomOfChart = createGuidelineFromBottom(0.4f)
                    val glStartOfContent = createGuidelineFromStart(0.1f)
                    val glEndOfContent = createGuidelineFromEnd(0.1f)

                    val margin = dimensionResource(id = R.dimen.margin_xl)

                    OutlinedTextField(
                        modifier = Modifier
                            .constrainAs(tfProductName) {
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
                        value = state.searchInput,
                        onValueChange = { viewModel.onEvent(InformationEvent.SearchInputChanged(it))},
                        singleLine = true,
                        label = {
                            Text(
                                text = stringResource(id = R.string.searchbar_placeholder_text),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = { viewModel.onEvent(InformationEvent.LoadChart) }
                            ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_search),
                                        contentDescription = null
                                    )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            focusedTextColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                            focusedBorderColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                            focusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                            focusedTrailingIconColor = MaterialTheme.colorScheme.onTertiary
                        )
                    )
                    PriceInfoChart(
                        dataProvider = viewModel,
                        modifier = Modifier
                            .constrainAs(priceChart){
                                start.linkTo(glStartOfContent)
                                end.linkTo(glEndOfContent)
                                top.linkTo(
                                    anchor = tfProductName.bottom,
                                    margin = margin
                                )
                                bottom.linkTo(glBottomOfChart)
                                width = Dimension.fillToConstraints
                                height = Dimension.fillToConstraints
                            }
                    )
                    Row(
                        modifier = Modifier
                            .constrainAs(shopSelectorRow){
                                start.linkTo(glStartOfContent)
                                end.linkTo(glEndOfContent)
                                top.linkTo(
                                    anchor = priceChart.bottom,
                                    margin = margin
                                )
                                width = Dimension.fillToConstraints
                                height = Dimension.wrapContent
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { viewModel.onEvent(InformationEvent.WaitInfoButtonClicked("lidl")) },
                            border = BorderStroke(
                                width = dimensionResource(id = R.dimen.border_thin),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.lidl_price_label).dropLast(1),
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                        Button(
                            onClick = { viewModel.onEvent(InformationEvent.WaitInfoButtonClicked("tesco")) },
                            border = BorderStroke(
                                width = dimensionResource(id = R.dimen.border_thin),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.tesco_price_label).dropLast(1),
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                        Button(
                            onClick = { viewModel.onEvent(InformationEvent.WaitInfoButtonClicked("spar")) },
                            border = BorderStroke(
                                width = dimensionResource(id = R.dimen.border_thin),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.spar_price_label).dropLast(1),
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .constrainAs(estimatedTime){
                                start.linkTo(glStartOfContent)
                                end.linkTo(glEndOfContent)
                                top.linkTo(
                                    anchor = shopSelectorRow.bottom,
                                    margin = margin
                                )
                                width = Dimension.fillToConstraints
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = stringResource(id = R.string.estimated_waiting_time_info_text),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "${state.estimatedWaitTime} min",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        ) 
                    }
                    Row(
                        modifier = Modifier
                            .constrainAs(contributeRow){
                                start.linkTo(glStartOfContent)
                                end.linkTo(glEndOfContent)
                                top.linkTo(estimatedTime.bottom)
                                bottom.linkTo(parent.bottom)
                                width = Dimension.fillToConstraints
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .shadow(
                                    elevation = dimensionResource(id = R.dimen.shadow_elevation_large),
                                    shape = OutlinedTextFieldDefaults.shape,
                                    ambientColor = MaterialTheme.colorScheme.background
                                ),
                            value = state.contributionInput,
                            onValueChange = { viewModel.onEvent(InformationEvent.ContributionInputChanged(it))},
                            singleLine = true,
                            label = {
                                Text(
                                    text = stringResource(id = R.string.contribute_input_field_label),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                focusedTextColor = MaterialTheme.colorScheme.onTertiary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                                focusedBorderColor = MaterialTheme.colorScheme.onTertiary,
                                unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                                focusedLabelColor = MaterialTheme.colorScheme.onTertiary
                            )
                        )
                        Button(
                            onClick = { viewModel.onEvent(InformationEvent.ContributeButtonPressed) },
                            border = BorderStroke(
                                width = dimensionResource(id = R.dimen.border_thin),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.contribute_button_text),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    )
}