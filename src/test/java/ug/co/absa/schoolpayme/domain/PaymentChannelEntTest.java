package ug.co.absa.schoolpayme.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ug.co.absa.schoolpayme.web.rest.TestUtil;

class PaymentChannelEntTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentChannelEnt.class);
        PaymentChannelEnt paymentChannelEnt1 = new PaymentChannelEnt();
        paymentChannelEnt1.setId(1L);
        PaymentChannelEnt paymentChannelEnt2 = new PaymentChannelEnt();
        paymentChannelEnt2.setId(paymentChannelEnt1.getId());
        assertThat(paymentChannelEnt1).isEqualTo(paymentChannelEnt2);
        paymentChannelEnt2.setId(2L);
        assertThat(paymentChannelEnt1).isNotEqualTo(paymentChannelEnt2);
        paymentChannelEnt1.setId(null);
        assertThat(paymentChannelEnt1).isNotEqualTo(paymentChannelEnt2);
    }
}
