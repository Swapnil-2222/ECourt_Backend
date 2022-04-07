package com.mycompany.myapp.ecourt.domain.enumeration;

/**
 * The CaseStatus enumeration.
 */
public enum CaseStatus {
    CREATED("Created"),
    ONPROGRESS("OnProgress"),
    CLEARED("Cleared");

    private final String value;

    CaseStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
