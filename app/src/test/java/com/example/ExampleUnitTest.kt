package com.example

import com.example.data.AHSGPaperRepository
import com.example.model.AuditStatus
import com.example.model.CosmoParams
import com.example.physics.AHSGPhysicsEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testPhysicsCalculations_GUTScale() {
        val params = CosmoParams(tGUT_log10 = 16.0, mu5OverT = 1.0)
        val metrics = AHSGPhysicsEngine.calculateMetrics(params)

        // B_sat should be ~ 10^49 to 10^50 Gauss
        assertTrue("B_sat Gauss log10 should be in [48, 51]", metrics.bSatGaussLog10 in 48.0..51.0)

        // B_0 relic field should be in [10^-18, 10^-8] Gauss
        assertTrue("B_0 Gauss log10 should be in [-18, -7]", metrics.b0GaussLog10 in -18.0..-7.0)

        // Surface energy density Sigma must be strictly positive
        assertTrue("Sigma shell must be positive", metrics.surfaceEnergySigma > 0)

        // Delta N_eff should be below 0.30 for standard parameters
        assertTrue("Delta N_eff must be below 0.30", metrics.deltaNeff < 0.30)
    }

    @Test
    fun testConsistencyAudits_AllPass() {
        val params = CosmoParams()
        val audits = AHSGPhysicsEngine.runConsistencyAudits(params)

        assertEquals("Must evaluate 7 physical audits", 7, audits.size)

        val ghostAudit = audits.find { it.id == "ghost_freedom" }
        assertEquals(AuditStatus.PASS, ghostAudit?.status)

        val causalityAudit = audits.find { it.id == "causality" }
        assertEquals(AuditStatus.PASS, causalityAudit?.status)

        val bbnAudit = audits.find { it.id == "bbn_bound" }
        assertTrue(bbnAudit?.status == AuditStatus.PASS || bbnAudit?.status == AuditStatus.MARGINAL)
    }

    @Test
    fun testPaperRepository_ContainsAllSections() {
        assertEquals("Paper repository must have 14 sections", 14, AHSGPaperRepository.sections.size)
        AHSGPaperRepository.sections.forEach { sec ->
            assertFalse("Section ${sec.id} title cannot be empty", sec.title.isEmpty())
            assertFalse("Section ${sec.id} summary cannot be empty", sec.summary.isEmpty())
        }
    }
}
