package ug.co.absa.schoolpayme.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentEntMapperTest {

    private PaymentEntMapper paymentEntMapper;

    @BeforeEach
    public void setUp() {
        paymentEntMapper = new PaymentEntMapperImpl();
    }
}
