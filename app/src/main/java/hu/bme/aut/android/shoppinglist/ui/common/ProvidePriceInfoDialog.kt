package hu.bme.aut.android.shoppinglist.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.util.IProvidePriceDialogUser

@Composable
fun ProvidePriceInfoDialog(
    onDismissRequest: () -> Unit,
    dataProvider: IProvidePriceDialogUser,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(dimensionResource(id = R.dimen.padding_m)),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_rounding)),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            border = BorderStroke(
                width = dimensionResource(id = R.dimen.border_normal),
                color = MaterialTheme.colorScheme.onTertiary
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.padding_m)),
                    value = dataProvider.getSearchbarInput(),
                    onValueChange = { dataProvider.updateSearchbarInput(it) },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                        focusedContainerColor = MaterialTheme.colorScheme.secondary,
                        unfocusedTextColor = MaterialTheme.colorScheme.primary,
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onTertiary,
                        focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                        focusedLabelColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    label = {
                        Text(
                            text = stringResource(id = R.string.searchbar_placeholder_text)
                        )
                    }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_m)),
                    value = dataProvider.getLidlPrice(),
                    onValueChange = { dataProvider.updateLidlPrice(it) },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                        focusedContainerColor = MaterialTheme.colorScheme.secondary,
                        unfocusedTextColor = MaterialTheme.colorScheme.primary,
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onTertiary,
                        focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                        focusedLabelColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    label = {
                        Text(
                            text = stringResource(id = R.string.lidl_price_label)
                        )
                    }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_m)),
                    value = dataProvider.getTescoPrice(),
                    onValueChange = { dataProvider.updateTescoPrice(it) },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                        focusedContainerColor = MaterialTheme.colorScheme.secondary,
                        unfocusedTextColor = MaterialTheme.colorScheme.primary,
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onTertiary,
                        focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                        focusedLabelColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    label = {
                        Text(
                            text = stringResource(id = R.string.tesco_price_label)
                        )
                    }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_m)),
                    value = dataProvider.getSparPrice(),
                    onValueChange = { dataProvider.updateSparPrice(it) },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                        focusedContainerColor = MaterialTheme.colorScheme.secondary,
                        unfocusedTextColor = MaterialTheme.colorScheme.primary,
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onTertiary,
                        focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                        focusedLabelColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    label = {
                        Text(
                            text = stringResource(id = R.string.spar_price_label)
                        )
                    }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        modifier = Modifier
                            .padding(bottom = dimensionResource(id = R.dimen.padding_m)),
                        onClick = { dataProvider.processInfoDialogResult() },
                        border = BorderStroke(
                            width = dimensionResource(id = R.dimen.border_thin),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.submit_button_text),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    Button(
                        modifier = Modifier
                            .padding(bottom = dimensionResource(id = R.dimen.padding_m)),
                        onClick = { onDismissRequest() },
                        border = BorderStroke(
                            width = dimensionResource(id = R.dimen.border_thin),
                            color = MaterialTheme.colorScheme.onPrimary
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