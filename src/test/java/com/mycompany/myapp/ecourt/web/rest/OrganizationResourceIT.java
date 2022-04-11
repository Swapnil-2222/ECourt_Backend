package com.mycompany.myapp.ecourt.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.ecourt.IntegrationTest;
import com.mycompany.myapp.ecourt.domain.Organization;
import com.mycompany.myapp.ecourt.repository.OrganizationRepository;
import com.mycompany.myapp.ecourt.service.criteria.OrganizationCriteria;
import com.mycompany.myapp.ecourt.service.dto.OrganizationDTO;
import com.mycompany.myapp.ecourt.service.mapper.OrganizationMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OrganizationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrganizationResourceIT {

    private static final String DEFAULT_ORGANIZATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PINCODE = "AAAAAAAAAA";
    private static final String UPDATED_PINCODE = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_FREEFIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREEFIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREEFIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREEFIELD_2 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVATED = false;
    private static final Boolean UPDATED_IS_ACTIVATED = true;

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_ON = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_ON = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/organizations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganizationMockMvc;

    private Organization organization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organization createEntity(EntityManager em) {
        Organization organization = new Organization()
            .organizationName(DEFAULT_ORGANIZATION_NAME)
            .contactNo(DEFAULT_CONTACT_NO)
            .address(DEFAULT_ADDRESS)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .pincode(DEFAULT_PINCODE)
            .website(DEFAULT_WEBSITE)
            .freefield1(DEFAULT_FREEFIELD_1)
            .freefield2(DEFAULT_FREEFIELD_2)
            .isActivated(DEFAULT_IS_ACTIVATED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .createdOn(DEFAULT_CREATED_ON);
        return organization;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organization createUpdatedEntity(EntityManager em) {
        Organization organization = new Organization()
            .organizationName(UPDATED_ORGANIZATION_NAME)
            .contactNo(UPDATED_CONTACT_NO)
            .address(UPDATED_ADDRESS)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .pincode(UPDATED_PINCODE)
            .website(UPDATED_WEBSITE)
            .freefield1(UPDATED_FREEFIELD_1)
            .freefield2(UPDATED_FREEFIELD_2)
            .isActivated(UPDATED_IS_ACTIVATED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .createdOn(UPDATED_CREATED_ON);
        return organization;
    }

    @BeforeEach
    public void initTest() {
        organization = createEntity(em);
    }

    @Test
    @Transactional
    void createOrganization() throws Exception {
        int databaseSizeBeforeCreate = organizationRepository.findAll().size();
        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);
        restOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeCreate + 1);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getOrganizationName()).isEqualTo(DEFAULT_ORGANIZATION_NAME);
        assertThat(testOrganization.getContactNo()).isEqualTo(DEFAULT_CONTACT_NO);
        assertThat(testOrganization.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testOrganization.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testOrganization.getPincode()).isEqualTo(DEFAULT_PINCODE);
        assertThat(testOrganization.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testOrganization.getFreefield1()).isEqualTo(DEFAULT_FREEFIELD_1);
        assertThat(testOrganization.getFreefield2()).isEqualTo(DEFAULT_FREEFIELD_2);
        assertThat(testOrganization.getIsActivated()).isEqualTo(DEFAULT_IS_ACTIVATED);
        assertThat(testOrganization.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testOrganization.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testOrganization.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    void createOrganizationWithExistingId() throws Exception {
        // Create the Organization with an existing ID
        organization.setId(1L);
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        int databaseSizeBeforeCreate = organizationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrganizations() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList
        restOrganizationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organization.getId().intValue())))
            .andExpect(jsonPath("$.[*].organizationName").value(hasItem(DEFAULT_ORGANIZATION_NAME)))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].freefield1").value(hasItem(DEFAULT_FREEFIELD_1)))
            .andExpect(jsonPath("$.[*].freefield2").value(hasItem(DEFAULT_FREEFIELD_2)))
            .andExpect(jsonPath("$.[*].isActivated").value(hasItem(DEFAULT_IS_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON)));
    }

    @Test
    @Transactional
    void getOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get the organization
        restOrganizationMockMvc
            .perform(get(ENTITY_API_URL_ID, organization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organization.getId().intValue()))
            .andExpect(jsonPath("$.organizationName").value(DEFAULT_ORGANIZATION_NAME))
            .andExpect(jsonPath("$.contactNo").value(DEFAULT_CONTACT_NO))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.freefield1").value(DEFAULT_FREEFIELD_1))
            .andExpect(jsonPath("$.freefield2").value(DEFAULT_FREEFIELD_2))
            .andExpect(jsonPath("$.isActivated").value(DEFAULT_IS_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON));
    }

    @Test
    @Transactional
    void getOrganizationsByIdFiltering() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        Long id = organization.getId();

        defaultOrganizationShouldBeFound("id.equals=" + id);
        defaultOrganizationShouldNotBeFound("id.notEquals=" + id);

        defaultOrganizationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrganizationShouldNotBeFound("id.greaterThan=" + id);

        defaultOrganizationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrganizationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrganizationsByOrganizationNameIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where organizationName equals to DEFAULT_ORGANIZATION_NAME
        defaultOrganizationShouldBeFound("organizationName.equals=" + DEFAULT_ORGANIZATION_NAME);

        // Get all the organizationList where organizationName equals to UPDATED_ORGANIZATION_NAME
        defaultOrganizationShouldNotBeFound("organizationName.equals=" + UPDATED_ORGANIZATION_NAME);
    }

    @Test
    @Transactional
    void getAllOrganizationsByOrganizationNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where organizationName not equals to DEFAULT_ORGANIZATION_NAME
        defaultOrganizationShouldNotBeFound("organizationName.notEquals=" + DEFAULT_ORGANIZATION_NAME);

        // Get all the organizationList where organizationName not equals to UPDATED_ORGANIZATION_NAME
        defaultOrganizationShouldBeFound("organizationName.notEquals=" + UPDATED_ORGANIZATION_NAME);
    }

    @Test
    @Transactional
    void getAllOrganizationsByOrganizationNameIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where organizationName in DEFAULT_ORGANIZATION_NAME or UPDATED_ORGANIZATION_NAME
        defaultOrganizationShouldBeFound("organizationName.in=" + DEFAULT_ORGANIZATION_NAME + "," + UPDATED_ORGANIZATION_NAME);

        // Get all the organizationList where organizationName equals to UPDATED_ORGANIZATION_NAME
        defaultOrganizationShouldNotBeFound("organizationName.in=" + UPDATED_ORGANIZATION_NAME);
    }

    @Test
    @Transactional
    void getAllOrganizationsByOrganizationNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where organizationName is not null
        defaultOrganizationShouldBeFound("organizationName.specified=true");

        // Get all the organizationList where organizationName is null
        defaultOrganizationShouldNotBeFound("organizationName.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByOrganizationNameContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where organizationName contains DEFAULT_ORGANIZATION_NAME
        defaultOrganizationShouldBeFound("organizationName.contains=" + DEFAULT_ORGANIZATION_NAME);

        // Get all the organizationList where organizationName contains UPDATED_ORGANIZATION_NAME
        defaultOrganizationShouldNotBeFound("organizationName.contains=" + UPDATED_ORGANIZATION_NAME);
    }

    @Test
    @Transactional
    void getAllOrganizationsByOrganizationNameNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where organizationName does not contain DEFAULT_ORGANIZATION_NAME
        defaultOrganizationShouldNotBeFound("organizationName.doesNotContain=" + DEFAULT_ORGANIZATION_NAME);

        // Get all the organizationList where organizationName does not contain UPDATED_ORGANIZATION_NAME
        defaultOrganizationShouldBeFound("organizationName.doesNotContain=" + UPDATED_ORGANIZATION_NAME);
    }

    @Test
    @Transactional
    void getAllOrganizationsByContactNoIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where contactNo equals to DEFAULT_CONTACT_NO
        defaultOrganizationShouldBeFound("contactNo.equals=" + DEFAULT_CONTACT_NO);

        // Get all the organizationList where contactNo equals to UPDATED_CONTACT_NO
        defaultOrganizationShouldNotBeFound("contactNo.equals=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllOrganizationsByContactNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where contactNo not equals to DEFAULT_CONTACT_NO
        defaultOrganizationShouldNotBeFound("contactNo.notEquals=" + DEFAULT_CONTACT_NO);

        // Get all the organizationList where contactNo not equals to UPDATED_CONTACT_NO
        defaultOrganizationShouldBeFound("contactNo.notEquals=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllOrganizationsByContactNoIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where contactNo in DEFAULT_CONTACT_NO or UPDATED_CONTACT_NO
        defaultOrganizationShouldBeFound("contactNo.in=" + DEFAULT_CONTACT_NO + "," + UPDATED_CONTACT_NO);

        // Get all the organizationList where contactNo equals to UPDATED_CONTACT_NO
        defaultOrganizationShouldNotBeFound("contactNo.in=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllOrganizationsByContactNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where contactNo is not null
        defaultOrganizationShouldBeFound("contactNo.specified=true");

        // Get all the organizationList where contactNo is null
        defaultOrganizationShouldNotBeFound("contactNo.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByContactNoContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where contactNo contains DEFAULT_CONTACT_NO
        defaultOrganizationShouldBeFound("contactNo.contains=" + DEFAULT_CONTACT_NO);

        // Get all the organizationList where contactNo contains UPDATED_CONTACT_NO
        defaultOrganizationShouldNotBeFound("contactNo.contains=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllOrganizationsByContactNoNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where contactNo does not contain DEFAULT_CONTACT_NO
        defaultOrganizationShouldNotBeFound("contactNo.doesNotContain=" + DEFAULT_CONTACT_NO);

        // Get all the organizationList where contactNo does not contain UPDATED_CONTACT_NO
        defaultOrganizationShouldBeFound("contactNo.doesNotContain=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllOrganizationsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where address equals to DEFAULT_ADDRESS
        defaultOrganizationShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the organizationList where address equals to UPDATED_ADDRESS
        defaultOrganizationShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllOrganizationsByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where address not equals to DEFAULT_ADDRESS
        defaultOrganizationShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the organizationList where address not equals to UPDATED_ADDRESS
        defaultOrganizationShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllOrganizationsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultOrganizationShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the organizationList where address equals to UPDATED_ADDRESS
        defaultOrganizationShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllOrganizationsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where address is not null
        defaultOrganizationShouldBeFound("address.specified=true");

        // Get all the organizationList where address is null
        defaultOrganizationShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByAddressContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where address contains DEFAULT_ADDRESS
        defaultOrganizationShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the organizationList where address contains UPDATED_ADDRESS
        defaultOrganizationShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllOrganizationsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where address does not contain DEFAULT_ADDRESS
        defaultOrganizationShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the organizationList where address does not contain UPDATED_ADDRESS
        defaultOrganizationShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllOrganizationsByEmailAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where emailAddress equals to DEFAULT_EMAIL_ADDRESS
        defaultOrganizationShouldBeFound("emailAddress.equals=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the organizationList where emailAddress equals to UPDATED_EMAIL_ADDRESS
        defaultOrganizationShouldNotBeFound("emailAddress.equals=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllOrganizationsByEmailAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where emailAddress not equals to DEFAULT_EMAIL_ADDRESS
        defaultOrganizationShouldNotBeFound("emailAddress.notEquals=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the organizationList where emailAddress not equals to UPDATED_EMAIL_ADDRESS
        defaultOrganizationShouldBeFound("emailAddress.notEquals=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllOrganizationsByEmailAddressIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where emailAddress in DEFAULT_EMAIL_ADDRESS or UPDATED_EMAIL_ADDRESS
        defaultOrganizationShouldBeFound("emailAddress.in=" + DEFAULT_EMAIL_ADDRESS + "," + UPDATED_EMAIL_ADDRESS);

        // Get all the organizationList where emailAddress equals to UPDATED_EMAIL_ADDRESS
        defaultOrganizationShouldNotBeFound("emailAddress.in=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllOrganizationsByEmailAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where emailAddress is not null
        defaultOrganizationShouldBeFound("emailAddress.specified=true");

        // Get all the organizationList where emailAddress is null
        defaultOrganizationShouldNotBeFound("emailAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByEmailAddressContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where emailAddress contains DEFAULT_EMAIL_ADDRESS
        defaultOrganizationShouldBeFound("emailAddress.contains=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the organizationList where emailAddress contains UPDATED_EMAIL_ADDRESS
        defaultOrganizationShouldNotBeFound("emailAddress.contains=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllOrganizationsByEmailAddressNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where emailAddress does not contain DEFAULT_EMAIL_ADDRESS
        defaultOrganizationShouldNotBeFound("emailAddress.doesNotContain=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the organizationList where emailAddress does not contain UPDATED_EMAIL_ADDRESS
        defaultOrganizationShouldBeFound("emailAddress.doesNotContain=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllOrganizationsByPincodeIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where pincode equals to DEFAULT_PINCODE
        defaultOrganizationShouldBeFound("pincode.equals=" + DEFAULT_PINCODE);

        // Get all the organizationList where pincode equals to UPDATED_PINCODE
        defaultOrganizationShouldNotBeFound("pincode.equals=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByPincodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where pincode not equals to DEFAULT_PINCODE
        defaultOrganizationShouldNotBeFound("pincode.notEquals=" + DEFAULT_PINCODE);

        // Get all the organizationList where pincode not equals to UPDATED_PINCODE
        defaultOrganizationShouldBeFound("pincode.notEquals=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByPincodeIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where pincode in DEFAULT_PINCODE or UPDATED_PINCODE
        defaultOrganizationShouldBeFound("pincode.in=" + DEFAULT_PINCODE + "," + UPDATED_PINCODE);

        // Get all the organizationList where pincode equals to UPDATED_PINCODE
        defaultOrganizationShouldNotBeFound("pincode.in=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByPincodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where pincode is not null
        defaultOrganizationShouldBeFound("pincode.specified=true");

        // Get all the organizationList where pincode is null
        defaultOrganizationShouldNotBeFound("pincode.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByPincodeContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where pincode contains DEFAULT_PINCODE
        defaultOrganizationShouldBeFound("pincode.contains=" + DEFAULT_PINCODE);

        // Get all the organizationList where pincode contains UPDATED_PINCODE
        defaultOrganizationShouldNotBeFound("pincode.contains=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByPincodeNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where pincode does not contain DEFAULT_PINCODE
        defaultOrganizationShouldNotBeFound("pincode.doesNotContain=" + DEFAULT_PINCODE);

        // Get all the organizationList where pincode does not contain UPDATED_PINCODE
        defaultOrganizationShouldBeFound("pincode.doesNotContain=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByWebsiteIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where website equals to DEFAULT_WEBSITE
        defaultOrganizationShouldBeFound("website.equals=" + DEFAULT_WEBSITE);

        // Get all the organizationList where website equals to UPDATED_WEBSITE
        defaultOrganizationShouldNotBeFound("website.equals=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByWebsiteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where website not equals to DEFAULT_WEBSITE
        defaultOrganizationShouldNotBeFound("website.notEquals=" + DEFAULT_WEBSITE);

        // Get all the organizationList where website not equals to UPDATED_WEBSITE
        defaultOrganizationShouldBeFound("website.notEquals=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByWebsiteIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where website in DEFAULT_WEBSITE or UPDATED_WEBSITE
        defaultOrganizationShouldBeFound("website.in=" + DEFAULT_WEBSITE + "," + UPDATED_WEBSITE);

        // Get all the organizationList where website equals to UPDATED_WEBSITE
        defaultOrganizationShouldNotBeFound("website.in=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByWebsiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where website is not null
        defaultOrganizationShouldBeFound("website.specified=true");

        // Get all the organizationList where website is null
        defaultOrganizationShouldNotBeFound("website.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByWebsiteContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where website contains DEFAULT_WEBSITE
        defaultOrganizationShouldBeFound("website.contains=" + DEFAULT_WEBSITE);

        // Get all the organizationList where website contains UPDATED_WEBSITE
        defaultOrganizationShouldNotBeFound("website.contains=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByWebsiteNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where website does not contain DEFAULT_WEBSITE
        defaultOrganizationShouldNotBeFound("website.doesNotContain=" + DEFAULT_WEBSITE);

        // Get all the organizationList where website does not contain UPDATED_WEBSITE
        defaultOrganizationShouldBeFound("website.doesNotContain=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreefield1IsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freefield1 equals to DEFAULT_FREEFIELD_1
        defaultOrganizationShouldBeFound("freefield1.equals=" + DEFAULT_FREEFIELD_1);

        // Get all the organizationList where freefield1 equals to UPDATED_FREEFIELD_1
        defaultOrganizationShouldNotBeFound("freefield1.equals=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreefield1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freefield1 not equals to DEFAULT_FREEFIELD_1
        defaultOrganizationShouldNotBeFound("freefield1.notEquals=" + DEFAULT_FREEFIELD_1);

        // Get all the organizationList where freefield1 not equals to UPDATED_FREEFIELD_1
        defaultOrganizationShouldBeFound("freefield1.notEquals=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreefield1IsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freefield1 in DEFAULT_FREEFIELD_1 or UPDATED_FREEFIELD_1
        defaultOrganizationShouldBeFound("freefield1.in=" + DEFAULT_FREEFIELD_1 + "," + UPDATED_FREEFIELD_1);

        // Get all the organizationList where freefield1 equals to UPDATED_FREEFIELD_1
        defaultOrganizationShouldNotBeFound("freefield1.in=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreefield1IsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freefield1 is not null
        defaultOrganizationShouldBeFound("freefield1.specified=true");

        // Get all the organizationList where freefield1 is null
        defaultOrganizationShouldNotBeFound("freefield1.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreefield1ContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freefield1 contains DEFAULT_FREEFIELD_1
        defaultOrganizationShouldBeFound("freefield1.contains=" + DEFAULT_FREEFIELD_1);

        // Get all the organizationList where freefield1 contains UPDATED_FREEFIELD_1
        defaultOrganizationShouldNotBeFound("freefield1.contains=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreefield1NotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freefield1 does not contain DEFAULT_FREEFIELD_1
        defaultOrganizationShouldNotBeFound("freefield1.doesNotContain=" + DEFAULT_FREEFIELD_1);

        // Get all the organizationList where freefield1 does not contain UPDATED_FREEFIELD_1
        defaultOrganizationShouldBeFound("freefield1.doesNotContain=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreefield2IsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freefield2 equals to DEFAULT_FREEFIELD_2
        defaultOrganizationShouldBeFound("freefield2.equals=" + DEFAULT_FREEFIELD_2);

        // Get all the organizationList where freefield2 equals to UPDATED_FREEFIELD_2
        defaultOrganizationShouldNotBeFound("freefield2.equals=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreefield2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freefield2 not equals to DEFAULT_FREEFIELD_2
        defaultOrganizationShouldNotBeFound("freefield2.notEquals=" + DEFAULT_FREEFIELD_2);

        // Get all the organizationList where freefield2 not equals to UPDATED_FREEFIELD_2
        defaultOrganizationShouldBeFound("freefield2.notEquals=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreefield2IsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freefield2 in DEFAULT_FREEFIELD_2 or UPDATED_FREEFIELD_2
        defaultOrganizationShouldBeFound("freefield2.in=" + DEFAULT_FREEFIELD_2 + "," + UPDATED_FREEFIELD_2);

        // Get all the organizationList where freefield2 equals to UPDATED_FREEFIELD_2
        defaultOrganizationShouldNotBeFound("freefield2.in=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreefield2IsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freefield2 is not null
        defaultOrganizationShouldBeFound("freefield2.specified=true");

        // Get all the organizationList where freefield2 is null
        defaultOrganizationShouldNotBeFound("freefield2.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreefield2ContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freefield2 contains DEFAULT_FREEFIELD_2
        defaultOrganizationShouldBeFound("freefield2.contains=" + DEFAULT_FREEFIELD_2);

        // Get all the organizationList where freefield2 contains UPDATED_FREEFIELD_2
        defaultOrganizationShouldNotBeFound("freefield2.contains=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreefield2NotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freefield2 does not contain DEFAULT_FREEFIELD_2
        defaultOrganizationShouldNotBeFound("freefield2.doesNotContain=" + DEFAULT_FREEFIELD_2);

        // Get all the organizationList where freefield2 does not contain UPDATED_FREEFIELD_2
        defaultOrganizationShouldBeFound("freefield2.doesNotContain=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllOrganizationsByIsActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where isActivated equals to DEFAULT_IS_ACTIVATED
        defaultOrganizationShouldBeFound("isActivated.equals=" + DEFAULT_IS_ACTIVATED);

        // Get all the organizationList where isActivated equals to UPDATED_IS_ACTIVATED
        defaultOrganizationShouldNotBeFound("isActivated.equals=" + UPDATED_IS_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllOrganizationsByIsActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where isActivated not equals to DEFAULT_IS_ACTIVATED
        defaultOrganizationShouldNotBeFound("isActivated.notEquals=" + DEFAULT_IS_ACTIVATED);

        // Get all the organizationList where isActivated not equals to UPDATED_IS_ACTIVATED
        defaultOrganizationShouldBeFound("isActivated.notEquals=" + UPDATED_IS_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllOrganizationsByIsActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where isActivated in DEFAULT_IS_ACTIVATED or UPDATED_IS_ACTIVATED
        defaultOrganizationShouldBeFound("isActivated.in=" + DEFAULT_IS_ACTIVATED + "," + UPDATED_IS_ACTIVATED);

        // Get all the organizationList where isActivated equals to UPDATED_IS_ACTIVATED
        defaultOrganizationShouldNotBeFound("isActivated.in=" + UPDATED_IS_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllOrganizationsByIsActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where isActivated is not null
        defaultOrganizationShouldBeFound("isActivated.specified=true");

        // Get all the organizationList where isActivated is null
        defaultOrganizationShouldNotBeFound("isActivated.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultOrganizationShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the organizationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultOrganizationShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOrganizationsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultOrganizationShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the organizationList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultOrganizationShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOrganizationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultOrganizationShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the organizationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultOrganizationShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOrganizationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where lastModifiedBy is not null
        defaultOrganizationShouldBeFound("lastModifiedBy.specified=true");

        // Get all the organizationList where lastModifiedBy is null
        defaultOrganizationShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultOrganizationShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the organizationList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultOrganizationShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOrganizationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultOrganizationShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the organizationList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultOrganizationShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOrganizationsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultOrganizationShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the organizationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultOrganizationShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllOrganizationsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultOrganizationShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the organizationList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultOrganizationShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllOrganizationsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultOrganizationShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the organizationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultOrganizationShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllOrganizationsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where lastModified is not null
        defaultOrganizationShouldBeFound("lastModified.specified=true");

        // Get all the organizationList where lastModified is null
        defaultOrganizationShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultOrganizationShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the organizationList where lastModified contains UPDATED_LAST_MODIFIED
        defaultOrganizationShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllOrganizationsByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultOrganizationShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the organizationList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultOrganizationShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllOrganizationsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where createdOn equals to DEFAULT_CREATED_ON
        defaultOrganizationShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the organizationList where createdOn equals to UPDATED_CREATED_ON
        defaultOrganizationShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllOrganizationsByCreatedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where createdOn not equals to DEFAULT_CREATED_ON
        defaultOrganizationShouldNotBeFound("createdOn.notEquals=" + DEFAULT_CREATED_ON);

        // Get all the organizationList where createdOn not equals to UPDATED_CREATED_ON
        defaultOrganizationShouldBeFound("createdOn.notEquals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllOrganizationsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultOrganizationShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the organizationList where createdOn equals to UPDATED_CREATED_ON
        defaultOrganizationShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllOrganizationsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where createdOn is not null
        defaultOrganizationShouldBeFound("createdOn.specified=true");

        // Get all the organizationList where createdOn is null
        defaultOrganizationShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByCreatedOnContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where createdOn contains DEFAULT_CREATED_ON
        defaultOrganizationShouldBeFound("createdOn.contains=" + DEFAULT_CREATED_ON);

        // Get all the organizationList where createdOn contains UPDATED_CREATED_ON
        defaultOrganizationShouldNotBeFound("createdOn.contains=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllOrganizationsByCreatedOnNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where createdOn does not contain DEFAULT_CREATED_ON
        defaultOrganizationShouldNotBeFound("createdOn.doesNotContain=" + DEFAULT_CREATED_ON);

        // Get all the organizationList where createdOn does not contain UPDATED_CREATED_ON
        defaultOrganizationShouldBeFound("createdOn.doesNotContain=" + UPDATED_CREATED_ON);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrganizationShouldBeFound(String filter) throws Exception {
        restOrganizationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organization.getId().intValue())))
            .andExpect(jsonPath("$.[*].organizationName").value(hasItem(DEFAULT_ORGANIZATION_NAME)))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].freefield1").value(hasItem(DEFAULT_FREEFIELD_1)))
            .andExpect(jsonPath("$.[*].freefield2").value(hasItem(DEFAULT_FREEFIELD_2)))
            .andExpect(jsonPath("$.[*].isActivated").value(hasItem(DEFAULT_IS_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON)));

        // Check, that the count call also returns 1
        restOrganizationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrganizationShouldNotBeFound(String filter) throws Exception {
        restOrganizationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrganizationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrganization() throws Exception {
        // Get the organization
        restOrganizationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Update the organization
        Organization updatedOrganization = organizationRepository.findById(organization.getId()).get();
        // Disconnect from session so that the updates on updatedOrganization are not directly saved in db
        em.detach(updatedOrganization);
        updatedOrganization
            .organizationName(UPDATED_ORGANIZATION_NAME)
            .contactNo(UPDATED_CONTACT_NO)
            .address(UPDATED_ADDRESS)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .pincode(UPDATED_PINCODE)
            .website(UPDATED_WEBSITE)
            .freefield1(UPDATED_FREEFIELD_1)
            .freefield2(UPDATED_FREEFIELD_2)
            .isActivated(UPDATED_IS_ACTIVATED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .createdOn(UPDATED_CREATED_ON);
        OrganizationDTO organizationDTO = organizationMapper.toDto(updatedOrganization);

        restOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getOrganizationName()).isEqualTo(UPDATED_ORGANIZATION_NAME);
        assertThat(testOrganization.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testOrganization.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testOrganization.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testOrganization.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testOrganization.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testOrganization.getFreefield1()).isEqualTo(UPDATED_FREEFIELD_1);
        assertThat(testOrganization.getFreefield2()).isEqualTo(UPDATED_FREEFIELD_2);
        assertThat(testOrganization.getIsActivated()).isEqualTo(UPDATED_IS_ACTIVATED);
        assertThat(testOrganization.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testOrganization.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testOrganization.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(count.incrementAndGet());

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(count.incrementAndGet());

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(count.incrementAndGet());

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganizationWithPatch() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Update the organization using partial update
        Organization partialUpdatedOrganization = new Organization();
        partialUpdatedOrganization.setId(organization.getId());

        partialUpdatedOrganization
            .organizationName(UPDATED_ORGANIZATION_NAME)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .pincode(UPDATED_PINCODE)
            .freefield2(UPDATED_FREEFIELD_2)
            .isActivated(UPDATED_IS_ACTIVATED)
            .lastModified(UPDATED_LAST_MODIFIED);

        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganization.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganization))
            )
            .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getOrganizationName()).isEqualTo(UPDATED_ORGANIZATION_NAME);
        assertThat(testOrganization.getContactNo()).isEqualTo(DEFAULT_CONTACT_NO);
        assertThat(testOrganization.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testOrganization.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testOrganization.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testOrganization.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testOrganization.getFreefield1()).isEqualTo(DEFAULT_FREEFIELD_1);
        assertThat(testOrganization.getFreefield2()).isEqualTo(UPDATED_FREEFIELD_2);
        assertThat(testOrganization.getIsActivated()).isEqualTo(UPDATED_IS_ACTIVATED);
        assertThat(testOrganization.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testOrganization.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testOrganization.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateOrganizationWithPatch() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Update the organization using partial update
        Organization partialUpdatedOrganization = new Organization();
        partialUpdatedOrganization.setId(organization.getId());

        partialUpdatedOrganization
            .organizationName(UPDATED_ORGANIZATION_NAME)
            .contactNo(UPDATED_CONTACT_NO)
            .address(UPDATED_ADDRESS)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .pincode(UPDATED_PINCODE)
            .website(UPDATED_WEBSITE)
            .freefield1(UPDATED_FREEFIELD_1)
            .freefield2(UPDATED_FREEFIELD_2)
            .isActivated(UPDATED_IS_ACTIVATED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .createdOn(UPDATED_CREATED_ON);

        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganization.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganization))
            )
            .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getOrganizationName()).isEqualTo(UPDATED_ORGANIZATION_NAME);
        assertThat(testOrganization.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testOrganization.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testOrganization.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testOrganization.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testOrganization.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testOrganization.getFreefield1()).isEqualTo(UPDATED_FREEFIELD_1);
        assertThat(testOrganization.getFreefield2()).isEqualTo(UPDATED_FREEFIELD_2);
        assertThat(testOrganization.getIsActivated()).isEqualTo(UPDATED_IS_ACTIVATED);
        assertThat(testOrganization.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testOrganization.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testOrganization.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(count.incrementAndGet());

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organizationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(count.incrementAndGet());

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(count.incrementAndGet());

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        int databaseSizeBeforeDelete = organizationRepository.findAll().size();

        // Delete the organization
        restOrganizationMockMvc
            .perform(delete(ENTITY_API_URL_ID, organization.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
