package ug.co.absa.schoolpayme.domain.enumeration;

/**
 * The RecordStatus enumeration.
 */
public enum RecordStatus {
    ACTIVE("True"),
    INACTIVE("False");

    private final String value;

    RecordStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
