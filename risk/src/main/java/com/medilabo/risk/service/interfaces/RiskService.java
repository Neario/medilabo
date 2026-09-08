package com.medilabo.risk.service.interfaces;

import com.medilabo.risk.dto.enumeration.RiskLevel;

/**
 * Calculate a patient's diabetes risk level
 */
public interface RiskService {

    /**
     * Calculate the diabetes risk level for a patient, from their age, gender
     * and the trigger terms found in their notes.
     *
     * @param patId identifier of the patient
     * @return the computed risk level
     */
    RiskLevel diabetesRisk(Long patId);
}
