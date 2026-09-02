package com.medilabo.risk.controller;

import com.medilabo.risk.dto.RiskResponseDTO;
import com.medilabo.risk.dto.enumeration.RiskLevel;
import com.medilabo.risk.service.interfaces.RiskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/risk")
public class RiskController {

    private final RiskService riskService;

    @GetMapping("/{patId}")
    public ResponseEntity<RiskResponseDTO>  getRisk(@PathVariable Long patId) {
        RiskLevel level = riskService.diabetesRisk(patId);
        return ResponseEntity.ok(new RiskResponseDTO(level.getLabel()));
    }
}
