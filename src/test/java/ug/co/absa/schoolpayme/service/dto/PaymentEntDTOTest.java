package ug.co.absa.schoolpayme.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ug.co.absa.schoolpayme.web.rest.TestUtil;

class PaymentEntDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentEntDTO.class);
        PaymentEntDTO paymentEntDTO1 = new PaymentEntDTO();
        paymentEntDTO1.setId(1L);
        PaymentEntDTO paymentEntDTO2 = new PaymentEntDTO();
        assertThat(paymentEntDTO1).isNotEqualTo(paymentEntDTO2);
        paymentEntDTO2.setId(paymentEntDTO1.getId());
        assertThat(paymentEntDTO1).isEqualTo(paymentEntDTO2);
        paymentEntDTO2.setId(2L);
        assertThat(paymentEntDTO1).isNotEqualTo(paymentEntDTO2);
        paymentEntDTO1.setId(null);
        assertThat(paymentEntDTO1).isNotEqualTo(paymentEntDTO2);
    }
}
