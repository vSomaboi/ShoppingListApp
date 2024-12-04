package hu.bme.aut.android.shoppinglist.feature.info

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
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

@Composable
fun InformationScreen(
    viewModel: InformationViewModel = hiltViewModel()
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

    Scaffold(
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
                    val (tfProductName, priceDiagram, shopSelector, estimatedTime) = createRefs()

                    val glTopOfContent = createGuidelineFromTop(0.2f)
                    val glBottomOfContent = createGuidelineFromBottom(0.2f)
                    val glStartOfContent = createGuidelineFromStart(0.1f)
                    val glEndOfContent = createGuidelineFromEnd(0.1f)
                    val glVerticalCenter = createGuidelineFromTop(0.5f)

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

                    
                }
            }
        }
    )
}

@Composable
@Preview
fun InfoScreenPreview(){
    val vm = InformationViewModel()
    ShoppingListTheme {
        InformationScreen(
            viewModel = vm
        )
    }
}