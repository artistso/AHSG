package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.screens.AuditsScreen
import com.example.ui.screens.DerivationsScreen
import com.example.ui.screens.SimulationScreen
import com.example.ui.screens.WorkbenchScreen
import com.example.ui.theme.CosmicCyan
import com.example.ui.theme.DeepObsidian
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.PulsarViolet
import com.example.ui.theme.SpaceGlassBorder
import com.example.ui.theme.SpaceGlassNavy
import com.example.ui.theme.StarlightMuted
import com.example.ui.theme.StarlightWhite
import com.example.viewmodel.AHSGViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: AHSGViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                AHSGApp(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AHSGApp(viewModel: AHSGViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var showInfoDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "AHSG Cosmos Lab",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = StarlightWhite
                        )
                        Text(
                            text = "Anisotropic Higgs-Soup Genesis • Speculative Toy Model",
                            style = MaterialTheme.typography.bodySmall,
                            color = CosmicCyan,
                            fontSize = 11.sp
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showInfoDialog = true },
                        modifier = Modifier.testTag("button_app_info")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "About AHSG Model",
                            tint = CosmicCyan,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SpaceGlassNavy,
                    titleContentColor = StarlightWhite
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = SpaceGlassNavy,
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .testTag("bottom_nav_bar")
            ) {
                val navItems = listOf(
                    Triple(0, "Theory", Icons.Default.MenuBook),
                    Triple(1, "2D Cosmos", Icons.Default.Public),
                    Triple(2, "Spectra", Icons.Default.ShowChart),
                    Triple(3, "Audits", Icons.Default.FactCheck)
                )

                navItems.forEach { (index, label, icon) ->
                    val isSelected = uiState.currentTab == index
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { viewModel.setTab(index) },
                        icon = {
                            Icon(
                                imageVector = icon,
                                contentDescription = label,
                                modifier = Modifier.size(22.dp)
                            )
                        },
                        label = {
                            Text(
                                text = label,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = CosmicCyan,
                            selectedTextColor = CosmicCyan,
                            unselectedIconColor = StarlightMuted,
                            unselectedTextColor = StarlightMuted,
                            indicatorColor = CosmicCyan.copy(alpha = 0.2f)
                        ),
                        modifier = Modifier.testTag("nav_item_$label")
                    )
                }
            }
        },
        containerColor = DeepObsidian
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.currentTab) {
                0 -> DerivationsScreen(
                    selectedSectionId = uiState.selectedSectionId,
                    searchQuery = uiState.searchQuery,
                    onSelectSection = { viewModel.selectSection(it) },
                    onSearchQueryChange = { viewModel.setSearchQuery(it) },
                    onNavigateToSim = { viewModel.setTab(1) },
                    onNavigateToWorkbench = { viewModel.setTab(2) }
                )
                1 -> SimulationScreen(
                    epoch = uiState.currentEpoch,
                    params = uiState.params,
                    metrics = uiState.metrics,
                    isPlaying = uiState.isPlayingSimulation,
                    layers = uiState.visualizerLayers,
                    onEpochChange = { viewModel.setEpoch(it) },
                    onTogglePlayPause = { viewModel.togglePlayPauseSimulation() },
                    onToggleLayer = { viewModel.toggleLayer(it) }
                )
                2 -> WorkbenchScreen(
                    params = uiState.params,
                    metrics = uiState.metrics,
                    activeChartMode = uiState.activeChartMode,
                    sgwbPoints = uiState.sgwbPoints,
                    cmbPoints = uiState.cmbPoints,
                    bFieldPoints = uiState.bFieldPoints,
                    onSelectChartMode = { viewModel.setActiveChartMode(it) },
                    onUpdateTGUT = { viewModel.updateTGUT(it) },
                    onUpdateMu5OverT = { viewModel.updateMu5OverT(it) },
                    onUpdateLambdaAxion = { viewModel.updateLambdaAxion(it) },
                    onUpdateAnisotropyEpsilon = { viewModel.updateAnisotropyEpsilon(it) },
                    onUpdateWallTension = { viewModel.updateWallTension(it) },
                    onUpdateReservoirW = { viewModel.updateReservoirW(it) },
                    onResetParams = { viewModel.resetParameters() }
                )
                3 -> AuditsScreen(
                    audits = uiState.audits
                )
            }
        }
    }

    // Theoretical Info Dialog
    if (showInfoDialog) {
        AlertDialog(
            onDismissRequest = { showInfoDialog = false },
            title = {
                Text(
                    text = "Anisotropic Higgs-Soup Genesis",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = StarlightWhite
                )
            },
            text = {
                Column {
                    Text(
                        text = "Formalized Speculative Cosmological Toy Model",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = CosmicCyan
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "This app is an interactive scientific companion to the AHSG research paper. It simulates how an anisotropic pre-Big-Bang plasma collision drives a complex scalar condensate, generating primordial magnetic fields via Chiral Plasma Instability (CPI) with an emergent magnetic dipole shell.",
                        style = MaterialTheme.typography.bodySmall,
                        color = StarlightMuted,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Key Features:\n• Step-by-step mathematical derivations\n• 2D dynamic cosmological cross-section\n• Stochastic GW background (LISA band)\n• CMB concentric ring anomaly predictions\n• Relativistic consistency audits (ghost freedom, causality, BBN limits)",
                        fontSize = 11.sp,
                        color = StarlightWhite,
                        lineHeight = 16.sp
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showInfoDialog = false }) {
                    Text("Close", color = CosmicCyan)
                }
            },
            containerColor = SpaceGlassNavy,
            shape = RoundedCornerShape(16.dp)
        )
    }
}
