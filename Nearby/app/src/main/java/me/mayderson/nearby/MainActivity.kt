package me.mayderson.nearby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import me.mayderson.nearby.data.model.Market
import me.mayderson.nearby.ui.screen.home.HomeScreen
import me.mayderson.nearby.ui.screen.home.HomeViewModel
import me.mayderson.nearby.ui.screen.market_details.MarketDetailsScreen
import me.mayderson.nearby.ui.screen.market_details.MarketDetailsUiEvent
import me.mayderson.nearby.ui.screen.market_details.MarketDetailsViewModel
import me.mayderson.nearby.ui.screen.qrcode_scanner.QRCodeScannerScreen
import me.mayderson.nearby.ui.screen.route.Home
import me.mayderson.nearby.ui.screen.route.QRCodeScanner
import me.mayderson.nearby.ui.screen.route.Splash
import me.mayderson.nearby.ui.screen.route.Welcome
import me.mayderson.nearby.ui.screen.splash.SplashScreen
import me.mayderson.nearby.ui.screen.welcome.WelcomeScreen
import me.mayderson.nearby.ui.theme.NearbyTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      NearbyTheme {
        val navController = rememberNavController()

        val homeViewModel by viewModels<HomeViewModel>()
        val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()
        val marketDetailsViewModel by viewModels<MarketDetailsViewModel>()
        val marketDetailsUiState by marketDetailsViewModel.uiState.collectAsStateWithLifecycle()

        NavHost(
          navController = navController,
          startDestination = Splash
        ) {
          composable<Splash> {
            SplashScreen(
              onNavigateToWelcome = {
                navController.navigate(Welcome)
              }
            )
          }
          composable<Welcome> {
            WelcomeScreen(
              onNavigateToHome = {
                navController.navigate(Home)
              }
            )
          }
          composable<Home> {
            HomeScreen(
              uiState = homeUiState,
              onEvent = homeViewModel::onEvent,
              onNavigateToMarketDetails = { selectedMarket ->
                navController.navigate(selectedMarket)
              }
            )
          }
          composable<Market> {
            val selectedMarket = it.toRoute<Market>()

            MarketDetailsScreen(
              market = selectedMarket,
              uiState = marketDetailsUiState,
              onEvent = marketDetailsViewModel::onEvent,
              onNavigateToQRCodeScanner = {
                navController.navigate(QRCodeScanner)
              },
              onNavigateBack = {
                navController.popBackStack()
              }
            )
          }
          composable<QRCodeScanner> {
            QRCodeScannerScreen(onCompletedScan = { qrCodeContent ->
              if (qrCodeContent.isNotEmpty()) {
                marketDetailsViewModel.onEvent(
                  MarketDetailsUiEvent.OnFetchCoupon(
                    qrCodeContent = qrCodeContent
                  )
                )
              }

              navController.popBackStack()
            })
          }
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
  NearbyTheme {}
}