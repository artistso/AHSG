package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AHSGPaperRepository
import com.example.model.AuditItem
import com.example.model.CosmicEpoch
import com.example.model.CosmoParams
import com.example.model.PaperSection
import com.example.model.SimulationMetrics
import com.example.model.SpectrumPoint
import com.example.physics.AHSGPhysicsEngine
import com.example.ui.components.VisualizerLayers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AHSGUIState(
    val currentTab: Int = 0, // 0: Paper & Derivations, 1: 2D Cosmos, 2: Observables, 3: Audits
    val params: CosmoParams = CosmoParams(),
    val metrics: SimulationMetrics = AHSGPhysicsEngine.calculateMetrics(CosmoParams()),
    val currentEpoch: CosmicEpoch = CosmicEpoch.STRATIFICATION,
    val selectedSectionId: String = "sec_1",
    val searchQuery: String = "",
    val isPlayingSimulation: Boolean = false,
    val visualizerLayers: VisualizerLayers = VisualizerLayers(),
    val activeChartMode: Int = 0, // 0: SGWB, 1: CMB, 2: B-field
    val sgwbPoints: List<SpectrumPoint> = emptyList(),
    val cmbPoints: List<SpectrumPoint> = emptyList(),
    val bFieldPoints: List<SpectrumPoint> = emptyList(),
    val audits: List<AuditItem> = emptyList()
)

class AHSGViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AHSGUIState())
    val uiState: StateFlow<AHSGUIState> = _uiState.asStateFlow()

    private var simulationPlaybackJob: Job? = null

    init {
        recalculatePhysics()
    }

    fun setTab(index: Int) {
        _uiState.update { it.copy(currentTab = index) }
    }

    fun selectSection(id: String) {
        _uiState.update { it.copy(selectedSectionId = id) }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun setEpoch(epoch: CosmicEpoch) {
        _uiState.update { it.copy(currentEpoch = epoch) }
    }

    fun togglePlayPauseSimulation() {
        val currentlyPlaying = _uiState.value.isPlayingSimulation
        if (currentlyPlaying) {
            simulationPlaybackJob?.cancel()
            _uiState.update { it.copy(isPlayingSimulation = false) }
        } else {
            _uiState.update { it.copy(isPlayingSimulation = true) }
            simulationPlaybackJob = viewModelScope.launch {
                val epochs = CosmicEpoch.values()
                while (true) {
                    delay(2800)
                    _uiState.update { state ->
                        val nextIdx = (state.currentEpoch.ordinal + 1) % epochs.size
                        state.copy(currentEpoch = epochs[nextIdx])
                    }
                }
            }
        }
    }

    fun toggleLayer(layerKey: String) {
        _uiState.update { state ->
            val cur = state.visualizerLayers
            val updated = when (layerKey) {
                "field_lines" -> cur.copy(showFieldLines = !cur.showFieldLines)
                "domain_walls" -> cur.copy(showDomainWalls = !cur.showDomainWalls)
                "decoupling" -> cur.copy(showDecouplingSurface = !cur.showDecouplingSurface)
                "poles" -> cur.copy(showPolesAndVectors = !cur.showPolesAndVectors)
                else -> cur
            }
            state.copy(visualizerLayers = updated)
        }
    }

    fun setActiveChartMode(mode: Int) {
        _uiState.update { it.copy(activeChartMode = mode) }
    }

    fun updateTGUT(log10GUT: Double) {
        _uiState.update { it.copy(params = it.params.copy(tGUT_log10 = log10GUT)) }
        recalculatePhysics()
    }

    fun updateMu5OverT(mu5Ratio: Double) {
        _uiState.update { it.copy(params = it.params.copy(mu5OverT = mu5Ratio)) }
        recalculatePhysics()
    }

    fun updateLambdaAxion(log10Lambda: Double) {
        _uiState.update { it.copy(params = it.params.copy(lambdaAxion_log10 = log10Lambda)) }
        recalculatePhysics()
    }

    fun updateAnisotropyEpsilon(epsilon: Double) {
        _uiState.update { it.copy(params = it.params.copy(anisotropyEpsilon = epsilon)) }
        recalculatePhysics()
    }

    fun updateWallTension(sigma: Double) {
        _uiState.update { it.copy(params = it.params.copy(wallTensionSigma = sigma)) }
        recalculatePhysics()
    }

    fun updateReservoirW(w: Double) {
        _uiState.update { it.copy(params = it.params.copy(reservoirW = w)) }
        recalculatePhysics()
    }

    fun resetParameters() {
        _uiState.update { it.copy(params = CosmoParams()) }
        recalculatePhysics()
    }

    private fun recalculatePhysics() {
        val params = _uiState.value.params
        val metrics = AHSGPhysicsEngine.calculateMetrics(params)
        val sgwb = AHSGPhysicsEngine.computeSGWBSpectrum(params)
        val cmb = AHSGPhysicsEngine.computeCMBMultipoles(params)
        val bField = AHSGPhysicsEngine.computeBFieldRedshiftCurve(params)
        val audits = AHSGPhysicsEngine.runConsistencyAudits(params)

        _uiState.update {
            it.copy(
                metrics = metrics,
                sgwbPoints = sgwb,
                cmbPoints = cmb,
                bFieldPoints = bField,
                audits = audits
            )
        }
    }

    fun getFilteredSections(): List<PaperSection> {
        val query = _uiState.value.searchQuery.trim().lowercase()
        if (query.isEmpty()) return AHSGPaperRepository.sections

        return AHSGPaperRepository.sections.filter { sec ->
            sec.title.lowercase().contains(query) ||
            sec.summary.lowercase().contains(query) ||
            sec.category.lowercase().contains(query) ||
            sec.derivations.any { it.title.lowercase().contains(query) || it.explanation.lowercase().contains(query) }
        }
    }
}
