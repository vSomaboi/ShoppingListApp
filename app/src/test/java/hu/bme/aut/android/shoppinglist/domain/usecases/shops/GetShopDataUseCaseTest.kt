package hu.bme.aut.android.shoppinglist.domain.usecases.shops

import hu.bme.aut.android.shoppinglist.data.shops.ShopService
import hu.bme.aut.android.shoppinglist.domain.model.Shop
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.assertEquals
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.anyString
import org.mockito.kotlin.whenever


@RunWith(MockitoJUnitRunner::class)
class GetShopDataUseCaseTest {
    private lateinit var getShopDataUseCase: GetShopDataUseCase
    @Mock
    private lateinit var mockShopService: ShopService

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        getShopDataUseCase = GetShopDataUseCase(mockShopService)
    }
    @Test
    fun testGetShopData() : Unit = runBlocking{
        val mockShopData = Shop(id = "testId", name = "testShop", 1, 5)
        whenever(mockShopService.getShopData(anyString())).thenReturn(mockShopData)

        val data = getShopDataUseCase.invoke("testShop").getOrThrow()
        assertEquals(data.id, "testId")
        assertEquals(data.name, "testShop")
        assertEquals(data.numberOfRatings, 1)
        assertEquals(data.estimatedWaitingTime, 5)
    }


}