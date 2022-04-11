package com.mycompany.myapp.ecourt.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.ecourt.IntegrationTest;
import com.mycompany.myapp.ecourt.domain.CourtCase;
import com.mycompany.myapp.ecourt.domain.Hearing;
import com.mycompany.myapp.ecourt.domain.enumeration.CaseStatus;
import com.mycompany.myapp.ecourt.repository.HearingRepository;
import com.mycompany.myapp.ecourt.service.HearingService;
import com.mycompany.myapp.ecourt.service.criteria.HearingCriteria;
import com.mycompany.myapp.ecourt.service.dto.HearingDTO;
import com.mycompany.myapp.ecourt.service.mapper.HearingMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HearingResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class HearingResourceIT {

    private static final String DEFAULT_HEARING_DATE = "AAAAAAAAAA";
    private static final String UPDATED_HEARING_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_NEXT_HEARING_DATE = "AAAAAAAAAA";
    private static final String UPDATED_NEXT_HEARING_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PREVIOUS_HEARING_DATE = "AAAAAAAAAA";
    private static final String UPDATED_PREVIOUS_HEARING_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_CONCLUSION = "AAAAAAAAAA";
    private static final String UPDATED_CONCLUSION = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final CaseStatus DEFAULT_CASE_STATUS = CaseStatus.CREATED;
    private static final CaseStatus UPDATED_CASE_STATUS = CaseStatus.ONPROGRESS;

    private static final String DEFAULT_FREEFIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREEFIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREEFIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREEFIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FREEFIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREEFIELD_3 = "BBBBBBBBBB";

    private static final String DEFAULT_FREEFIELD_4 = "AAAAAAAAAA";
    private static final String UPDATED_FREEFIELD_4 = "BBBBBBBBBB";

    private static final String DEFAULT_FREEFIELD_5 = "AAAAAAAAAA";
    private static final String UPDATED_FREEFIELD_5 = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/hearings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HearingRepository hearingRepository;

    @Mock
    private HearingRepository hearingRepositoryMock;

    @Autowired
    private HearingMapper hearingMapper;

    @Mock
    private HearingService hearingServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHearingMockMvc;

    private Hearing hearing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hearing createEntity(EntityManager em) {
        Hearing hearing = new Hearing()
            .hearingDate(DEFAULT_HEARING_DATE)
            .nextHearingDate(DEFAULT_NEXT_HEARING_DATE)
            .description(DEFAULT_DESCRIPTION)
            .previousHearingDate(DEFAULT_PREVIOUS_HEARING_DATE)
            .conclusion(DEFAULT_CONCLUSION)
            .comment(DEFAULT_COMMENT)
            .caseStatus(DEFAULT_CASE_STATUS)
            .freefield1(DEFAULT_FREEFIELD_1)
            .freefield2(DEFAULT_FREEFIELD_2)
            .freefield3(DEFAULT_FREEFIELD_3)
            .freefield4(DEFAULT_FREEFIELD_4)
            .freefield5(DEFAULT_FREEFIELD_5)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModified(DEFAULT_LAST_MODIFIED);
        return hearing;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hearing createUpdatedEntity(EntityManager em) {
        Hearing hearing = new Hearing()
            .hearingDate(UPDATED_HEARING_DATE)
            .nextHearingDate(UPDATED_NEXT_HEARING_DATE)
            .description(UPDATED_DESCRIPTION)
            .previousHearingDate(UPDATED_PREVIOUS_HEARING_DATE)
            .conclusion(UPDATED_CONCLUSION)
            .comment(UPDATED_COMMENT)
            .caseStatus(UPDATED_CASE_STATUS)
            .freefield1(UPDATED_FREEFIELD_1)
            .freefield2(UPDATED_FREEFIELD_2)
            .freefield3(UPDATED_FREEFIELD_3)
            .freefield4(UPDATED_FREEFIELD_4)
            .freefield5(UPDATED_FREEFIELD_5)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModified(UPDATED_LAST_MODIFIED);
        return hearing;
    }

    @BeforeEach
    public void initTest() {
        hearing = createEntity(em);
    }

    @Test
    @Transactional
    void createHearing() throws Exception {
        int databaseSizeBeforeCreate = hearingRepository.findAll().size();
        // Create the Hearing
        HearingDTO hearingDTO = hearingMapper.toDto(hearing);
        restHearingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hearingDTO)))
            .andExpect(status().isCreated());

        // Validate the Hearing in the database
        List<Hearing> hearingList = hearingRepository.findAll();
        assertThat(hearingList).hasSize(databaseSizeBeforeCreate + 1);
        Hearing testHearing = hearingList.get(hearingList.size() - 1);
        assertThat(testHearing.getHearingDate()).isEqualTo(DEFAULT_HEARING_DATE);
        assertThat(testHearing.getNextHearingDate()).isEqualTo(DEFAULT_NEXT_HEARING_DATE);
        assertThat(testHearing.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testHearing.getPreviousHearingDate()).isEqualTo(DEFAULT_PREVIOUS_HEARING_DATE);
        assertThat(testHearing.getConclusion()).isEqualTo(DEFAULT_CONCLUSION);
        assertThat(testHearing.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testHearing.getCaseStatus()).isEqualTo(DEFAULT_CASE_STATUS);
        assertThat(testHearing.getFreefield1()).isEqualTo(DEFAULT_FREEFIELD_1);
        assertThat(testHearing.getFreefield2()).isEqualTo(DEFAULT_FREEFIELD_2);
        assertThat(testHearing.getFreefield3()).isEqualTo(DEFAULT_FREEFIELD_3);
        assertThat(testHearing.getFreefield4()).isEqualTo(DEFAULT_FREEFIELD_4);
        assertThat(testHearing.getFreefield5()).isEqualTo(DEFAULT_FREEFIELD_5);
        assertThat(testHearing.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testHearing.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void createHearingWithExistingId() throws Exception {
        // Create the Hearing with an existing ID
        hearing.setId(1L);
        HearingDTO hearingDTO = hearingMapper.toDto(hearing);

        int databaseSizeBeforeCreate = hearingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHearingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hearingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hearing in the database
        List<Hearing> hearingList = hearingRepository.findAll();
        assertThat(hearingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHearings() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList
        restHearingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hearing.getId().intValue())))
            .andExpect(jsonPath("$.[*].hearingDate").value(hasItem(DEFAULT_HEARING_DATE)))
            .andExpect(jsonPath("$.[*].nextHearingDate").value(hasItem(DEFAULT_NEXT_HEARING_DATE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].previousHearingDate").value(hasItem(DEFAULT_PREVIOUS_HEARING_DATE)))
            .andExpect(jsonPath("$.[*].conclusion").value(hasItem(DEFAULT_CONCLUSION)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].caseStatus").value(hasItem(DEFAULT_CASE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].freefield1").value(hasItem(DEFAULT_FREEFIELD_1)))
            .andExpect(jsonPath("$.[*].freefield2").value(hasItem(DEFAULT_FREEFIELD_2)))
            .andExpect(jsonPath("$.[*].freefield3").value(hasItem(DEFAULT_FREEFIELD_3)))
            .andExpect(jsonPath("$.[*].freefield4").value(hasItem(DEFAULT_FREEFIELD_4)))
            .andExpect(jsonPath("$.[*].freefield5").value(hasItem(DEFAULT_FREEFIELD_5)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllHearingsWithEagerRelationshipsIsEnabled() throws Exception {
        when(hearingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restHearingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(hearingServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllHearingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(hearingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restHearingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(hearingServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getHearing() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get the hearing
        restHearingMockMvc
            .perform(get(ENTITY_API_URL_ID, hearing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hearing.getId().intValue()))
            .andExpect(jsonPath("$.hearingDate").value(DEFAULT_HEARING_DATE))
            .andExpect(jsonPath("$.nextHearingDate").value(DEFAULT_NEXT_HEARING_DATE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.previousHearingDate").value(DEFAULT_PREVIOUS_HEARING_DATE))
            .andExpect(jsonPath("$.conclusion").value(DEFAULT_CONCLUSION))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.caseStatus").value(DEFAULT_CASE_STATUS.toString()))
            .andExpect(jsonPath("$.freefield1").value(DEFAULT_FREEFIELD_1))
            .andExpect(jsonPath("$.freefield2").value(DEFAULT_FREEFIELD_2))
            .andExpect(jsonPath("$.freefield3").value(DEFAULT_FREEFIELD_3))
            .andExpect(jsonPath("$.freefield4").value(DEFAULT_FREEFIELD_4))
            .andExpect(jsonPath("$.freefield5").value(DEFAULT_FREEFIELD_5))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED));
    }

    @Test
    @Transactional
    void getHearingsByIdFiltering() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        Long id = hearing.getId();

        defaultHearingShouldBeFound("id.equals=" + id);
        defaultHearingShouldNotBeFound("id.notEquals=" + id);

        defaultHearingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHearingShouldNotBeFound("id.greaterThan=" + id);

        defaultHearingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHearingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllHearingsByHearingDateIsEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where hearingDate equals to DEFAULT_HEARING_DATE
        defaultHearingShouldBeFound("hearingDate.equals=" + DEFAULT_HEARING_DATE);

        // Get all the hearingList where hearingDate equals to UPDATED_HEARING_DATE
        defaultHearingShouldNotBeFound("hearingDate.equals=" + UPDATED_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllHearingsByHearingDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where hearingDate not equals to DEFAULT_HEARING_DATE
        defaultHearingShouldNotBeFound("hearingDate.notEquals=" + DEFAULT_HEARING_DATE);

        // Get all the hearingList where hearingDate not equals to UPDATED_HEARING_DATE
        defaultHearingShouldBeFound("hearingDate.notEquals=" + UPDATED_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllHearingsByHearingDateIsInShouldWork() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where hearingDate in DEFAULT_HEARING_DATE or UPDATED_HEARING_DATE
        defaultHearingShouldBeFound("hearingDate.in=" + DEFAULT_HEARING_DATE + "," + UPDATED_HEARING_DATE);

        // Get all the hearingList where hearingDate equals to UPDATED_HEARING_DATE
        defaultHearingShouldNotBeFound("hearingDate.in=" + UPDATED_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllHearingsByHearingDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where hearingDate is not null
        defaultHearingShouldBeFound("hearingDate.specified=true");

        // Get all the hearingList where hearingDate is null
        defaultHearingShouldNotBeFound("hearingDate.specified=false");
    }

    @Test
    @Transactional
    void getAllHearingsByHearingDateContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where hearingDate contains DEFAULT_HEARING_DATE
        defaultHearingShouldBeFound("hearingDate.contains=" + DEFAULT_HEARING_DATE);

        // Get all the hearingList where hearingDate contains UPDATED_HEARING_DATE
        defaultHearingShouldNotBeFound("hearingDate.contains=" + UPDATED_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllHearingsByHearingDateNotContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where hearingDate does not contain DEFAULT_HEARING_DATE
        defaultHearingShouldNotBeFound("hearingDate.doesNotContain=" + DEFAULT_HEARING_DATE);

        // Get all the hearingList where hearingDate does not contain UPDATED_HEARING_DATE
        defaultHearingShouldBeFound("hearingDate.doesNotContain=" + UPDATED_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllHearingsByNextHearingDateIsEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where nextHearingDate equals to DEFAULT_NEXT_HEARING_DATE
        defaultHearingShouldBeFound("nextHearingDate.equals=" + DEFAULT_NEXT_HEARING_DATE);

        // Get all the hearingList where nextHearingDate equals to UPDATED_NEXT_HEARING_DATE
        defaultHearingShouldNotBeFound("nextHearingDate.equals=" + UPDATED_NEXT_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllHearingsByNextHearingDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where nextHearingDate not equals to DEFAULT_NEXT_HEARING_DATE
        defaultHearingShouldNotBeFound("nextHearingDate.notEquals=" + DEFAULT_NEXT_HEARING_DATE);

        // Get all the hearingList where nextHearingDate not equals to UPDATED_NEXT_HEARING_DATE
        defaultHearingShouldBeFound("nextHearingDate.notEquals=" + UPDATED_NEXT_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllHearingsByNextHearingDateIsInShouldWork() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where nextHearingDate in DEFAULT_NEXT_HEARING_DATE or UPDATED_NEXT_HEARING_DATE
        defaultHearingShouldBeFound("nextHearingDate.in=" + DEFAULT_NEXT_HEARING_DATE + "," + UPDATED_NEXT_HEARING_DATE);

        // Get all the hearingList where nextHearingDate equals to UPDATED_NEXT_HEARING_DATE
        defaultHearingShouldNotBeFound("nextHearingDate.in=" + UPDATED_NEXT_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllHearingsByNextHearingDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where nextHearingDate is not null
        defaultHearingShouldBeFound("nextHearingDate.specified=true");

        // Get all the hearingList where nextHearingDate is null
        defaultHearingShouldNotBeFound("nextHearingDate.specified=false");
    }

    @Test
    @Transactional
    void getAllHearingsByNextHearingDateContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where nextHearingDate contains DEFAULT_NEXT_HEARING_DATE
        defaultHearingShouldBeFound("nextHearingDate.contains=" + DEFAULT_NEXT_HEARING_DATE);

        // Get all the hearingList where nextHearingDate contains UPDATED_NEXT_HEARING_DATE
        defaultHearingShouldNotBeFound("nextHearingDate.contains=" + UPDATED_NEXT_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllHearingsByNextHearingDateNotContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where nextHearingDate does not contain DEFAULT_NEXT_HEARING_DATE
        defaultHearingShouldNotBeFound("nextHearingDate.doesNotContain=" + DEFAULT_NEXT_HEARING_DATE);

        // Get all the hearingList where nextHearingDate does not contain UPDATED_NEXT_HEARING_DATE
        defaultHearingShouldBeFound("nextHearingDate.doesNotContain=" + UPDATED_NEXT_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllHearingsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where description equals to DEFAULT_DESCRIPTION
        defaultHearingShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the hearingList where description equals to UPDATED_DESCRIPTION
        defaultHearingShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllHearingsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where description not equals to DEFAULT_DESCRIPTION
        defaultHearingShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the hearingList where description not equals to UPDATED_DESCRIPTION
        defaultHearingShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllHearingsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultHearingShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the hearingList where description equals to UPDATED_DESCRIPTION
        defaultHearingShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllHearingsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where description is not null
        defaultHearingShouldBeFound("description.specified=true");

        // Get all the hearingList where description is null
        defaultHearingShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllHearingsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where description contains DEFAULT_DESCRIPTION
        defaultHearingShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the hearingList where description contains UPDATED_DESCRIPTION
        defaultHearingShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllHearingsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where description does not contain DEFAULT_DESCRIPTION
        defaultHearingShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the hearingList where description does not contain UPDATED_DESCRIPTION
        defaultHearingShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllHearingsByPreviousHearingDateIsEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where previousHearingDate equals to DEFAULT_PREVIOUS_HEARING_DATE
        defaultHearingShouldBeFound("previousHearingDate.equals=" + DEFAULT_PREVIOUS_HEARING_DATE);

        // Get all the hearingList where previousHearingDate equals to UPDATED_PREVIOUS_HEARING_DATE
        defaultHearingShouldNotBeFound("previousHearingDate.equals=" + UPDATED_PREVIOUS_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllHearingsByPreviousHearingDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where previousHearingDate not equals to DEFAULT_PREVIOUS_HEARING_DATE
        defaultHearingShouldNotBeFound("previousHearingDate.notEquals=" + DEFAULT_PREVIOUS_HEARING_DATE);

        // Get all the hearingList where previousHearingDate not equals to UPDATED_PREVIOUS_HEARING_DATE
        defaultHearingShouldBeFound("previousHearingDate.notEquals=" + UPDATED_PREVIOUS_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllHearingsByPreviousHearingDateIsInShouldWork() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where previousHearingDate in DEFAULT_PREVIOUS_HEARING_DATE or UPDATED_PREVIOUS_HEARING_DATE
        defaultHearingShouldBeFound("previousHearingDate.in=" + DEFAULT_PREVIOUS_HEARING_DATE + "," + UPDATED_PREVIOUS_HEARING_DATE);

        // Get all the hearingList where previousHearingDate equals to UPDATED_PREVIOUS_HEARING_DATE
        defaultHearingShouldNotBeFound("previousHearingDate.in=" + UPDATED_PREVIOUS_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllHearingsByPreviousHearingDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where previousHearingDate is not null
        defaultHearingShouldBeFound("previousHearingDate.specified=true");

        // Get all the hearingList where previousHearingDate is null
        defaultHearingShouldNotBeFound("previousHearingDate.specified=false");
    }

    @Test
    @Transactional
    void getAllHearingsByPreviousHearingDateContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where previousHearingDate contains DEFAULT_PREVIOUS_HEARING_DATE
        defaultHearingShouldBeFound("previousHearingDate.contains=" + DEFAULT_PREVIOUS_HEARING_DATE);

        // Get all the hearingList where previousHearingDate contains UPDATED_PREVIOUS_HEARING_DATE
        defaultHearingShouldNotBeFound("previousHearingDate.contains=" + UPDATED_PREVIOUS_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllHearingsByPreviousHearingDateNotContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where previousHearingDate does not contain DEFAULT_PREVIOUS_HEARING_DATE
        defaultHearingShouldNotBeFound("previousHearingDate.doesNotContain=" + DEFAULT_PREVIOUS_HEARING_DATE);

        // Get all the hearingList where previousHearingDate does not contain UPDATED_PREVIOUS_HEARING_DATE
        defaultHearingShouldBeFound("previousHearingDate.doesNotContain=" + UPDATED_PREVIOUS_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllHearingsByConclusionIsEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where conclusion equals to DEFAULT_CONCLUSION
        defaultHearingShouldBeFound("conclusion.equals=" + DEFAULT_CONCLUSION);

        // Get all the hearingList where conclusion equals to UPDATED_CONCLUSION
        defaultHearingShouldNotBeFound("conclusion.equals=" + UPDATED_CONCLUSION);
    }

    @Test
    @Transactional
    void getAllHearingsByConclusionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where conclusion not equals to DEFAULT_CONCLUSION
        defaultHearingShouldNotBeFound("conclusion.notEquals=" + DEFAULT_CONCLUSION);

        // Get all the hearingList where conclusion not equals to UPDATED_CONCLUSION
        defaultHearingShouldBeFound("conclusion.notEquals=" + UPDATED_CONCLUSION);
    }

    @Test
    @Transactional
    void getAllHearingsByConclusionIsInShouldWork() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where conclusion in DEFAULT_CONCLUSION or UPDATED_CONCLUSION
        defaultHearingShouldBeFound("conclusion.in=" + DEFAULT_CONCLUSION + "," + UPDATED_CONCLUSION);

        // Get all the hearingList where conclusion equals to UPDATED_CONCLUSION
        defaultHearingShouldNotBeFound("conclusion.in=" + UPDATED_CONCLUSION);
    }

    @Test
    @Transactional
    void getAllHearingsByConclusionIsNullOrNotNull() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where conclusion is not null
        defaultHearingShouldBeFound("conclusion.specified=true");

        // Get all the hearingList where conclusion is null
        defaultHearingShouldNotBeFound("conclusion.specified=false");
    }

    @Test
    @Transactional
    void getAllHearingsByConclusionContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where conclusion contains DEFAULT_CONCLUSION
        defaultHearingShouldBeFound("conclusion.contains=" + DEFAULT_CONCLUSION);

        // Get all the hearingList where conclusion contains UPDATED_CONCLUSION
        defaultHearingShouldNotBeFound("conclusion.contains=" + UPDATED_CONCLUSION);
    }

    @Test
    @Transactional
    void getAllHearingsByConclusionNotContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where conclusion does not contain DEFAULT_CONCLUSION
        defaultHearingShouldNotBeFound("conclusion.doesNotContain=" + DEFAULT_CONCLUSION);

        // Get all the hearingList where conclusion does not contain UPDATED_CONCLUSION
        defaultHearingShouldBeFound("conclusion.doesNotContain=" + UPDATED_CONCLUSION);
    }

    @Test
    @Transactional
    void getAllHearingsByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where comment equals to DEFAULT_COMMENT
        defaultHearingShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the hearingList where comment equals to UPDATED_COMMENT
        defaultHearingShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllHearingsByCommentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where comment not equals to DEFAULT_COMMENT
        defaultHearingShouldNotBeFound("comment.notEquals=" + DEFAULT_COMMENT);

        // Get all the hearingList where comment not equals to UPDATED_COMMENT
        defaultHearingShouldBeFound("comment.notEquals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllHearingsByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultHearingShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the hearingList where comment equals to UPDATED_COMMENT
        defaultHearingShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllHearingsByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where comment is not null
        defaultHearingShouldBeFound("comment.specified=true");

        // Get all the hearingList where comment is null
        defaultHearingShouldNotBeFound("comment.specified=false");
    }

    @Test
    @Transactional
    void getAllHearingsByCommentContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where comment contains DEFAULT_COMMENT
        defaultHearingShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the hearingList where comment contains UPDATED_COMMENT
        defaultHearingShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllHearingsByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where comment does not contain DEFAULT_COMMENT
        defaultHearingShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the hearingList where comment does not contain UPDATED_COMMENT
        defaultHearingShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllHearingsByCaseStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where caseStatus equals to DEFAULT_CASE_STATUS
        defaultHearingShouldBeFound("caseStatus.equals=" + DEFAULT_CASE_STATUS);

        // Get all the hearingList where caseStatus equals to UPDATED_CASE_STATUS
        defaultHearingShouldNotBeFound("caseStatus.equals=" + UPDATED_CASE_STATUS);
    }

    @Test
    @Transactional
    void getAllHearingsByCaseStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where caseStatus not equals to DEFAULT_CASE_STATUS
        defaultHearingShouldNotBeFound("caseStatus.notEquals=" + DEFAULT_CASE_STATUS);

        // Get all the hearingList where caseStatus not equals to UPDATED_CASE_STATUS
        defaultHearingShouldBeFound("caseStatus.notEquals=" + UPDATED_CASE_STATUS);
    }

    @Test
    @Transactional
    void getAllHearingsByCaseStatusIsInShouldWork() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where caseStatus in DEFAULT_CASE_STATUS or UPDATED_CASE_STATUS
        defaultHearingShouldBeFound("caseStatus.in=" + DEFAULT_CASE_STATUS + "," + UPDATED_CASE_STATUS);

        // Get all the hearingList where caseStatus equals to UPDATED_CASE_STATUS
        defaultHearingShouldNotBeFound("caseStatus.in=" + UPDATED_CASE_STATUS);
    }

    @Test
    @Transactional
    void getAllHearingsByCaseStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where caseStatus is not null
        defaultHearingShouldBeFound("caseStatus.specified=true");

        // Get all the hearingList where caseStatus is null
        defaultHearingShouldNotBeFound("caseStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield1IsEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield1 equals to DEFAULT_FREEFIELD_1
        defaultHearingShouldBeFound("freefield1.equals=" + DEFAULT_FREEFIELD_1);

        // Get all the hearingList where freefield1 equals to UPDATED_FREEFIELD_1
        defaultHearingShouldNotBeFound("freefield1.equals=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield1 not equals to DEFAULT_FREEFIELD_1
        defaultHearingShouldNotBeFound("freefield1.notEquals=" + DEFAULT_FREEFIELD_1);

        // Get all the hearingList where freefield1 not equals to UPDATED_FREEFIELD_1
        defaultHearingShouldBeFound("freefield1.notEquals=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield1IsInShouldWork() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield1 in DEFAULT_FREEFIELD_1 or UPDATED_FREEFIELD_1
        defaultHearingShouldBeFound("freefield1.in=" + DEFAULT_FREEFIELD_1 + "," + UPDATED_FREEFIELD_1);

        // Get all the hearingList where freefield1 equals to UPDATED_FREEFIELD_1
        defaultHearingShouldNotBeFound("freefield1.in=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield1IsNullOrNotNull() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield1 is not null
        defaultHearingShouldBeFound("freefield1.specified=true");

        // Get all the hearingList where freefield1 is null
        defaultHearingShouldNotBeFound("freefield1.specified=false");
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield1ContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield1 contains DEFAULT_FREEFIELD_1
        defaultHearingShouldBeFound("freefield1.contains=" + DEFAULT_FREEFIELD_1);

        // Get all the hearingList where freefield1 contains UPDATED_FREEFIELD_1
        defaultHearingShouldNotBeFound("freefield1.contains=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield1NotContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield1 does not contain DEFAULT_FREEFIELD_1
        defaultHearingShouldNotBeFound("freefield1.doesNotContain=" + DEFAULT_FREEFIELD_1);

        // Get all the hearingList where freefield1 does not contain UPDATED_FREEFIELD_1
        defaultHearingShouldBeFound("freefield1.doesNotContain=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield2IsEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield2 equals to DEFAULT_FREEFIELD_2
        defaultHearingShouldBeFound("freefield2.equals=" + DEFAULT_FREEFIELD_2);

        // Get all the hearingList where freefield2 equals to UPDATED_FREEFIELD_2
        defaultHearingShouldNotBeFound("freefield2.equals=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield2 not equals to DEFAULT_FREEFIELD_2
        defaultHearingShouldNotBeFound("freefield2.notEquals=" + DEFAULT_FREEFIELD_2);

        // Get all the hearingList where freefield2 not equals to UPDATED_FREEFIELD_2
        defaultHearingShouldBeFound("freefield2.notEquals=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield2IsInShouldWork() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield2 in DEFAULT_FREEFIELD_2 or UPDATED_FREEFIELD_2
        defaultHearingShouldBeFound("freefield2.in=" + DEFAULT_FREEFIELD_2 + "," + UPDATED_FREEFIELD_2);

        // Get all the hearingList where freefield2 equals to UPDATED_FREEFIELD_2
        defaultHearingShouldNotBeFound("freefield2.in=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield2IsNullOrNotNull() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield2 is not null
        defaultHearingShouldBeFound("freefield2.specified=true");

        // Get all the hearingList where freefield2 is null
        defaultHearingShouldNotBeFound("freefield2.specified=false");
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield2ContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield2 contains DEFAULT_FREEFIELD_2
        defaultHearingShouldBeFound("freefield2.contains=" + DEFAULT_FREEFIELD_2);

        // Get all the hearingList where freefield2 contains UPDATED_FREEFIELD_2
        defaultHearingShouldNotBeFound("freefield2.contains=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield2NotContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield2 does not contain DEFAULT_FREEFIELD_2
        defaultHearingShouldNotBeFound("freefield2.doesNotContain=" + DEFAULT_FREEFIELD_2);

        // Get all the hearingList where freefield2 does not contain UPDATED_FREEFIELD_2
        defaultHearingShouldBeFound("freefield2.doesNotContain=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield3IsEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield3 equals to DEFAULT_FREEFIELD_3
        defaultHearingShouldBeFound("freefield3.equals=" + DEFAULT_FREEFIELD_3);

        // Get all the hearingList where freefield3 equals to UPDATED_FREEFIELD_3
        defaultHearingShouldNotBeFound("freefield3.equals=" + UPDATED_FREEFIELD_3);
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield3 not equals to DEFAULT_FREEFIELD_3
        defaultHearingShouldNotBeFound("freefield3.notEquals=" + DEFAULT_FREEFIELD_3);

        // Get all the hearingList where freefield3 not equals to UPDATED_FREEFIELD_3
        defaultHearingShouldBeFound("freefield3.notEquals=" + UPDATED_FREEFIELD_3);
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield3IsInShouldWork() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield3 in DEFAULT_FREEFIELD_3 or UPDATED_FREEFIELD_3
        defaultHearingShouldBeFound("freefield3.in=" + DEFAULT_FREEFIELD_3 + "," + UPDATED_FREEFIELD_3);

        // Get all the hearingList where freefield3 equals to UPDATED_FREEFIELD_3
        defaultHearingShouldNotBeFound("freefield3.in=" + UPDATED_FREEFIELD_3);
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield3IsNullOrNotNull() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield3 is not null
        defaultHearingShouldBeFound("freefield3.specified=true");

        // Get all the hearingList where freefield3 is null
        defaultHearingShouldNotBeFound("freefield3.specified=false");
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield3ContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield3 contains DEFAULT_FREEFIELD_3
        defaultHearingShouldBeFound("freefield3.contains=" + DEFAULT_FREEFIELD_3);

        // Get all the hearingList where freefield3 contains UPDATED_FREEFIELD_3
        defaultHearingShouldNotBeFound("freefield3.contains=" + UPDATED_FREEFIELD_3);
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield3NotContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield3 does not contain DEFAULT_FREEFIELD_3
        defaultHearingShouldNotBeFound("freefield3.doesNotContain=" + DEFAULT_FREEFIELD_3);

        // Get all the hearingList where freefield3 does not contain UPDATED_FREEFIELD_3
        defaultHearingShouldBeFound("freefield3.doesNotContain=" + UPDATED_FREEFIELD_3);
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield4IsEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield4 equals to DEFAULT_FREEFIELD_4
        defaultHearingShouldBeFound("freefield4.equals=" + DEFAULT_FREEFIELD_4);

        // Get all the hearingList where freefield4 equals to UPDATED_FREEFIELD_4
        defaultHearingShouldNotBeFound("freefield4.equals=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield4IsNotEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield4 not equals to DEFAULT_FREEFIELD_4
        defaultHearingShouldNotBeFound("freefield4.notEquals=" + DEFAULT_FREEFIELD_4);

        // Get all the hearingList where freefield4 not equals to UPDATED_FREEFIELD_4
        defaultHearingShouldBeFound("freefield4.notEquals=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield4IsInShouldWork() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield4 in DEFAULT_FREEFIELD_4 or UPDATED_FREEFIELD_4
        defaultHearingShouldBeFound("freefield4.in=" + DEFAULT_FREEFIELD_4 + "," + UPDATED_FREEFIELD_4);

        // Get all the hearingList where freefield4 equals to UPDATED_FREEFIELD_4
        defaultHearingShouldNotBeFound("freefield4.in=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield4IsNullOrNotNull() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield4 is not null
        defaultHearingShouldBeFound("freefield4.specified=true");

        // Get all the hearingList where freefield4 is null
        defaultHearingShouldNotBeFound("freefield4.specified=false");
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield4ContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield4 contains DEFAULT_FREEFIELD_4
        defaultHearingShouldBeFound("freefield4.contains=" + DEFAULT_FREEFIELD_4);

        // Get all the hearingList where freefield4 contains UPDATED_FREEFIELD_4
        defaultHearingShouldNotBeFound("freefield4.contains=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield4NotContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield4 does not contain DEFAULT_FREEFIELD_4
        defaultHearingShouldNotBeFound("freefield4.doesNotContain=" + DEFAULT_FREEFIELD_4);

        // Get all the hearingList where freefield4 does not contain UPDATED_FREEFIELD_4
        defaultHearingShouldBeFound("freefield4.doesNotContain=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield5IsEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield5 equals to DEFAULT_FREEFIELD_5
        defaultHearingShouldBeFound("freefield5.equals=" + DEFAULT_FREEFIELD_5);

        // Get all the hearingList where freefield5 equals to UPDATED_FREEFIELD_5
        defaultHearingShouldNotBeFound("freefield5.equals=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield5IsNotEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield5 not equals to DEFAULT_FREEFIELD_5
        defaultHearingShouldNotBeFound("freefield5.notEquals=" + DEFAULT_FREEFIELD_5);

        // Get all the hearingList where freefield5 not equals to UPDATED_FREEFIELD_5
        defaultHearingShouldBeFound("freefield5.notEquals=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield5IsInShouldWork() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield5 in DEFAULT_FREEFIELD_5 or UPDATED_FREEFIELD_5
        defaultHearingShouldBeFound("freefield5.in=" + DEFAULT_FREEFIELD_5 + "," + UPDATED_FREEFIELD_5);

        // Get all the hearingList where freefield5 equals to UPDATED_FREEFIELD_5
        defaultHearingShouldNotBeFound("freefield5.in=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield5IsNullOrNotNull() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield5 is not null
        defaultHearingShouldBeFound("freefield5.specified=true");

        // Get all the hearingList where freefield5 is null
        defaultHearingShouldNotBeFound("freefield5.specified=false");
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield5ContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield5 contains DEFAULT_FREEFIELD_5
        defaultHearingShouldBeFound("freefield5.contains=" + DEFAULT_FREEFIELD_5);

        // Get all the hearingList where freefield5 contains UPDATED_FREEFIELD_5
        defaultHearingShouldNotBeFound("freefield5.contains=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllHearingsByFreefield5NotContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where freefield5 does not contain DEFAULT_FREEFIELD_5
        defaultHearingShouldNotBeFound("freefield5.doesNotContain=" + DEFAULT_FREEFIELD_5);

        // Get all the hearingList where freefield5 does not contain UPDATED_FREEFIELD_5
        defaultHearingShouldBeFound("freefield5.doesNotContain=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllHearingsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultHearingShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the hearingList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultHearingShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllHearingsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultHearingShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the hearingList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultHearingShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllHearingsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultHearingShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the hearingList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultHearingShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllHearingsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where lastModifiedBy is not null
        defaultHearingShouldBeFound("lastModifiedBy.specified=true");

        // Get all the hearingList where lastModifiedBy is null
        defaultHearingShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllHearingsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultHearingShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the hearingList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultHearingShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllHearingsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultHearingShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the hearingList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultHearingShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllHearingsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultHearingShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the hearingList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultHearingShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllHearingsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultHearingShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the hearingList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultHearingShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllHearingsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultHearingShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the hearingList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultHearingShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllHearingsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where lastModified is not null
        defaultHearingShouldBeFound("lastModified.specified=true");

        // Get all the hearingList where lastModified is null
        defaultHearingShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllHearingsByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultHearingShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the hearingList where lastModified contains UPDATED_LAST_MODIFIED
        defaultHearingShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllHearingsByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        // Get all the hearingList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultHearingShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the hearingList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultHearingShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllHearingsByCourtCaseIsEqualToSomething() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);
        CourtCase courtCase;
        if (TestUtil.findAll(em, CourtCase.class).isEmpty()) {
            courtCase = CourtCaseResourceIT.createEntity(em);
            em.persist(courtCase);
            em.flush();
        } else {
            courtCase = TestUtil.findAll(em, CourtCase.class).get(0);
        }
        em.persist(courtCase);
        em.flush();
        hearing.setCourtCase(courtCase);
        hearingRepository.saveAndFlush(hearing);
        Long courtCaseId = courtCase.getId();

        // Get all the hearingList where courtCase equals to courtCaseId
        defaultHearingShouldBeFound("courtCaseId.equals=" + courtCaseId);

        // Get all the hearingList where courtCase equals to (courtCaseId + 1)
        defaultHearingShouldNotBeFound("courtCaseId.equals=" + (courtCaseId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHearingShouldBeFound(String filter) throws Exception {
        restHearingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hearing.getId().intValue())))
            .andExpect(jsonPath("$.[*].hearingDate").value(hasItem(DEFAULT_HEARING_DATE)))
            .andExpect(jsonPath("$.[*].nextHearingDate").value(hasItem(DEFAULT_NEXT_HEARING_DATE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].previousHearingDate").value(hasItem(DEFAULT_PREVIOUS_HEARING_DATE)))
            .andExpect(jsonPath("$.[*].conclusion").value(hasItem(DEFAULT_CONCLUSION)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].caseStatus").value(hasItem(DEFAULT_CASE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].freefield1").value(hasItem(DEFAULT_FREEFIELD_1)))
            .andExpect(jsonPath("$.[*].freefield2").value(hasItem(DEFAULT_FREEFIELD_2)))
            .andExpect(jsonPath("$.[*].freefield3").value(hasItem(DEFAULT_FREEFIELD_3)))
            .andExpect(jsonPath("$.[*].freefield4").value(hasItem(DEFAULT_FREEFIELD_4)))
            .andExpect(jsonPath("$.[*].freefield5").value(hasItem(DEFAULT_FREEFIELD_5)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)));

        // Check, that the count call also returns 1
        restHearingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHearingShouldNotBeFound(String filter) throws Exception {
        restHearingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHearingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingHearing() throws Exception {
        // Get the hearing
        restHearingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHearing() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        int databaseSizeBeforeUpdate = hearingRepository.findAll().size();

        // Update the hearing
        Hearing updatedHearing = hearingRepository.findById(hearing.getId()).get();
        // Disconnect from session so that the updates on updatedHearing are not directly saved in db
        em.detach(updatedHearing);
        updatedHearing
            .hearingDate(UPDATED_HEARING_DATE)
            .nextHearingDate(UPDATED_NEXT_HEARING_DATE)
            .description(UPDATED_DESCRIPTION)
            .previousHearingDate(UPDATED_PREVIOUS_HEARING_DATE)
            .conclusion(UPDATED_CONCLUSION)
            .comment(UPDATED_COMMENT)
            .caseStatus(UPDATED_CASE_STATUS)
            .freefield1(UPDATED_FREEFIELD_1)
            .freefield2(UPDATED_FREEFIELD_2)
            .freefield3(UPDATED_FREEFIELD_3)
            .freefield4(UPDATED_FREEFIELD_4)
            .freefield5(UPDATED_FREEFIELD_5)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModified(UPDATED_LAST_MODIFIED);
        HearingDTO hearingDTO = hearingMapper.toDto(updatedHearing);

        restHearingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hearingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hearingDTO))
            )
            .andExpect(status().isOk());

        // Validate the Hearing in the database
        List<Hearing> hearingList = hearingRepository.findAll();
        assertThat(hearingList).hasSize(databaseSizeBeforeUpdate);
        Hearing testHearing = hearingList.get(hearingList.size() - 1);
        assertThat(testHearing.getHearingDate()).isEqualTo(UPDATED_HEARING_DATE);
        assertThat(testHearing.getNextHearingDate()).isEqualTo(UPDATED_NEXT_HEARING_DATE);
        assertThat(testHearing.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testHearing.getPreviousHearingDate()).isEqualTo(UPDATED_PREVIOUS_HEARING_DATE);
        assertThat(testHearing.getConclusion()).isEqualTo(UPDATED_CONCLUSION);
        assertThat(testHearing.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testHearing.getCaseStatus()).isEqualTo(UPDATED_CASE_STATUS);
        assertThat(testHearing.getFreefield1()).isEqualTo(UPDATED_FREEFIELD_1);
        assertThat(testHearing.getFreefield2()).isEqualTo(UPDATED_FREEFIELD_2);
        assertThat(testHearing.getFreefield3()).isEqualTo(UPDATED_FREEFIELD_3);
        assertThat(testHearing.getFreefield4()).isEqualTo(UPDATED_FREEFIELD_4);
        assertThat(testHearing.getFreefield5()).isEqualTo(UPDATED_FREEFIELD_5);
        assertThat(testHearing.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testHearing.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void putNonExistingHearing() throws Exception {
        int databaseSizeBeforeUpdate = hearingRepository.findAll().size();
        hearing.setId(count.incrementAndGet());

        // Create the Hearing
        HearingDTO hearingDTO = hearingMapper.toDto(hearing);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHearingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hearingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hearingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hearing in the database
        List<Hearing> hearingList = hearingRepository.findAll();
        assertThat(hearingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHearing() throws Exception {
        int databaseSizeBeforeUpdate = hearingRepository.findAll().size();
        hearing.setId(count.incrementAndGet());

        // Create the Hearing
        HearingDTO hearingDTO = hearingMapper.toDto(hearing);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHearingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hearingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hearing in the database
        List<Hearing> hearingList = hearingRepository.findAll();
        assertThat(hearingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHearing() throws Exception {
        int databaseSizeBeforeUpdate = hearingRepository.findAll().size();
        hearing.setId(count.incrementAndGet());

        // Create the Hearing
        HearingDTO hearingDTO = hearingMapper.toDto(hearing);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHearingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hearingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hearing in the database
        List<Hearing> hearingList = hearingRepository.findAll();
        assertThat(hearingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHearingWithPatch() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        int databaseSizeBeforeUpdate = hearingRepository.findAll().size();

        // Update the hearing using partial update
        Hearing partialUpdatedHearing = new Hearing();
        partialUpdatedHearing.setId(hearing.getId());

        partialUpdatedHearing
            .hearingDate(UPDATED_HEARING_DATE)
            .nextHearingDate(UPDATED_NEXT_HEARING_DATE)
            .description(UPDATED_DESCRIPTION)
            .conclusion(UPDATED_CONCLUSION)
            .comment(UPDATED_COMMENT)
            .caseStatus(UPDATED_CASE_STATUS)
            .freefield1(UPDATED_FREEFIELD_1)
            .freefield2(UPDATED_FREEFIELD_2)
            .freefield4(UPDATED_FREEFIELD_4)
            .freefield5(UPDATED_FREEFIELD_5)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModified(UPDATED_LAST_MODIFIED);

        restHearingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHearing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHearing))
            )
            .andExpect(status().isOk());

        // Validate the Hearing in the database
        List<Hearing> hearingList = hearingRepository.findAll();
        assertThat(hearingList).hasSize(databaseSizeBeforeUpdate);
        Hearing testHearing = hearingList.get(hearingList.size() - 1);
        assertThat(testHearing.getHearingDate()).isEqualTo(UPDATED_HEARING_DATE);
        assertThat(testHearing.getNextHearingDate()).isEqualTo(UPDATED_NEXT_HEARING_DATE);
        assertThat(testHearing.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testHearing.getPreviousHearingDate()).isEqualTo(DEFAULT_PREVIOUS_HEARING_DATE);
        assertThat(testHearing.getConclusion()).isEqualTo(UPDATED_CONCLUSION);
        assertThat(testHearing.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testHearing.getCaseStatus()).isEqualTo(UPDATED_CASE_STATUS);
        assertThat(testHearing.getFreefield1()).isEqualTo(UPDATED_FREEFIELD_1);
        assertThat(testHearing.getFreefield2()).isEqualTo(UPDATED_FREEFIELD_2);
        assertThat(testHearing.getFreefield3()).isEqualTo(DEFAULT_FREEFIELD_3);
        assertThat(testHearing.getFreefield4()).isEqualTo(UPDATED_FREEFIELD_4);
        assertThat(testHearing.getFreefield5()).isEqualTo(UPDATED_FREEFIELD_5);
        assertThat(testHearing.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testHearing.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void fullUpdateHearingWithPatch() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        int databaseSizeBeforeUpdate = hearingRepository.findAll().size();

        // Update the hearing using partial update
        Hearing partialUpdatedHearing = new Hearing();
        partialUpdatedHearing.setId(hearing.getId());

        partialUpdatedHearing
            .hearingDate(UPDATED_HEARING_DATE)
            .nextHearingDate(UPDATED_NEXT_HEARING_DATE)
            .description(UPDATED_DESCRIPTION)
            .previousHearingDate(UPDATED_PREVIOUS_HEARING_DATE)
            .conclusion(UPDATED_CONCLUSION)
            .comment(UPDATED_COMMENT)
            .caseStatus(UPDATED_CASE_STATUS)
            .freefield1(UPDATED_FREEFIELD_1)
            .freefield2(UPDATED_FREEFIELD_2)
            .freefield3(UPDATED_FREEFIELD_3)
            .freefield4(UPDATED_FREEFIELD_4)
            .freefield5(UPDATED_FREEFIELD_5)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModified(UPDATED_LAST_MODIFIED);

        restHearingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHearing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHearing))
            )
            .andExpect(status().isOk());

        // Validate the Hearing in the database
        List<Hearing> hearingList = hearingRepository.findAll();
        assertThat(hearingList).hasSize(databaseSizeBeforeUpdate);
        Hearing testHearing = hearingList.get(hearingList.size() - 1);
        assertThat(testHearing.getHearingDate()).isEqualTo(UPDATED_HEARING_DATE);
        assertThat(testHearing.getNextHearingDate()).isEqualTo(UPDATED_NEXT_HEARING_DATE);
        assertThat(testHearing.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testHearing.getPreviousHearingDate()).isEqualTo(UPDATED_PREVIOUS_HEARING_DATE);
        assertThat(testHearing.getConclusion()).isEqualTo(UPDATED_CONCLUSION);
        assertThat(testHearing.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testHearing.getCaseStatus()).isEqualTo(UPDATED_CASE_STATUS);
        assertThat(testHearing.getFreefield1()).isEqualTo(UPDATED_FREEFIELD_1);
        assertThat(testHearing.getFreefield2()).isEqualTo(UPDATED_FREEFIELD_2);
        assertThat(testHearing.getFreefield3()).isEqualTo(UPDATED_FREEFIELD_3);
        assertThat(testHearing.getFreefield4()).isEqualTo(UPDATED_FREEFIELD_4);
        assertThat(testHearing.getFreefield5()).isEqualTo(UPDATED_FREEFIELD_5);
        assertThat(testHearing.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testHearing.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void patchNonExistingHearing() throws Exception {
        int databaseSizeBeforeUpdate = hearingRepository.findAll().size();
        hearing.setId(count.incrementAndGet());

        // Create the Hearing
        HearingDTO hearingDTO = hearingMapper.toDto(hearing);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHearingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hearingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hearingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hearing in the database
        List<Hearing> hearingList = hearingRepository.findAll();
        assertThat(hearingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHearing() throws Exception {
        int databaseSizeBeforeUpdate = hearingRepository.findAll().size();
        hearing.setId(count.incrementAndGet());

        // Create the Hearing
        HearingDTO hearingDTO = hearingMapper.toDto(hearing);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHearingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hearingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hearing in the database
        List<Hearing> hearingList = hearingRepository.findAll();
        assertThat(hearingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHearing() throws Exception {
        int databaseSizeBeforeUpdate = hearingRepository.findAll().size();
        hearing.setId(count.incrementAndGet());

        // Create the Hearing
        HearingDTO hearingDTO = hearingMapper.toDto(hearing);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHearingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(hearingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hearing in the database
        List<Hearing> hearingList = hearingRepository.findAll();
        assertThat(hearingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHearing() throws Exception {
        // Initialize the database
        hearingRepository.saveAndFlush(hearing);

        int databaseSizeBeforeDelete = hearingRepository.findAll().size();

        // Delete the hearing
        restHearingMockMvc
            .perform(delete(ENTITY_API_URL_ID, hearing.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Hearing> hearingList = hearingRepository.findAll();
        assertThat(hearingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
