package com.mycompany.myapp.ecourt.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LawyerDetailsMapperTest {

    private LawyerDetailsMapper lawyerDetailsMapper;

    @BeforeEach
    public void setUp() {
        lawyerDetailsMapper = new LawyerDetailsMapperImpl();
    }
}
