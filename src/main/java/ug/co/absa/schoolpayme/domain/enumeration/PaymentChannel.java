package ug.co.absa.schoolpayme.domain.enumeration;

/**
 * The PaymentChannel enumeration.
 */
public enum PaymentChannel {
    OVERTHECOUNTER("COUNTER"),
    ABSAINTERNETBANKING("INTERNETBANKING"),
    POINTOFSALE("POS"),
    MOBILEAPP("APP"),
    CHATBOT("ABBY"),
    USSD("USSD");

    private final String value;

    PaymentChannel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
