package com.mycompany.myapp.ecourt.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.ecourt.IntegrationTest;
import com.mycompany.myapp.ecourt.domain.Hearing;
import com.mycompany.myapp.ecourt.domain.enumeration.CaseStatus;
import com.mycompany.myapp.ecourt.repository.HearingRepository;
import com.mycompany.myapp.ecourt.service.HearingService;
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
