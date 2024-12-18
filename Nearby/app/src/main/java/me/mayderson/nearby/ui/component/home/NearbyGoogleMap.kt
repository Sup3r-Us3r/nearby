package me.mayderson.nearby.ui.component.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.mayderson.nearby.R
import me.mayderson.nearby.data.model.mock.mockUserLocation
import me.mayderson.nearby.ui.screen.home.HomeUiState
import me.mayderson.nearby.ui.util.findNorthEastPoint
import me.mayderson.nearby.ui.util.findSouthWestPoint
import okhttp3.internal.toImmutableList
import kotlin.math.roundToInt

@Composable
fun NearbyGoogleMap(modifier: Modifier = Modifier, uiState: HomeUiState) {
  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()
  val density = LocalDensity.current

  val cameraPositionState = rememberCameraPositionState {
    position = CameraPosition.fromLatLngZoom(mockUserLocation, 13f)
  }
  val uiSettings by remember {
    mutableStateOf(MapUiSettings(zoomControlsEnabled = true))
  }

  GoogleMap(
    modifier = modifier.fillMaxSize(),
    cameraPositionState = cameraPositionState,
    uiSettings = uiSettings
  ) {
    context.getDrawable(R.drawable.ic_user_location)?.let {
      Marker(
        state = MarkerState(position = mockUserLocation),
        icon = BitmapDescriptorFactory.fromBitmap(
          it.toBitmap(
            width = density.run { 72.dp.toPx() }.roundToInt(),
            height = density.run { 72.dp.toPx() }.roundToInt(),
          )
        )
      )
    }

    if (!uiState.markets.isNullOrEmpty()) {
      context.getDrawable(R.drawable.img_pin)?.let {
        uiState.marketLocations?.toImmutableList()?.forEachIndexed { index, location ->
          Marker(
            state = MarkerState(position = location),
            icon = BitmapDescriptorFactory.fromBitmap(
              it.toBitmap(
                width = density.run { 36.dp.toPx() }.roundToInt(),
                height = density.run { 36.dp.toPx() }.roundToInt(),
              )
            ),
            title = uiState.markets[index].name
          )
        }.also {
          coroutineScope.launch {
            val allMarks = uiState.marketLocations?.plus(mockUserLocation)

            val southWestPoint = findSouthWestPoint(allMarks.orEmpty())
            val northEastPoint = findNorthEastPoint(allMarks.orEmpty())

            val centerPointLatitude =
              (southWestPoint.latitude + northEastPoint.latitude) / 2
            val centerPointLongitude =
              (southWestPoint.longitude + northEastPoint.longitude) / 2

            val cameraUpdate = CameraUpdateFactory.newCameraPosition(
              CameraPosition(
                LatLng(centerPointLatitude, centerPointLongitude),
                13f,
                0f,
                0f
              )
            )

            delay(200)
            cameraPositionState.animate(cameraUpdate, durationMs = 500)
          }
        }
      }
    }
  }
}