package hu.bme.aut.android.shoppinglist.data.products

import androidx.paging.PagingSource
import androidx.paging.PagingState
import hu.bme.aut.android.shoppinglist.data.products.firebase.FirebaseProductService
import hu.bme.aut.android.shoppinglist.domain.model.Product



class ProductPagingSource(
    private val service: FirebaseProductService
) : PagingSource<String, Product>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, Product> {
        val start = params.key ?: STARTING_KEY
        TODO("Not yet implemented")
    }
    override fun getRefreshKey(state: PagingState<String, Product>): String? {
        TODO("Not yet implemented")
    }

    companion object{
        private const val STARTING_KEY = "a"
    }
}