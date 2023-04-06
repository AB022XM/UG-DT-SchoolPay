package ug.co.absa.schoolpayme.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ug.co.absa.schoolpayme.web.rest.TestUtil;

class PaymentEntTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentEnt.class);
        PaymentEnt paymentEnt1 = new PaymentEnt();
        paymentEnt1.setId(1L);
        PaymentEnt paymentEnt2 = new PaymentEnt();
        paymentEnt2.setId(paymentEnt1.getId());
        assertThat(paymentEnt1).isEqualTo(paymentEnt2);
        paymentEnt2.setId(2L);
        assertThat(paymentEnt1).isNotEqualTo(paymentEnt2);
        paymentEnt1.setId(null);
        assertThat(paymentEnt1).isNotEqualTo(paymentEnt2);
    }
}
