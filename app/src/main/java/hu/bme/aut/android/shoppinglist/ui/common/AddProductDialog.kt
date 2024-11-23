package hu.bme.aut.android.shoppinglist.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.util.IAddProductDialogUser

@Composable
fun AddProductDialog(
    onDismissRequest: () -> Unit,
    dataProvider : IAddProductDialogUser
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
                width = dimensionResource(id = R.dimen.border_normal),
                color = MaterialTheme.colorScheme.onSurface
            )
        ){
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val (tfName, tInfo , tfLidlPrice, tfTescoPrice, tfSparPrice, btnSubmit, btnCancel) = createRefs()

                val glVerticalCenter = createGuidelineFromStart(0.5f)

                val largeMargin = dimensionResource(id = R.dimen.margin_l)
                val smallMargin = dimensionResource(id = R.dimen.margin_s)

                OutlinedTextField(
                    modifier = Modifier
                        .constrainAs(tfName){
                            top.linkTo(
                                anchor = parent.top,
                                margin = smallMargin
                            )
                            start.linkTo(
                                anchor = parent.start,
                                margin = smallMargin
                            )
                            end.linkTo(
                                anchor = parent.end,
                                margin = smallMargin
                            )
                            width = Dimension.fillToConstraints
                        },
                    value = dataProvider.getProductName(),
                    onValueChange = { dataProvider.updateName(it) },
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
                            text = stringResource(id = R.string.label_name)
                        )
                    }
                )
                Text(
                    modifier = Modifier
                        .constrainAs(tInfo){
                            start.linkTo(
                                anchor = parent.start,
                                margin = largeMargin
                            )
                            top.linkTo(tfName.bottom)
                        },
                    text = stringResource(id = R.string.add_product_dialog_info_text)
                )
                OutlinedTextField(
                    modifier = Modifier
                        .constrainAs(tfLidlPrice){
                            top.linkTo(tInfo.bottom)
                            start.linkTo(
                                anchor = parent.start,
                                margin = smallMargin
                            )
                            end.linkTo(
                                anchor = glVerticalCenter,
                                margin = smallMargin
                            )
                            width = Dimension.fillToConstraints
                        },
                    value = dataProvider.getLidlPrice(),
                    onValueChange = { dataProvider.updateLidlPrice(it) },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                        focusedContainerColor = MaterialTheme.colorScheme.secondary,
                        unfocusedTextColor = MaterialTheme.colorScheme.primary,
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedPrefixColor = MaterialTheme.colorScheme.onTertiary,
                        focusedPrefixColor = MaterialTheme.colorScheme.onSecondary,
                        unfocusedSuffixColor = MaterialTheme.colorScheme.onTertiary,
                        focusedSuffixColor = MaterialTheme.colorScheme.onSecondary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onTertiary,
                        focusedBorderColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    prefix = {
                        Text(
                            text = stringResource(id = R.string.lidl_price_label),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    suffix = {
                        Text(
                            text = stringResource(id = R.string.price_unit_huf),
                            style = MaterialTheme.typography.bodyLarge,
                            fontStyle = FontStyle.Italic
                        )
                    }
                )

                OutlinedTextField(
                    modifier = Modifier
                        .constrainAs(tfTescoPrice){
                            top.linkTo(tInfo.bottom)
                            start.linkTo(
                                anchor = glVerticalCenter,
                                margin = smallMargin
                            )
                            end.linkTo(
                                anchor = parent.end,
                                margin = smallMargin
                            )
                            width = Dimension.fillToConstraints
                        },
                    value = dataProvider.getTescoPrice(),
                    onValueChange = { dataProvider.updateTescoPrice(it) },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                        focusedContainerColor = MaterialTheme.colorScheme.secondary,
                        unfocusedTextColor = MaterialTheme.colorScheme.primary,
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedPrefixColor = MaterialTheme.colorScheme.onTertiary,
                        focusedPrefixColor = MaterialTheme.colorScheme.onSecondary,
                        unfocusedSuffixColor = MaterialTheme.colorScheme.onTertiary,
                        focusedSuffixColor = MaterialTheme.colorScheme.onSecondary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onTertiary,
                        focusedBorderColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    prefix = {
                        Text(
                            text = stringResource(id = R.string.tesco_price_label),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    suffix = {
                        Text(
                            text = stringResource(id = R.string.price_unit_huf),
                            style = MaterialTheme.typography.bodyLarge,
                            fontStyle = FontStyle.Italic
                        )
                    }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .constrainAs(tfSparPrice){
                            start.linkTo(
                                anchor = parent.start,
                                margin = smallMargin
                            )
                            end.linkTo(
                                anchor = glVerticalCenter,
                                margin = smallMargin
                            )
                            top.linkTo(
                                anchor = tfLidlPrice.bottom,
                                margin = smallMargin
                            )
                            width = Dimension.fillToConstraints
                        },
                    value = dataProvider.getSparPrice(),
                    onValueChange = { dataProvider.updateSparPrice(it) },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                        focusedContainerColor = MaterialTheme.colorScheme.secondary,
                        unfocusedTextColor = MaterialTheme.colorScheme.primary,
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedPrefixColor = MaterialTheme.colorScheme.onTertiary,
                        focusedPrefixColor = MaterialTheme.colorScheme.onSecondary,
                        unfocusedSuffixColor = MaterialTheme.colorScheme.onTertiary,
                        focusedSuffixColor = MaterialTheme.colorScheme.onSecondary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onTertiary,
                        focusedBorderColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    prefix = {
                        Text(
                            text = stringResource(id = R.string.spar_price_label),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    suffix = {
                        Text(
                            text = stringResource(id = R.string.price_unit_huf),
                            style = MaterialTheme.typography.bodyLarge,
                            fontStyle = FontStyle.Italic
                        )
                    }
                )
                Button(
                    modifier = Modifier
                        .constrainAs(btnSubmit){
                                               start.linkTo(parent.start)
                            end.linkTo(glVerticalCenter)
                            top.linkTo(tfSparPrice.bottom)
                            bottom.linkTo(parent.bottom)
                        },
                    onClick = { dataProvider.processDialogResult() },
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
                        .constrainAs(btnCancel){
                            start.linkTo(glVerticalCenter)
                            end.linkTo(parent.end)
                            top.linkTo(tfSparPrice.bottom)
                            bottom.linkTo(parent.bottom)
                        },
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

/*
@Composable
@Preview
fun AddDialogPreview(){
    ShoppingListTheme {
        AddProductDialog(
            onDismissRequest = { */
/*TODO*//*
 },
            dataProvider = MainViewModel())
    }
}*/
