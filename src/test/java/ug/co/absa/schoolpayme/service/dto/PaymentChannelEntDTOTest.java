package ug.co.absa.schoolpayme.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ug.co.absa.schoolpayme.web.rest.TestUtil;

class PaymentChannelEntDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentChannelEntDTO.class);
        PaymentChannelEntDTO paymentChannelEntDTO1 = new PaymentChannelEntDTO();
        paymentChannelEntDTO1.setId(1L);
        PaymentChannelEntDTO paymentChannelEntDTO2 = new PaymentChannelEntDTO();
        assertThat(paymentChannelEntDTO1).isNotEqualTo(paymentChannelEntDTO2);
        paymentChannelEntDTO2.setId(paymentChannelEntDTO1.getId());
        assertThat(paymentChannelEntDTO1).isEqualTo(paymentChannelEntDTO2);
        paymentChannelEntDTO2.setId(2L);
        assertThat(paymentChannelEntDTO1).isNotEqualTo(paymentChannelEntDTO2);
        paymentChannelEntDTO1.setId(null);
        assertThat(paymentChannelEntDTO1).isNotEqualTo(paymentChannelEntDTO2);
    }
}
