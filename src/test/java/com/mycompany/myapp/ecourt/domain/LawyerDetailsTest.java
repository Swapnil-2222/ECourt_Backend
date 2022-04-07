package com.mycompany.myapp.ecourt.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.ecourt.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LawyerDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LawyerDetails.class);
        LawyerDetails lawyerDetails1 = new LawyerDetails();
        lawyerDetails1.setId(1L);
        LawyerDetails lawyerDetails2 = new LawyerDetails();
        lawyerDetails2.setId(lawyerDetails1.getId());
        assertThat(lawyerDetails1).isEqualTo(lawyerDetails2);
        lawyerDetails2.setId(2L);
        assertThat(lawyerDetails1).isNotEqualTo(lawyerDetails2);
        lawyerDetails1.setId(null);
        assertThat(lawyerDetails1).isNotEqualTo(lawyerDetails2);
    }
}
