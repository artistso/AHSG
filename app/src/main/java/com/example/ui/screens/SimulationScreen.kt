package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CosmicEpoch
import com.example.model.CosmoParams
import com.example.model.SimulationMetrics
import com.example.ui.components.RadialCosmosVisualizer
import com.example.ui.components.VisualizerLayers
import com.example.ui.theme.CosmicCyan
import com.example.ui.theme.DeepObsidian
import com.example.ui.theme.PolarBlueSouth
import com.example.ui.theme.PulsarViolet
import com.example.ui.theme.RadiationMiddleLight
import com.example.ui.theme.SolarAmberNorth
import com.example.ui.theme.SpaceGlassBorder
import com.example.ui.theme.SpaceGlassNavy
import com.example.ui.theme.StatusPass
import com.example.ui.theme.StarlightDark
import com.example.ui.theme.StarlightMuted
import com.example.ui.theme.StarlightWhite
import java.util.Locale

@Composable
fun SimulationScreen(
    epoch: CosmicEpoch,
    params: CosmoParams,
    metrics: SimulationMetrics,
    isPlaying: Boolean,
    layers: VisualizerLayers,
    onEpochChange: (CosmicEpoch) -> Unit,
    onTogglePlayPause: () -> Unit,
    onToggleLayer: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showLayersMenu by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DeepObsidian)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("simulation_screen")
    ) {
        // Top Header with Play/Pause & Epoch Info
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "AHSG 2D Radial Cosmos",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = StarlightWhite
                )
                Text(
                    text = "${epoch.title} (${epoch.timeLabel})",
                    style = MaterialTheme.typography.bodySmall,
                    color = CosmicCyan
                )
            }

            // Play / Pause Simulation Button
            IconButton(
                onClick = onTogglePlayPause,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(CosmicCyan.copy(alpha = 0.2f))
                    .border(1.dp, CosmicCyan, CircleShape)
                    .testTag("button_play_pause_sim")
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause simulation" else "Play simulation",
                    tint = CosmicCyan,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Layers Toggle Button
            IconButton(
                onClick = { showLayersMenu = !showLayersMenu },
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(SpaceGlassNavy)
                    .border(1.dp, SpaceGlassBorder, CircleShape)
                    .testTag("button_toggle_layers_menu")
            ) {
                Icon(
                    imageVector = Icons.Default.Layers,
                    contentDescription = "Toggle Visualizer Layers",
                    tint = if (showLayersMenu) CosmicCyan else StarlightMuted,
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        // Layer Filter Chips (Collapsible)
        AnimatedVisibility(
            visible = showLayersMenu,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = layers.showFieldLines,
                    onClick = { onToggleLayer("field_lines") },
                    label = { Text("Field Lines", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PulsarViolet.copy(alpha = 0.25f),
                        selectedLabelColor = PulsarViolet
                    )
                )
                FilterChip(
                    selected = layers.showDomainWalls,
                    onClick = { onToggleLayer("domain_walls") },
                    label = { Text("Domain Walls", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = CosmicCyan.copy(alpha = 0.25f),
                        selectedLabelColor = CosmicCyan
                    )
                )
                FilterChip(
                    selected = layers.showDecouplingSurface,
                    onClick = { onToggleLayer("decoupling") },
                    label = { Text("Decoupling (τ ~ 1)", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = SolarAmberNorth.copy(alpha = 0.25f),
                        selectedLabelColor = SolarAmberNorth
                    )
                )
                FilterChip(
                    selected = layers.showPolesAndVectors,
                    onClick = { onToggleLayer("poles") },
                    label = { Text("Poles & Axis", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PolarBlueSouth.copy(alpha = 0.25f),
                        selectedLabelColor = PolarBlueSouth
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Central 2D Canvas Visualizer
        RadialCosmosVisualizer(
            epoch = epoch,
            params = params,
            metrics = metrics,
            layers = layers,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Epoch Timeline Slider / Scrubber Row
        Text(
            text = "Cosmic Epoch Evolution",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = StarlightMuted
        )
        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CosmicEpoch.values().forEach { ep ->
                val isSelected = ep == epoch
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isSelected) CosmicCyan.copy(alpha = 0.2f) else SpaceGlassNavy)
                        .border(
                            1.dp,
                            if (isSelected) CosmicCyan else SpaceGlassBorder,
                            RoundedCornerShape(10.dp)
                        )
                        .clickable { onEpochChange(ep) }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .testTag("epoch_chip_${ep.name}")
                ) {
                    Column {
                        Text(
                            text = ep.title,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) CosmicCyan else StarlightWhite
                        )
                        Text(
                            text = ep.timeLabel,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 10.sp,
                            color = StarlightMuted
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Real-Time Relativistic Telemetry Grid
        Text(
            text = "Relativistic Telemetry & Boundary Status",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = CosmicCyan
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TelemetryCard(
                label = "Surface Energy Σ",
                value = String.format(Locale.US, "%.2f M_Pl", metrics.surfaceEnergySigma),
                subtitle = "Σ_shell = σ + 0.5⟨B²⟩ > 0",
                valueColor = StatusPass,
                modifier = Modifier.weight(1f)
            )
            TelemetryCard(
                label = "Effective Tension τ",
                value = String.format(Locale.US, "%.2f", metrics.effectiveTensionTau),
                subtitle = if (metrics.isOutwardPressureDominated) "Outward Pressure" else "Inward Tension",
                valueColor = if (metrics.isOutwardPressureDominated) PulsarViolet else SolarAmberNorth,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TelemetryCard(
                label = "GUT Saturation B_sat",
                value = String.format(Locale.US, "10^%.1f G", metrics.bSatGaussLog10),
                subtitle = "√(α_em) μ_5 T_GUT",
                valueColor = CosmicCyan,
                modifier = Modifier.weight(1f)
            )
            TelemetryCard(
                label = "Net Monopole",
                value = "0.00",
                subtitle = "∮ B·dS ≡ 0 (Dipole)",
                valueColor = StatusPass,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Epoch Explanatory Card
        EpochPhysicsExplainer(epoch = epoch)
    }
}

@Composable
fun TelemetryCard(
    label: String,
    value: String,
    subtitle: String,
    valueColor: Color,
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
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = valueColor
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = subtitle, fontSize = 9.sp, color = StarlightDark)
        }
    }
}

@Composable
fun EpochPhysicsExplainer(epoch: CosmicEpoch) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SpaceGlassNavy)
            .border(1.dp, SpaceGlassBorder, RoundedCornerShape(12.dp))
            .padding(14.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Timeline,
                    contentDescription = "Epoch description",
                    tint = CosmicCyan,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Physics at ${epoch.title}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = StarlightWhite
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            val desc = when (epoch) {
                CosmicEpoch.COLLISION ->
                    "Anisotropic plasma collisions source the scalar condensate J_coll. The system exhibits directional asymmetry along axis n̂. Energy conservation is maintained by the covariant reservoir sector."
                CosmicEpoch.CPI_MAGNETOGENESIS ->
                    "The rolling pseudoscalar phase θ drives Maxwell modes inside the tachyonic band k < |θ'|/Λ into exponential growth. Chiral plasma instability (CPI) pumps magnetic helicity until reaching saturation B_sat ~ √(α_em) μ_5 T."
                CosmicEpoch.STRATIFICATION ->
                    "The radial field ρ develops domain-wall kinks separating vacuum steps v_i. Vacuum pressure pushes the walls outward against thermal plasma friction. Core oscillations generate 'middle light' radiation."
                CosmicEpoch.DECOUPLING ->
                    "Optical depth drops to τ(t_dec) ~ 1. Radiation that was previously trapped in the opaque plasma shell decouples cleanly at the last-scattering surface, preserving concentric ring signatures."
                CosmicEpoch.PRESENT_ERA ->
                    "Flux conservation B ∝ a⁻² has diluted the primordial magnetic field down to B_0 in cosmic voids. The closed FLRW Hamiltonian constraint E_in^eff = -E_N/S ensures total zero energy."
            }

            Text(
                text = desc,
                style = MaterialTheme.typography.bodySmall,
                color = StarlightMuted,
                lineHeight = 18.sp
            )
        }
    }
}
