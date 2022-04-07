package com.mycompany.myapp.ecourt.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.ecourt.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourtCaseDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourtCaseDTO.class);
        CourtCaseDTO courtCaseDTO1 = new CourtCaseDTO();
        courtCaseDTO1.setId(1L);
        CourtCaseDTO courtCaseDTO2 = new CourtCaseDTO();
        assertThat(courtCaseDTO1).isNotEqualTo(courtCaseDTO2);
        courtCaseDTO2.setId(courtCaseDTO1.getId());
        assertThat(courtCaseDTO1).isEqualTo(courtCaseDTO2);
        courtCaseDTO2.setId(2L);
        assertThat(courtCaseDTO1).isNotEqualTo(courtCaseDTO2);
        courtCaseDTO1.setId(null);
        assertThat(courtCaseDTO1).isNotEqualTo(courtCaseDTO2);
    }
}
