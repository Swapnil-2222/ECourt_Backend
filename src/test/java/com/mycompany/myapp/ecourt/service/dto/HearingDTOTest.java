package com.mycompany.myapp.ecourt.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.ecourt.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HearingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HearingDTO.class);
        HearingDTO hearingDTO1 = new HearingDTO();
        hearingDTO1.setId(1L);
        HearingDTO hearingDTO2 = new HearingDTO();
        assertThat(hearingDTO1).isNotEqualTo(hearingDTO2);
        hearingDTO2.setId(hearingDTO1.getId());
        assertThat(hearingDTO1).isEqualTo(hearingDTO2);
        hearingDTO2.setId(2L);
        assertThat(hearingDTO1).isNotEqualTo(hearingDTO2);
        hearingDTO1.setId(null);
        assertThat(hearingDTO1).isNotEqualTo(hearingDTO2);
    }
}
