package com.medilabo.risk.service.interfaces;

import com.medilabo.risk.dto.enumeration.RiskLevel;

public interface RiskService {
    RiskLevel diabetesRisk(Long patId);
}
