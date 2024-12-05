package hu.bme.aut.android.shoppinglist.domain.usecases.products

import hu.bme.aut.android.shoppinglist.data.products.ProductService
import hu.bme.aut.android.shoppinglist.domain.model.Product
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class SaveProductUseCaseTest {
    private lateinit var saveProductUseCase: SaveProductUseCase
    @Mock
    private lateinit var mockProductService: ProductService

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        saveProductUseCase = SaveProductUseCase(mockProductService)
    }

    @Test
    fun testSaveProduct() : Unit = runBlocking {
        val mockData : MutableList<Product> = mutableListOf()

        whenever(mockProductService.saveProduct(any<Product>())).thenAnswer { invocation ->
            val param = invocation.getArgument<Product>(0)
            mockData.add(param)
        }

        saveProductUseCase.invoke(Product(id = "newId", name = "newProduct"))

        assertEquals(mockData.size, 1)
    }
}