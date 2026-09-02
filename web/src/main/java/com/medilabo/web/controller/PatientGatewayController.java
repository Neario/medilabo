package com.medilabo.web.controller;

import com.medilabo.web.client.NoteGatewayClient;
import com.medilabo.web.client.RiskGatewayClient;
import com.medilabo.web.dto.NoteRequestDTO;
import com.medilabo.web.dto.PatientRequestDTO;
import com.medilabo.web.service.PatientGatewayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Renders the patient pages, and handles the corresponding forms.
 */
@Controller
@RequiredArgsConstructor
public class PatientGatewayController {

    private final PatientGatewayService patientGatewayService;
    private final NoteGatewayClient noteGatewayClient;
    private final RiskGatewayClient riskGatewayClient;

    /**
     * Render patient list page
     * @param model the view model, populated with the list of patient
     * @return the patient list view
     */
    @GetMapping("/patients")
    public String patients(Model model) {
        model.addAttribute("patients", patientGatewayService.getPatients());
        return "patients/list";
    }


    /**
     * Render a patient page
     * @param id identifier of the patient
     * @param model the view model, populated with the patient and their notes
     * @return the patient detail view
     */
    @GetMapping("/patients/{id}")
    public String patient(@PathVariable Long id, Model model) {
        model.addAttribute("patient", patientGatewayService.getPatientById(id));
        model.addAttribute("notes", noteGatewayClient.getNotes(id));
        model.addAttribute("risk", riskGatewayClient.getRisk(id));
        return "patients/view";
    }

    /**
     * Render a form page for create patient
     * @param model the view model, populated with an empty patient and the create form's action URL
     * @return the create patient form view
     */
    @GetMapping("/patients/create")
    public String patientCreateForm(Model model) {
        model.addAttribute("patient", new PatientRequestDTO(null, null, null,
                null, null, null));
        model.addAttribute("formAction", "/patients");
        return "patients/create";
    }

    /**
     * Create a patient from the submitted form
     *
     * @param patientRequestDTO submitted patient valid data
     * @param bindingResult validation result for patientRequestDTO
     * @param model the view model, populated with errors if failure
     * @return a redirect to patient list on success, or the form view again on failure
     */
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

    /**
     * Render form page for edit Patient
     * @param id identifier of the patient to edit
     * @param model the view model, populated with the patient's current data and the edit form's action URL
     * @return the edited patient form view, reusing the creation template
     */
    @GetMapping("/patients/{id}/edit")
    public String updatePatientForm(@PathVariable Long id, Model model) {
        model.addAttribute("patient", patientGatewayService.getPatientById(id));
        model.addAttribute("formAction", "/patients/"+id);
        return "patients/create";
    }

    /**
     * Updates a patient
     *
     * @param id identifier of the patient to update
     * @param patientRequestDTO submitted patient valid data
     * @param bindingResult validation result for patientRequestDTO
     * @param model the view model, populated with form data on failure
     * @return a redirect to patient list on success, or the form view again on failure
     */
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

    /**
     * Render form page for new Note
     * @param id identifier of the patient
     * @param model the view model, populated with the patient
     * @return the create note form view
     */
    @GetMapping("/patients/{id}/notes/new")
    public String newNoteForm(@PathVariable Long id, Model model) {
        model.addAttribute("patient", patientGatewayService.getPatientById(id));
        return "notes/create";
    }

    /**
     * Create a note from the submitted form.
     *
     * @param id identifier of the patient
     * @param patient name of the patient
     * @param note the note's content
     * @return a redirect to the patient's detail page
     */
    @PostMapping("/patients/{id}/notes")
    public String addNote(@PathVariable Long id, @RequestParam String patient, @RequestParam String note) {
        noteGatewayClient.createNote(new NoteRequestDTO(id, patient, note));
        return "redirect:/patients/"+id;
    }
}
