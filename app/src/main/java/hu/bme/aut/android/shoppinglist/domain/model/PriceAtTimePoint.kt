package hu.bme.aut.android.shoppinglist.domain.model

import com.google.firebase.Timestamp
import java.util.Date

data class PriceAtTimePoint(
    val price: Int = 0,
    val dateOfProviding: Timestamp = Timestamp(Date())
)