package hu.bme.aut.android.shoppinglist.feature.contacts

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.ui.common.AddContactDialog
import hu.bme.aut.android.shoppinglist.ui.common.ShareDialog
import hu.bme.aut.android.shoppinglist.ui.util.UiEvent
import hu.bme.aut.android.shoppinglist.util.iconMinSize

@Composable
fun ContactsScreen(
    viewModel: ContactsViewModel = hiltViewModel()
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
        viewModel.onEvent(ContactEvent.LoadContacts)
    }

    if(state.isContactDialogOpen){
        AddContactDialog(
            onDismissRequest = { viewModel.onEvent(ContactEvent.AddContactDialogDismissed) },
            dataProvider = viewModel)
    }

    if(state.isShareDialogOpen){
        ShareDialog(
            onDismissRequest = { viewModel.onEvent(ContactEvent.ShareDialogDismissed) },
            dataProvider = viewModel
        )
    }

    Scaffold(
        content = {paddingValues ->
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                val (btnAddContact, contactsList) = createRefs()
                val largeMargin = dimensionResource(id = R.dimen.margin_l)

                Button(
                    modifier = Modifier
                        .constrainAs(btnAddContact){
                            end.linkTo(
                                anchor = parent.end,
                                margin = largeMargin
                            )
                            top.linkTo(
                                anchor = parent.top,
                                margin = largeMargin
                            )
                        },
                    onClick = { viewModel.onEvent(ContactEvent.AddContactDialogOpened) },
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(end = dimensionResource(id = R.dimen.padding_m)),
                            text = stringResource(id = R.string.add_contact_button_text),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Icon(
                            modifier = Modifier
                                .requiredSize(iconMinSize.dp)
                                .padding(dimensionResource(id = R.dimen.padding_s)),
                            painter = painterResource(id = R.drawable.ic_add_contact),
                            contentDescription = null
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .constrainAs(contactsList) {
                            start.linkTo(
                                anchor = parent.start,
                                margin = largeMargin
                            )
                            end.linkTo(
                                anchor = parent.end,
                                margin = largeMargin
                            )
                            top.linkTo(
                                anchor = btnAddContact.bottom,
                                margin = largeMargin
                            )
                            bottom.linkTo(
                                anchor = parent.bottom,
                                margin = largeMargin
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
                    items(state.contactList, key = { contact -> contact }){ contact ->
                        ListItem(
                            headlineContent = { 
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = contact,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onTertiary
                                    )
                                    IconButton(
                                        onClick = {
                                            viewModel.onEvent(
                                                ContactEvent.ShareDialogOpened(contact)
                                            )
                                        },
                                        colors = IconButtonDefaults.iconButtonColors(
                                            contentColor = if(isPressed){
                                                MaterialTheme.colorScheme.primary
                                            }else{
                                                MaterialTheme.colorScheme.onTertiary
                                            }
                                        )
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_share),
                                            contentDescription = null
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            viewModel.onEvent(
                                                ContactEvent.ContactRemoved(contact)
                                            )
                                        },
                                        colors = IconButtonDefaults.iconButtonColors(
                                            contentColor = if(isPressed){
                                                MaterialTheme.colorScheme.primary
                                            }else{
                                                MaterialTheme.colorScheme.onTertiary
                                            }
                                        )
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_delete),
                                            contentDescription = null
                                        )
                                    }
                                }
                            },
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.tertiary
                            ),
                            supportingContent = {
                                HorizontalDivider(
                                    thickness = dimensionResource(id = R.dimen.border_thin),
                                    color = MaterialTheme.colorScheme.onTertiary
                                )
                            }
                        )
                    }
                }

            }
        }
    )
}
