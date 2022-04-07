package com.mycompany.myapp.ecourt.domain.enumeration;

/**
 * The CourtType enumeration.
 */
public enum CourtType {
    DISTRICTCOURT("DistrictCourt"),
    HIGHCOURT("HighCourt"),
    OTHERS("Others");

    private final String value;

    CourtType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
