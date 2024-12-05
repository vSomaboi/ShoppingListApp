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
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetProductWithExactNameTest {
    private lateinit var getProductWithExactNameUseCase: GetProductWithExactNameUseCase
    @Mock
    private lateinit var mockProductService: ProductService

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        getProductWithExactNameUseCase = GetProductWithExactNameUseCase(mockProductService)
    }

    @Test
    fun testGetProductWithExactName() : Unit = runBlocking {
        val mockProductData = listOf(
            Product(
                id = "testId",
                name = "testName",
                lidlPrices = emptyList(),
                tescoPrices = emptyList(),
                sparPrices = emptyList(),
                selectedAmount = 2.5f
            ),
            Product(
                id = "testId2",
                name = "anotherName"
            )
        )
        whenever(mockProductService.getProductWithExactName("testName")).thenReturn(mockProductData[0])

        val data = getProductWithExactNameUseCase.invoke("testName").getOrThrow()
        assertEquals(data.selectedAmount, 2.5f)
    }
}