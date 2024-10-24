package hu.bme.aut.android.shoppinglist.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.feature.createshoppinglist.CreateShoppingListViewModel
import hu.bme.aut.android.shoppinglist.ui.theme.ShoppingListTheme
import hu.bme.aut.android.shoppinglist.util.SelectionDialogUser

@Composable
fun ProductSelectionDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dataProvider: SelectionDialogUser
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
                        unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                        focusedContainerColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                        focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
                        focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                        focusedLabelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    label = {
                        Text(
                            text = stringResource(id = R.string.searchbar_label),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(id = R.dimen.padding_l))
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_rounding))
                        )
                        .border(
                            width = dimensionResource(id = R.dimen.border_extra_thin),
                            brush = SolidColor(value = MaterialTheme.colorScheme.onSecondary),
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_rounding))
                        ),
                    contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_m))
                ) {
                    items(dataProvider.getItemList(), key = { product -> product.id }){product ->
                        ListItem(
                            //TODO paging
                            headlineContent = { /*TODO*/ }
                        )

                    }
                }
            }
        }
        
    }
}

@Composable
@Preview
fun SelectionDialogPreview(){
    ShoppingListTheme {
        ProductSelectionDialog(
            onDismissRequest = { /*TODO*/ },
            onConfirmation = { /*TODO*/ },
            dataProvider = CreateShoppingListViewModel()
        )
    }
}