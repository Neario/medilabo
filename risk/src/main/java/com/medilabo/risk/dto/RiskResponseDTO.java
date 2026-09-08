package com.medilabo.risk.dto;

import com.medilabo.risk.dto.enumeration.RiskLevel;

/**
 * Response returned by the {@code /risk/{patId}} endpoint.
 *
 * @param level the calculate risk level
 */
public record RiskResponseDTO(
        RiskLevel level
) {
}
