package hu.bme.aut.android.shoppinglist.util

import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModel
import hu.bme.aut.android.shoppinglist.feature.info.InfoChartState
import kotlinx.coroutines.flow.StateFlow

interface IPriceInfoChartUser {
    val chartState: StateFlow<InfoChartState>
}