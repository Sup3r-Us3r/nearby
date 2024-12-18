package me.mayderson.nearby.data.model

import androidx.annotation.DrawableRes
import kotlinx.serialization.Serializable
import me.mayderson.nearby.ui.component.category.CategoryFilterChipView

@Serializable
data class Category(
  val id: String,
  val name: String
) {
  @get:DrawableRes
  val icon: Int?
    get() = CategoryFilterChipView.fromDescription(description = name)?.icon
}
