package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DerivationStep
import com.example.ui.theme.CosmicCyan
import com.example.ui.theme.SolarAmberNorth
import com.example.ui.theme.SpaceGlassBorder
import com.example.ui.theme.SpaceGlassNavy
import com.example.ui.theme.StarlightDark
import com.example.ui.theme.StarlightMuted
import com.example.ui.theme.StarlightWhite

@Composable
fun DerivationCard(
    step: DerivationStep,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SpaceGlassNavy)
            .border(1.dp, SpaceGlassBorder, RoundedCornerShape(12.dp))
            .clickable { expanded = !expanded }
            .padding(14.dp)
            .testTag("derivation_card_${step.stepNumber}")
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Step Badge
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(CosmicCyan.copy(alpha = 0.15f))
                        .border(1.dp, CosmicCyan.copy(alpha = 0.5f), CircleShape)
                ) {
                    Text(
                        text = "${step.stepNumber}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = CosmicCyan
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = step.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = StarlightWhite,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (expanded) "Collapse derivation" else "Expand derivation",
                    tint = StarlightMuted,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Primary Math Formula Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(com.example.ui.theme.DeepObsidian)
                    .border(1.dp, SpaceGlassBorder, RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = step.formulaDisplay,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    color = CosmicCyan,
                    lineHeight = 18.sp
                )
            }

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    Text(
                        text = step.explanation,
                        style = MaterialTheme.typography.bodyMedium,
                        color = StarlightMuted,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Physical Intuition Box
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SolarAmberNorth.copy(alpha = 0.10f))
                            .border(1.dp, SolarAmberNorth.copy(alpha = 0.30f), RoundedCornerShape(8.dp))
                            .padding(10.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = "Physical intuition",
                            tint = SolarAmberNorth,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = step.physicalIntuition,
                            fontSize = 12.sp,
                            color = StarlightWhite,
                            lineHeight = 17.sp
                        )
                    }
                }
            }

            if (!expanded) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Tap to expand mathematical derivation & intuition",
                    fontSize = 10.sp,
                    color = StarlightDark
                )
            }
        }
    }
}
