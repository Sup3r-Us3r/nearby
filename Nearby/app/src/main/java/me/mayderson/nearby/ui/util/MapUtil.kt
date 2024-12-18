package me.mayderson.nearby.ui.util

import com.google.android.gms.maps.model.LatLng

fun findSouthWestPoint(points: List<LatLng>): LatLng {
  if (points.isEmpty()) {
    return LatLng(0.0, 0.0)
  }

  var southWestPoint = points[0]

  for (point in points) {
    if (
      point.latitude < southWestPoint.latitude ||
      (point.latitude == southWestPoint.latitude && point.longitude < southWestPoint.longitude)
    ) {
      southWestPoint = point
    }
  }

  return southWestPoint
}

fun findNorthEastPoint(points: List<LatLng>): LatLng {
  if (points.isEmpty()) {
    return LatLng(0.0, 0.0)
  }

  var northEastPoint = points[0]

  for (point in points) {
    if (
      point.latitude > northEastPoint.latitude ||
      (point.latitude == northEastPoint.latitude && point.longitude > northEastPoint.longitude)
    ) {
      northEastPoint = point
    }
  }

  return northEastPoint
}
