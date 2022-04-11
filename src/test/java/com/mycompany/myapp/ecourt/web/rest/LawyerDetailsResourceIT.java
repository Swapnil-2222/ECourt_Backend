package com.mycompany.myapp.ecourt.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.ecourt.IntegrationTest;
import com.mycompany.myapp.ecourt.domain.CourtCase;
import com.mycompany.myapp.ecourt.domain.LawyerDetails;
import com.mycompany.myapp.ecourt.domain.enumeration.LawyerType;
import com.mycompany.myapp.ecourt.domain.enumeration.UserType;
import com.mycompany.myapp.ecourt.repository.LawyerDetailsRepository;
import com.mycompany.myapp.ecourt.service.LawyerDetailsService;
import com.mycompany.myapp.ecourt.service.criteria.LawyerDetailsCriteria;
import com.mycompany.myapp.ecourt.service.dto.LawyerDetailsDTO;
import com.mycompany.myapp.ecourt.service.mapper.LawyerDetailsMapper;
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
 * Integration tests for the {@link LawyerDetailsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class LawyerDetailsResourceIT {

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_BAR_REGN_NO = "AAAAAAAAAA";
    private static final String UPDATED_BAR_REGN_NO = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_LAWYER_EXPERIENCE = "AAAAAAAAAA";
    private static final String UPDATED_LAWYER_EXPERIENCE = "BBBBBBBBBB";

    private static final String DEFAULT_FREEFIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREEFIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREEFIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREEFIELD_2 = "BBBBBBBBBB";

    private static final UserType DEFAULT_USER_TYPE = UserType.USERTYPE;
    private static final UserType UPDATED_USER_TYPE = UserType.LAWYERTYPE;

    private static final LawyerType DEFAULT_LAWYER_TYPE = LawyerType.PRIVATELAWYER;
    private static final LawyerType UPDATED_LAWYER_TYPE = LawyerType.CORPORATIONLAWYER;

    private static final Boolean DEFAULT_IS_ACTIVATED = false;
    private static final Boolean UPDATED_IS_ACTIVATED = true;

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/lawyer-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LawyerDetailsRepository lawyerDetailsRepository;

    @Mock
    private LawyerDetailsRepository lawyerDetailsRepositoryMock;

    @Autowired
    private LawyerDetailsMapper lawyerDetailsMapper;

    @Mock
    private LawyerDetailsService lawyerDetailsServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLawyerDetailsMockMvc;

    private LawyerDetails lawyerDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LawyerDetails createEntity(EntityManager em) {
        LawyerDetails lawyerDetails = new LawyerDetails()
            .fullName(DEFAULT_FULL_NAME)
            .contactNo(DEFAULT_CONTACT_NO)
            .emailId(DEFAULT_EMAIL_ID)
            .barRegnNo(DEFAULT_BAR_REGN_NO)
            .address(DEFAULT_ADDRESS)
            .lawyerExperience(DEFAULT_LAWYER_EXPERIENCE)
            .freefield1(DEFAULT_FREEFIELD_1)
            .freefield2(DEFAULT_FREEFIELD_2)
            .userType(DEFAULT_USER_TYPE)
            .lawyerType(DEFAULT_LAWYER_TYPE)
            .isActivated(DEFAULT_IS_ACTIVATED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModified(DEFAULT_LAST_MODIFIED);
        return lawyerDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LawyerDetails createUpdatedEntity(EntityManager em) {
        LawyerDetails lawyerDetails = new LawyerDetails()
            .fullName(UPDATED_FULL_NAME)
            .contactNo(UPDATED_CONTACT_NO)
            .emailId(UPDATED_EMAIL_ID)
            .barRegnNo(UPDATED_BAR_REGN_NO)
            .address(UPDATED_ADDRESS)
            .lawyerExperience(UPDATED_LAWYER_EXPERIENCE)
            .freefield1(UPDATED_FREEFIELD_1)
            .freefield2(UPDATED_FREEFIELD_2)
            .userType(UPDATED_USER_TYPE)
            .lawyerType(UPDATED_LAWYER_TYPE)
            .isActivated(UPDATED_IS_ACTIVATED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModified(UPDATED_LAST_MODIFIED);
        return lawyerDetails;
    }

    @BeforeEach
    public void initTest() {
        lawyerDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createLawyerDetails() throws Exception {
        int databaseSizeBeforeCreate = lawyerDetailsRepository.findAll().size();
        // Create the LawyerDetails
        LawyerDetailsDTO lawyerDetailsDTO = lawyerDetailsMapper.toDto(lawyerDetails);
        restLawyerDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lawyerDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LawyerDetails in the database
        List<LawyerDetails> lawyerDetailsList = lawyerDetailsRepository.findAll();
        assertThat(lawyerDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        LawyerDetails testLawyerDetails = lawyerDetailsList.get(lawyerDetailsList.size() - 1);
        assertThat(testLawyerDetails.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testLawyerDetails.getContactNo()).isEqualTo(DEFAULT_CONTACT_NO);
        assertThat(testLawyerDetails.getEmailId()).isEqualTo(DEFAULT_EMAIL_ID);
        assertThat(testLawyerDetails.getBarRegnNo()).isEqualTo(DEFAULT_BAR_REGN_NO);
        assertThat(testLawyerDetails.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testLawyerDetails.getLawyerExperience()).isEqualTo(DEFAULT_LAWYER_EXPERIENCE);
        assertThat(testLawyerDetails.getFreefield1()).isEqualTo(DEFAULT_FREEFIELD_1);
        assertThat(testLawyerDetails.getFreefield2()).isEqualTo(DEFAULT_FREEFIELD_2);
        assertThat(testLawyerDetails.getUserType()).isEqualTo(DEFAULT_USER_TYPE);
        assertThat(testLawyerDetails.getLawyerType()).isEqualTo(DEFAULT_LAWYER_TYPE);
        assertThat(testLawyerDetails.getIsActivated()).isEqualTo(DEFAULT_IS_ACTIVATED);
        assertThat(testLawyerDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testLawyerDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void createLawyerDetailsWithExistingId() throws Exception {
        // Create the LawyerDetails with an existing ID
        lawyerDetails.setId(1L);
        LawyerDetailsDTO lawyerDetailsDTO = lawyerDetailsMapper.toDto(lawyerDetails);

        int databaseSizeBeforeCreate = lawyerDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLawyerDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lawyerDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LawyerDetails in the database
        List<LawyerDetails> lawyerDetailsList = lawyerDetailsRepository.findAll();
        assertThat(lawyerDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLawyerDetails() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList
        restLawyerDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lawyerDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO)))
            .andExpect(jsonPath("$.[*].emailId").value(hasItem(DEFAULT_EMAIL_ID)))
            .andExpect(jsonPath("$.[*].barRegnNo").value(hasItem(DEFAULT_BAR_REGN_NO)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].lawyerExperience").value(hasItem(DEFAULT_LAWYER_EXPERIENCE)))
            .andExpect(jsonPath("$.[*].freefield1").value(hasItem(DEFAULT_FREEFIELD_1)))
            .andExpect(jsonPath("$.[*].freefield2").value(hasItem(DEFAULT_FREEFIELD_2)))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].lawyerType").value(hasItem(DEFAULT_LAWYER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isActivated").value(hasItem(DEFAULT_IS_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLawyerDetailsWithEagerRelationshipsIsEnabled() throws Exception {
        when(lawyerDetailsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLawyerDetailsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(lawyerDetailsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLawyerDetailsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(lawyerDetailsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLawyerDetailsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(lawyerDetailsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getLawyerDetails() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get the lawyerDetails
        restLawyerDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, lawyerDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lawyerDetails.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.contactNo").value(DEFAULT_CONTACT_NO))
            .andExpect(jsonPath("$.emailId").value(DEFAULT_EMAIL_ID))
            .andExpect(jsonPath("$.barRegnNo").value(DEFAULT_BAR_REGN_NO))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.lawyerExperience").value(DEFAULT_LAWYER_EXPERIENCE))
            .andExpect(jsonPath("$.freefield1").value(DEFAULT_FREEFIELD_1))
            .andExpect(jsonPath("$.freefield2").value(DEFAULT_FREEFIELD_2))
            .andExpect(jsonPath("$.userType").value(DEFAULT_USER_TYPE.toString()))
            .andExpect(jsonPath("$.lawyerType").value(DEFAULT_LAWYER_TYPE.toString()))
            .andExpect(jsonPath("$.isActivated").value(DEFAULT_IS_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED));
    }

    @Test
    @Transactional
    void getLawyerDetailsByIdFiltering() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        Long id = lawyerDetails.getId();

        defaultLawyerDetailsShouldBeFound("id.equals=" + id);
        defaultLawyerDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultLawyerDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLawyerDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultLawyerDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLawyerDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where fullName equals to DEFAULT_FULL_NAME
        defaultLawyerDetailsShouldBeFound("fullName.equals=" + DEFAULT_FULL_NAME);

        // Get all the lawyerDetailsList where fullName equals to UPDATED_FULL_NAME
        defaultLawyerDetailsShouldNotBeFound("fullName.equals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByFullNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where fullName not equals to DEFAULT_FULL_NAME
        defaultLawyerDetailsShouldNotBeFound("fullName.notEquals=" + DEFAULT_FULL_NAME);

        // Get all the lawyerDetailsList where fullName not equals to UPDATED_FULL_NAME
        defaultLawyerDetailsShouldBeFound("fullName.notEquals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where fullName in DEFAULT_FULL_NAME or UPDATED_FULL_NAME
        defaultLawyerDetailsShouldBeFound("fullName.in=" + DEFAULT_FULL_NAME + "," + UPDATED_FULL_NAME);

        // Get all the lawyerDetailsList where fullName equals to UPDATED_FULL_NAME
        defaultLawyerDetailsShouldNotBeFound("fullName.in=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where fullName is not null
        defaultLawyerDetailsShouldBeFound("fullName.specified=true");

        // Get all the lawyerDetailsList where fullName is null
        defaultLawyerDetailsShouldNotBeFound("fullName.specified=false");
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByFullNameContainsSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where fullName contains DEFAULT_FULL_NAME
        defaultLawyerDetailsShouldBeFound("fullName.contains=" + DEFAULT_FULL_NAME);

        // Get all the lawyerDetailsList where fullName contains UPDATED_FULL_NAME
        defaultLawyerDetailsShouldNotBeFound("fullName.contains=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where fullName does not contain DEFAULT_FULL_NAME
        defaultLawyerDetailsShouldNotBeFound("fullName.doesNotContain=" + DEFAULT_FULL_NAME);

        // Get all the lawyerDetailsList where fullName does not contain UPDATED_FULL_NAME
        defaultLawyerDetailsShouldBeFound("fullName.doesNotContain=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByContactNoIsEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where contactNo equals to DEFAULT_CONTACT_NO
        defaultLawyerDetailsShouldBeFound("contactNo.equals=" + DEFAULT_CONTACT_NO);

        // Get all the lawyerDetailsList where contactNo equals to UPDATED_CONTACT_NO
        defaultLawyerDetailsShouldNotBeFound("contactNo.equals=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByContactNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where contactNo not equals to DEFAULT_CONTACT_NO
        defaultLawyerDetailsShouldNotBeFound("contactNo.notEquals=" + DEFAULT_CONTACT_NO);

        // Get all the lawyerDetailsList where contactNo not equals to UPDATED_CONTACT_NO
        defaultLawyerDetailsShouldBeFound("contactNo.notEquals=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByContactNoIsInShouldWork() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where contactNo in DEFAULT_CONTACT_NO or UPDATED_CONTACT_NO
        defaultLawyerDetailsShouldBeFound("contactNo.in=" + DEFAULT_CONTACT_NO + "," + UPDATED_CONTACT_NO);

        // Get all the lawyerDetailsList where contactNo equals to UPDATED_CONTACT_NO
        defaultLawyerDetailsShouldNotBeFound("contactNo.in=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByContactNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where contactNo is not null
        defaultLawyerDetailsShouldBeFound("contactNo.specified=true");

        // Get all the lawyerDetailsList where contactNo is null
        defaultLawyerDetailsShouldNotBeFound("contactNo.specified=false");
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByContactNoContainsSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where contactNo contains DEFAULT_CONTACT_NO
        defaultLawyerDetailsShouldBeFound("contactNo.contains=" + DEFAULT_CONTACT_NO);

        // Get all the lawyerDetailsList where contactNo contains UPDATED_CONTACT_NO
        defaultLawyerDetailsShouldNotBeFound("contactNo.contains=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByContactNoNotContainsSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where contactNo does not contain DEFAULT_CONTACT_NO
        defaultLawyerDetailsShouldNotBeFound("contactNo.doesNotContain=" + DEFAULT_CONTACT_NO);

        // Get all the lawyerDetailsList where contactNo does not contain UPDATED_CONTACT_NO
        defaultLawyerDetailsShouldBeFound("contactNo.doesNotContain=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByEmailIdIsEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where emailId equals to DEFAULT_EMAIL_ID
        defaultLawyerDetailsShouldBeFound("emailId.equals=" + DEFAULT_EMAIL_ID);

        // Get all the lawyerDetailsList where emailId equals to UPDATED_EMAIL_ID
        defaultLawyerDetailsShouldNotBeFound("emailId.equals=" + UPDATED_EMAIL_ID);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByEmailIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where emailId not equals to DEFAULT_EMAIL_ID
        defaultLawyerDetailsShouldNotBeFound("emailId.notEquals=" + DEFAULT_EMAIL_ID);

        // Get all the lawyerDetailsList where emailId not equals to UPDATED_EMAIL_ID
        defaultLawyerDetailsShouldBeFound("emailId.notEquals=" + UPDATED_EMAIL_ID);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByEmailIdIsInShouldWork() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where emailId in DEFAULT_EMAIL_ID or UPDATED_EMAIL_ID
        defaultLawyerDetailsShouldBeFound("emailId.in=" + DEFAULT_EMAIL_ID + "," + UPDATED_EMAIL_ID);

        // Get all the lawyerDetailsList where emailId equals to UPDATED_EMAIL_ID
        defaultLawyerDetailsShouldNotBeFound("emailId.in=" + UPDATED_EMAIL_ID);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByEmailIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where emailId is not null
        defaultLawyerDetailsShouldBeFound("emailId.specified=true");

        // Get all the lawyerDetailsList where emailId is null
        defaultLawyerDetailsShouldNotBeFound("emailId.specified=false");
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByEmailIdContainsSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where emailId contains DEFAULT_EMAIL_ID
        defaultLawyerDetailsShouldBeFound("emailId.contains=" + DEFAULT_EMAIL_ID);

        // Get all the lawyerDetailsList where emailId contains UPDATED_EMAIL_ID
        defaultLawyerDetailsShouldNotBeFound("emailId.contains=" + UPDATED_EMAIL_ID);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByEmailIdNotContainsSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where emailId does not contain DEFAULT_EMAIL_ID
        defaultLawyerDetailsShouldNotBeFound("emailId.doesNotContain=" + DEFAULT_EMAIL_ID);

        // Get all the lawyerDetailsList where emailId does not contain UPDATED_EMAIL_ID
        defaultLawyerDetailsShouldBeFound("emailId.doesNotContain=" + UPDATED_EMAIL_ID);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByBarRegnNoIsEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where barRegnNo equals to DEFAULT_BAR_REGN_NO
        defaultLawyerDetailsShouldBeFound("barRegnNo.equals=" + DEFAULT_BAR_REGN_NO);

        // Get all the lawyerDetailsList where barRegnNo equals to UPDATED_BAR_REGN_NO
        defaultLawyerDetailsShouldNotBeFound("barRegnNo.equals=" + UPDATED_BAR_REGN_NO);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByBarRegnNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where barRegnNo not equals to DEFAULT_BAR_REGN_NO
        defaultLawyerDetailsShouldNotBeFound("barRegnNo.notEquals=" + DEFAULT_BAR_REGN_NO);

        // Get all the lawyerDetailsList where barRegnNo not equals to UPDATED_BAR_REGN_NO
        defaultLawyerDetailsShouldBeFound("barRegnNo.notEquals=" + UPDATED_BAR_REGN_NO);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByBarRegnNoIsInShouldWork() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where barRegnNo in DEFAULT_BAR_REGN_NO or UPDATED_BAR_REGN_NO
        defaultLawyerDetailsShouldBeFound("barRegnNo.in=" + DEFAULT_BAR_REGN_NO + "," + UPDATED_BAR_REGN_NO);

        // Get all the lawyerDetailsList where barRegnNo equals to UPDATED_BAR_REGN_NO
        defaultLawyerDetailsShouldNotBeFound("barRegnNo.in=" + UPDATED_BAR_REGN_NO);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByBarRegnNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where barRegnNo is not null
        defaultLawyerDetailsShouldBeFound("barRegnNo.specified=true");

        // Get all the lawyerDetailsList where barRegnNo is null
        defaultLawyerDetailsShouldNotBeFound("barRegnNo.specified=false");
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByBarRegnNoContainsSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where barRegnNo contains DEFAULT_BAR_REGN_NO
        defaultLawyerDetailsShouldBeFound("barRegnNo.contains=" + DEFAULT_BAR_REGN_NO);

        // Get all the lawyerDetailsList where barRegnNo contains UPDATED_BAR_REGN_NO
        defaultLawyerDetailsShouldNotBeFound("barRegnNo.contains=" + UPDATED_BAR_REGN_NO);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByBarRegnNoNotContainsSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where barRegnNo does not contain DEFAULT_BAR_REGN_NO
        defaultLawyerDetailsShouldNotBeFound("barRegnNo.doesNotContain=" + DEFAULT_BAR_REGN_NO);

        // Get all the lawyerDetailsList where barRegnNo does not contain UPDATED_BAR_REGN_NO
        defaultLawyerDetailsShouldBeFound("barRegnNo.doesNotContain=" + UPDATED_BAR_REGN_NO);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where address equals to DEFAULT_ADDRESS
        defaultLawyerDetailsShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the lawyerDetailsList where address equals to UPDATED_ADDRESS
        defaultLawyerDetailsShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where address not equals to DEFAULT_ADDRESS
        defaultLawyerDetailsShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the lawyerDetailsList where address not equals to UPDATED_ADDRESS
        defaultLawyerDetailsShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultLawyerDetailsShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the lawyerDetailsList where address equals to UPDATED_ADDRESS
        defaultLawyerDetailsShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where address is not null
        defaultLawyerDetailsShouldBeFound("address.specified=true");

        // Get all the lawyerDetailsList where address is null
        defaultLawyerDetailsShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByAddressContainsSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where address contains DEFAULT_ADDRESS
        defaultLawyerDetailsShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the lawyerDetailsList where address contains UPDATED_ADDRESS
        defaultLawyerDetailsShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where address does not contain DEFAULT_ADDRESS
        defaultLawyerDetailsShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the lawyerDetailsList where address does not contain UPDATED_ADDRESS
        defaultLawyerDetailsShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByLawyerExperienceIsEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where lawyerExperience equals to DEFAULT_LAWYER_EXPERIENCE
        defaultLawyerDetailsShouldBeFound("lawyerExperience.equals=" + DEFAULT_LAWYER_EXPERIENCE);

        // Get all the lawyerDetailsList where lawyerExperience equals to UPDATED_LAWYER_EXPERIENCE
        defaultLawyerDetailsShouldNotBeFound("lawyerExperience.equals=" + UPDATED_LAWYER_EXPERIENCE);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByLawyerExperienceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where lawyerExperience not equals to DEFAULT_LAWYER_EXPERIENCE
        defaultLawyerDetailsShouldNotBeFound("lawyerExperience.notEquals=" + DEFAULT_LAWYER_EXPERIENCE);

        // Get all the lawyerDetailsList where lawyerExperience not equals to UPDATED_LAWYER_EXPERIENCE
        defaultLawyerDetailsShouldBeFound("lawyerExperience.notEquals=" + UPDATED_LAWYER_EXPERIENCE);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByLawyerExperienceIsInShouldWork() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where lawyerExperience in DEFAULT_LAWYER_EXPERIENCE or UPDATED_LAWYER_EXPERIENCE
        defaultLawyerDetailsShouldBeFound("lawyerExperience.in=" + DEFAULT_LAWYER_EXPERIENCE + "," + UPDATED_LAWYER_EXPERIENCE);

        // Get all the lawyerDetailsList where lawyerExperience equals to UPDATED_LAWYER_EXPERIENCE
        defaultLawyerDetailsShouldNotBeFound("lawyerExperience.in=" + UPDATED_LAWYER_EXPERIENCE);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByLawyerExperienceIsNullOrNotNull() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where lawyerExperience is not null
        defaultLawyerDetailsShouldBeFound("lawyerExperience.specified=true");

        // Get all the lawyerDetailsList where lawyerExperience is null
        defaultLawyerDetailsShouldNotBeFound("lawyerExperience.specified=false");
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByLawyerExperienceContainsSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where lawyerExperience contains DEFAULT_LAWYER_EXPERIENCE
        defaultLawyerDetailsShouldBeFound("lawyerExperience.contains=" + DEFAULT_LAWYER_EXPERIENCE);

        // Get all the lawyerDetailsList where lawyerExperience contains UPDATED_LAWYER_EXPERIENCE
        defaultLawyerDetailsShouldNotBeFound("lawyerExperience.contains=" + UPDATED_LAWYER_EXPERIENCE);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByLawyerExperienceNotContainsSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where lawyerExperience does not contain DEFAULT_LAWYER_EXPERIENCE
        defaultLawyerDetailsShouldNotBeFound("lawyerExperience.doesNotContain=" + DEFAULT_LAWYER_EXPERIENCE);

        // Get all the lawyerDetailsList where lawyerExperience does not contain UPDATED_LAWYER_EXPERIENCE
        defaultLawyerDetailsShouldBeFound("lawyerExperience.doesNotContain=" + UPDATED_LAWYER_EXPERIENCE);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByFreefield1IsEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where freefield1 equals to DEFAULT_FREEFIELD_1
        defaultLawyerDetailsShouldBeFound("freefield1.equals=" + DEFAULT_FREEFIELD_1);

        // Get all the lawyerDetailsList where freefield1 equals to UPDATED_FREEFIELD_1
        defaultLawyerDetailsShouldNotBeFound("freefield1.equals=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByFreefield1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where freefield1 not equals to DEFAULT_FREEFIELD_1
        defaultLawyerDetailsShouldNotBeFound("freefield1.notEquals=" + DEFAULT_FREEFIELD_1);

        // Get all the lawyerDetailsList where freefield1 not equals to UPDATED_FREEFIELD_1
        defaultLawyerDetailsShouldBeFound("freefield1.notEquals=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByFreefield1IsInShouldWork() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where freefield1 in DEFAULT_FREEFIELD_1 or UPDATED_FREEFIELD_1
        defaultLawyerDetailsShouldBeFound("freefield1.in=" + DEFAULT_FREEFIELD_1 + "," + UPDATED_FREEFIELD_1);

        // Get all the lawyerDetailsList where freefield1 equals to UPDATED_FREEFIELD_1
        defaultLawyerDetailsShouldNotBeFound("freefield1.in=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByFreefield1IsNullOrNotNull() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where freefield1 is not null
        defaultLawyerDetailsShouldBeFound("freefield1.specified=true");

        // Get all the lawyerDetailsList where freefield1 is null
        defaultLawyerDetailsShouldNotBeFound("freefield1.specified=false");
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByFreefield1ContainsSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where freefield1 contains DEFAULT_FREEFIELD_1
        defaultLawyerDetailsShouldBeFound("freefield1.contains=" + DEFAULT_FREEFIELD_1);

        // Get all the lawyerDetailsList where freefield1 contains UPDATED_FREEFIELD_1
        defaultLawyerDetailsShouldNotBeFound("freefield1.contains=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByFreefield1NotContainsSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where freefield1 does not contain DEFAULT_FREEFIELD_1
        defaultLawyerDetailsShouldNotBeFound("freefield1.doesNotContain=" + DEFAULT_FREEFIELD_1);

        // Get all the lawyerDetailsList where freefield1 does not contain UPDATED_FREEFIELD_1
        defaultLawyerDetailsShouldBeFound("freefield1.doesNotContain=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByFreefield2IsEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where freefield2 equals to DEFAULT_FREEFIELD_2
        defaultLawyerDetailsShouldBeFound("freefield2.equals=" + DEFAULT_FREEFIELD_2);

        // Get all the lawyerDetailsList where freefield2 equals to UPDATED_FREEFIELD_2
        defaultLawyerDetailsShouldNotBeFound("freefield2.equals=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByFreefield2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where freefield2 not equals to DEFAULT_FREEFIELD_2
        defaultLawyerDetailsShouldNotBeFound("freefield2.notEquals=" + DEFAULT_FREEFIELD_2);

        // Get all the lawyerDetailsList where freefield2 not equals to UPDATED_FREEFIELD_2
        defaultLawyerDetailsShouldBeFound("freefield2.notEquals=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByFreefield2IsInShouldWork() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where freefield2 in DEFAULT_FREEFIELD_2 or UPDATED_FREEFIELD_2
        defaultLawyerDetailsShouldBeFound("freefield2.in=" + DEFAULT_FREEFIELD_2 + "," + UPDATED_FREEFIELD_2);

        // Get all the lawyerDetailsList where freefield2 equals to UPDATED_FREEFIELD_2
        defaultLawyerDetailsShouldNotBeFound("freefield2.in=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByFreefield2IsNullOrNotNull() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where freefield2 is not null
        defaultLawyerDetailsShouldBeFound("freefield2.specified=true");

        // Get all the lawyerDetailsList where freefield2 is null
        defaultLawyerDetailsShouldNotBeFound("freefield2.specified=false");
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByFreefield2ContainsSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where freefield2 contains DEFAULT_FREEFIELD_2
        defaultLawyerDetailsShouldBeFound("freefield2.contains=" + DEFAULT_FREEFIELD_2);

        // Get all the lawyerDetailsList where freefield2 contains UPDATED_FREEFIELD_2
        defaultLawyerDetailsShouldNotBeFound("freefield2.contains=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByFreefield2NotContainsSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where freefield2 does not contain DEFAULT_FREEFIELD_2
        defaultLawyerDetailsShouldNotBeFound("freefield2.doesNotContain=" + DEFAULT_FREEFIELD_2);

        // Get all the lawyerDetailsList where freefield2 does not contain UPDATED_FREEFIELD_2
        defaultLawyerDetailsShouldBeFound("freefield2.doesNotContain=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByUserTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where userType equals to DEFAULT_USER_TYPE
        defaultLawyerDetailsShouldBeFound("userType.equals=" + DEFAULT_USER_TYPE);

        // Get all the lawyerDetailsList where userType equals to UPDATED_USER_TYPE
        defaultLawyerDetailsShouldNotBeFound("userType.equals=" + UPDATED_USER_TYPE);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByUserTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where userType not equals to DEFAULT_USER_TYPE
        defaultLawyerDetailsShouldNotBeFound("userType.notEquals=" + DEFAULT_USER_TYPE);

        // Get all the lawyerDetailsList where userType not equals to UPDATED_USER_TYPE
        defaultLawyerDetailsShouldBeFound("userType.notEquals=" + UPDATED_USER_TYPE);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByUserTypeIsInShouldWork() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where userType in DEFAULT_USER_TYPE or UPDATED_USER_TYPE
        defaultLawyerDetailsShouldBeFound("userType.in=" + DEFAULT_USER_TYPE + "," + UPDATED_USER_TYPE);

        // Get all the lawyerDetailsList where userType equals to UPDATED_USER_TYPE
        defaultLawyerDetailsShouldNotBeFound("userType.in=" + UPDATED_USER_TYPE);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByUserTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where userType is not null
        defaultLawyerDetailsShouldBeFound("userType.specified=true");

        // Get all the lawyerDetailsList where userType is null
        defaultLawyerDetailsShouldNotBeFound("userType.specified=false");
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByLawyerTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where lawyerType equals to DEFAULT_LAWYER_TYPE
        defaultLawyerDetailsShouldBeFound("lawyerType.equals=" + DEFAULT_LAWYER_TYPE);

        // Get all the lawyerDetailsList where lawyerType equals to UPDATED_LAWYER_TYPE
        defaultLawyerDetailsShouldNotBeFound("lawyerType.equals=" + UPDATED_LAWYER_TYPE);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByLawyerTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where lawyerType not equals to DEFAULT_LAWYER_TYPE
        defaultLawyerDetailsShouldNotBeFound("lawyerType.notEquals=" + DEFAULT_LAWYER_TYPE);

        // Get all the lawyerDetailsList where lawyerType not equals to UPDATED_LAWYER_TYPE
        defaultLawyerDetailsShouldBeFound("lawyerType.notEquals=" + UPDATED_LAWYER_TYPE);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByLawyerTypeIsInShouldWork() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where lawyerType in DEFAULT_LAWYER_TYPE or UPDATED_LAWYER_TYPE
        defaultLawyerDetailsShouldBeFound("lawyerType.in=" + DEFAULT_LAWYER_TYPE + "," + UPDATED_LAWYER_TYPE);

        // Get all the lawyerDetailsList where lawyerType equals to UPDATED_LAWYER_TYPE
        defaultLawyerDetailsShouldNotBeFound("lawyerType.in=" + UPDATED_LAWYER_TYPE);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByLawyerTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where lawyerType is not null
        defaultLawyerDetailsShouldBeFound("lawyerType.specified=true");

        // Get all the lawyerDetailsList where lawyerType is null
        defaultLawyerDetailsShouldNotBeFound("lawyerType.specified=false");
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByIsActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where isActivated equals to DEFAULT_IS_ACTIVATED
        defaultLawyerDetailsShouldBeFound("isActivated.equals=" + DEFAULT_IS_ACTIVATED);

        // Get all the lawyerDetailsList where isActivated equals to UPDATED_IS_ACTIVATED
        defaultLawyerDetailsShouldNotBeFound("isActivated.equals=" + UPDATED_IS_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByIsActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where isActivated not equals to DEFAULT_IS_ACTIVATED
        defaultLawyerDetailsShouldNotBeFound("isActivated.notEquals=" + DEFAULT_IS_ACTIVATED);

        // Get all the lawyerDetailsList where isActivated not equals to UPDATED_IS_ACTIVATED
        defaultLawyerDetailsShouldBeFound("isActivated.notEquals=" + UPDATED_IS_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByIsActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where isActivated in DEFAULT_IS_ACTIVATED or UPDATED_IS_ACTIVATED
        defaultLawyerDetailsShouldBeFound("isActivated.in=" + DEFAULT_IS_ACTIVATED + "," + UPDATED_IS_ACTIVATED);

        // Get all the lawyerDetailsList where isActivated equals to UPDATED_IS_ACTIVATED
        defaultLawyerDetailsShouldNotBeFound("isActivated.in=" + UPDATED_IS_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByIsActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where isActivated is not null
        defaultLawyerDetailsShouldBeFound("isActivated.specified=true");

        // Get all the lawyerDetailsList where isActivated is null
        defaultLawyerDetailsShouldNotBeFound("isActivated.specified=false");
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultLawyerDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the lawyerDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLawyerDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultLawyerDetailsShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the lawyerDetailsList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultLawyerDetailsShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultLawyerDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the lawyerDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLawyerDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where lastModifiedBy is not null
        defaultLawyerDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the lawyerDetailsList where lastModifiedBy is null
        defaultLawyerDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultLawyerDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the lawyerDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultLawyerDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultLawyerDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the lawyerDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultLawyerDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultLawyerDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the lawyerDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLawyerDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultLawyerDetailsShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the lawyerDetailsList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultLawyerDetailsShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultLawyerDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the lawyerDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLawyerDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where lastModified is not null
        defaultLawyerDetailsShouldBeFound("lastModified.specified=true");

        // Get all the lawyerDetailsList where lastModified is null
        defaultLawyerDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultLawyerDetailsShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the lawyerDetailsList where lastModified contains UPDATED_LAST_MODIFIED
        defaultLawyerDetailsShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        // Get all the lawyerDetailsList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultLawyerDetailsShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the lawyerDetailsList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultLawyerDetailsShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLawyerDetailsByCourtCaseIsEqualToSomething() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);
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
        lawyerDetails.addCourtCase(courtCase);
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);
        Long courtCaseId = courtCase.getId();

        // Get all the lawyerDetailsList where courtCase equals to courtCaseId
        defaultLawyerDetailsShouldBeFound("courtCaseId.equals=" + courtCaseId);

        // Get all the lawyerDetailsList where courtCase equals to (courtCaseId + 1)
        defaultLawyerDetailsShouldNotBeFound("courtCaseId.equals=" + (courtCaseId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLawyerDetailsShouldBeFound(String filter) throws Exception {
        restLawyerDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lawyerDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO)))
            .andExpect(jsonPath("$.[*].emailId").value(hasItem(DEFAULT_EMAIL_ID)))
            .andExpect(jsonPath("$.[*].barRegnNo").value(hasItem(DEFAULT_BAR_REGN_NO)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].lawyerExperience").value(hasItem(DEFAULT_LAWYER_EXPERIENCE)))
            .andExpect(jsonPath("$.[*].freefield1").value(hasItem(DEFAULT_FREEFIELD_1)))
            .andExpect(jsonPath("$.[*].freefield2").value(hasItem(DEFAULT_FREEFIELD_2)))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].lawyerType").value(hasItem(DEFAULT_LAWYER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isActivated").value(hasItem(DEFAULT_IS_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)));

        // Check, that the count call also returns 1
        restLawyerDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLawyerDetailsShouldNotBeFound(String filter) throws Exception {
        restLawyerDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLawyerDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLawyerDetails() throws Exception {
        // Get the lawyerDetails
        restLawyerDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLawyerDetails() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        int databaseSizeBeforeUpdate = lawyerDetailsRepository.findAll().size();

        // Update the lawyerDetails
        LawyerDetails updatedLawyerDetails = lawyerDetailsRepository.findById(lawyerDetails.getId()).get();
        // Disconnect from session so that the updates on updatedLawyerDetails are not directly saved in db
        em.detach(updatedLawyerDetails);
        updatedLawyerDetails
            .fullName(UPDATED_FULL_NAME)
            .contactNo(UPDATED_CONTACT_NO)
            .emailId(UPDATED_EMAIL_ID)
            .barRegnNo(UPDATED_BAR_REGN_NO)
            .address(UPDATED_ADDRESS)
            .lawyerExperience(UPDATED_LAWYER_EXPERIENCE)
            .freefield1(UPDATED_FREEFIELD_1)
            .freefield2(UPDATED_FREEFIELD_2)
            .userType(UPDATED_USER_TYPE)
            .lawyerType(UPDATED_LAWYER_TYPE)
            .isActivated(UPDATED_IS_ACTIVATED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModified(UPDATED_LAST_MODIFIED);
        LawyerDetailsDTO lawyerDetailsDTO = lawyerDetailsMapper.toDto(updatedLawyerDetails);

        restLawyerDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lawyerDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lawyerDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the LawyerDetails in the database
        List<LawyerDetails> lawyerDetailsList = lawyerDetailsRepository.findAll();
        assertThat(lawyerDetailsList).hasSize(databaseSizeBeforeUpdate);
        LawyerDetails testLawyerDetails = lawyerDetailsList.get(lawyerDetailsList.size() - 1);
        assertThat(testLawyerDetails.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testLawyerDetails.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testLawyerDetails.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testLawyerDetails.getBarRegnNo()).isEqualTo(UPDATED_BAR_REGN_NO);
        assertThat(testLawyerDetails.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testLawyerDetails.getLawyerExperience()).isEqualTo(UPDATED_LAWYER_EXPERIENCE);
        assertThat(testLawyerDetails.getFreefield1()).isEqualTo(UPDATED_FREEFIELD_1);
        assertThat(testLawyerDetails.getFreefield2()).isEqualTo(UPDATED_FREEFIELD_2);
        assertThat(testLawyerDetails.getUserType()).isEqualTo(UPDATED_USER_TYPE);
        assertThat(testLawyerDetails.getLawyerType()).isEqualTo(UPDATED_LAWYER_TYPE);
        assertThat(testLawyerDetails.getIsActivated()).isEqualTo(UPDATED_IS_ACTIVATED);
        assertThat(testLawyerDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLawyerDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void putNonExistingLawyerDetails() throws Exception {
        int databaseSizeBeforeUpdate = lawyerDetailsRepository.findAll().size();
        lawyerDetails.setId(count.incrementAndGet());

        // Create the LawyerDetails
        LawyerDetailsDTO lawyerDetailsDTO = lawyerDetailsMapper.toDto(lawyerDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLawyerDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lawyerDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lawyerDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LawyerDetails in the database
        List<LawyerDetails> lawyerDetailsList = lawyerDetailsRepository.findAll();
        assertThat(lawyerDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLawyerDetails() throws Exception {
        int databaseSizeBeforeUpdate = lawyerDetailsRepository.findAll().size();
        lawyerDetails.setId(count.incrementAndGet());

        // Create the LawyerDetails
        LawyerDetailsDTO lawyerDetailsDTO = lawyerDetailsMapper.toDto(lawyerDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLawyerDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lawyerDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LawyerDetails in the database
        List<LawyerDetails> lawyerDetailsList = lawyerDetailsRepository.findAll();
        assertThat(lawyerDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLawyerDetails() throws Exception {
        int databaseSizeBeforeUpdate = lawyerDetailsRepository.findAll().size();
        lawyerDetails.setId(count.incrementAndGet());

        // Create the LawyerDetails
        LawyerDetailsDTO lawyerDetailsDTO = lawyerDetailsMapper.toDto(lawyerDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLawyerDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lawyerDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LawyerDetails in the database
        List<LawyerDetails> lawyerDetailsList = lawyerDetailsRepository.findAll();
        assertThat(lawyerDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLawyerDetailsWithPatch() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        int databaseSizeBeforeUpdate = lawyerDetailsRepository.findAll().size();

        // Update the lawyerDetails using partial update
        LawyerDetails partialUpdatedLawyerDetails = new LawyerDetails();
        partialUpdatedLawyerDetails.setId(lawyerDetails.getId());

        partialUpdatedLawyerDetails
            .fullName(UPDATED_FULL_NAME)
            .contactNo(UPDATED_CONTACT_NO)
            .emailId(UPDATED_EMAIL_ID)
            .barRegnNo(UPDATED_BAR_REGN_NO)
            .lawyerExperience(UPDATED_LAWYER_EXPERIENCE)
            .freefield1(UPDATED_FREEFIELD_1)
            .freefield2(UPDATED_FREEFIELD_2);

        restLawyerDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLawyerDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLawyerDetails))
            )
            .andExpect(status().isOk());

        // Validate the LawyerDetails in the database
        List<LawyerDetails> lawyerDetailsList = lawyerDetailsRepository.findAll();
        assertThat(lawyerDetailsList).hasSize(databaseSizeBeforeUpdate);
        LawyerDetails testLawyerDetails = lawyerDetailsList.get(lawyerDetailsList.size() - 1);
        assertThat(testLawyerDetails.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testLawyerDetails.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testLawyerDetails.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testLawyerDetails.getBarRegnNo()).isEqualTo(UPDATED_BAR_REGN_NO);
        assertThat(testLawyerDetails.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testLawyerDetails.getLawyerExperience()).isEqualTo(UPDATED_LAWYER_EXPERIENCE);
        assertThat(testLawyerDetails.getFreefield1()).isEqualTo(UPDATED_FREEFIELD_1);
        assertThat(testLawyerDetails.getFreefield2()).isEqualTo(UPDATED_FREEFIELD_2);
        assertThat(testLawyerDetails.getUserType()).isEqualTo(DEFAULT_USER_TYPE);
        assertThat(testLawyerDetails.getLawyerType()).isEqualTo(DEFAULT_LAWYER_TYPE);
        assertThat(testLawyerDetails.getIsActivated()).isEqualTo(DEFAULT_IS_ACTIVATED);
        assertThat(testLawyerDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testLawyerDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void fullUpdateLawyerDetailsWithPatch() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        int databaseSizeBeforeUpdate = lawyerDetailsRepository.findAll().size();

        // Update the lawyerDetails using partial update
        LawyerDetails partialUpdatedLawyerDetails = new LawyerDetails();
        partialUpdatedLawyerDetails.setId(lawyerDetails.getId());

        partialUpdatedLawyerDetails
            .fullName(UPDATED_FULL_NAME)
            .contactNo(UPDATED_CONTACT_NO)
            .emailId(UPDATED_EMAIL_ID)
            .barRegnNo(UPDATED_BAR_REGN_NO)
            .address(UPDATED_ADDRESS)
            .lawyerExperience(UPDATED_LAWYER_EXPERIENCE)
            .freefield1(UPDATED_FREEFIELD_1)
            .freefield2(UPDATED_FREEFIELD_2)
            .userType(UPDATED_USER_TYPE)
            .lawyerType(UPDATED_LAWYER_TYPE)
            .isActivated(UPDATED_IS_ACTIVATED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModified(UPDATED_LAST_MODIFIED);

        restLawyerDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLawyerDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLawyerDetails))
            )
            .andExpect(status().isOk());

        // Validate the LawyerDetails in the database
        List<LawyerDetails> lawyerDetailsList = lawyerDetailsRepository.findAll();
        assertThat(lawyerDetailsList).hasSize(databaseSizeBeforeUpdate);
        LawyerDetails testLawyerDetails = lawyerDetailsList.get(lawyerDetailsList.size() - 1);
        assertThat(testLawyerDetails.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testLawyerDetails.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testLawyerDetails.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testLawyerDetails.getBarRegnNo()).isEqualTo(UPDATED_BAR_REGN_NO);
        assertThat(testLawyerDetails.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testLawyerDetails.getLawyerExperience()).isEqualTo(UPDATED_LAWYER_EXPERIENCE);
        assertThat(testLawyerDetails.getFreefield1()).isEqualTo(UPDATED_FREEFIELD_1);
        assertThat(testLawyerDetails.getFreefield2()).isEqualTo(UPDATED_FREEFIELD_2);
        assertThat(testLawyerDetails.getUserType()).isEqualTo(UPDATED_USER_TYPE);
        assertThat(testLawyerDetails.getLawyerType()).isEqualTo(UPDATED_LAWYER_TYPE);
        assertThat(testLawyerDetails.getIsActivated()).isEqualTo(UPDATED_IS_ACTIVATED);
        assertThat(testLawyerDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLawyerDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void patchNonExistingLawyerDetails() throws Exception {
        int databaseSizeBeforeUpdate = lawyerDetailsRepository.findAll().size();
        lawyerDetails.setId(count.incrementAndGet());

        // Create the LawyerDetails
        LawyerDetailsDTO lawyerDetailsDTO = lawyerDetailsMapper.toDto(lawyerDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLawyerDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lawyerDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lawyerDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LawyerDetails in the database
        List<LawyerDetails> lawyerDetailsList = lawyerDetailsRepository.findAll();
        assertThat(lawyerDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLawyerDetails() throws Exception {
        int databaseSizeBeforeUpdate = lawyerDetailsRepository.findAll().size();
        lawyerDetails.setId(count.incrementAndGet());

        // Create the LawyerDetails
        LawyerDetailsDTO lawyerDetailsDTO = lawyerDetailsMapper.toDto(lawyerDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLawyerDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lawyerDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LawyerDetails in the database
        List<LawyerDetails> lawyerDetailsList = lawyerDetailsRepository.findAll();
        assertThat(lawyerDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLawyerDetails() throws Exception {
        int databaseSizeBeforeUpdate = lawyerDetailsRepository.findAll().size();
        lawyerDetails.setId(count.incrementAndGet());

        // Create the LawyerDetails
        LawyerDetailsDTO lawyerDetailsDTO = lawyerDetailsMapper.toDto(lawyerDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLawyerDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lawyerDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LawyerDetails in the database
        List<LawyerDetails> lawyerDetailsList = lawyerDetailsRepository.findAll();
        assertThat(lawyerDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLawyerDetails() throws Exception {
        // Initialize the database
        lawyerDetailsRepository.saveAndFlush(lawyerDetails);

        int databaseSizeBeforeDelete = lawyerDetailsRepository.findAll().size();

        // Delete the lawyerDetails
        restLawyerDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, lawyerDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LawyerDetails> lawyerDetailsList = lawyerDetailsRepository.findAll();
        assertThat(lawyerDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
