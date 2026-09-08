package com.medilabo.risk.dto.enumeration;

/**
 * Patient gender
 */
public enum Gender {
    M("M"),
    F("F");

    private String value;
    Gender(String value) {
        this.value = value;
    }
}
