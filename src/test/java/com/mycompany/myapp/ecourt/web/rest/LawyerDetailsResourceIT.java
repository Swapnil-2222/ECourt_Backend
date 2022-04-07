package com.mycompany.myapp.ecourt.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.ecourt.IntegrationTest;
import com.mycompany.myapp.ecourt.domain.LawyerDetails;
import com.mycompany.myapp.ecourt.domain.enumeration.LawyerType;
import com.mycompany.myapp.ecourt.domain.enumeration.UserType;
import com.mycompany.myapp.ecourt.repository.LawyerDetailsRepository;
import com.mycompany.myapp.ecourt.service.LawyerDetailsService;
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
