package hu.bme.aut.android.shoppinglist.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
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
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.util.ISelectionDialogUser
import hu.bme.aut.android.shoppinglist.util.iconMinSize
import hu.bme.aut.android.shoppinglist.util.selectedAmountInputFieldSize

@Composable
fun ProductSelectionDialog(
    onDismissRequest: () -> Unit,
    dataProvider: ISelectionDialogUser
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .padding(dimensionResource(id = R.dimen.padding_m)),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_rounding)),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            border = BorderStroke(
                width = dimensionResource(id = R.dimen.border_thin),
                color = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.padding_m)),
                    value = dataProvider.getSearchBarInput(),
                    onValueChange = { dataProvider.updateSearchBar(it) },
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
                            text = stringResource(id = R.string.searchbar_label)
                        )
                    }
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.padding_l))
                        .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_rounding)))
                        .background(
                            color = MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_rounding))
                        )
                        .border(
                            width = dimensionResource(id = R.dimen.border_extra_thin),
                            brush = SolidColor(value = MaterialTheme.colorScheme.onTertiary),
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_rounding))
                        )
                ) {
                    items(dataProvider.getItemList(), key = { product -> product.id }){product ->
                        ListItem(
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.tertiary
                            ),
                            headlineContent = {
                                Column{
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = product.name,
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.onTertiary,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Button(
                                            border = BorderStroke(
                                                width = dimensionResource(id = R.dimen.border_thin),
                                                color = MaterialTheme.colorScheme.onSecondary
                                            ),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = MaterialTheme.colorScheme.secondary
                                            ),
                                            onClick = {
                                                dataProvider.processSelectionResult(product)
                                                onDismissRequest()
                                            }
                                        ) {
                                            Text(
                                                text = stringResource(id = R.string.add_button_text),
                                                style = MaterialTheme.typography.bodyLarge,
                                                color = MaterialTheme.colorScheme.onSecondary
                                            )
                                        }
                                    }

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column {
                                            Text(
                                                text = buildAnnotatedString {
                                                    withStyle(style = SpanStyle(
                                                        color = MaterialTheme.colorScheme.onTertiary
                                                    )){
                                                        append(stringResource(id = R.string.lidl_price_label))
                                                    }
                                                    append(" ")
                                                    withStyle(style = SpanStyle(
                                                        color = MaterialTheme.colorScheme.primary,
                                                        fontStyle = FontStyle.Italic
                                                    )){
                                                        append("${product.lidlPrices.lastOrNull()?.price ?: "Unknown"}")
                                                    }
                                                    withStyle(style = SpanStyle(
                                                        color = MaterialTheme.colorScheme.onTertiary,
                                                        fontWeight = FontWeight.Bold
                                                    )){
                                                        append(stringResource(id = R.string.price_unit_huf))
                                                    }
                                                }
                                            )
                                            Text(
                                                text = buildAnnotatedString {
                                                    withStyle(style = SpanStyle(
                                                        color = MaterialTheme.colorScheme.onTertiary
                                                    )){
                                                        append(stringResource(id = R.string.tesco_price_label))
                                                    }
                                                    append(" ")
                                                    withStyle(style = SpanStyle(
                                                        color = MaterialTheme.colorScheme.primary,
                                                        fontStyle = FontStyle.Italic
                                                    )){
                                                        append("${product.tescoPrices.lastOrNull()?.price ?: "Unknown"}")
                                                    }
                                                    withStyle(style = SpanStyle(
                                                        color = MaterialTheme.colorScheme.onTertiary,
                                                        fontWeight = FontWeight.Bold
                                                    )){
                                                        append(stringResource(id = R.string.price_unit_huf))
                                                    }
                                                }
                                            )
                                        }
                                        Column {
                                            Text(
                                                text = buildAnnotatedString {
                                                    withStyle(style = SpanStyle(
                                                        color = MaterialTheme.colorScheme.onTertiary
                                                    )){
                                                        append(stringResource(id = R.string.spar_price_label))
                                                    }
                                                    append(" ")
                                                    withStyle(style = SpanStyle(
                                                        color = MaterialTheme.colorScheme.primary,
                                                        fontStyle = FontStyle.Italic
                                                    )){
                                                        append("${product.sparPrices.lastOrNull()?.price ?: "Unknown"}")
                                                    }
                                                    withStyle(style = SpanStyle(
                                                        color = MaterialTheme.colorScheme.onTertiary,
                                                        fontWeight = FontWeight.Bold
                                                    )){
                                                        append(stringResource(id = R.string.price_unit_huf))
                                                    }
                                                }
                                            )
                                        }
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        IconButton(
                                            onClick = {
                                                dataProvider.changeSelectedAmountWholePart(product, -1)
                                            },
                                            modifier = Modifier
                                                .requiredSize(iconMinSize.dp)
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_decrease),
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                        OutlinedTextField(
                                            modifier = Modifier
                                                .padding(
                                                    dimensionResource(id = R.dimen.padding_s)
                                                )
                                                .requiredWidth(selectedAmountInputFieldSize.dp),
                                            value = product.selectedAmountWholePart.toString(),
                                            onValueChange = { dataProvider.setSelectedAmountWholePart(product, it) },
                                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                                textAlign = TextAlign.Center,
                                                letterSpacing = 0.sp
                                            ),
                                            colors = OutlinedTextFieldDefaults.colors(
                                                unfocusedTextColor = MaterialTheme.colorScheme.onTertiary,
                                                focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                                                unfocusedBorderColor = MaterialTheme.colorScheme.onTertiary,
                                                focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                                                unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                                                focusedContainerColor = MaterialTheme.colorScheme.secondary
                                            )
                                        )
                                        Text(
                                            text = ".",
                                            style = MaterialTheme.typography.bodyLarge.copy(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 30.sp
                                            ),
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier
                                                .requiredWidth(10.dp)
                                        )
                                        OutlinedTextField(
                                            modifier = Modifier
                                                .padding(dimensionResource(id = R.dimen.padding_s))
                                                .requiredWidth(selectedAmountInputFieldSize.dp),
                                            value = product.selectedAmountFractionPart.toString(),
                                            onValueChange = { dataProvider.setSelectedAmountFractionPart(product, it) },
                                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                                textAlign = TextAlign.Center
                                            ),
                                            colors = OutlinedTextFieldDefaults.colors(
                                                unfocusedTextColor = MaterialTheme.colorScheme.onTertiary,
                                                focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                                                unfocusedBorderColor = MaterialTheme.colorScheme.onTertiary,
                                                focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                                                unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                                                focusedContainerColor = MaterialTheme.colorScheme.secondary
                                            )
                                        )
                                        IconButton(onClick = {
                                                dataProvider.changeSelectedAmountWholePart(product, 1)
                                            },
                                            modifier = Modifier
                                                .requiredSize(iconMinSize.dp)
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_increase),
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    }
                                }
                            },
                            supportingContent = {
                                HorizontalDivider(
                                    thickness = dimensionResource(id = R.dimen.border_thin),
                                    color = MaterialTheme.colorScheme.onTertiary
                                )
                            }
                        )

                    }
                }
                Button(
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

/*@Composable
@Preview
fun SelectionDialogPreview(){
    ShoppingListTheme {
        ProductSelectionDialog(
            onDismissRequest = {
  //TODO
 },
            dataProvider = CreateShoppingListViewModel()
        )
    }
}*/
