package me.mayderson.nearby.core.network

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.patch
import me.mayderson.nearby.core.network.KtorHttpClient.httpClientAndroid
import me.mayderson.nearby.data.model.Category
import me.mayderson.nearby.data.model.Coupon
import me.mayderson.nearby.data.model.Market
import me.mayderson.nearby.data.model.MarketDetails

object NearbyRemoteDataSource {
  private const val LOCAL_HOST_EMULATOR_BASE_URL = "http://10.0.2.2:3333"
//  private const val LOCAL_HOST_PHYSICAL_BASE_URL = "http://"

  private const val BASE_URL = LOCAL_HOST_EMULATOR_BASE_URL

  suspend fun getCategories(): Result<List<Category>> = try {
    val categories = httpClientAndroid
      .get("$BASE_URL/categories")
      .body<List<Category>>()

    Result.success(categories)
  } catch (error: Exception) {
    Result.failure(error)
  }

  suspend fun getMarkets(categoryId: String): Result<List<Market>> = try {
    val markets = httpClientAndroid
      .get("$BASE_URL/markets/category/${categoryId}")
      .body<List<Market>>()

    Result.success(markets)
  } catch (error: Exception) {
    Result.failure(error)
  }

  suspend fun getMarketDetails(marketId: String): Result<MarketDetails> = try {
    val market = httpClientAndroid
      .get("$BASE_URL/markets/${marketId}")
      .body<MarketDetails>()

    Result.success(market)
  } catch (error: Exception) {
    Result.failure(error)
  }


  suspend fun patchCoupon(marketId: String): Result<Coupon> = try {
    val coupon = httpClientAndroid
      .patch("$BASE_URL/coupons/${marketId}")
      .body<Coupon>()

    Result.success(coupon)
  } catch (error: Exception) {
    Result.failure(error)
  }
}