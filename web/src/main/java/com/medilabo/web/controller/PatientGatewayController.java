package com.medilabo.web.controller;

import com.medilabo.web.dto.PatientRequestDTO;
import com.medilabo.web.service.PatientGatewayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/patients/create")
    public String patientCreateForm(Model model) {
        model.addAttribute("patient", new PatientRequestDTO(null, null, null,
                null, null, null));
        model.addAttribute("formAction", "/patients");
        return "patients/create";
    }

    @PostMapping("/patients")
    public String createPatient(@Valid @ModelAttribute("patient") PatientRequestDTO patientRequestDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formAction", "/patients");
            return "patients/create";
        }
        try {
            patientGatewayService.createPatient(patientRequestDTO);
            return "redirect:/patients";
        } catch (Exception e) {
            model.addAttribute("formAction", "/patients");
            model.addAttribute("error", e.getMessage());
            return "patients/create";
        }
    }

    @GetMapping("/patients/{id}/edit")
    public String updatePatientForm(@PathVariable Long id, Model model) {
        model.addAttribute("patient", patientGatewayService.getPatientById(id));
        model.addAttribute("formAction", "/patients/"+id);
        return "patients/create";
    }

    @PostMapping("/patients/{id}")
    public String updatePatient(@PathVariable Long id, @Valid @ModelAttribute("patient") PatientRequestDTO patientRequestDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formAction", "/patients/"+id);
            return "patients/create";
        }
        try {
            patientGatewayService.updatePatient(id, patientRequestDTO);
            return "redirect:/patients";
        } catch (Exception e) {
            model.addAttribute("formAction", "/patients/"+id);
            model.addAttribute("error", e.getMessage());
            return "patients/create";
        }
    }
}
