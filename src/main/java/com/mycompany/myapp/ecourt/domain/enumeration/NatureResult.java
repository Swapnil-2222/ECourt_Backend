package com.mycompany.myapp.ecourt.domain.enumeration;

/**
 * The NatureResult enumeration.
 */
public enum NatureResult {
    JUDGEMENT("judgement"),
    OTHERS("Others");

    private final String value;

    NatureResult(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
