package com.medilabo.web.controller;

import com.medilabo.web.service.PatientGatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class PatientGatewayController {

    private final PatientGatewayService patientGatewayService;

    @GetMapping("/patients")
    public String patients(Model model) {
        model.addAttribute("patients", patientGatewayService.getPatients());
        return "patients/list";
    }


    @GetMapping("/patients/{id}")
    public String patient(@PathVariable Long id, Model model) {
        model.addAttribute("patient", patientGatewayService.getPatientById(id));
        return "patients/view";
    }
}
