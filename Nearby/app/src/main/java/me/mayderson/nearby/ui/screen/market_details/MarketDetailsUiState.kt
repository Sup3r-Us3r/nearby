package me.mayderson.nearby.ui.screen.market_details

import me.mayderson.nearby.data.model.Rule

data class MarketDetailsUiState(
  val rules: List<Rule>? = null,
  val coupon: String? = null
)
