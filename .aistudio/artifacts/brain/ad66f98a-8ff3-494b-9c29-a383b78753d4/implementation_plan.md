# AHSG Cosmos Lab: Anisotropic Higgs-Soup Genesis Companion & Simulation Suite

An interactive theoretical physics companion and multi-scale simulation workbench for the Anisotropic Higgs-Soup Genesis (AHSG) cosmological model. It bridges rigorous mathematical derivations with real-time numerical solvers, 2D relativistic canvas visualizations, and observational falsification benchmarks.

## User Review & Critical Decisions

> [!IMPORTANT]
> The following core user preferences from Phase 1 are confirmed and incorporated into the design:
> - **Primary Focus**: Theoretical paper companion with step-by-step mathematical derivations, formula breakdowns, and interactive parameter calculators.
> - **Visualization Style**: Both an interactive radial 2D cosmological visualizer and real-time physics spectra charts (SGWB, CMB rings, and magnetic field redshifting).
> - **Visual Aesthetic**: Deep space dark theme with obsidian backgrounds, nebula accents, cosmic cyan highlights, and crisp mathematical typography.

---

### 1. Overview & Core Concept

**AHSG Cosmos Lab** translates the complete formal physics paper into an accessible, rigorous, and visually striking Android application:
- **Paper Explorer & Derivation Walker**: Walk through all 14 core sections and appendices with step-by-step mathematical expansions, parameter definitions, and physical interpretations.
- **Interactive Cosmological Engine**: Real-time simulation of the complex condensate $\Phi = \frac{1}{\sqrt{2}}\rho e^{i\theta}$, Chiral Plasma Instability (CPI) magnetogenesis, Israel junction boundary conditions, and the closed FLRW Hamiltonian constraint $E_{\rm in}^{\rm eff} = -E_{N/S}$.
- **Dual Visualizer**:
  1. *2D Cosmic Cross-Section*: Animated Canvas rendering the expanding domain walls, central "middle light" core, outward magnetic dipole field lines ($B \propto a^{-2}$), and optical decoupling surface ($\tau \simeq 1$).
  2. *Live Observational Spectra*: Dynamic logarithmic Canvas charts plotting Stochastic Gravitational-Wave Background ($\Omega_{\rm GW}$ vs. LISA/NANOGrav bands), CMB multipole modulation ($C_\ell^{TT}$ concentric rings), and magnetic relic field evolution ($B_{\rm sat} \to B_0$).
- **Consistency & Falsification Suite**: Interactive audit table evaluating BBN ($\Delta N_{\rm eff} < 0.3$), ghost freedom ($1 + g\rho > 0$), gradient stability, and CMB-S4 sensitivity criteria.

---

### 2. User Experience & Visual Design

#### Key User Flows
1. **Explore Theory (Derivation Walker)**:
   - Browse sections: *Field Content & Action*, *Axion-CPI Magnetogenesis*, *Hamiltonian Constraint & Closed FLRW*, *Israel Junction Boundary*, *Radial Stratification*, *Middle Light Decoupling*, and *Stress-Energy Conservation*.
   - Tap formula cards to expand step-by-step algebraic derivations (e.g., how $B_{\rm sat} \sim \sqrt{\alpha_{\rm em}}\mu_5 T$ is derived from chiral anomaly helicity balance).
   - Live evaluate equations with instant slider feedback.

2. **Cosmological Simulation (2D Radial Cross-Section)**:
   - Scrub through cosmic epochs via a timeline controller:
     - **Pre-Collision** $\to$ **CPI Magnetogenesis ($t_{\rm GUT}$)** $\to$ **Stratification & Radiation** $\to$ **Optical Decoupling ($t_{\rm dec}$)** $\to$ **Present Era ($t_0$)**.
   - Watch concentric domain walls expand according to $\dot r_i = \Delta V_i / \eta_i$.
   - Observe the North/South magnetic boundary shell with dipole field lines and outward magnetic pressure $\tau_{\rm eff} = \sigma - \frac{1}{2}\langle B^2\rangle$.
   - Interactive toggle: Show/hide magnetic field lines, radiation diffusion, and metric curvature vectors.

3. **Observational Spectra & Sensitivity (Physics Workbench)**:
   - Adjust model parameters in real time: $T_{\rm GUT}$, chiral chemical potential $\mu_5/T$, axial scale $\Lambda$, and collision anisotropy $\epsilon$.
   - Inspect live curves:
     - **LISA/ET Gravitational-Wave Peak**: $\Omega_{\rm GW} \sim 10^{-10}$ at mHz frequencies compared against NANOGrav bounds.
     - **CMB Ring Anomalies**: $C_\ell^{TT}$ multipole shifts and angular scales $\theta_i \approx r_i/r_{\rm dec}$.
     - **Relic B-field Window**: Tracking $B_{\rm sat} \in [10^{40}, 10^{50}]$ G down to $B_0 \in [10^{-18}, 10^{-8}]$ G against Planck CMB Faraday limits.

4. **Consistency Audit & Falsification**:
   - Live checklist with pass/warning status indicators for Ghost Freedom, Causality, BBN $\Delta N_{\rm eff}$, and Gradient Stability based on active user parameters.

#### Visual Identity & Theme
- **Color Palette**:
  - `Background`: Deep Obsidian (`#0B0E14`) and Space Charcoal (`#121722`)
  - `Surface / Cards`: Translucent Glass Navy (`#1A2234` with subtle borders `#2A3650`)
  - `Primary / Accents`: Electric Cosmic Cyan (`#00E5FF`) and Pulsar Violet (`#B388FF`)
  - `Magnetic North / South`: Solar Amber (`#FFAB00`) and Polar Blue (`#448AFF`)
  - `Radiation / Middle Light`: Radiant Gold (`#FFD54F`)
  - `Text`: Ultra-white (`#F0F4F8`) and Muted Starlight (`#8E9EB5`)
- **Typography**: Clean monospace for equations and physical units; crisp sans-serif with strong hierarchy for theoretical prose and metric badges.
- **Layout**: Adaptive mobile-first layout with smooth tab navigation, collapsible derivation cards, and high-DPI custom Canvas graphics with touch inspection.

---

### 3. Key Product Decisions & Trade-Offs

- **Custom Compose Canvas Rendering vs. Webview**:
  - *Chosen Approach*: Pure Jetpack Compose `Canvas` with hardware acceleration for both the 2D cosmological cross-section and the scientific log-scale charts.
  - *Why*: Ultra-smooth 60fps animations, zero lag during slider interactions, full offline support, and native Material 3 theme alignment without external heavy dependencies.
- **State Management**:
  - *Chosen Approach*: Unidirectional MVVM using Kotlin `StateFlow` and domain calculation engines (`AHSGPhysicsEngine`, `SpectraCalculator`).
  - *Why*: Decouples high-precision relativistic math from UI recompositions, ensuring instant recalculation when parameters shift.
- **Zero Heavy External Plotting Dependencies**:
  - *Chosen Approach*: Tailored native Compose logarithmic chart renderer.
  - *Why*: Avoids third-party library bloat and compatibility snags with Kotlin 2.0+ while offering custom astrophysical styling (shaded detector sensitivity curves for LISA, NANOGrav, and ET).

---

### 4. Technical Architecture & Data Strategy

```
┌────────────────────────────────────────────────────────────────────────┐
│                          AHSG Cosmos Lab UI                            │
├─────────────────┬──────────────────────┬───────────────────────────────┤
│ Derivation      │ 2D Radial Cosmos     │ Observational Workbench &     │
│ Explorer        │ Canvas View          │ Spectra Charts                │
│ • Step-by-step  │ • Domain walls       │ • SGWB vs. LISA/NANOGrav      │
│ • Equation cards│ • Dipole field lines │ • CMB Ring Multipoles C_l     │
│ • Parameter test│ • Middle light core  │ • B-field Redshifting         │
└────────┬────────┴──────────┬───────────┴───────────────┬───────────────┘
         │                   │                           │
         ▼                   ▼                           ▼
┌────────────────────────────────────────────────────────────────────────┐
│                        AHSGViewModel & UI State                        │
│ • Selected Section     • Cosmological Time (a/t)  • Physics Params     │
│ • Expanded Derivations • Simulation Play/Pause    • Active Chart Mode  │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │
                                    ▼
┌────────────────────────────────────────────────────────────────────────┐
│                       AHSGPhysicsEngine (Domain)                       │
│ • Chiral Plasma Instability Solver (k_inst, B_sat, gamma_max)          │
│ • Israel Junction Boundary Evaluator (R_out, tau_eff, Sigma_shell)     │
│ • Closed FLRW Hamiltonian Constraint Engine (E_eff = -E_NS)            │
│ • Domain Wall Stratification & Decoupling Surface (r_i, tau_dec)       │
│ • Relativistic Spectra Generators (Omega_GW(f), C_ell, B_0)            │
└────────────────────────────────────────────────────────────────────────┘
```

#### Key Domain State & Parameters
- `CosmologicalParams`:
  - $T_{\rm GUT}$ ($10^{16}$ GeV default)
  - $\mu_5/T$ ($0.5$ default)
  - $\Lambda$ ($10^{16}$ GeV default)
  - $\epsilon$ anisotropy ($0.01$ default)
  - $w_{\rm res}$ equation of state ($0.0$ default)
  - $\sigma_{\rm wall}$ bare tension
- `SimulationState`:
  - Epoch: `COLLISION`, `CPI_MAGNETOGENESIS`, `STRATIFICATION`, `DECOUPLING`, `PRESENT_ERA`
  - Scale factor $a/a_0$, temperature $T(t)$, Hubble parameter $H(t)$
  - Calculated observables: $B_{\rm sat}$, $B_0$, $\Omega_{\rm GW}^{\rm peak}$, $\tau_{\rm eff}$, $\Delta N_{\rm eff}$
- `PaperSection`: 14 distinct structured chapters mapping 1:1 with the formalized manuscript, containing formal text, LaTeX math formulas, physical significance, and step-by-step derivations.
