package com.medilabo.risk.dto.enumeration;

import lombok.Getter;

/**
 * A patient's diabetes risk level
 */
@Getter
public enum RiskLevel {
    NONE,
    BORDERLINE,
    IN_DANGER,
    EARLY_ONSET
}
