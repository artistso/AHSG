package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CosmicCyan
import com.example.ui.theme.SpaceGlassBorder
import com.example.ui.theme.StarlightMuted
import com.example.ui.theme.StarlightWhite

@Composable
fun ParameterSlider(
    label: String,
    symbol: String,
    value: Float,
    valueDisplay: String,
    range: ClosedFloatingPointRange<Float>,
    steps: Int = 0,
    testTagKey: String,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .testTag("slider_container_$testTagKey")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = symbol,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = CosmicCyan
                )
                Text(
                    text = "  $label",
                    style = MaterialTheme.typography.bodySmall,
                    color = StarlightWhite
                )
            }
            Text(
                text = valueDisplay,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = StarlightMuted
            )
        }

        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = range,
            steps = steps,
            colors = SliderDefaults.colors(
                thumbColor = CosmicCyan,
                activeTrackColor = CosmicCyan,
                inactiveTrackColor = SpaceGlassBorder
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("slider_$testTagKey")
        )
    }
}
