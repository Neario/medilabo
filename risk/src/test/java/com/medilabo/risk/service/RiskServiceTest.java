package com.medilabo.risk.service;

import com.medilabo.risk.client.NoteClient;
import com.medilabo.risk.client.PatientClient;
import com.medilabo.risk.dto.NoteResponseDTO;
import com.medilabo.risk.dto.PatientResponseDTO;
import com.medilabo.risk.dto.enumeration.Gender;
import com.medilabo.risk.dto.enumeration.RiskLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RiskServiceTest {

    @Mock
    PatientClient patientClient;

    @Mock
    NoteClient noteClient;

    @InjectMocks
    RiskServiceImpl riskServiceImpl;

    private static final Long PAT_ID = 1L;

    @ParameterizedTest
    @MethodSource("riskScenarios")
    public void shouldCalculateExpectedRiskLevel(int age, Gender gender, List<String> notes, RiskLevel expected) {
        when(patientClient.getPatient(PAT_ID)).thenReturn(new PatientResponseDTO(LocalDate.now().minusYears(age), gender));
        when(noteClient.getNotes(PAT_ID)).thenReturn(notes.stream().map(NoteResponseDTO::new).toList());

        Assertions.assertEquals(expected, riskServiceImpl.diabetesRisk(PAT_ID));
    }

    static Stream<Arguments> riskScenarios() {
        return Stream.of(
                Arguments.of(
                        45, Gender.M,
                        List.of(),
                        RiskLevel.NONE),

                Arguments.of(
                        45, Gender.F,
                        List.of("Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé"),
                        RiskLevel.NONE),

                Arguments.of(
                        45, Gender.M,
                        List.of("Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement",
                                "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale"),
                        RiskLevel.BORDERLINE),

                Arguments.of(
                        22, Gender.M,
                        List.of("Le patient déclare qu'il fume depuis peu",
                                "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d'apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé"),
                        RiskLevel.IN_DANGER),

                Arguments.of(
                        26, Gender.F,
                        List.of("Taille, Poids, Cholestérol, Vertige et Réaction",
                                "Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d'être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments",
                                "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps",
                                "Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé"),
                        RiskLevel.EARLY_ONSET
                )
        );
    }
}
