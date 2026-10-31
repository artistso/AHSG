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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.data.AHSGPaperRepository
import com.example.model.PaperSection
import com.example.ui.components.DerivationCard
import com.example.ui.theme.CosmicCyan
import com.example.ui.theme.DeepObsidian
import com.example.ui.theme.PulsarViolet
import com.example.ui.theme.SolarAmberNorth
import com.example.ui.theme.SpaceGlassBorder
import com.example.ui.theme.SpaceGlassNavy
import com.example.ui.theme.StarlightMuted
import com.example.ui.theme.StarlightWhite

@Composable
fun DerivationsScreen(
    selectedSectionId: String,
    searchQuery: String,
    onSelectSection: (String) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onNavigateToSim: () -> Unit,
    onNavigateToWorkbench: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Foundations", "Geometry", "Lagrangian Dynamics", "Magnetogenesis", "Gravitation", "Astrophysics", "Relativity", "Scalar Dynamics", "Cosmology", "Observables", "Stress Tests", "Falsification", "Derivations")

    val sections = remember(searchQuery, selectedCategory) {
        AHSGPaperRepository.sections.filter { sec ->
            val matchesCategory = selectedCategory == "All" || sec.category == selectedCategory
            val matchesSearch = if (searchQuery.isBlank()) true else {
                val q = searchQuery.trim().lowercase()
                sec.title.lowercase().contains(q) ||
                sec.summary.lowercase().contains(q) ||
                sec.number.lowercase().contains(q) ||
                sec.coreEquations.any { it.first.lowercase().contains(q) || it.second.lowercase().contains(q) } ||
                sec.derivations.any { it.title.lowercase().contains(q) || it.explanation.lowercase().contains(q) }
            }
            matchesCategory && matchesSearch
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DeepObsidian)
            .testTag("derivations_screen")
    ) {
        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = { Text("Search formal equations, CPI, Israel junction...", color = StarlightMuted, fontSize = 13.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = CosmicCyan) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchQueryChange("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear search", tint = StarlightMuted)
                    }
                }
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CosmicCyan,
                unfocusedBorderColor = SpaceGlassBorder,
                focusedTextColor = StarlightWhite,
                unfocusedTextColor = StarlightWhite,
                focusedContainerColor = SpaceGlassNavy,
                unfocusedContainerColor = SpaceGlassNavy
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .testTag("search_derivations_input")
        )

        // Categories horizontal chip row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { cat ->
                val isSelected = cat == selectedCategory
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedCategory = cat },
                    label = { Text(cat, fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = CosmicCyan.copy(alpha = 0.2f),
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
                    modifier = Modifier.testTag("chip_category_$cat")
                )
            }
        }

        // Section list with equations and derivation expanders
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(sections, key = { it.id }) { sec ->
                PaperSectionCard(
                    section = sec,
                    isSelected = sec.id == selectedSectionId,
                    onCardClick = { onSelectSection(sec.id) },
                    onNavigateToSim = onNavigateToSim,
                    onNavigateToWorkbench = onNavigateToWorkbench
                )
            }
        }
    }
}

@Composable
fun PaperSectionCard(
    section: PaperSection,
    isSelected: Boolean,
    onCardClick: () -> Unit,
    onNavigateToSim: () -> Unit,
    onNavigateToWorkbench: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(isSelected) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(if (isSelected) SpaceGlassNavy else SpaceGlassNavy.copy(alpha = 0.75f))
            .border(
                1.2.dp,
                if (isSelected) CosmicCyan.copy(alpha = 0.6f) else SpaceGlassBorder,
                RoundedCornerShape(14.dp)
            )
            .clickable {
                expanded = !expanded
                onCardClick()
            }
            .padding(14.dp)
            .testTag("section_card_${section.id}")
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(PulsarViolet.copy(alpha = 0.15f))
                        .border(1.dp, PulsarViolet.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = section.number,
                        color = PulsarViolet,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(SolarAmberNorth.copy(alpha = 0.12f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = section.category,
                        color = SolarAmberNorth,
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = section.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = StarlightWhite
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = section.summary,
                style = MaterialTheme.typography.bodySmall,
                color = StarlightMuted,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Core Equations Block
            if (section.coreEquations.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(DeepObsidian)
                        .border(1.dp, SpaceGlassBorder, RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Functions,
                            contentDescription = "Core Equations",
                            tint = CosmicCyan,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Core Formulas",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = CosmicCyan
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    section.coreEquations.forEach { (name, eq) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = name,
                                fontSize = 11.sp,
                                color = StarlightMuted
                            )
                            Text(
                                text = eq,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                color = StarlightWhite
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    // Physical Significance
                    Text(
                        text = "Physical Significance",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = CosmicCyan
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = section.physicalSignificance,
                        style = MaterialTheme.typography.bodySmall,
                        color = StarlightMuted,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Derivation Steps
                    if (section.derivations.isNotEmpty()) {
                        Text(
                            text = "Step-by-Step Derivations (${section.derivations.size})",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SolarAmberNorth
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        section.derivations.forEach { step ->
                            DerivationCard(
                                step = step,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Navigation Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        ElevatedButton(
                            onClick = onNavigateToSim,
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = CosmicCyan.copy(alpha = 0.15f),
                                contentColor = CosmicCyan
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text("2D Cosmos View", fontSize = 11.sp)
                        }

                        ElevatedButton(
                            onClick = onNavigateToWorkbench,
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = PulsarViolet.copy(alpha = 0.15f),
                                contentColor = PulsarViolet
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Spectra Workbench", fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}
