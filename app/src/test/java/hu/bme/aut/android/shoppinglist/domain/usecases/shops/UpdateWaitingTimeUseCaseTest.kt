package hu.bme.aut.android.shoppinglist.domain.usecases.shops

import hu.bme.aut.android.shoppinglist.data.shops.ShopService
import hu.bme.aut.android.shoppinglist.domain.model.Shop
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class UpdateWaitingTimeUseCaseTest {
    private lateinit var updateWaitingTimeUseCase: UpdateWaitingTimeUseCase
    @Mock
    private lateinit var mockShopService: ShopService

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        updateWaitingTimeUseCase = UpdateWaitingTimeUseCase(mockShopService)
    }
    @Test
    fun testUpdateWaitingTime() : Unit = runBlocking {
        val mockShopData = Shop(id = "testId", name = "testShop", 1, 5)

        whenever(mockShopService.updateWaitingTime(shopName = Mockito.anyString(), value = Mockito.anyInt())).thenAnswer{
            mockShopData.numberOfRatings++
            mockShopData.estimatedWaitingTime++
        }

        updateWaitingTimeUseCase.invoke("testShop", 7)

        Assert.assertEquals(mockShopData.numberOfRatings, 2)
        Assert.assertEquals(mockShopData.estimatedWaitingTime, 6)
    }
}