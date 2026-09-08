package com.medilabo.web.dto.enumeration;

import lombok.Getter;

@Getter
public enum RiskLevel {
    NONE("Aucun risque"),
    BORDERLINE("Risque limité"),
    IN_DANGER("Danger"),
    EARLY_ONSET("Apparition précoce");

    private final String label;
    RiskLevel(String label) { this.label = label; }
}