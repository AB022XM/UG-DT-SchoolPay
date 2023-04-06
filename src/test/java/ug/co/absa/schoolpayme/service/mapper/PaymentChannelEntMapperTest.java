package ug.co.absa.schoolpayme.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentChannelEntMapperTest {

    private PaymentChannelEntMapper paymentChannelEntMapper;

    @BeforeEach
    public void setUp() {
        paymentChannelEntMapper = new PaymentChannelEntMapperImpl();
    }
}
