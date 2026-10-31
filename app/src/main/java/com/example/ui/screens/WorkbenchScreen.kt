package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CosmoParams
import com.example.model.SimulationMetrics
import com.example.model.SpectrumPoint
import com.example.ui.components.BFieldRedshiftChart
import com.example.ui.components.CMBMultipoleChart
import com.example.ui.components.ParameterSlider
import com.example.ui.components.SGWBChart
import com.example.ui.theme.CosmicCyan
import com.example.ui.theme.DeepObsidian
import com.example.ui.theme.PolarBlueSouth
import com.example.ui.theme.PulsarViolet
import com.example.ui.theme.SolarAmberNorth
import com.example.ui.theme.SpaceGlassBorder
import com.example.ui.theme.SpaceGlassNavy
import com.example.ui.theme.StatusPass
import com.example.ui.theme.StarlightDark
import com.example.ui.theme.StarlightMuted
import com.example.ui.theme.StarlightWhite
import java.util.Locale

@Composable
fun WorkbenchScreen(
    params: CosmoParams,
    metrics: SimulationMetrics,
    activeChartMode: Int,
    sgwbPoints: List<SpectrumPoint>,
    cmbPoints: List<SpectrumPoint>,
    bFieldPoints: List<SpectrumPoint>,
    onSelectChartMode: (Int) -> Unit,
    onUpdateTGUT: (Double) -> Unit,
    onUpdateMu5OverT: (Double) -> Unit,
    onUpdateLambdaAxion: (Double) -> Unit,
    onUpdateAnisotropyEpsilon: (Double) -> Unit,
    onUpdateWallTension: (Double) -> Unit,
    onUpdateReservoirW: (Double) -> Unit,
    onResetParams: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DeepObsidian)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("workbench_screen")
    ) {
        // Screen Title & Reset Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Observational Spectra Workbench",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = StarlightWhite
                )
                Text(
                    text = "LISA GW, Planck CMB Rings, and Relic B-fields",
                    style = MaterialTheme.typography.bodySmall,
                    color = StarlightMuted
                )
            }

            ElevatedButton(
                onClick = onResetParams,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = CosmicCyan.copy(alpha = 0.15f),
                    contentColor = CosmicCyan
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("button_reset_parameters")
            ) {
                Icon(Icons.Default.RestartAlt, contentDescription = "Reset parameters", modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Reset", fontSize = 11.sp)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Chart Selector Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val chartModes = listOf(
                "LISA SGWB Background",
                "CMB Rings (C_ℓ Multipoles)",
                "Relic B-field Redshift"
            )
            chartModes.forEachIndexed { index, title ->
                val isSelected = activeChartMode == index
                FilterChip(
                    selected = isSelected,
                    onClick = { onSelectChartMode(index) },
                    label = { Text(title, fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = CosmicCyan.copy(alpha = 0.25f),
                        selectedLabelColor = CosmicCyan,
                        containerColor = SpaceGlassNavy,
                        labelColor = StarlightMuted
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isSelected,
                        selectedBorderColor = CosmicCyan,
                        borderColor = SpaceGlassBorder
                    ),
                    modifier = Modifier.testTag("chip_chart_mode_$index")
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Active Chart Display
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
        ) {
            when (activeChartMode) {
                0 -> SGWBChart(
                    points = sgwbPoints,
                    peakLogOmega = metrics.omegaGWPeakLog10,
                    fPeakHz = metrics.fPeakHz,
                    modifier = Modifier.fillMaxSize()
                )
                1 -> CMBMultipoleChart(
                    points = cmbPoints,
                    epsilon = params.anisotropyEpsilon,
                    modifier = Modifier.fillMaxSize()
                )
                2 -> BFieldRedshiftChart(
                    points = bFieldPoints,
                    b0GaussStr = metrics.b0GaussString,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Summary Metric Badges
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MetricPill(
                label = "Peak Ω_GW",
                value = String.format(Locale.US, "10^(%.1f)", metrics.omegaGWPeakLog10),
                caption = "LISA Detection Window",
                accentColor = PulsarViolet,
                modifier = Modifier.weight(1f)
            )
            MetricPill(
                label = "Relic B_0",
                value = metrics.b0GaussString,
                caption = if (metrics.b0GaussLog10 < -9.0) "Passes Planck Limit" else "Near Planck Bound",
                accentColor = PolarBlueSouth,
                modifier = Modifier.weight(1f)
            )
            MetricPill(
                label = "ΔN_eff",
                value = String.format(Locale.US, "%.3f", metrics.deltaNeff),
                caption = "BBN Bound < 0.30",
                accentColor = StatusPass,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Interactive Parameter Tuner Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(SpaceGlassNavy)
                .border(1.dp, SpaceGlassBorder, RoundedCornerShape(14.dp))
                .padding(14.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Tune, contentDescription = "Parameter Tuner", tint = CosmicCyan, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Cosmological Parameter Space",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = StarlightWhite
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // 1. T_GUT Slider
                ParameterSlider(
                    label = "GUT Energy Scale",
                    symbol = "T_GUT",
                    value = params.tGUT_log10.toFloat(),
                    valueDisplay = "10^${String.format(Locale.US, "%.1f", params.tGUT_log10)} GeV",
                    range = 14.0f..17.0f,
                    testTagKey = "t_gut",
                    onValueChange = { onUpdateTGUT(it.toDouble()) }
                )

                // 2. mu5 / T Slider
                ParameterSlider(
                    label = "Chiral Asymmetry Ratio",
                    symbol = "μ_5 / T",
                    value = params.mu5OverT.toFloat(),
                    valueDisplay = String.format(Locale.US, "%.2f", params.mu5OverT),
                    range = 0.05f..2.0f,
                    testTagKey = "mu5",
                    onValueChange = { onUpdateMu5OverT(it.toDouble()) }
                )

                // 3. Lambda Axion Slider
                ParameterSlider(
                    label = "Axial Decay Constant",
                    symbol = "Λ_axial",
                    value = params.lambdaAxion_log10.toFloat(),
                    valueDisplay = "10^${String.format(Locale.US, "%.1f", params.lambdaAxion_log10)} GeV",
                    range = 14.0f..17.5f,
                    testTagKey = "lambda_axion",
                    onValueChange = { onUpdateLambdaAxion(it.toDouble()) }
                )

                // 4. Anisotropy Epsilon
                ParameterSlider(
                    label = "Collision Anisotropy Amplitude",
                    symbol = "ε_coll",
                    value = params.anisotropyEpsilon.toFloat(),
                    valueDisplay = String.format(Locale.US, "%.3f", params.anisotropyEpsilon),
                    range = 0.001f..0.05f,
                    testTagKey = "epsilon",
                    onValueChange = { onUpdateAnisotropyEpsilon(it.toDouble()) }
                )

                // 5. Bare Wall Tension
                ParameterSlider(
                    label = "Domain Wall Tension",
                    symbol = "σ_wall",
                    value = params.wallTensionSigma.toFloat(),
                    valueDisplay = String.format(Locale.US, "%.2f M_Pl", params.wallTensionSigma),
                    range = 0.2f..3.0f,
                    testTagKey = "wall_tension",
                    onValueChange = { onUpdateWallTension(it.toDouble()) }
                )

                // 6. Reservoir Equation of State
                ParameterSlider(
                    label = "Reservoir EoS Parameter",
                    symbol = "w_res",
                    value = params.reservoirW.toFloat(),
                    valueDisplay = String.format(Locale.US, "%.2f", params.reservoirW),
                    range = -1.0f..1.0f,
                    testTagKey = "reservoir_w",
                    onValueChange = { onUpdateReservoirW(it.toDouble()) }
                )
            }
        }
    }
}

@Composable
fun MetricPill(
    label: String,
    value: String,
    caption: String,
    accentColor: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(SpaceGlassNavy)
            .border(1.dp, SpaceGlassBorder, RoundedCornerShape(10.dp))
            .padding(10.dp)
    ) {
        Column {
            Text(text = label, fontSize = 10.sp, color = StarlightMuted)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = accentColor
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = caption, fontSize = 9.sp, color = StarlightDark)
        }
    }
}
