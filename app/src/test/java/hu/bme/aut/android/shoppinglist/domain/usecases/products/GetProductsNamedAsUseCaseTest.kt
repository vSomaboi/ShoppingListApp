package hu.bme.aut.android.shoppinglist.domain.usecases.products

import hu.bme.aut.android.shoppinglist.data.products.ProductService
import hu.bme.aut.android.shoppinglist.domain.model.Product
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetProductsNamedAsUseCaseTest {
    private lateinit var getProductsNamedAsUseCase: GetProductsNamedAsUseCase
    @Mock
    private lateinit var mockProductService: ProductService

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        getProductsNamedAsUseCase = GetProductsNamedAsUseCase(mockProductService)
    }

    @Test
    fun testGetProductsNamedAs() : Unit = runBlocking{
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

        whenever(mockProductService.getProductsNamedAs("testName")).thenReturn(listOf(mockProductData[0]))
        whenever(mockProductService.getProductsNamedAs("anotherName")).thenReturn(listOf(mockProductData[1]))
        whenever(mockProductService.getProductsNamedAs("notExistingName")).thenReturn(emptyList())

        val data1 = getProductsNamedAsUseCase.invoke("testName").getOrThrow()
        val data2 = getProductsNamedAsUseCase.invoke("anotherName").getOrThrow()
        val errorData = getProductsNamedAsUseCase.invoke("notExistingName").getOrThrow()

        assertEquals(data1.first().id, "testId")
        assertEquals(data2.first().selectedAmount, 0f)
        assertEquals(errorData.isEmpty(), true)
    }
}