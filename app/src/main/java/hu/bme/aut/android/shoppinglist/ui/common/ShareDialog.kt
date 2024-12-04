package hu.bme.aut.android.shoppinglist.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.util.IShareDialogUser

@Composable
fun ShareDialog(
    onDismissRequest: () -> Unit,
    dataProvider : IShareDialogUser
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.secondary),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_l)),
                    text = stringResource(id = R.string.info_text),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
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
                items(dataProvider.getShoppingLists(), key = { list -> list.firebaseId }){ list ->
                    ListItem(
                        modifier = Modifier
                            .clickable {
                                       dataProvider.listSelected(list)
                            },
                        headlineContent = {
                            Text(
                                text = list.name,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    )
                }
            }
        }
    }
}