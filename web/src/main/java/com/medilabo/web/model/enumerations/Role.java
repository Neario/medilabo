package com.medilabo.web.model.enumerations;

public enum Role {
    ORGANIZER("organizer"),
    PRACTITIONER("practitioner");

    private String value;
    Role(String value) {
        this.value = value;
    }
}
