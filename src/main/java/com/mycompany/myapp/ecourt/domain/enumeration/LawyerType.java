package com.mycompany.myapp.ecourt.domain.enumeration;

/**
 * The LawyerType enumeration.
 */
public enum LawyerType {
    PRIVATELAWYER("PrivateLawyer"),
    CORPORATIONLAWYER("CorporationLaywer");

    private final String value;

    LawyerType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
