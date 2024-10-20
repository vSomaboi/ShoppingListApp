package hu.bme.aut.android.shoppinglist.feature.authentication.register

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.ui.common.ShoppingListLoadingScreen
import hu.bme.aut.android.shoppinglist.ui.theme.ShoppingListTheme
import hu.bme.aut.android.shoppinglist.ui.theme.TicketShape
import hu.bme.aut.android.shoppinglist.ui.util.UiEvent
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onRegisterClick: () -> Unit = {}
){
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
                    onRegisterClick()
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
                    val (imgTop, tfEmail, tfPassword, tfConfirmPassword, btnRegister) = createRefs()

                    val glStartOfTextFields = createGuidelineFromStart(0.1f)
                    val glEndOfTextFields = createGuidelineFromEnd(0.1f)
                    val glTopOfContent = createGuidelineFromTop(0.2f)
                    val glMiddleVertically = createGuidelineFromTop(0.5f)
                    val glBottomOfContent = createGuidelineFromBottom(0.2f)

                    val marginFromMiddle = dimensionResource(id = R.dimen.margin_xl)

                    OutlinedTextField(
                        modifier = Modifier
                            .constrainAs(tfEmail) {
                                start.linkTo(glStartOfTextFields)
                                end.linkTo(glEndOfTextFields)
                                top.linkTo(glTopOfContent)
                                width = Dimension.fillToConstraints
                            }
                            .shadow(
                                elevation = dimensionResource(id = R.dimen.shadow_elevation_normal),
                                shape = OutlinedTextFieldDefaults.shape,
                                ambientColor = MaterialTheme.colorScheme.background
                            ),
                        value = state.email,
                        onValueChange = { viewModel.onEvent(RegisterEvent.EmailChanged(it)) },
                        singleLine = true,
                        supportingText = {
                            Text(
                                text = stringResource(id = R.string.email_supporting_text),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            focusedTextColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                            focusedLeadingIconColor = MaterialTheme.colorScheme.onTertiary
                        ),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_email),
                                contentDescription = null,
                                modifier = Modifier
                                    .wrapContentSize(
                                        Alignment.CenterStart,
                                        unbounded = true
                                    )
                                    .padding(start = dimensionResource(id = R.dimen.padding_m))
                            )
                        }
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .constrainAs(tfPassword) {
                                start.linkTo(glStartOfTextFields)
                                end.linkTo(glEndOfTextFields)
                                bottom.linkTo(
                                    anchor = glMiddleVertically,
                                    margin = marginFromMiddle
                                )
                                width = Dimension.fillToConstraints
                            }
                            .shadow(
                                elevation = dimensionResource(id = R.dimen.shadow_elevation_large),
                                shape = OutlinedTextFieldDefaults.shape,
                                ambientColor = MaterialTheme.colorScheme.background
                            ),
                        value = state.password,
                        onValueChange = { viewModel.onEvent(RegisterEvent.PasswordChanged(it)) },
                        singleLine = true,
                        supportingText = {
                            Text(
                                text = stringResource(id = R.string.password_supporting_text),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            focusedTextColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                            focusedLeadingIconColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                            focusedTrailingIconColor = MaterialTheme.colorScheme.onTertiary
                        ),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_password_leading),
                                contentDescription = null,
                                modifier = Modifier
                                    .wrapContentSize(
                                        Alignment.CenterStart,
                                        unbounded = true
                                    )
                                    .padding(start = dimensionResource(id = R.dimen.padding_m))
                            )
                        },
                        trailingIcon = {
                            Icon(
                                painter = if (state.isPasswordVisible) {
                                    painterResource(id = R.drawable.ic_password_shown)
                                } else {
                                    painterResource(id = R.drawable.ic_password_hidden)
                                },
                                contentDescription = null,
                                modifier = Modifier
                                    .clickable {
                                        viewModel.onEvent(RegisterEvent.PasswordVisibilityChanged)
                                    }
                                    .wrapContentSize(
                                        Alignment.CenterStart,
                                        unbounded = true
                                    )
                                    .padding(end = dimensionResource(id = R.dimen.padding_m))
                            )
                        },
                        visualTransformation = if (state.isPasswordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        }
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .constrainAs(tfConfirmPassword) {
                                start.linkTo(glStartOfTextFields)
                                end.linkTo(glEndOfTextFields)
                                top.linkTo(
                                    anchor = glMiddleVertically,
                                    margin = marginFromMiddle
                                )
                                width = Dimension.fillToConstraints
                            }
                            .shadow(
                                elevation = dimensionResource(id = R.dimen.shadow_elevation_large),
                                shape = OutlinedTextFieldDefaults.shape,
                                ambientColor = MaterialTheme.colorScheme.background
                            ),
                        value = state.password,
                        onValueChange = { viewModel.onEvent(RegisterEvent.ConfirmPasswordChanged(it)) },
                        singleLine = true,
                        supportingText = {
                            Text(
                                text = stringResource(id = R.string.confirm_password_supporting_text),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            focusedTextColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                            focusedLeadingIconColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                            focusedTrailingIconColor = MaterialTheme.colorScheme.onTertiary
                        ),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_password_leading),
                                contentDescription = null,
                                modifier = Modifier
                                    .wrapContentSize(
                                        Alignment.CenterStart,
                                        unbounded = true
                                    )
                                    .padding(start = dimensionResource(id = R.dimen.padding_m))
                            )
                        },
                        trailingIcon = {
                            Icon(
                                painter = if (state.isPasswordVisible) {
                                    painterResource(id = R.drawable.ic_password_shown)
                                } else {
                                    painterResource(id = R.drawable.ic_password_hidden)
                                },
                                contentDescription = null,
                                modifier = Modifier
                                    .clickable {
                                        viewModel.onEvent(RegisterEvent.ConfirmPasswordVisibilityChanged)
                                    }
                                    .wrapContentSize(
                                        Alignment.CenterStart,
                                        unbounded = true
                                    )
                                    .padding(end = dimensionResource(id = R.dimen.padding_m))
                            )
                        },
                        visualTransformation = if (state.isPasswordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        }
                    )
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                        .constrainAs(btnRegister) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(glBottomOfContent)
                            width = Dimension.wrapContent
                        }
                        .shadow(
                            elevation = dimensionResource(id = R.dimen.shadow_elevation_normal),
                            shape = TicketShape()
                        )
                        .clip(
                            shape = TicketShape()
                        )
                        .background(
                            color = if (isPressed) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.secondary
                            }
                        )
                        .border(
                            width = dimensionResource(id = R.dimen.border_normal),
                            shape = TicketShape(),
                            brush = SolidColor(
                                value = if (isPressed) {
                                    MaterialTheme.colorScheme.onPrimary
                                } else {
                                    MaterialTheme.colorScheme.onSecondary
                                }
                            )
                        )
                    ){
                        Button(
                            onClick = { viewModel.onEvent(RegisterEvent.RegisterClicked) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = if (isPressed) {
                                    MaterialTheme.colorScheme.onPrimary
                                } else {
                                    MaterialTheme.colorScheme.onSecondary
                                }
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.register_button_text),
                                style = MaterialTheme.typography.titleLarge
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
fun RegisterPreview(){
    val viewModel = RegisterViewModel()
    ShoppingListTheme {
        RegisterScreen(viewModel)
    }
}