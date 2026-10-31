package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.model.CosmicEpoch
import com.example.model.CosmoParams
import com.example.model.SimulationMetrics
import com.example.ui.theme.CosmicCyan
import com.example.ui.theme.DeepObsidian
import com.example.ui.theme.PolarBlueSouth
import com.example.ui.theme.PulsarViolet
import com.example.ui.theme.RadiationMiddleLight
import com.example.ui.theme.SolarAmberNorth
import com.example.ui.theme.SpaceGlassBorder
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

data class VisualizerLayers(
    val showFieldLines: Boolean = true,
    val showDomainWalls: Boolean = true,
    val showDecouplingSurface: Boolean = true,
    val showPolesAndVectors: Boolean = true
)

@Composable
fun RadialCosmosVisualizer(
    epoch: CosmicEpoch,
    params: CosmoParams,
    metrics: SimulationMetrics,
    layers: VisualizerLayers,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "CosmicPulse")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "PulsePhase"
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(DeepObsidian)
            .border(1.dp, SpaceGlassBorder, RoundedCornerShape(16.dp))
            .testTag("radial_cosmos_visualizer")
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val maxRadius = (size.minDimension / 2f) * 0.90f

            // Dynamic expansion factor depending on the cosmic epoch
            val expansion = when (epoch) {
                CosmicEpoch.COLLISION -> 0.25f
                CosmicEpoch.CPI_MAGNETOGENESIS -> 0.45f
                CosmicEpoch.STRATIFICATION -> 0.70f
                CosmicEpoch.DECOUPLING -> 0.88f
                CosmicEpoch.PRESENT_ERA -> 1.00f
            }

            drawBackgroundGrid(center, maxRadius)

            // Draw Decoupling Surface Sphere (tau ~ 1)
            val rDecoupling = maxRadius * 0.78f * expansion
            if (layers.showDecouplingSurface && epoch >= CosmicEpoch.STRATIFICATION) {
                drawDecouplingSphere(center, rDecoupling, epoch == CosmicEpoch.DECOUPLING)
            }

            // Draw Magnetic Dipole Field Lines (N to S loops)
            val rOuterShell = maxRadius * expansion
            if (layers.showFieldLines && epoch >= CosmicEpoch.CPI_MAGNETOGENESIS) {
                drawDipoleFieldLines(center, rOuterShell, pulse, params.mu5OverT.toFloat())
            }

            // Draw Concentric Domain Walls (kink boundaries)
            if (layers.showDomainWalls && epoch >= CosmicEpoch.STRATIFICATION) {
                drawDomainWalls(center, rOuterShell, pulse)
            }

            // Draw Outer Magnetic Shell (Israel Junction Boundary)
            drawOuterShell(center, rOuterShell, metrics.isOutwardPressureDominated)

            // Draw Core "Middle Light" Radiation Core
            val rCore = (maxRadius * 0.16f).coerceAtLeast(18f)
            drawMiddleLightCore(center, rCore, pulse, epoch)

            // Draw Polar Markers & Anisotropy Axis
            if (layers.showPolesAndVectors && epoch >= CosmicEpoch.CPI_MAGNETOGENESIS) {
                drawPolarIndicators(center, rOuterShell, params.anisotropyEpsilon.toFloat())
            }
        }
    }
}

private fun DrawScope.drawBackgroundGrid(center: Offset, maxRadius: Float) {
    // Subtle cosmic radar rings
    for (step in 1..4) {
        val r = maxRadius * (step / 4f)
        drawCircle(
            color = Color(0x15FFFFFF),
            radius = r,
            center = center,
            style = Stroke(width = 1f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 8f)))
        )
    }
}

private fun DrawScope.drawOuterShell(center: Offset, radius: Float, isOutwardPressure: Boolean) {
    // Shell glow
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(Color(0x33B388FF), Color(0x05B388FF), Color.Transparent),
            center = center,
            radius = radius * 1.15f
        ),
        radius = radius * 1.15f,
        center = center
    )

    // Shell Boundary Line
    val shellColor = if (isOutwardPressure) PulsarViolet else Color(0xFF7E57C2)
    drawCircle(
        color = shellColor,
        radius = radius,
        center = center,
        style = Stroke(width = 3.5f)
    )
}

private fun DrawScope.drawDomainWalls(center: Offset, rOuter: Float, pulse: Float) {
    val wallFractions = listOf(0.35f, 0.52f, 0.68f)
    wallFractions.forEachIndexed { idx, frac ->
        val baseR = rOuter * frac
        val waveR = baseR + sin((pulse * 2 * PI + idx * 1.5).toFloat()) * 2f
        drawCircle(
            color = CosmicCyan.copy(alpha = 0.55f + idx * 0.15f),
            radius = waveR,
            center = center,
            style = Stroke(
                width = 2.2f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 6f))
            )
        )
    }
}

private fun DrawScope.drawMiddleLightCore(center: Offset, rCore: Float, pulse: Float, epoch: CosmicEpoch) {
    val coreGlowAlpha = (0.4f + 0.3f * sin(pulse * 2 * PI.toFloat())).coerceIn(0.2f, 0.9f)
    
    // Radiant dispersion
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(
                RadiationMiddleLight.copy(alpha = coreGlowAlpha),
                Color(0x44FFA000),
                Color.Transparent
            ),
            center = center,
            radius = rCore * 2.5f
        ),
        radius = rCore * 2.5f,
        center = center
    )

    // Central core
    drawCircle(
        color = RadiationMiddleLight,
        radius = rCore,
        center = center
    )

    // Photons radiating outwards if in collision or stratification
    if (epoch == CosmicEpoch.COLLISION || epoch == CosmicEpoch.STRATIFICATION) {
        val numRays = 8
        for (i in 0 until numRays) {
            val angle = (i * (2 * PI / numRays) + pulse * PI).toFloat()
            val startDist = rCore * 1.1f
            val endDist = rCore * (1.8f + 0.4f * sin(pulse * 2 * PI.toFloat()))
            val pStart = Offset(center.x + cos(angle) * startDist, center.y + sin(angle) * startDist)
            val pEnd = Offset(center.x + cos(angle) * endDist, center.y + sin(angle) * endDist)
            drawLine(
                color = RadiationMiddleLight.copy(alpha = 0.7f),
                start = pStart,
                end = pEnd,
                strokeWidth = 2.5f
            )
        }
    }
}

private fun DrawScope.drawDecouplingSphere(center: Offset, rDec: Float, isCurrentEpoch: Boolean) {
    val alpha = if (isCurrentEpoch) 0.85f else 0.40f
    drawCircle(
        color = Color(0xFFFFD54F).copy(alpha = alpha),
        radius = rDec,
        center = center,
        style = Stroke(
            width = if (isCurrentEpoch) 2.5f else 1.5f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 6f))
        )
    )
}

private fun DrawScope.drawDipoleFieldLines(
    center: Offset,
    rOuter: Float,
    pulse: Float,
    intensity: Float
) {
    val baseRadii = listOf(0.40f, 0.65f, 0.90f, 1.15f)
    val northY = center.y - rOuter
    val southY = center.y + rOuter

    baseRadii.forEachIndexed { index, scale ->
        val xSpread = rOuter * scale * (0.8f + 0.2f * intensity)
        val pathLeft = Path().apply {
            moveTo(center.x, northY)
            cubicTo(
                center.x - xSpread, center.y - rOuter * 0.4f,
                center.x - xSpread, center.y + rOuter * 0.4f,
                center.x, southY
            )
        }
        val pathRight = Path().apply {
            moveTo(center.x, northY)
            cubicTo(
                center.x + xSpread, center.y - rOuter * 0.4f,
                center.x + xSpread, center.y + rOuter * 0.4f,
                center.x, southY
            )
        }

        val alpha = (0.35f + 0.15f * sin((pulse * 2 * PI + index).toFloat())).coerceIn(0.15f, 0.75f)
        val lineColor = if (scale > 1.0f) Color(0xFF7986CB) else PulsarViolet

        drawPath(pathLeft, color = lineColor.copy(alpha = alpha), style = Stroke(width = 1.8f))
        drawPath(pathRight, color = lineColor.copy(alpha = alpha), style = Stroke(width = 1.8f))
    }
}

private fun DrawScope.drawPolarIndicators(center: Offset, rOuter: Float, epsilon: Float) {
    val northPos = Offset(center.x, center.y - rOuter)
    val southPos = Offset(center.x, center.y + rOuter)

    // Axis of Anisotropy Line
    drawLine(
        color = Color(0x60FFFFFF),
        start = Offset(center.x, center.y - rOuter * 1.25f),
        end = Offset(center.x, center.y + rOuter * 1.25f),
        strokeWidth = 1.5f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(4f, 6f))
    )

    // North Magnetic Pole Marker (Amber)
    drawCircle(color = SolarAmberNorth, radius = 7f, center = northPos)
    val northArrow = Path().apply {
        moveTo(northPos.x, northPos.y - 14f)
        lineTo(northPos.x - 6f, northPos.y - 4f)
        lineTo(northPos.x + 6f, northPos.y - 4f)
        close()
    }
    drawPath(northArrow, color = SolarAmberNorth)

    // South Magnetic Pole Marker (Blue)
    drawCircle(color = PolarBlueSouth, radius = 7f, center = southPos)
    val southArrow = Path().apply {
        moveTo(southPos.x, southPos.y + 14f)
        lineTo(southPos.x - 6f, southPos.y + 4f)
        lineTo(southPos.x + 6f, southPos.y + 4f)
        close()
    }
    drawPath(southArrow, color = PolarBlueSouth)
}
