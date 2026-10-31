package com.example.data

import com.example.model.DerivationStep
import com.example.model.PaperSection

object AHSGPaperRepository {
    val sections: List<PaperSection> = listOf(
        PaperSection(
            id = "sec_1",
            number = "Section 1",
            title = "Introduction & Epistemological Status",
            category = "Foundations",
            summary = "Presents the Anisotropic Higgs-Soup Genesis (AHSG) model: an anisotropic, time-ordered pre-Big-Bang collision driving a complex scalar condensate whose phase couples to gauge fields, generating an emergent magnetic dipole boundary without magnetic monopoles.",
            coreEquations = listOf(
                "Positive Magnetic Energy" to "E_{N/S} = \\frac{1}{2}\\int_{\\rm shell}|\\mathbf{B}|^2 dV > 0",
                "Interior Hamiltonian Balance" to "E_{\\rm in}^{\\rm eff} = -E_{N/S}"
            ),
            physicalSignificance = "Resolves previous toy model placeholders by grounding magnetic generation in the anomaly-driven Chiral Plasma Instability (CPI), proving the negative interior balance arises from closed general relativity, and deriving radial domain stratification.",
            derivations = listOf(
                DerivationStep(
                    stepNumber = 1,
                    title = "Positive Energy Density in the Shell",
                    formulaDisplay = "\\rho_B = \\frac{1}{2}|\\mathbf{B}|^2 \\ge 0 \\implies E_{N/S} = \\int_{\\rm shell}\\rho_B dV > 0",
                    explanation = "Maxwell stress-energy in flat or curved backgrounds has positive definite Hamiltonian density for magnetic configurations.",
                    physicalIntuition = "There is no unphysical negative magnetic energy. The minus sign in the global relation belongs to the gravitational potential energy of the interior closed space."
                ),
                DerivationStep(
                    stepNumber = 2,
                    title = "Epistemological Status of the Toy Model",
                    formulaDisplay = "\\mathcal{L}_{\\rm AHSG} \\in \\text{Self-Consistent Field Theories (Ghost-Free, Causal)}",
                    explanation = "While not derived from fundamental string compactifications, the model is fully covariant, unitary, causal, and testable against current CMB, gravitational wave, and cosmic-ray surveys.",
                    physicalIntuition = "A falsifiable phenomenology providing concrete observational targets for LISA and CMB-S4."
                )
            )
        ),

        PaperSection(
            id = "sec_2",
            number = "Section 2",
            title = "Conventions & Geometric Setup",
            category = "Geometry",
            summary = "Establishes natural Heaviside-Lorentz units (c = ħ = 1), signature (-,+,+,+), reduced Planck mass M_Pl = (8πG)^(-1/2), and Gibbons-Hawking-York boundary terms.",
            coreEquations = listOf(
                "Reduced Planck Mass" to "M_{\\rm Pl} = (8\\pi G)^{-1/2} \\approx 2.435 \\times 10^{18} \\text{ GeV}",
                "Gravitational Action" to "S_{\\rm grav} = \\int d^4x \\sqrt{-g}\\, \\frac{M_{\\rm Pl}^2}{2}R",
                "Israel Junction" to "[K_{ab}] - h_{ab}[K] = -8\\pi G\\, S_{ab} = -\\frac{1}{M_{\\rm Pl}^2}S_{ab}"
            ),
            physicalSignificance = "Restores explicit 8πG factors in the Einstein field equations and Israel conditions, ensuring dimensional exactness across all scales.",
            derivations = listOf(
                DerivationStep(
                    stepNumber = 1,
                    title = "Extrinsic Curvature Definition",
                    formulaDisplay = "K_{ab} = h_a{}^c h_b{}^d \\nabla_c n_d, \\quad h_{ab} = g_{ab} - n_a n_b",
                    explanation = "The hypersurface normal n^a satisfies n_a n^a = +1 for timelike boundaries. The extrinsic curvature measures embedding bending in the 4D spacetime.",
                    physicalIntuition = "Tracks how the expanding domain wall shell curves relative to the interior and exterior geometries."
                )
            )
        ),

        PaperSection(
            id = "sec_3",
            number = "Section 3",
            title = "Field Content, Action & Potential",
            category = "Lagrangian Dynamics",
            summary = "The scalar condensate is formulated as a complex field Φ = (1/√2)ρ e^(iθ) with spontaneously broken U(1). The radial mode ρ drives domain expansion, while the phase θ couples to the CP-odd term θ/(4Λ) F F̃.",
            coreEquations = listOf(
                "Complex Condensate" to "\\Phi(x) = \\frac{1}{\\sqrt{2}}\\rho(x) e^{i\\theta(x)}",
                "Smooth Potential" to "V(\\rho) = \\sum_{i=0}^N \\frac{\\lambda_i}{4}(\\rho^2 - v_i^2)^2 \\mathcal{S}_i(\\rho)",
                "Smoothing Function" to "\\mathcal{S}_i(\\rho) = \\frac{1}{2}\\left[1 + \\tanh\\left(\\frac{\\rho - \\rho_{i-1}}{\\delta_i}\\right)\\right]",
                "Total Action" to "S = \\int d^4x \\sqrt{-g}\\left[ \\frac{M_{\\rm Pl}^2}{2}R - \\frac{1}{2}(\\partial\\rho)^2 - \\frac{\\rho^2}{2}(\\partial\\theta)^2 - V(\\rho) - \\frac{1+g\\rho}{4}F^2 + \\frac{\\theta}{4\\Lambda}F\\tilde F + \\dots \\right]"
            ),
            physicalSignificance = "Eliminates ill-defined distribution forces by smoothly regularizing domain vacuum steps with tanh profiles, and ensures CP-conservation by coupling F F̃ to the pseudoscalar phase θ.",
            derivations = listOf(
                DerivationStep(
                    stepNumber = 1,
                    title = "Gauge Invariance and CP-Odd Coupling",
                    formulaDisplay = "\\mathcal{L}_{\\theta F\\tilde F} = \\frac{\\theta}{4\\Lambda}F_{\\mu\\nu}\\tilde F^{\\mu\\nu} = \\frac{\\theta}{\\Lambda}\\mathbf{E}\\cdot\\mathbf{B}",
                    explanation = "Under parity P, θ → -θ and E·B → -E·B, so the dimension-5 Lagrangian term is strictly CP-invariant.",
                    physicalIntuition = "Standard Model Higgs cannot couple directly to F F̃ without breaking CP. The phase of a complex condensate naturally acts as an axion-like field."
                ),
                DerivationStep(
                    stepNumber = 2,
                    title = "Ghost Freedom Condition",
                    formulaDisplay = "-\\frac{1}{4}(1 + g\\rho)F_{\\mu\\nu}F^{\\mu\\nu} \\implies 1 + g\\rho > 0",
                    explanation = "Because ρ ≥ 0 represents the radial amplitude of Φ, choosing g > 0 guarantees the gauge kinetic coefficient is strictly positive everywhere.",
                    physicalIntuition = "Prevents negative-norm ghost states and maintains standard Maxwell kinetic positivity."
                )
            )
        ),

        PaperSection(
            id = "sec_4",
            number = "Section 4",
            title = "Dynamical Magnetogenesis via Axion-CPI",
            category = "Magnetogenesis",
            summary = "Solves the Maxwell equations in conformal time with axial coupling. Modes in the tachyonic band undergo exponential amplification until saturated by the chiral anomaly.",
            coreEquations = listOf(
                "Mode Equation" to "A_\\pm'' + \\left(k^2 \\mp \\frac{k\\theta'}{\\Lambda}\\right)A_\\pm = 0",
                "Instability Band" to "0 < k < k_{\\rm inst} \\equiv \\frac{|\\theta'|}{\\Lambda}",
                "Saturation Field" to "B_{\\rm sat}^2 \\sim \\alpha_{\\rm em}\\, \\mu_5^2\\, T^2 \\implies B_{\\rm sat} \\sim \\sqrt{\\alpha_{\\rm em}}\\, \\mu_5\\, T"
            ),
            physicalSignificance = "Generates primordial magnetic fields dynamically through the Chiral Magnetic Effect (CME) and chiral anomaly, producing calculable fields B_sat ~ 10^40 to 10^50 G without artificial boundary insertion.",
            derivations = listOf(
                DerivationStep(
                    stepNumber = 1,
                    title = "Derivation of Tachyonic Band",
                    formulaDisplay = "\\omega_\\pm^2(k) = k^2 \\mp \\frac{k\\theta'}{\\Lambda} < 0 \\iff k < \\frac{|\\theta'|}{\\Lambda} \\equiv k_{\\rm inst}",
                    explanation = "When the scalar phase evolves (θ' ≠ 0), one circular helicity polarization has an imaginary frequency, producing tachyonic exponential growth A_± ∝ exp(γ_k τ).",
                    physicalIntuition = "The rolling phase pumps energy directly from the scalar condensate into helical electromagnetic waves."
                ),
                DerivationStep(
                    stepNumber = 2,
                    title = "Helicity-Anomaly Saturation Balance",
                    formulaDisplay = "h_M \\equiv \\frac{1}{V}\\int \\mathbf{A}\\cdot\\mathbf{B}\\, d^3x \\sim \\frac{B^2}{k_{\\rm inst}}, \\quad \\Delta n_5 = -\\frac{\\alpha_{\\rm em}}{\\pi}\\Delta h_M",
                    explanation = "Growth stops when all available chiral fermion asymmetry n_5 ~ μ_5 T^2 has been converted into magnetic helicity.",
                    physicalIntuition = "Energy transfer ceases once the anomalous plasma reaches equilibrium, locking the saturation field at B_sat ~ √(α_em) μ_5 T."
                ),
                DerivationStep(
                    stepNumber = 3,
                    title = "Numerical Evaluation at the GUT Scale",
                    formulaDisplay = "B_{\\rm sat} \\sim \\sqrt{1/137} \\times (10^{16}\\text{ GeV})^2 \\approx 8.5 \\times 10^{30}\\text{ GeV}^2 \\approx 4.4 \\times 10^{49}\\text{ G}",
                    explanation = "Using 1 GeV^2 ≈ 5.128 × 10^19 Gauss. Thermal damping and backreaction suppress this by a factor of 0.05-0.1, yielding 10^40 - 10^50 Gauss.",
                    physicalIntuition = "Enormous primordial fields naturally generated at grand unification energy scales."
                )
            )
        ),

        PaperSection(
            id = "sec_5",
            number = "Section 5",
            title = "Background Cosmology & Hamiltonian Constraint",
            category = "Gravitation",
            summary = "Proves that the global energy balance E_in^eff = -E_{N/S} is an exact realization of the Hamiltonian constraint of closed (k = +1) general relativity, not an ad-hoc postulate.",
            coreEquations = listOf(
                "Closed Friedmann Eq." to "3M_{\\rm Pl}^2\\left(H^2 + \\frac{1}{a^2}\\right) = \\rho_{\\rm tot}",
                "Hamiltonian Constraint" to "\\mathcal{H} = \\int d^3x \\sqrt{\\gamma}\\left[ \\rho_{\\rm tot} - 3M_{\\rm Pl}^2\\left(H^2 + \\frac{1}{a^2}\\right) \\right] = 0",
                "Effective Interior Energy" to "E_{\\rm in}^{\\rm eff} \\equiv (\\rho_{\\rm in} - 3M_{\\rm Pl}^2(H^2 + a^{-2}))V = -E_{N/S}"
            ),
            physicalSignificance = "Replaces mysterious 'negative magnetic energy' with standard gravitational binding energy: a closed FLRW universe has zero total ADM energy.",
            derivations = listOf(
                DerivationStep(
                    stepNumber = 1,
                    title = "Derivation of Global Vanishing Energy",
                    formulaDisplay = "V(a) = 2\\pi^2 a^3 \\implies \\rho_{\\rm tot}V - 3M_{\\rm Pl}^2(H^2 + a^{-2})V = 0",
                    explanation = "Partition total density into interior matter and boundary shell: ρ_tot = ρ_in + ρ_{N/S}.",
                    physicalIntuition = "Multiplying the Friedmann equation by volume shows that interior energy plus shell magnetic energy exactly equals positive gravitational expansion terms."
                ),
                DerivationStep(
                    stepNumber = 2,
                    title = "Exact Vanishing Sum",
                    formulaDisplay = "E_{\\rm in}^{\\rm eff} + E_{N/S} = 0 \\iff E_{\\rm in}^{\\rm eff} = -E_{N/S}",
                    explanation = "E_in^eff is negative because gravitational potential energy in closed curved spacetime is negative.",
                    physicalIntuition = "A closed universe is self-contained: the net energy of the entire cosmos is identically zero."
                )
            )
        ),

        PaperSection(
            id = "sec_6",
            number = "Section 6",
            title = "Scaling Laws & Present-Day Relic Field",
            category = "Astrophysics",
            summary = "By magnetic flux conservation in an ideal cosmic plasma, |B| ∝ a^(-2) and ρ_B ∝ a^(-4). Redshifting from T_GUT to T_0 yields a testable relic field B_0 in cosmic voids.",
            coreEquations = listOf(
                "Flux Conservation" to "|\\mathbf{B}(a)| = |\\mathbf{B}(a_{\\rm gen})|\\left(\\frac{a_{\\rm gen}}{a}\\right)^2",
                "Scale Factor Ratio" to "\\frac{a_{\\rm gen}}{a_0} \\approx \\frac{T_0}{T_{\\rm GUT}} \\approx 2.35 \\times 10^{-29}",
                "Present-Day Field" to "B_0 = B_{\\rm sat}\\left(\\frac{a_{\\rm gen}}{a_0}\\right)^2 \\in [10^{-18},\\, 10^{-8}]\\text{ G}"
            ),
            physicalSignificance = "The upper boundary B_0 ~ 10^-8 G approaches the Planck CMB Faraday rotation constraint (B_Mpc < 10^-9 G), providing immediate empirical testability.",
            derivations = listOf(
                DerivationStep(
                    stepNumber = 1,
                    title = "Adiabatic Magnetic Redshifting",
                    formulaDisplay = "\\nabla \\cdot \\mathbf{B} = 0 \\implies \\Phi_B = \\int \\mathbf{B}\\cdot d\\mathbf{S} = \\text{const} \\implies B \\propto a^{-2}",
                    explanation = "Cosmological plasma has extremely high conductivity σ >> H, freezing magnetic field lines into the fluid.",
                    physicalIntuition = "As the universe expands by a factor of 10^29, the magnetic field is diluted by (10^29)^2 = 10^58."
                )
            )
        ),

        PaperSection(
            id = "sec_7",
            number = "Section 7",
            title = "Boundary Dynamics & Israel Junction Conditions",
            category = "Relativity",
            summary = "Formulates the outer shell boundary via the Israel junction condition. Outbound magnetic pressure counteracts bare wall tension, generating effective outward pressure.",
            coreEquations = listOf(
                "Surface Stress Tensor" to "S_{ab} = -\\sigma h_{ab} + B_a B_b - \\frac{1}{2}h_{ab}B^2",
                "Surface Energy Density" to "\\Sigma_{\\rm shell} = -S^0{}_0 = \\sigma + \\frac{1}{2}\\langle B^2 \\rangle > 0",
                "Effective Tension / Pressure" to "\\tau_{\\rm eff} = -p_\\parallel = \\sigma - \\frac{1}{2}\\langle B^2 \\rangle",
                "Outward Expansion Criterion" to "\\frac{1}{2}\\langle B^2 \\rangle > \\sigma \\implies \\tau_{\\rm eff} < 0 \\text{ (Outward Surface Pressure)}"
            ),
            physicalSignificance = "Proves that the boundary expands without requiring unphysical negative surface energy density (Σ_shell > 0 always), driven by outbound magnetic Maxwell stress.",
            derivations = listOf(
                DerivationStep(
                    stepNumber = 1,
                    title = "Extrinsic Curvature Jump across Shell",
                    formulaDisplay = "[K_{ab}] - h_{ab}[K] = -8\\pi G\\, S_{ab} = -\\frac{1}{M_{\\rm Pl}^2}S_{ab}",
                    explanation = "Matching the interior FLRW metric to the exterior geometry across the timelike shell Σ at R_out(t).",
                    physicalIntuition = "The jump in curvature across the wall is balanced by the localized stress-energy of the wall and magnetic fields."
                ),
                DerivationStep(
                    stepNumber = 2,
                    title = "Repulsive Boundary Acceleration",
                    formulaDisplay = "\\tau_{\\rm eff} = \\sigma - \\frac{B^2}{2} < 0 \\implies \\ddot R_{\\rm out} > 0",
                    explanation = "When magnetic field energy density exceeds the domain wall tension, effective tension becomes negative, driving accelerated outward expansion.",
                    physicalIntuition = "Magnetic pressure pushes the boundary outward like an inflating cosmic bubble."
                )
            )
        ),

        PaperSection(
            id = "sec_8",
            number = "Section 8",
            title = "Radial Stratification from Klein-Gordon Dynamics",
            category = "Scalar Dynamics",
            summary = "Solves the spherically symmetric Klein-Gordon equation with the multi-minimum potential, generating concentric domain walls separating vacuum vacua v_i.",
            coreEquations = listOf(
                "Radial Klein-Gordon" to "\\ddot\\rho + 3H\\dot\\rho - \\frac{1}{a^2}\\left(\\partial_r^2\\rho + \\frac{2}{r}\\partial_r\\rho\\right) + V'(\\rho) = 0",
                "Concentric Kink Decomposition" to "\\rho(r, t) \\approx \\sum_i \\Delta\\rho_i\\, \\chi_i(r - r_i(t))",
                "Overdamped Wall Velocity" to "\\dot r_i(t) = -\\frac{\\Delta V_i}{\\eta_i}, \\quad \\eta_i \\sim T^3"
            ),
            physicalSignificance = "Replaces ad-hoc onion shell layers with dynamical nonlinear field kinks whose radial velocities are controlled by vacuum pressure and thermal drag.",
            derivations = listOf(
                DerivationStep(
                    stepNumber = 1,
                    title = "Balance of Vacuum Pressure and Thermal Drag",
                    formulaDisplay = "F_{\\rm net} = 4\\pi r_i^2 \\Delta V_i - 4\\pi r_i^2 \\eta_i \\dot r_i = 0 \\implies \\dot r_i = \\frac{\\Delta V_i}{\\eta_i}",
                    explanation = "In the dense early plasma, friction η_i ~ T^3 damps acceleration rapidly into an overdamped terminal velocity.",
                    physicalIntuition = "Domain walls move steadily outwards until temperatures drop and vacua freeze."
                )
            )
        ),

        PaperSection(
            id = "sec_9",
            number = "Section 9",
            title = "Middle Light, Radiative Transfer & Decoupling",
            category = "Cosmology",
            summary = "Resolves the apparent tension between interior radiation and the opaque shell. Radiation produced at early times thermalizes in the plasma and is released at an optical depth τ(t_dec) ~ 1.",
            coreEquations = listOf(
                "Interaction Coupling" to "\\mathcal{L}_{\\rm int} = -\\frac{g}{4}\\rho F_{\\mu\\nu}F^{\\mu\\nu}",
                "Optical Depth" to "\\tau(t) = \\int_t^{t_0}\\kappa \\rho_R(t')dt' \\simeq \\frac{\\kappa \\rho_R(t)}{H(t)} \\propto a^{-2}",
                "Decoupling Scale Factor" to "a_{\\rm dec} \\approx a_{\\rm gen}\\left(\\frac{\\kappa \\rho_{R,\\rm gen}}{H_{\\rm gen}}\\right)^{1/2}"
            ),
            physicalSignificance = "Explains why interior 'middle light' does not escape immediately: it thermalizes into a blackbody spectrum, emerging as the CMB when the plasma recombines at τ ~ 1.",
            derivations = listOf(
                DerivationStep(
                    stepNumber = 1,
                    title = "Thomson Scattering and Plasma Opacity",
                    formulaDisplay = "\\tau(a) \\propto \\frac{\\rho_R}{H} \\propto \\frac{a^{-4}}{a^{-2}} = a^{-2}",
                    explanation = "At very early times a << a_dec, τ >> 1 (deeply opaque). As the universe expands, τ drops rapidly below 1.",
                    physicalIntuition = "Exactly mirrors the standard cosmological last scattering surface: photons decouple cleanly once the mean free path exceeds the Hubble horizon."
                )
            )
        ),

        PaperSection(
            id = "sec_10",
            number = "Section 10",
            title = "Stress-Energy Conservation & Collision Dynamics",
            category = "Conservation Laws",
            summary = "Introduces a dynamical collision reservoir sector ϕ_coll ensuring covariant conservation ∇_μ T^(μν)_tot = 0 without breaking diffeomorphism invariance.",
            coreEquations = listOf(
                "Total Conservation" to "\\nabla_\\mu T^{\\mu\\nu}_{\\rm tot} = 0",
                "Collision Coupling" to "\\nabla_\\mu T^{\\mu\\nu}_\\rho = -Q^\\nu, \\quad \\nabla_\\mu T^{\\mu\\nu}_{\\rm coll} = +Q^\\nu",
                "Reservoir Evolution" to "\\rho_{\\rm res}(a) \\propto a^{-3(1 + w_{\\rm res})}"
            ),
            physicalSignificance = "Guarantees no energy is created ex nihilo: the initial collision energy is precisely conserved and transferred into the scalar condensate and gauge sectors.",
            derivations = listOf(
                DerivationStep(
                    stepNumber = 1,
                    title = "Energy Transfer Vector Q^ν",
                    formulaDisplay = "Q^\\nu = g_{\\rm res}\\rho \\partial^\\nu \\phi_{\\rm res} + J_{\\rm coll}\\partial^\\nu \\rho",
                    explanation = "Covariant exchange vector between the expanding domain and the reservoir.",
                    physicalIntuition = "Like an engine absorbing fuel from an external reservoir: the combined closed system conserves energy-momentum perfectly."
                )
            )
        ),

        PaperSection(
            id = "sec_11",
            number = "Section 11",
            title = "Observables & Empirical Signatures",
            category = "Observables",
            summary = "Derives quantitative predictions for the CMB preferred axis and concentric ring anomalies, the stochastic gravitational wave background (SGWB), and UHECR cutoffs.",
            coreEquations = listOf(
                "CMB Multipole Modulation" to "C_\\ell^{TT} = \\bar C_\\ell^{TT}\\left[1 + \\epsilon\\, P_\\ell(\\hat n \\cdot \\hat z)\\right]",
                "Concentric Ring Radii" to "\\theta_i \\approx \\frac{r_i}{r_{\\rm dec}}",
                "LISA SGWB Amplitude" to "\\Omega_{\\rm GW}(f) \\sim \\epsilon \\left(\\frac{T_{\\rm GUT}}{M_{\\rm Pl}}\\right)^4 \\sim 10^{-10} \\text{ at } f \\in [10^{-3}, 1]\\text{ Hz}",
                "UHECR Cutoff" to "E_{\\rm cut} \\sim T_{\\rm dec} \\in [10^{15}, 10^{17}]\\text{ GeV}"
            ),
            physicalSignificance = "Provides explicit numerical targets for space-based gravitational wave detectors (LISA) and next-generation CMB surveys (CMB-S4).",
            derivations = listOf(
                DerivationStep(
                    stepNumber = 1,
                    title = "LISA Frequency and Energy Density",
                    formulaDisplay = "\\Omega_{\\rm GW} \\sim 10^{-2} \\times \\left(\\frac{10^{16}\\text{ GeV}}{2.435\\times 10^{18}\\text{ GeV}}\\right)^4 \\approx 2.8 \\times 10^{-10}",
                    explanation = "Redshifted collision frequency lands squarely in the mHz band, peaking within LISA's maximum sensitivity range.",
                    physicalIntuition = "A distinct gravitational-wave signature distinguishable from standard slow-roll inflation."
                )
            )
        ),

        PaperSection(
            id = "sec_12",
            number = "Section 12",
            title = "Stress Tests & Consistency Audits",
            category = "Stress Tests",
            summary = "Executes rigorous theoretical audits against ghost freedom, gradient stability, relativistic causality, and Big Bang Nucleosynthesis (BBN) constraints.",
            coreEquations = listOf(
                "Ghost-Free Condition" to "1 + g\\rho > 0 \\quad (\\text{Holds } \\forall \\rho \\ge 0)",
                "BBN Bound" to "\\Delta N_{\\rm eff} = \\frac{8}{7}\\left(\\frac{11}{4}\\right)^{4/3}\\frac{\\rho_{\\rm extra}}{\\rho_\\gamma} < 0.30",
                "Residual Limit" to "\\frac{\\rho_{\\rm res}}{\\rho_{\\rm tot}} \\le 10^{-2} \\text{ at } T \\sim 1\\text{ MeV}"
            ),
            physicalSignificance = "Proves the model passes all standard theoretical and observational viability filters without pathology.",
            derivations = listOf(
                DerivationStep(
                    stepNumber = 1,
                    title = "Derivation of ΔN_eff from Relic Densities",
                    formulaDisplay = "\\Delta N_{\\rm eff} \\approx 7.44 \\times \\frac{\\rho_{\\rm res}}{\\rho_\\gamma} \\approx 7.44 \\times 0.008 \\approx 0.06 < 0.30",
                    explanation = "Residual energy from the reservoir and magnetic field contributes minimally to radiation at 1 MeV, leaving deuterium synthesis unaltered.",
                    physicalIntuition = "BBN bounds are respected comfortably."
                )
            )
        ),

        PaperSection(
            id = "sec_13",
            number = "Section 13",
            title = "Falsifiability & Empirical Benchmarks",
            category = "Falsification",
            summary = "Defines 4 decisive falsification channels for near-term surveys: CMB-S4 concentric rings, LISA SGWB slope, anisotropy axis alignment, and cosmic void field bounds.",
            coreEquations = listOf(
                "Ring Detection" to "\\Delta T / T \\sim 10^{-2} \\quad (\\text{CMB-S4 target})",
                "LISA Power-Law" to "\\Omega_{\\rm GW}(f) \\propto f^3 \\text{ for } f < f_{\\rm peak}, \\quad f^{-1} \\text{ for } f > f_{\\rm peak}",
                "Axis Alignment" to "\\hat n_{\\rm CMB} \\cdot \\hat n_{\\rm SGWB} = 1 \\pm \\delta \\quad (\\text{Shared Collision Axis})"
            ),
            physicalSignificance = "Clearly specifies experimental outcomes that would definitively rule out or support the model.",
            derivations = listOf(
                DerivationStep(
                    stepNumber = 1,
                    title = "Cross-Correlation of SGWB and CMB Dipoles",
                    formulaDisplay = "\\cos(\\theta_{\\rm align}) = \\hat n_{\\rm CMB} \\cdot \\hat n_{\\rm SGWB} \\approx 1",
                    explanation = "Because both the CMB preferred axis and the gravitational wave background originate from the same collision source, their dipoles must align.",
                    physicalIntuition = "An observed angular discrepancy > 10° between SGWB dipole and CMB rings would immediately falsify the model."
                )
            )
        ),

        PaperSection(
            id = "sec_14",
            number = "Appendix",
            title = "Mathematical Appendices",
            category = "Derivations",
            summary = "Complete mathematical proofs for CPI mode equations, Israel junction trace decomposition, and overdamped domain-wall kink equations of motion.",
            coreEquations = listOf(
                "Helicity Rate" to "\\frac{dh_M}{d\\tau} = -2\\int \\frac{d^3k}{(2\\pi)^3}\\frac{k}{\\sigma}|\\mathbf{B}_k|^2 + \\frac{2\\Lambda}{\\theta'}\\dots",
                "Extrinsic Trace Jump" to "[K^0{}_0] = \\frac{1}{M_{\\rm Pl}^2}\\left(\\frac{\\sigma}{2} + \\frac{3}{4}B^2\\right)",
                "Wall Lagrangian" to "L_{\\rm wall} = 4\\pi r_i^2(-\\sigma_i\\sqrt{1 - \\dot r_i^2}) - \\frac{4\\pi}{3}r_i^3 \\Delta V_i"
            ),
            physicalSignificance = "Provides explicit tensor algebra and functional variations verifying all analytical steps.",
            derivations = listOf(
                DerivationStep(
                    stepNumber = 1,
                    title = "Israel Junction Trace Calculation",
                    formulaDisplay = "S = S^a{}_a = -\\Sigma_{\\rm shell} + 2p_\\parallel = -3\\sigma + \\frac{1}{2}B^2 \\implies [K^0{}_0] = \\frac{1}{M_{\\rm Pl}^2}\\left(\\frac{\\sigma}{2} + \\frac{3}{4}B^2\\right)",
                    explanation = "Direct contraction of Israel condition [K^a_b] - δ^a_b [K] = -8πG S^a_b.",
                    physicalIntuition = "Proves outbound magnetic pressure consistently maintains positive extrinsic curvature step."
                )
            )
        )
    )
}
