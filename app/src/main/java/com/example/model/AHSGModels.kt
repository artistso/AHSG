package com.example.model

enum class CosmicEpoch(
    val title: String,
    val subtitle: String,
    val timeLabel: String,
    val scaleFactorNormalized: Float
) {
    COLLISION("Pre-Big-Bang Collision", "Anisotropic shock source J_coll", "t < t_coll", 0.05f),
    CPI_MAGNETOGENESIS("Axion-CPI Magnetogenesis", "Tachyonic helicity saturation", "t ~ t_GUT", 0.25f),
    STRATIFICATION("Domain Stratification", "Nonlinear scalar kinks & Middle Light", "t_GUT < t < t_dec", 0.55f),
    DECOUPLING("Optical Decoupling", "Last-scattering boundary tau ~ 1", "t ~ t_dec", 0.80f),
    PRESENT_ERA("Cosmic Present", "Relic void magnetic field & CMB rings", "t = t_0", 1.0f)
}

data class CosmoParams(
    val tGUT_log10: Double = 16.0,          // log10(T_GUT / GeV), 14.0 to 17.0
    val mu5OverT: Double = 0.5,             // mu_5 / T, 0.05 to 2.0
    val lambdaAxion_log10: Double = 16.0,   // log10(Lambda / GeV), 14.0 to 17.5
    val anisotropyEpsilon: Double = 0.01,   // epsilon, 0.001 to 0.05
    val wallTensionSigma: Double = 1.0,     // normalized bare tension
    val reservoirW: Double = 0.0            // w_res in [-1, 1]
)

data class SimulationMetrics(
    val bSatGaussLog10: Double,
    val bSatGeV2Log10: Double,
    val b0GaussLog10: Double,
    val b0GaussString: String,
    val omegaGWPeakLog10: Double,
    val fPeakHz: Double,
    val surfaceEnergySigma: Double,
    val effectiveTensionTau: Double,
    val isOutwardPressureDominated: Boolean,
    val deltaNeff: Double,
    val uHECRCutoffGeV: Double,
    val planckRatioB0: Double,
    val hamiltonianBalanceZero: Double = 0.0
)

data class DerivationStep(
    val stepNumber: Int,
    val title: String,
    val formulaDisplay: String,
    val explanation: String,
    val physicalIntuition: String
)

data class PaperSection(
    val id: String,
    val number: String,
    val title: String,
    val category: String,
    val summary: String,
    val coreEquations: List<Pair<String, String>>,
    val physicalSignificance: String,
    val derivations: List<DerivationStep>
)

enum class AuditStatus {
    PASS, MARGINAL, WARNING
}

data class AuditItem(
    val id: String,
    val name: String,
    val status: AuditStatus,
    val constraintFormula: String,
    val resultSummary: String,
    val technicalRationale: String
)

data class SpectrumPoint(
    val xLog10: Float,
    val yLog10: Float
)
