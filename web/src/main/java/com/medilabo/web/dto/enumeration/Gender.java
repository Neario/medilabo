package com.medilabo.web.dto.enumeration;

public enum Gender {
    M("M"),
    F("F");

    private String value;
    Gender(String value) {
        this.value = value;
    }
}
