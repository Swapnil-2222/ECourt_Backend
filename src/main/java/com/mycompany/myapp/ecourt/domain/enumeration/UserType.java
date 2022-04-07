package com.mycompany.myapp.ecourt.domain.enumeration;

/**
 * The UserType enumeration.
 */
public enum UserType {
    USERTYPE("UserType"),
    LAWYERTYPE("LawyerType"),
    OTHERS("Others");

    private final String value;

    UserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
