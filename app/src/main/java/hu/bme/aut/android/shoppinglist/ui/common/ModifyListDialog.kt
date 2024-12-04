package hu.bme.aut.android.shoppinglist.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.util.IModifyListDialogUser

@Composable
fun ModifyListDialog(
    onDismissRequest: () -> Unit,
    dataProvider : IModifyListDialogUser
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.padding_m)),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_rounding)),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            border = BorderStroke(
                width = dimensionResource(id = R.dimen.border_normal),
                color = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .shadow(
                            elevation = dimensionResource(id = R.dimen.shadow_elevation_large),
                            shape = OutlinedTextFieldDefaults.shape,
                            ambientColor = MaterialTheme.colorScheme.background
                        )
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(id = R.dimen.padding_m),
                            end = dimensionResource(id = R.dimen.padding_m)
                        ),
                    value = dataProvider.getListName(),
                    onValueChange = { dataProvider.updateListName(it)},
                    singleLine = true,
                    label = {
                        Text(
                            text = stringResource(id = R.string.list_name_supporting_text),
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(id = R.dimen.padding_m),
                            end = dimensionResource(id = R.dimen.padding_m)
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.text_above_list),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    IconButton(
                        onClick = { dataProvider.openSelectionDialog() },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.onBackground
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = stringResource(id = R.string.add_item_content_description)
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_rounding))
                        )
                        .border(
                            width = dimensionResource(id = R.dimen.border_extra_thin),
                            brush = SolidColor(value = MaterialTheme.colorScheme.onSurface),
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_rounding))
                        )
                        .padding(dimensionResource(id = R.dimen.padding_l)),
                    contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_m))
                ) {
                    items(dataProvider.getModifiedItemList(), key = { product -> product.id}){ product ->
                        ListItem(
                            headlineContent = {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(
                                            text = product.name,
                                            color = MaterialTheme.colorScheme.onTertiary,
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                        Text(
                                            text = product.selectedAmount.toString(),
                                            color = MaterialTheme.colorScheme.onTertiary,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                    IconButton(
                                        onClick = { dataProvider.deleteListItem(product) }
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_delete),
                                            contentDescription = stringResource(id = R.string.edit_button_content_description),
                                            tint = MaterialTheme.colorScheme.onTertiary
                                        )
                                    }
                                }
                            },
                            supportingContent = {
                                HorizontalDivider(
                                    thickness = dimensionResource(id = R.dimen.border_thin),
                                    color = MaterialTheme.colorScheme.onTertiary
                                )
                            },
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.tertiary
                            )
                        )
                    }

                }
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { dataProvider.updateList() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        border = BorderStroke(
                            width = dimensionResource(id = R.dimen.border_thin),
                            color = MaterialTheme.colorScheme.onSecondary
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = dimensionResource(id = R.dimen.shadow_elevation_small),
                            pressedElevation = dimensionResource(id = R.dimen.shadow_elevation_normal)
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.update_button_text),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    Button(
                        onClick = { onDismissRequest() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        border = BorderStroke(
                            width = dimensionResource(id = R.dimen.border_thin),
                            color = MaterialTheme.colorScheme.onSecondary
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = dimensionResource(id = R.dimen.shadow_elevation_small),
                            pressedElevation = dimensionResource(id = R.dimen.shadow_elevation_normal)
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.cancel_dialog),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }

        }
    }
}