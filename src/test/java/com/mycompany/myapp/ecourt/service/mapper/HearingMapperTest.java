package com.mycompany.myapp.ecourt.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HearingMapperTest {

    private HearingMapper hearingMapper;

    @BeforeEach
    public void setUp() {
        hearingMapper = new HearingMapperImpl();
    }
}
