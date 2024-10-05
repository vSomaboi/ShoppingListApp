package hu.bme.aut.android.shoppinglist.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.ui.theme.ShoppingListTheme

@Composable
fun ShoppingListLoadingScreen() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        val (progressIndicator, tfLoading, imgBackGround) = createRefs()
        val marginUnderProgressIndicator = dimensionResource(id = R.dimen.margin_xl)

        Image(
            painter = painterResource(id = R.drawable.loading_screen_background),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            alpha = 0.3f,
            modifier = Modifier
                .constrainAs(imgBackGround){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )

        CircularProgressIndicator(
            modifier = Modifier
                .constrainAs(progressIndicator){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = stringResource(id = R.string.loading_text),
            modifier = Modifier
                .constrainAs(tfLoading){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(
                        anchor = progressIndicator.bottom,
                        margin = marginUnderProgressIndicator
                    )
                },
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge
        )

    }
}

@Preview
@Composable
fun LoadingScreenPreview(){
    ShoppingListTheme {
        ShoppingListLoadingScreen()
    }
}