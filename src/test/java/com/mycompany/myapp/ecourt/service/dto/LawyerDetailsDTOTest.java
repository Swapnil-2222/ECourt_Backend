package com.mycompany.myapp.ecourt.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.ecourt.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LawyerDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LawyerDetailsDTO.class);
        LawyerDetailsDTO lawyerDetailsDTO1 = new LawyerDetailsDTO();
        lawyerDetailsDTO1.setId(1L);
        LawyerDetailsDTO lawyerDetailsDTO2 = new LawyerDetailsDTO();
        assertThat(lawyerDetailsDTO1).isNotEqualTo(lawyerDetailsDTO2);
        lawyerDetailsDTO2.setId(lawyerDetailsDTO1.getId());
        assertThat(lawyerDetailsDTO1).isEqualTo(lawyerDetailsDTO2);
        lawyerDetailsDTO2.setId(2L);
        assertThat(lawyerDetailsDTO1).isNotEqualTo(lawyerDetailsDTO2);
        lawyerDetailsDTO1.setId(null);
        assertThat(lawyerDetailsDTO1).isNotEqualTo(lawyerDetailsDTO2);
    }
}
