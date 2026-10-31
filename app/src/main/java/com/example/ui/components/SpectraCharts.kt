package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SpectrumPoint
import com.example.ui.theme.CosmicCyan
import com.example.ui.theme.DeepObsidian
import com.example.ui.theme.PolarBlueSouth
import com.example.ui.theme.PulsarViolet
import com.example.ui.theme.SolarAmberNorth
import com.example.ui.theme.SpaceGlassBorder
import com.example.ui.theme.StarlightMuted
import com.example.ui.theme.StarlightWhite

@Composable
fun SGWBChart(
    points: List<SpectrumPoint>,
    peakLogOmega: Double,
    fPeakHz: Double,
    modifier: Modifier = Modifier
) {
    ChartCard(
        title = "Gravitational-Wave Spectrum: Ω_GW(f)",
        subtitle = "AHSG vs. LISA & NANOGrav Sensitivity Bands",
        legend = listOf(
            LegendItem("AHSG Signal", CosmicCyan),
            LegendItem("LISA Band", PulsarViolet.copy(alpha = 0.5f)),
            LegendItem("NANOGrav Limit", Color(0xFFFF5252).copy(alpha = 0.5f))
        ),
        modifier = modifier.testTag("sgwb_chart")
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val paddingLeft = 40.dp.toPx()
            val paddingBottom = 30.dp.toPx()
            val plotWidth = size.width - paddingLeft - 16.dp.toPx()
            val plotHeight = size.height - paddingBottom - 16.dp.toPx()

            // X-range: log10(f) from -9 to +2 (total span = 11)
            val minX = -9.0f
            val maxX = 2.0f
            // Y-range: log10(Omega) from -22 to -6 (total span = 16)
            val minY = -22.0f
            val maxY = -6.0f

            fun mapX(logF: Float): Float =
                paddingLeft + ((logF - minX) / (maxX - minX)) * plotWidth

            fun mapY(logOmega: Float): Float =
                (size.height - paddingBottom) - ((logOmega - minY) / (maxY - minY)) * plotHeight

            // Draw Grid Lines
            val xTicks = listOf(-9, -6, -3, 0, 2)
            xTicks.forEach { tick ->
                val x = mapX(tick.toFloat())
                drawLine(
                    color = Color(0x1AFFFFFF),
                    start = Offset(x, 16.dp.toPx()),
                    end = Offset(x, size.height - paddingBottom),
                    strokeWidth = 1f
                )
            }

            val yTicks = listOf(-20, -15, -10, -6)
            yTicks.forEach { tick ->
                val y = mapY(tick.toFloat())
                drawLine(
                    color = Color(0x1AFFFFFF),
                    start = Offset(paddingLeft, y),
                    end = Offset(size.width - 16.dp.toPx(), y),
                    strokeWidth = 1f
                )
            }

            // Detector Shading: NANOGrav 15-yr Window [-9, -7] with upper ceiling ~ 10^-8
            val nanoLeft = mapX(-9f)
            val nanoRight = mapX(-7f)
            val nanoTop = mapY(-8.5f)
            val nanoBottom = size.height - paddingBottom
            drawRect(
                color = Color(0x22FF5252),
                topLeft = Offset(nanoLeft, nanoTop),
                size = Size(nanoRight - nanoLeft, nanoBottom - nanoTop)
            )

            // Detector Shading: LISA Sensitive Band [-4, -1]
            val lisaLeft = mapX(-4f)
            val lisaRight = mapX(-1f)
            val lisaTop = mapY(-13.5f)
            drawRect(
                color = Color(0x25B388FF),
                topLeft = Offset(lisaLeft, lisaTop),
                size = Size(lisaRight - lisaLeft, nanoBottom - lisaTop)
            )

            // Plot Spectrum Curve
            if (points.isNotEmpty()) {
                val path = Path()
                points.forEachIndexed { i, p ->
                    val x = mapX(p.xLog10).coerceIn(paddingLeft, size.width - 16.dp.toPx())
                    val y = mapY(p.yLog10).coerceIn(16.dp.toPx(), size.height - paddingBottom)
                    if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                }
                drawPath(path, color = CosmicCyan, style = Stroke(width = 3.0f))
            }
        }
    }
}

@Composable
fun CMBMultipoleChart(
    points: List<SpectrumPoint>,
    epsilon: Double,
    modifier: Modifier = Modifier
) {
    ChartCard(
        title = "CMB Angular Multipoles: C_ℓ",
        subtitle = "Acoustic Peaks with Concentric Ring Anisotropy (ε = ${(epsilon * 100).toInt() / 100.0})",
        legend = listOf(
            LegendItem("AHSG Modulated", SolarAmberNorth),
            LegendItem("Isotropic Baseline", StarlightMuted)
        ),
        modifier = modifier.testTag("cmb_multipole_chart")
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val paddingLeft = 36.dp.toPx()
            val paddingBottom = 28.dp.toPx()
            val plotWidth = size.width - paddingLeft - 16.dp.toPx()
            val plotHeight = size.height - paddingBottom - 16.dp.toPx()

            val minEll = 2f
            val maxEll = 2200f
            val minPower = 0f
            val maxPower = 2200f

            fun mapX(ell: Float): Float =
                paddingLeft + ((ell - minEll) / (maxEll - minEll)) * plotWidth

            fun mapY(power: Float): Float =
                (size.height - paddingBottom) - ((power - minPower) / (maxPower - minPower)) * plotHeight

            // Draw Base Grid
            for (step in 1..4) {
                val ell = step * 500f
                val x = mapX(ell)
                drawLine(
                    color = Color(0x1AFFFFFF),
                    start = Offset(x, 16.dp.toPx()),
                    end = Offset(x, size.height - paddingBottom),
                    strokeWidth = 1f
                )
            }

            if (points.isNotEmpty()) {
                val path = Path()
                points.forEachIndexed { i, p ->
                    val x = mapX(p.xLog10)
                    val y = mapY(p.yLog10).coerceIn(16.dp.toPx(), size.height - paddingBottom)
                    if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                }
                drawPath(path, color = SolarAmberNorth, style = Stroke(width = 2.8f))
            }
        }
    }
}

@Composable
fun BFieldRedshiftChart(
    points: List<SpectrumPoint>,
    b0GaussStr: String,
    modifier: Modifier = Modifier
) {
    ChartCard(
        title = "Primordial Magnetic Evolution: B(a)",
        subtitle = "From GUT Saturation (10^49 G) to Cosmic Voids ($b0GaussStr)",
        legend = listOf(
            LegendItem("Flux Scaling B ∝ a⁻²", PolarBlueSouth),
            LegendItem("Planck Limit (10⁻⁹ G)", Color(0xFFFF5252))
        ),
        modifier = modifier.testTag("bfield_redshift_chart")
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val paddingLeft = 40.dp.toPx()
            val paddingBottom = 28.dp.toPx()
            val plotWidth = size.width - paddingLeft - 16.dp.toPx()
            val plotHeight = size.height - paddingBottom - 16.dp.toPx()

            val minLogA = -29.0f
            val maxLogA = 0.0f
            val minLogB = -20.0f
            val maxLogB = 52.0f

            fun mapX(logA: Float): Float =
                paddingLeft + ((logA - minLogA) / (maxLogA - minLogA)) * plotWidth

            fun mapY(logB: Float): Float =
                (size.height - paddingBottom) - ((logB - minLogB) / (maxLogB - minLogB)) * plotHeight

            // Planck Limit Line: 1e-9 Gauss
            val planckY = mapY(-9.0f)
            drawLine(
                color = Color(0xAAFF5252),
                start = Offset(paddingLeft, planckY),
                end = Offset(size.width - 16.dp.toPx(), planckY),
                strokeWidth = 1.8f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 6f))
            )

            if (points.isNotEmpty()) {
                val path = Path()
                points.forEachIndexed { i, p ->
                    val x = mapX(p.xLog10)
                    val y = mapY(p.yLog10).coerceIn(16.dp.toPx(), size.height - paddingBottom)
                    if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                }
                drawPath(path, color = PolarBlueSouth, style = Stroke(width = 2.8f))
            }
        }
    }
}

data class LegendItem(val label: String, val color: Color)

@Composable
private fun ChartCard(
    title: String,
    subtitle: String,
    legend: List<LegendItem>,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(DeepObsidian)
            .border(1.dp, SpaceGlassBorder, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = StarlightWhite
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = StarlightMuted
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Legend Row
            Row(modifier = Modifier.fillMaxWidth()) {
                legend.forEach { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(item.color)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = item.label,
                            fontSize = 10.sp,
                            color = StarlightMuted
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                content()
            }
        }
    }
}
