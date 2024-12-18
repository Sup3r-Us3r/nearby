package me.mayderson.nearby.ui.component.category

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.mayderson.nearby.data.model.Category
import me.mayderson.nearby.ui.theme.Gray300
import me.mayderson.nearby.ui.theme.Gray400
import me.mayderson.nearby.ui.theme.GreenBase
import me.mayderson.nearby.ui.theme.Typography

@Composable
fun NearbyCategoryFilterChip(
  modifier: Modifier = Modifier,
  category: Category,
  isSelected: Boolean,
  onClick: (isSelected: Boolean) -> Unit
) {
  FilterChip(
    modifier = modifier
        .padding(2.dp)
        .heightIn(min = 36.dp),
    elevation = FilterChipDefaults.filterChipElevation(
      elevation = 8.dp
    ),
    leadingIcon = {
      category.icon?.let {
        Icon(
          modifier = Modifier.size(16.dp),
          painter = painterResource(id = it),
          tint = if (isSelected) Color.White else Gray400,
          contentDescription = "Category filter icon"
        )
      }
    },
    border = FilterChipDefaults.filterChipBorder(
      enabled = false,
      selected = isSelected,
      disabledBorderColor = Gray300,
      borderWidth = 1.dp,
      selectedBorderColor = Color.Transparent
    ),
    colors = FilterChipDefaults.filterChipColors(
      containerColor = Color.White,
      selectedContainerColor = GreenBase
    ),
    label = {
      Text(
        text = category.name,
        style = Typography.bodyMedium,
        color = if (isSelected) Color.White else Gray400
      )
    },
    selected = isSelected,
    onClick = { onClick(!isSelected) },
  )
}

@Preview
@Composable
private fun NearbyCategoryFilterChipPreview() {
  NearbyCategoryFilterChip(
    category = Category(
      id = "1",
      name = "Alimentação"
    ),
    isSelected = true,
    onClick = {}
  )
}

@Preview
@Composable
private fun NearbyCategoryFilterChipNotSelectedPreview() {
  NearbyCategoryFilterChip(
    category = Category(
      id = "1",
      name = "Cinema"
    ),
    isSelected = false,
    onClick = {}
  )
}