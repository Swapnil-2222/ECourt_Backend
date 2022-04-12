package com.mycompany.myapp.ecourt.domain.enumeration;

/**
 * The CaseType enumeration.
 */
public enum CaseType {
    LARSEC18("LarSec18"),
    DARKHAST("Darkhast"),
    FIRSTAPPEAL("FirstAppeal"),
    CIVILMA("CivilMA"),
    OTHERS("Others");

    private final String value;

    CaseType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
