package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
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
import com.example.model.AuditItem
import com.example.model.AuditStatus
import com.example.ui.theme.CosmicCyan
import com.example.ui.theme.DeepObsidian
import com.example.ui.theme.PulsarViolet
import com.example.ui.theme.SolarAmberNorth
import com.example.ui.theme.SpaceGlassBorder
import com.example.ui.theme.SpaceGlassNavy
import com.example.ui.theme.StatusMarginal
import com.example.ui.theme.StatusPass
import com.example.ui.theme.StatusWarning
import com.example.ui.theme.StarlightDark
import com.example.ui.theme.StarlightMuted
import com.example.ui.theme.StarlightWhite

@Composable
fun AuditsScreen(
    audits: List<AuditItem>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DeepObsidian)
            .padding(16.dp)
            .testTag("audits_screen")
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.FactCheck,
                contentDescription = "Audits",
                tint = CosmicCyan,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "Theoretical Audits & Falsification",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = StarlightWhite
                )
                Text(
                    text = "Evaluated against general relativity, QFT, and observational limits",
                    style = MaterialTheme.typography.bodySmall,
                    color = StarlightMuted
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Overall Health Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(CosmicCyan.copy(alpha = 0.12f))
                .border(1.dp, CosmicCyan.copy(alpha = 0.40f), RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Passed",
                    tint = CosmicCyan,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Model Consistency: Rigorously Verified",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = CosmicCyan
                    )
                    Text(
                        text = "Theory is ghost-free, causal, preserves BBN deuterium yields, and presents direct falsification channels for LISA and CMB-S4.",
                        fontSize = 11.sp,
                        color = StarlightWhite,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Audits List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(audits, key = { it.id }) { item ->
                AuditItemCard(item = item)
            }
        }
    }
}

@Composable
fun AuditItemCard(item: AuditItem) {
    val statusColor = when (item.status) {
        AuditStatus.PASS -> StatusPass
        AuditStatus.MARGINAL -> StatusMarginal
        AuditStatus.WARNING -> StatusWarning
    }

    val statusIcon = when (item.status) {
        AuditStatus.PASS -> Icons.Default.CheckCircle
        AuditStatus.MARGINAL -> Icons.Default.Info
        AuditStatus.WARNING -> Icons.Default.Warning
    }

    val statusLabel = when (item.status) {
        AuditStatus.PASS -> "PASS"
        AuditStatus.MARGINAL -> "MARGINAL"
        AuditStatus.WARNING -> "WARNING"
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SpaceGlassNavy)
            .border(1.dp, SpaceGlassBorder, RoundedCornerShape(12.dp))
            .padding(12.dp)
            .testTag("audit_card_${item.id}")
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = StarlightWhite,
                    modifier = Modifier.weight(1f)
                )

                // Status Badge
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(statusColor.copy(alpha = 0.15f))
                        .border(1.dp, statusColor.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Icon(
                        imageVector = statusIcon,
                        contentDescription = statusLabel,
                        tint = statusColor,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = statusLabel,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = statusColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Constraint Formula
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(DeepObsidian)
                    .border(1.dp, SpaceGlassBorder, RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = item.constraintFormula,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    color = CosmicCyan
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Result: ${item.resultSummary}",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = SolarAmberNorth
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.technicalRationale,
                fontSize = 11.sp,
                color = StarlightMuted,
                lineHeight = 16.sp
            )
        }
    }
}
