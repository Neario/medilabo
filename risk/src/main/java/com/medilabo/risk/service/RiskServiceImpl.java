package com.medilabo.risk.service;

import com.medilabo.risk.client.NoteClient;
import com.medilabo.risk.client.PatientClient;
import com.medilabo.risk.dto.NoteResponseDTO;
import com.medilabo.risk.dto.PatientResponseDTO;
import com.medilabo.risk.dto.enumeration.Gender;
import com.medilabo.risk.dto.enumeration.RiskLevel;
import com.medilabo.risk.service.interfaces.RiskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class RiskServiceImpl implements RiskService {

    private static final List<String> TRIGGER_TERMS = List.of(
            "Hémoglobine A1C",
            "Microalbumine",
            "Taille",
            "Poids",
            "Fumeur",
            "Fumeuse",
            "Anormal",
            "Cholestérol",
            "Vertiges",
            "Rechute",
            "Réaction",
            "Anticorps");

    private final PatientClient patientClient;
    private final NoteClient noteClient;

    @Override
    public RiskLevel diabetesRisk(Long patId) {
        PatientResponseDTO patientResponseDTO = patientClient.getPatient(patId);
        List<NoteResponseDTO> noteResponseDTO = noteClient.getNotes(patId);

        int age = Period.between(patientResponseDTO.birthDate(), LocalDate.now()).getYears();
        Gender patientGender = patientResponseDTO.gender();
        int triggers = triggers(noteResponseDTO);

        return getRiskLevel(age, patientGender, triggers);
    }

    private int triggers(List<NoteResponseDTO> notes) {
        return notes.stream()
                .filter(Objects::nonNull)
                .mapToInt(note -> Math.toIntExact(TRIGGER_TERMS.stream()
                        .filter(term -> containsTerm(note.note(), term))
                        .count())
                ).sum();
    }

    private boolean containsTerm(String note, String term) {
        Pattern pattern = Pattern.compile(Pattern.quote(term), Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(note);
        return matcher.find();
    }


    private RiskLevel getRiskLevel(int age, Gender gender, int triggers) {
        if (triggers == 0) {
            return RiskLevel.NONE;
        }

        if (age >= 30) {
            if (triggers >= 8) {
                return RiskLevel.EARLY_ONSET;
            }
            if (triggers >= 6) {
                return RiskLevel.IN_DANGER;
            }
            if (triggers >= 2) {
                return RiskLevel.BORDERLINE;
            }
        }
        if (age < 30) {
            if ((gender == Gender.M && triggers >= 5) || (gender == Gender.F && triggers >=7)) {
                return RiskLevel.EARLY_ONSET;
            }
            if ((gender == Gender.M && triggers >= 3) || (gender == Gender.F && triggers >= 4)) {
                return RiskLevel.IN_DANGER;
            }
        }
        return RiskLevel.NONE;
    }
}
