package com.medilabo.web.dto;

import com.medilabo.web.dto.enumeration.RiskLevel;

public record RiskResponseDTO(
        RiskLevel level
) {
}
