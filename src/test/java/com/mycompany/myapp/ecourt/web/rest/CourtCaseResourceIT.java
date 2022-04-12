package com.mycompany.myapp.ecourt.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.ecourt.IntegrationTest;
import com.mycompany.myapp.ecourt.domain.CourtCase;
import com.mycompany.myapp.ecourt.domain.LawyerDetails;
import com.mycompany.myapp.ecourt.domain.Organization;
import com.mycompany.myapp.ecourt.domain.enumeration.CaseStatus;
import com.mycompany.myapp.ecourt.domain.enumeration.CaseType;
import com.mycompany.myapp.ecourt.domain.enumeration.CourtType;
import com.mycompany.myapp.ecourt.domain.enumeration.NatureResult;
import com.mycompany.myapp.ecourt.repository.CourtCaseRepository;
import com.mycompany.myapp.ecourt.service.CourtCaseService;
import com.mycompany.myapp.ecourt.service.criteria.CourtCaseCriteria;
import com.mycompany.myapp.ecourt.service.dto.CourtCaseDTO;
import com.mycompany.myapp.ecourt.service.mapper.CourtCaseMapper;
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
 * Integration tests for the {@link CourtCaseResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CourtCaseResourceIT {

    private static final String DEFAULT_LAND_REFERENCE_NO = "AAAAAAAAAA";
    private static final String UPDATED_LAND_REFERENCE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CASE_NO = "AAAAAAAAAA";
    private static final String UPDATED_CASE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_VILLAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VILLAGE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCUSER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCUSER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEFENDANT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEFENDANT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCUSER_LAWYER = "AAAAAAAAAA";
    private static final String UPDATED_ACCUSER_LAWYER = "BBBBBBBBBB";

    private static final String DEFAULT_DEFENDANT_LAWYER = "AAAAAAAAAA";
    private static final String UPDATED_DEFENDANT_LAWYER = "BBBBBBBBBB";

    private static final String DEFAULT_CASE_OFFICER = "AAAAAAAAAA";
    private static final String UPDATED_CASE_OFFICER = "BBBBBBBBBB";

    private static final String DEFAULT_CASE_OFFICER_ADD = "AAAAAAAAAA";
    private static final String UPDATED_CASE_OFFICER_ADD = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_AREA = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_AREA = "BBBBBBBBBB";

    private static final String DEFAULT_LAND_ACQUISITION_OFFICER_NO = "AAAAAAAAAA";
    private static final String UPDATED_LAND_ACQUISITION_OFFICER_NO = "BBBBBBBBBB";

    private static final String DEFAULT_SECTION_11_JUDGMENT_NO = "AAAAAAAAAA";
    private static final String UPDATED_SECTION_11_JUDGMENT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_SECTION_4_NOTICE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_SECTION_4_NOTICE_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_JUDGEMENT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_JUDGEMENT_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_OF_DECISION = "AAAAAAAAAA";
    private static final String UPDATED_DATE_OF_DECISION = "BBBBBBBBBB";

    private static final String DEFAULT_CASE_FILING_DATE = "AAAAAAAAAA";
    private static final String UPDATED_CASE_FILING_DATE = "BBBBBBBBBB";

    private static final CaseStatus DEFAULT_CASE_STATUS = CaseStatus.CREATED;
    private static final CaseStatus UPDATED_CASE_STATUS = CaseStatus.ONPROGRESS;

    private static final String DEFAULT_LAST_HEARING_DATE = "AAAAAAAAAA";
    private static final String UPDATED_LAST_HEARING_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_NEXT_HEARING_DATE = "AAAAAAAAAA";
    private static final String UPDATED_NEXT_HEARING_DATE = "BBBBBBBBBB";

    private static final NatureResult DEFAULT_NATURE_RESULT = NatureResult.JUDGEMENT;
    private static final NatureResult UPDATED_NATURE_RESULT = NatureResult.OTHERS;

    private static final String DEFAULT_AMOUNT_DEPOSITE_IN_COURT = "AAAAAAAAAA";
    private static final String UPDATED_AMOUNT_DEPOSITE_IN_COURT = "BBBBBBBBBB";

    private static final String DEFAULT_CLAIM_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_CLAIM_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_DEPOSITED_CHEQUE_NO = "AAAAAAAAAA";
    private static final String UPDATED_DEPOSITED_CHEQUE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_DEPOSITEDCHEQUE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_DEPOSITEDCHEQUE_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_ADD_INTEREST_AMOUNT_DIST_COURT = "AAAAAAAAAA";
    private static final String UPDATED_ADD_INTEREST_AMOUNT_DIST_COURT = "BBBBBBBBBB";

    private static final String DEFAULT_ADD_INTEREST_APPLICATION_NO = "AAAAAAAAAA";
    private static final String UPDATED_ADD_INTEREST_APPLICATION_NO = "BBBBBBBBBB";

    private static final String DEFAULT_ADD_INT_CHEQUE_NO = "AAAAAAAAAA";
    private static final String UPDATED_ADD_INT_CHEQUE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_ADD_INT_CHEQUE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_ADD_INT_CHEQUE_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_GUARANTEE_APP_NO = "AAAAAAAAAA";
    private static final String UPDATED_BANK_GUARANTEE_APP_NO = "BBBBBBBBBB";

    private static final String DEFAULT_COURT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COURT_NAME = "BBBBBBBBBB";

    private static final CourtType DEFAULT_COURT_TYPE = CourtType.DISTRICTCOURT;
    private static final CourtType UPDATED_COURT_TYPE = CourtType.HIGHCOURT;

    private static final CaseType DEFAULT_CASE_TYPE = CaseType.LARSEC18;
    private static final CaseType UPDATED_CASE_TYPE = CaseType.DARKHAST;

    private static final Boolean DEFAULT_IS_ACTIVATED = false;
    private static final Boolean UPDATED_IS_ACTIVATED = true;

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

    private static final String DEFAULT_FREEFIELD_6 = "AAAAAAAAAA";
    private static final String UPDATED_FREEFIELD_6 = "BBBBBBBBBB";

    private static final String DEFAULT_FREEFIELD_7 = "AAAAAAAAAA";
    private static final String UPDATED_FREEFIELD_7 = "BBBBBBBBBB";

    private static final String DEFAULT_FREEFIELD_8 = "AAAAAAAAAA";
    private static final String UPDATED_FREEFIELD_8 = "BBBBBBBBBB";

    private static final String DEFAULT_FREEFIELD_9 = "AAAAAAAAAA";
    private static final String UPDATED_FREEFIELD_9 = "BBBBBBBBBB";

    private static final String DEFAULT_FREEFIELD_10 = "AAAAAAAAAA";
    private static final String UPDATED_FREEFIELD_10 = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/court-cases";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CourtCaseRepository courtCaseRepository;

    @Mock
    private CourtCaseRepository courtCaseRepositoryMock;

    @Autowired
    private CourtCaseMapper courtCaseMapper;

    @Mock
    private CourtCaseService courtCaseServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourtCaseMockMvc;

    private CourtCase courtCase;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourtCase createEntity(EntityManager em) {
        CourtCase courtCase = new CourtCase()
            .landReferenceNo(DEFAULT_LAND_REFERENCE_NO)
            .caseNo(DEFAULT_CASE_NO)
            .villageName(DEFAULT_VILLAGE_NAME)
            .accuserName(DEFAULT_ACCUSER_NAME)
            .defendantName(DEFAULT_DEFENDANT_NAME)
            .accuserLawyer(DEFAULT_ACCUSER_LAWYER)
            .defendantLawyer(DEFAULT_DEFENDANT_LAWYER)
            .caseOfficer(DEFAULT_CASE_OFFICER)
            .caseOfficerAdd(DEFAULT_CASE_OFFICER_ADD)
            .totalArea(DEFAULT_TOTAL_AREA)
            .landAcquisitionOfficerNo(DEFAULT_LAND_ACQUISITION_OFFICER_NO)
            .section11JudgmentNo(DEFAULT_SECTION_11_JUDGMENT_NO)
            .section4NoticeDate(DEFAULT_SECTION_4_NOTICE_DATE)
            .judgementDate(DEFAULT_JUDGEMENT_DATE)
            .dateOfDecision(DEFAULT_DATE_OF_DECISION)
            .caseFilingDate(DEFAULT_CASE_FILING_DATE)
            .caseStatus(DEFAULT_CASE_STATUS)
            .lastHearingDate(DEFAULT_LAST_HEARING_DATE)
            .nextHearingDate(DEFAULT_NEXT_HEARING_DATE)
            .natureResult(DEFAULT_NATURE_RESULT)
            .amountDepositeInCourt(DEFAULT_AMOUNT_DEPOSITE_IN_COURT)
            .claimAmount(DEFAULT_CLAIM_AMOUNT)
            .depositedChequeNo(DEFAULT_DEPOSITED_CHEQUE_NO)
            .depositedchequeDate(DEFAULT_DEPOSITEDCHEQUE_DATE)
            .addInterestAmountDistCourt(DEFAULT_ADD_INTEREST_AMOUNT_DIST_COURT)
            .addInterestApplicationNo(DEFAULT_ADD_INTEREST_APPLICATION_NO)
            .addIntChequeNo(DEFAULT_ADD_INT_CHEQUE_NO)
            .addIntChequeDate(DEFAULT_ADD_INT_CHEQUE_DATE)
            .bankGuaranteeAppNo(DEFAULT_BANK_GUARANTEE_APP_NO)
            .courtName(DEFAULT_COURT_NAME)
            .courtType(DEFAULT_COURT_TYPE)
            .caseType(DEFAULT_CASE_TYPE)
            .isActivated(DEFAULT_IS_ACTIVATED)
            .freefield1(DEFAULT_FREEFIELD_1)
            .freefield2(DEFAULT_FREEFIELD_2)
            .freefield3(DEFAULT_FREEFIELD_3)
            .freefield4(DEFAULT_FREEFIELD_4)
            .freefield5(DEFAULT_FREEFIELD_5)
            .freefield6(DEFAULT_FREEFIELD_6)
            .freefield7(DEFAULT_FREEFIELD_7)
            .freefield8(DEFAULT_FREEFIELD_8)
            .freefield9(DEFAULT_FREEFIELD_9)
            .freefield10(DEFAULT_FREEFIELD_10)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModified(DEFAULT_LAST_MODIFIED);
        return courtCase;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourtCase createUpdatedEntity(EntityManager em) {
        CourtCase courtCase = new CourtCase()
            .landReferenceNo(UPDATED_LAND_REFERENCE_NO)
            .caseNo(UPDATED_CASE_NO)
            .villageName(UPDATED_VILLAGE_NAME)
            .accuserName(UPDATED_ACCUSER_NAME)
            .defendantName(UPDATED_DEFENDANT_NAME)
            .accuserLawyer(UPDATED_ACCUSER_LAWYER)
            .defendantLawyer(UPDATED_DEFENDANT_LAWYER)
            .caseOfficer(UPDATED_CASE_OFFICER)
            .caseOfficerAdd(UPDATED_CASE_OFFICER_ADD)
            .totalArea(UPDATED_TOTAL_AREA)
            .landAcquisitionOfficerNo(UPDATED_LAND_ACQUISITION_OFFICER_NO)
            .section11JudgmentNo(UPDATED_SECTION_11_JUDGMENT_NO)
            .section4NoticeDate(UPDATED_SECTION_4_NOTICE_DATE)
            .judgementDate(UPDATED_JUDGEMENT_DATE)
            .dateOfDecision(UPDATED_DATE_OF_DECISION)
            .caseFilingDate(UPDATED_CASE_FILING_DATE)
            .caseStatus(UPDATED_CASE_STATUS)
            .lastHearingDate(UPDATED_LAST_HEARING_DATE)
            .nextHearingDate(UPDATED_NEXT_HEARING_DATE)
            .natureResult(UPDATED_NATURE_RESULT)
            .amountDepositeInCourt(UPDATED_AMOUNT_DEPOSITE_IN_COURT)
            .claimAmount(UPDATED_CLAIM_AMOUNT)
            .depositedChequeNo(UPDATED_DEPOSITED_CHEQUE_NO)
            .depositedchequeDate(UPDATED_DEPOSITEDCHEQUE_DATE)
            .addInterestAmountDistCourt(UPDATED_ADD_INTEREST_AMOUNT_DIST_COURT)
            .addInterestApplicationNo(UPDATED_ADD_INTEREST_APPLICATION_NO)
            .addIntChequeNo(UPDATED_ADD_INT_CHEQUE_NO)
            .addIntChequeDate(UPDATED_ADD_INT_CHEQUE_DATE)
            .bankGuaranteeAppNo(UPDATED_BANK_GUARANTEE_APP_NO)
            .courtName(UPDATED_COURT_NAME)
            .courtType(UPDATED_COURT_TYPE)
            .caseType(UPDATED_CASE_TYPE)
            .isActivated(UPDATED_IS_ACTIVATED)
            .freefield1(UPDATED_FREEFIELD_1)
            .freefield2(UPDATED_FREEFIELD_2)
            .freefield3(UPDATED_FREEFIELD_3)
            .freefield4(UPDATED_FREEFIELD_4)
            .freefield5(UPDATED_FREEFIELD_5)
            .freefield6(UPDATED_FREEFIELD_6)
            .freefield7(UPDATED_FREEFIELD_7)
            .freefield8(UPDATED_FREEFIELD_8)
            .freefield9(UPDATED_FREEFIELD_9)
            .freefield10(UPDATED_FREEFIELD_10)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModified(UPDATED_LAST_MODIFIED);
        return courtCase;
    }

    @BeforeEach
    public void initTest() {
        courtCase = createEntity(em);
    }

    @Test
    @Transactional
    void createCourtCase() throws Exception {
        int databaseSizeBeforeCreate = courtCaseRepository.findAll().size();
        // Create the CourtCase
        CourtCaseDTO courtCaseDTO = courtCaseMapper.toDto(courtCase);
        restCourtCaseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courtCaseDTO)))
            .andExpect(status().isCreated());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeCreate + 1);
        CourtCase testCourtCase = courtCaseList.get(courtCaseList.size() - 1);
        assertThat(testCourtCase.getLandReferenceNo()).isEqualTo(DEFAULT_LAND_REFERENCE_NO);
        assertThat(testCourtCase.getCaseNo()).isEqualTo(DEFAULT_CASE_NO);
        assertThat(testCourtCase.getVillageName()).isEqualTo(DEFAULT_VILLAGE_NAME);
        assertThat(testCourtCase.getAccuserName()).isEqualTo(DEFAULT_ACCUSER_NAME);
        assertThat(testCourtCase.getDefendantName()).isEqualTo(DEFAULT_DEFENDANT_NAME);
        assertThat(testCourtCase.getAccuserLawyer()).isEqualTo(DEFAULT_ACCUSER_LAWYER);
        assertThat(testCourtCase.getDefendantLawyer()).isEqualTo(DEFAULT_DEFENDANT_LAWYER);
        assertThat(testCourtCase.getCaseOfficer()).isEqualTo(DEFAULT_CASE_OFFICER);
        assertThat(testCourtCase.getCaseOfficerAdd()).isEqualTo(DEFAULT_CASE_OFFICER_ADD);
        assertThat(testCourtCase.getTotalArea()).isEqualTo(DEFAULT_TOTAL_AREA);
        assertThat(testCourtCase.getLandAcquisitionOfficerNo()).isEqualTo(DEFAULT_LAND_ACQUISITION_OFFICER_NO);
        assertThat(testCourtCase.getSection11JudgmentNo()).isEqualTo(DEFAULT_SECTION_11_JUDGMENT_NO);
        assertThat(testCourtCase.getSection4NoticeDate()).isEqualTo(DEFAULT_SECTION_4_NOTICE_DATE);
        assertThat(testCourtCase.getJudgementDate()).isEqualTo(DEFAULT_JUDGEMENT_DATE);
        assertThat(testCourtCase.getDateOfDecision()).isEqualTo(DEFAULT_DATE_OF_DECISION);
        assertThat(testCourtCase.getCaseFilingDate()).isEqualTo(DEFAULT_CASE_FILING_DATE);
        assertThat(testCourtCase.getCaseStatus()).isEqualTo(DEFAULT_CASE_STATUS);
        assertThat(testCourtCase.getLastHearingDate()).isEqualTo(DEFAULT_LAST_HEARING_DATE);
        assertThat(testCourtCase.getNextHearingDate()).isEqualTo(DEFAULT_NEXT_HEARING_DATE);
        assertThat(testCourtCase.getNatureResult()).isEqualTo(DEFAULT_NATURE_RESULT);
        assertThat(testCourtCase.getAmountDepositeInCourt()).isEqualTo(DEFAULT_AMOUNT_DEPOSITE_IN_COURT);
        assertThat(testCourtCase.getClaimAmount()).isEqualTo(DEFAULT_CLAIM_AMOUNT);
        assertThat(testCourtCase.getDepositedChequeNo()).isEqualTo(DEFAULT_DEPOSITED_CHEQUE_NO);
        assertThat(testCourtCase.getDepositedchequeDate()).isEqualTo(DEFAULT_DEPOSITEDCHEQUE_DATE);
        assertThat(testCourtCase.getAddInterestAmountDistCourt()).isEqualTo(DEFAULT_ADD_INTEREST_AMOUNT_DIST_COURT);
        assertThat(testCourtCase.getAddInterestApplicationNo()).isEqualTo(DEFAULT_ADD_INTEREST_APPLICATION_NO);
        assertThat(testCourtCase.getAddIntChequeNo()).isEqualTo(DEFAULT_ADD_INT_CHEQUE_NO);
        assertThat(testCourtCase.getAddIntChequeDate()).isEqualTo(DEFAULT_ADD_INT_CHEQUE_DATE);
        assertThat(testCourtCase.getBankGuaranteeAppNo()).isEqualTo(DEFAULT_BANK_GUARANTEE_APP_NO);
        assertThat(testCourtCase.getCourtName()).isEqualTo(DEFAULT_COURT_NAME);
        assertThat(testCourtCase.getCourtType()).isEqualTo(DEFAULT_COURT_TYPE);
        assertThat(testCourtCase.getCaseType()).isEqualTo(DEFAULT_CASE_TYPE);
        assertThat(testCourtCase.getIsActivated()).isEqualTo(DEFAULT_IS_ACTIVATED);
        assertThat(testCourtCase.getFreefield1()).isEqualTo(DEFAULT_FREEFIELD_1);
        assertThat(testCourtCase.getFreefield2()).isEqualTo(DEFAULT_FREEFIELD_2);
        assertThat(testCourtCase.getFreefield3()).isEqualTo(DEFAULT_FREEFIELD_3);
        assertThat(testCourtCase.getFreefield4()).isEqualTo(DEFAULT_FREEFIELD_4);
        assertThat(testCourtCase.getFreefield5()).isEqualTo(DEFAULT_FREEFIELD_5);
        assertThat(testCourtCase.getFreefield6()).isEqualTo(DEFAULT_FREEFIELD_6);
        assertThat(testCourtCase.getFreefield7()).isEqualTo(DEFAULT_FREEFIELD_7);
        assertThat(testCourtCase.getFreefield8()).isEqualTo(DEFAULT_FREEFIELD_8);
        assertThat(testCourtCase.getFreefield9()).isEqualTo(DEFAULT_FREEFIELD_9);
        assertThat(testCourtCase.getFreefield10()).isEqualTo(DEFAULT_FREEFIELD_10);
        assertThat(testCourtCase.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testCourtCase.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void createCourtCaseWithExistingId() throws Exception {
        // Create the CourtCase with an existing ID
        courtCase.setId(1L);
        CourtCaseDTO courtCaseDTO = courtCaseMapper.toDto(courtCase);

        int databaseSizeBeforeCreate = courtCaseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourtCaseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courtCaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCourtCases() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList
        restCourtCaseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courtCase.getId().intValue())))
            .andExpect(jsonPath("$.[*].landReferenceNo").value(hasItem(DEFAULT_LAND_REFERENCE_NO)))
            .andExpect(jsonPath("$.[*].caseNo").value(hasItem(DEFAULT_CASE_NO)))
            .andExpect(jsonPath("$.[*].villageName").value(hasItem(DEFAULT_VILLAGE_NAME)))
            .andExpect(jsonPath("$.[*].accuserName").value(hasItem(DEFAULT_ACCUSER_NAME)))
            .andExpect(jsonPath("$.[*].defendantName").value(hasItem(DEFAULT_DEFENDANT_NAME)))
            .andExpect(jsonPath("$.[*].accuserLawyer").value(hasItem(DEFAULT_ACCUSER_LAWYER)))
            .andExpect(jsonPath("$.[*].defendantLawyer").value(hasItem(DEFAULT_DEFENDANT_LAWYER)))
            .andExpect(jsonPath("$.[*].caseOfficer").value(hasItem(DEFAULT_CASE_OFFICER)))
            .andExpect(jsonPath("$.[*].caseOfficerAdd").value(hasItem(DEFAULT_CASE_OFFICER_ADD)))
            .andExpect(jsonPath("$.[*].totalArea").value(hasItem(DEFAULT_TOTAL_AREA)))
            .andExpect(jsonPath("$.[*].landAcquisitionOfficerNo").value(hasItem(DEFAULT_LAND_ACQUISITION_OFFICER_NO)))
            .andExpect(jsonPath("$.[*].section11JudgmentNo").value(hasItem(DEFAULT_SECTION_11_JUDGMENT_NO)))
            .andExpect(jsonPath("$.[*].section4NoticeDate").value(hasItem(DEFAULT_SECTION_4_NOTICE_DATE)))
            .andExpect(jsonPath("$.[*].judgementDate").value(hasItem(DEFAULT_JUDGEMENT_DATE)))
            .andExpect(jsonPath("$.[*].dateOfDecision").value(hasItem(DEFAULT_DATE_OF_DECISION)))
            .andExpect(jsonPath("$.[*].caseFilingDate").value(hasItem(DEFAULT_CASE_FILING_DATE)))
            .andExpect(jsonPath("$.[*].caseStatus").value(hasItem(DEFAULT_CASE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lastHearingDate").value(hasItem(DEFAULT_LAST_HEARING_DATE)))
            .andExpect(jsonPath("$.[*].nextHearingDate").value(hasItem(DEFAULT_NEXT_HEARING_DATE)))
            .andExpect(jsonPath("$.[*].natureResult").value(hasItem(DEFAULT_NATURE_RESULT.toString())))
            .andExpect(jsonPath("$.[*].amountDepositeInCourt").value(hasItem(DEFAULT_AMOUNT_DEPOSITE_IN_COURT)))
            .andExpect(jsonPath("$.[*].claimAmount").value(hasItem(DEFAULT_CLAIM_AMOUNT)))
            .andExpect(jsonPath("$.[*].depositedChequeNo").value(hasItem(DEFAULT_DEPOSITED_CHEQUE_NO)))
            .andExpect(jsonPath("$.[*].depositedchequeDate").value(hasItem(DEFAULT_DEPOSITEDCHEQUE_DATE)))
            .andExpect(jsonPath("$.[*].addInterestAmountDistCourt").value(hasItem(DEFAULT_ADD_INTEREST_AMOUNT_DIST_COURT)))
            .andExpect(jsonPath("$.[*].addInterestApplicationNo").value(hasItem(DEFAULT_ADD_INTEREST_APPLICATION_NO)))
            .andExpect(jsonPath("$.[*].addIntChequeNo").value(hasItem(DEFAULT_ADD_INT_CHEQUE_NO)))
            .andExpect(jsonPath("$.[*].addIntChequeDate").value(hasItem(DEFAULT_ADD_INT_CHEQUE_DATE)))
            .andExpect(jsonPath("$.[*].bankGuaranteeAppNo").value(hasItem(DEFAULT_BANK_GUARANTEE_APP_NO)))
            .andExpect(jsonPath("$.[*].courtName").value(hasItem(DEFAULT_COURT_NAME)))
            .andExpect(jsonPath("$.[*].courtType").value(hasItem(DEFAULT_COURT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].caseType").value(hasItem(DEFAULT_CASE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isActivated").value(hasItem(DEFAULT_IS_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].freefield1").value(hasItem(DEFAULT_FREEFIELD_1)))
            .andExpect(jsonPath("$.[*].freefield2").value(hasItem(DEFAULT_FREEFIELD_2)))
            .andExpect(jsonPath("$.[*].freefield3").value(hasItem(DEFAULT_FREEFIELD_3)))
            .andExpect(jsonPath("$.[*].freefield4").value(hasItem(DEFAULT_FREEFIELD_4)))
            .andExpect(jsonPath("$.[*].freefield5").value(hasItem(DEFAULT_FREEFIELD_5)))
            .andExpect(jsonPath("$.[*].freefield6").value(hasItem(DEFAULT_FREEFIELD_6)))
            .andExpect(jsonPath("$.[*].freefield7").value(hasItem(DEFAULT_FREEFIELD_7)))
            .andExpect(jsonPath("$.[*].freefield8").value(hasItem(DEFAULT_FREEFIELD_8)))
            .andExpect(jsonPath("$.[*].freefield9").value(hasItem(DEFAULT_FREEFIELD_9)))
            .andExpect(jsonPath("$.[*].freefield10").value(hasItem(DEFAULT_FREEFIELD_10)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCourtCasesWithEagerRelationshipsIsEnabled() throws Exception {
        when(courtCaseServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCourtCaseMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(courtCaseServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCourtCasesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(courtCaseServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCourtCaseMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(courtCaseServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getCourtCase() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get the courtCase
        restCourtCaseMockMvc
            .perform(get(ENTITY_API_URL_ID, courtCase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courtCase.getId().intValue()))
            .andExpect(jsonPath("$.landReferenceNo").value(DEFAULT_LAND_REFERENCE_NO))
            .andExpect(jsonPath("$.caseNo").value(DEFAULT_CASE_NO))
            .andExpect(jsonPath("$.villageName").value(DEFAULT_VILLAGE_NAME))
            .andExpect(jsonPath("$.accuserName").value(DEFAULT_ACCUSER_NAME))
            .andExpect(jsonPath("$.defendantName").value(DEFAULT_DEFENDANT_NAME))
            .andExpect(jsonPath("$.accuserLawyer").value(DEFAULT_ACCUSER_LAWYER))
            .andExpect(jsonPath("$.defendantLawyer").value(DEFAULT_DEFENDANT_LAWYER))
            .andExpect(jsonPath("$.caseOfficer").value(DEFAULT_CASE_OFFICER))
            .andExpect(jsonPath("$.caseOfficerAdd").value(DEFAULT_CASE_OFFICER_ADD))
            .andExpect(jsonPath("$.totalArea").value(DEFAULT_TOTAL_AREA))
            .andExpect(jsonPath("$.landAcquisitionOfficerNo").value(DEFAULT_LAND_ACQUISITION_OFFICER_NO))
            .andExpect(jsonPath("$.section11JudgmentNo").value(DEFAULT_SECTION_11_JUDGMENT_NO))
            .andExpect(jsonPath("$.section4NoticeDate").value(DEFAULT_SECTION_4_NOTICE_DATE))
            .andExpect(jsonPath("$.judgementDate").value(DEFAULT_JUDGEMENT_DATE))
            .andExpect(jsonPath("$.dateOfDecision").value(DEFAULT_DATE_OF_DECISION))
            .andExpect(jsonPath("$.caseFilingDate").value(DEFAULT_CASE_FILING_DATE))
            .andExpect(jsonPath("$.caseStatus").value(DEFAULT_CASE_STATUS.toString()))
            .andExpect(jsonPath("$.lastHearingDate").value(DEFAULT_LAST_HEARING_DATE))
            .andExpect(jsonPath("$.nextHearingDate").value(DEFAULT_NEXT_HEARING_DATE))
            .andExpect(jsonPath("$.natureResult").value(DEFAULT_NATURE_RESULT.toString()))
            .andExpect(jsonPath("$.amountDepositeInCourt").value(DEFAULT_AMOUNT_DEPOSITE_IN_COURT))
            .andExpect(jsonPath("$.claimAmount").value(DEFAULT_CLAIM_AMOUNT))
            .andExpect(jsonPath("$.depositedChequeNo").value(DEFAULT_DEPOSITED_CHEQUE_NO))
            .andExpect(jsonPath("$.depositedchequeDate").value(DEFAULT_DEPOSITEDCHEQUE_DATE))
            .andExpect(jsonPath("$.addInterestAmountDistCourt").value(DEFAULT_ADD_INTEREST_AMOUNT_DIST_COURT))
            .andExpect(jsonPath("$.addInterestApplicationNo").value(DEFAULT_ADD_INTEREST_APPLICATION_NO))
            .andExpect(jsonPath("$.addIntChequeNo").value(DEFAULT_ADD_INT_CHEQUE_NO))
            .andExpect(jsonPath("$.addIntChequeDate").value(DEFAULT_ADD_INT_CHEQUE_DATE))
            .andExpect(jsonPath("$.bankGuaranteeAppNo").value(DEFAULT_BANK_GUARANTEE_APP_NO))
            .andExpect(jsonPath("$.courtName").value(DEFAULT_COURT_NAME))
            .andExpect(jsonPath("$.courtType").value(DEFAULT_COURT_TYPE.toString()))
            .andExpect(jsonPath("$.caseType").value(DEFAULT_CASE_TYPE.toString()))
            .andExpect(jsonPath("$.isActivated").value(DEFAULT_IS_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.freefield1").value(DEFAULT_FREEFIELD_1))
            .andExpect(jsonPath("$.freefield2").value(DEFAULT_FREEFIELD_2))
            .andExpect(jsonPath("$.freefield3").value(DEFAULT_FREEFIELD_3))
            .andExpect(jsonPath("$.freefield4").value(DEFAULT_FREEFIELD_4))
            .andExpect(jsonPath("$.freefield5").value(DEFAULT_FREEFIELD_5))
            .andExpect(jsonPath("$.freefield6").value(DEFAULT_FREEFIELD_6))
            .andExpect(jsonPath("$.freefield7").value(DEFAULT_FREEFIELD_7))
            .andExpect(jsonPath("$.freefield8").value(DEFAULT_FREEFIELD_8))
            .andExpect(jsonPath("$.freefield9").value(DEFAULT_FREEFIELD_9))
            .andExpect(jsonPath("$.freefield10").value(DEFAULT_FREEFIELD_10))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED));
    }

    @Test
    @Transactional
    void getCourtCasesByIdFiltering() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        Long id = courtCase.getId();

        defaultCourtCaseShouldBeFound("id.equals=" + id);
        defaultCourtCaseShouldNotBeFound("id.notEquals=" + id);

        defaultCourtCaseShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCourtCaseShouldNotBeFound("id.greaterThan=" + id);

        defaultCourtCaseShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCourtCaseShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLandReferenceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where landReferenceNo equals to DEFAULT_LAND_REFERENCE_NO
        defaultCourtCaseShouldBeFound("landReferenceNo.equals=" + DEFAULT_LAND_REFERENCE_NO);

        // Get all the courtCaseList where landReferenceNo equals to UPDATED_LAND_REFERENCE_NO
        defaultCourtCaseShouldNotBeFound("landReferenceNo.equals=" + UPDATED_LAND_REFERENCE_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLandReferenceNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where landReferenceNo not equals to DEFAULT_LAND_REFERENCE_NO
        defaultCourtCaseShouldNotBeFound("landReferenceNo.notEquals=" + DEFAULT_LAND_REFERENCE_NO);

        // Get all the courtCaseList where landReferenceNo not equals to UPDATED_LAND_REFERENCE_NO
        defaultCourtCaseShouldBeFound("landReferenceNo.notEquals=" + UPDATED_LAND_REFERENCE_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLandReferenceNoIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where landReferenceNo in DEFAULT_LAND_REFERENCE_NO or UPDATED_LAND_REFERENCE_NO
        defaultCourtCaseShouldBeFound("landReferenceNo.in=" + DEFAULT_LAND_REFERENCE_NO + "," + UPDATED_LAND_REFERENCE_NO);

        // Get all the courtCaseList where landReferenceNo equals to UPDATED_LAND_REFERENCE_NO
        defaultCourtCaseShouldNotBeFound("landReferenceNo.in=" + UPDATED_LAND_REFERENCE_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLandReferenceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where landReferenceNo is not null
        defaultCourtCaseShouldBeFound("landReferenceNo.specified=true");

        // Get all the courtCaseList where landReferenceNo is null
        defaultCourtCaseShouldNotBeFound("landReferenceNo.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByLandReferenceNoContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where landReferenceNo contains DEFAULT_LAND_REFERENCE_NO
        defaultCourtCaseShouldBeFound("landReferenceNo.contains=" + DEFAULT_LAND_REFERENCE_NO);

        // Get all the courtCaseList where landReferenceNo contains UPDATED_LAND_REFERENCE_NO
        defaultCourtCaseShouldNotBeFound("landReferenceNo.contains=" + UPDATED_LAND_REFERENCE_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLandReferenceNoNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where landReferenceNo does not contain DEFAULT_LAND_REFERENCE_NO
        defaultCourtCaseShouldNotBeFound("landReferenceNo.doesNotContain=" + DEFAULT_LAND_REFERENCE_NO);

        // Get all the courtCaseList where landReferenceNo does not contain UPDATED_LAND_REFERENCE_NO
        defaultCourtCaseShouldBeFound("landReferenceNo.doesNotContain=" + UPDATED_LAND_REFERENCE_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseNoIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseNo equals to DEFAULT_CASE_NO
        defaultCourtCaseShouldBeFound("caseNo.equals=" + DEFAULT_CASE_NO);

        // Get all the courtCaseList where caseNo equals to UPDATED_CASE_NO
        defaultCourtCaseShouldNotBeFound("caseNo.equals=" + UPDATED_CASE_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseNo not equals to DEFAULT_CASE_NO
        defaultCourtCaseShouldNotBeFound("caseNo.notEquals=" + DEFAULT_CASE_NO);

        // Get all the courtCaseList where caseNo not equals to UPDATED_CASE_NO
        defaultCourtCaseShouldBeFound("caseNo.notEquals=" + UPDATED_CASE_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseNoIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseNo in DEFAULT_CASE_NO or UPDATED_CASE_NO
        defaultCourtCaseShouldBeFound("caseNo.in=" + DEFAULT_CASE_NO + "," + UPDATED_CASE_NO);

        // Get all the courtCaseList where caseNo equals to UPDATED_CASE_NO
        defaultCourtCaseShouldNotBeFound("caseNo.in=" + UPDATED_CASE_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseNo is not null
        defaultCourtCaseShouldBeFound("caseNo.specified=true");

        // Get all the courtCaseList where caseNo is null
        defaultCourtCaseShouldNotBeFound("caseNo.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseNoContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseNo contains DEFAULT_CASE_NO
        defaultCourtCaseShouldBeFound("caseNo.contains=" + DEFAULT_CASE_NO);

        // Get all the courtCaseList where caseNo contains UPDATED_CASE_NO
        defaultCourtCaseShouldNotBeFound("caseNo.contains=" + UPDATED_CASE_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseNoNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseNo does not contain DEFAULT_CASE_NO
        defaultCourtCaseShouldNotBeFound("caseNo.doesNotContain=" + DEFAULT_CASE_NO);

        // Get all the courtCaseList where caseNo does not contain UPDATED_CASE_NO
        defaultCourtCaseShouldBeFound("caseNo.doesNotContain=" + UPDATED_CASE_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVillageNameIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where villageName equals to DEFAULT_VILLAGE_NAME
        defaultCourtCaseShouldBeFound("villageName.equals=" + DEFAULT_VILLAGE_NAME);

        // Get all the courtCaseList where villageName equals to UPDATED_VILLAGE_NAME
        defaultCourtCaseShouldNotBeFound("villageName.equals=" + UPDATED_VILLAGE_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVillageNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where villageName not equals to DEFAULT_VILLAGE_NAME
        defaultCourtCaseShouldNotBeFound("villageName.notEquals=" + DEFAULT_VILLAGE_NAME);

        // Get all the courtCaseList where villageName not equals to UPDATED_VILLAGE_NAME
        defaultCourtCaseShouldBeFound("villageName.notEquals=" + UPDATED_VILLAGE_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVillageNameIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where villageName in DEFAULT_VILLAGE_NAME or UPDATED_VILLAGE_NAME
        defaultCourtCaseShouldBeFound("villageName.in=" + DEFAULT_VILLAGE_NAME + "," + UPDATED_VILLAGE_NAME);

        // Get all the courtCaseList where villageName equals to UPDATED_VILLAGE_NAME
        defaultCourtCaseShouldNotBeFound("villageName.in=" + UPDATED_VILLAGE_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVillageNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where villageName is not null
        defaultCourtCaseShouldBeFound("villageName.specified=true");

        // Get all the courtCaseList where villageName is null
        defaultCourtCaseShouldNotBeFound("villageName.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByVillageNameContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where villageName contains DEFAULT_VILLAGE_NAME
        defaultCourtCaseShouldBeFound("villageName.contains=" + DEFAULT_VILLAGE_NAME);

        // Get all the courtCaseList where villageName contains UPDATED_VILLAGE_NAME
        defaultCourtCaseShouldNotBeFound("villageName.contains=" + UPDATED_VILLAGE_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVillageNameNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where villageName does not contain DEFAULT_VILLAGE_NAME
        defaultCourtCaseShouldNotBeFound("villageName.doesNotContain=" + DEFAULT_VILLAGE_NAME);

        // Get all the courtCaseList where villageName does not contain UPDATED_VILLAGE_NAME
        defaultCourtCaseShouldBeFound("villageName.doesNotContain=" + UPDATED_VILLAGE_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAccuserNameIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where accuserName equals to DEFAULT_ACCUSER_NAME
        defaultCourtCaseShouldBeFound("accuserName.equals=" + DEFAULT_ACCUSER_NAME);

        // Get all the courtCaseList where accuserName equals to UPDATED_ACCUSER_NAME
        defaultCourtCaseShouldNotBeFound("accuserName.equals=" + UPDATED_ACCUSER_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAccuserNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where accuserName not equals to DEFAULT_ACCUSER_NAME
        defaultCourtCaseShouldNotBeFound("accuserName.notEquals=" + DEFAULT_ACCUSER_NAME);

        // Get all the courtCaseList where accuserName not equals to UPDATED_ACCUSER_NAME
        defaultCourtCaseShouldBeFound("accuserName.notEquals=" + UPDATED_ACCUSER_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAccuserNameIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where accuserName in DEFAULT_ACCUSER_NAME or UPDATED_ACCUSER_NAME
        defaultCourtCaseShouldBeFound("accuserName.in=" + DEFAULT_ACCUSER_NAME + "," + UPDATED_ACCUSER_NAME);

        // Get all the courtCaseList where accuserName equals to UPDATED_ACCUSER_NAME
        defaultCourtCaseShouldNotBeFound("accuserName.in=" + UPDATED_ACCUSER_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAccuserNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where accuserName is not null
        defaultCourtCaseShouldBeFound("accuserName.specified=true");

        // Get all the courtCaseList where accuserName is null
        defaultCourtCaseShouldNotBeFound("accuserName.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByAccuserNameContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where accuserName contains DEFAULT_ACCUSER_NAME
        defaultCourtCaseShouldBeFound("accuserName.contains=" + DEFAULT_ACCUSER_NAME);

        // Get all the courtCaseList where accuserName contains UPDATED_ACCUSER_NAME
        defaultCourtCaseShouldNotBeFound("accuserName.contains=" + UPDATED_ACCUSER_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAccuserNameNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where accuserName does not contain DEFAULT_ACCUSER_NAME
        defaultCourtCaseShouldNotBeFound("accuserName.doesNotContain=" + DEFAULT_ACCUSER_NAME);

        // Get all the courtCaseList where accuserName does not contain UPDATED_ACCUSER_NAME
        defaultCourtCaseShouldBeFound("accuserName.doesNotContain=" + UPDATED_ACCUSER_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDefendantNameIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where defendantName equals to DEFAULT_DEFENDANT_NAME
        defaultCourtCaseShouldBeFound("defendantName.equals=" + DEFAULT_DEFENDANT_NAME);

        // Get all the courtCaseList where defendantName equals to UPDATED_DEFENDANT_NAME
        defaultCourtCaseShouldNotBeFound("defendantName.equals=" + UPDATED_DEFENDANT_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDefendantNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where defendantName not equals to DEFAULT_DEFENDANT_NAME
        defaultCourtCaseShouldNotBeFound("defendantName.notEquals=" + DEFAULT_DEFENDANT_NAME);

        // Get all the courtCaseList where defendantName not equals to UPDATED_DEFENDANT_NAME
        defaultCourtCaseShouldBeFound("defendantName.notEquals=" + UPDATED_DEFENDANT_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDefendantNameIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where defendantName in DEFAULT_DEFENDANT_NAME or UPDATED_DEFENDANT_NAME
        defaultCourtCaseShouldBeFound("defendantName.in=" + DEFAULT_DEFENDANT_NAME + "," + UPDATED_DEFENDANT_NAME);

        // Get all the courtCaseList where defendantName equals to UPDATED_DEFENDANT_NAME
        defaultCourtCaseShouldNotBeFound("defendantName.in=" + UPDATED_DEFENDANT_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDefendantNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where defendantName is not null
        defaultCourtCaseShouldBeFound("defendantName.specified=true");

        // Get all the courtCaseList where defendantName is null
        defaultCourtCaseShouldNotBeFound("defendantName.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByDefendantNameContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where defendantName contains DEFAULT_DEFENDANT_NAME
        defaultCourtCaseShouldBeFound("defendantName.contains=" + DEFAULT_DEFENDANT_NAME);

        // Get all the courtCaseList where defendantName contains UPDATED_DEFENDANT_NAME
        defaultCourtCaseShouldNotBeFound("defendantName.contains=" + UPDATED_DEFENDANT_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDefendantNameNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where defendantName does not contain DEFAULT_DEFENDANT_NAME
        defaultCourtCaseShouldNotBeFound("defendantName.doesNotContain=" + DEFAULT_DEFENDANT_NAME);

        // Get all the courtCaseList where defendantName does not contain UPDATED_DEFENDANT_NAME
        defaultCourtCaseShouldBeFound("defendantName.doesNotContain=" + UPDATED_DEFENDANT_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAccuserLawyerIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where accuserLawyer equals to DEFAULT_ACCUSER_LAWYER
        defaultCourtCaseShouldBeFound("accuserLawyer.equals=" + DEFAULT_ACCUSER_LAWYER);

        // Get all the courtCaseList where accuserLawyer equals to UPDATED_ACCUSER_LAWYER
        defaultCourtCaseShouldNotBeFound("accuserLawyer.equals=" + UPDATED_ACCUSER_LAWYER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAccuserLawyerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where accuserLawyer not equals to DEFAULT_ACCUSER_LAWYER
        defaultCourtCaseShouldNotBeFound("accuserLawyer.notEquals=" + DEFAULT_ACCUSER_LAWYER);

        // Get all the courtCaseList where accuserLawyer not equals to UPDATED_ACCUSER_LAWYER
        defaultCourtCaseShouldBeFound("accuserLawyer.notEquals=" + UPDATED_ACCUSER_LAWYER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAccuserLawyerIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where accuserLawyer in DEFAULT_ACCUSER_LAWYER or UPDATED_ACCUSER_LAWYER
        defaultCourtCaseShouldBeFound("accuserLawyer.in=" + DEFAULT_ACCUSER_LAWYER + "," + UPDATED_ACCUSER_LAWYER);

        // Get all the courtCaseList where accuserLawyer equals to UPDATED_ACCUSER_LAWYER
        defaultCourtCaseShouldNotBeFound("accuserLawyer.in=" + UPDATED_ACCUSER_LAWYER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAccuserLawyerIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where accuserLawyer is not null
        defaultCourtCaseShouldBeFound("accuserLawyer.specified=true");

        // Get all the courtCaseList where accuserLawyer is null
        defaultCourtCaseShouldNotBeFound("accuserLawyer.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByAccuserLawyerContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where accuserLawyer contains DEFAULT_ACCUSER_LAWYER
        defaultCourtCaseShouldBeFound("accuserLawyer.contains=" + DEFAULT_ACCUSER_LAWYER);

        // Get all the courtCaseList where accuserLawyer contains UPDATED_ACCUSER_LAWYER
        defaultCourtCaseShouldNotBeFound("accuserLawyer.contains=" + UPDATED_ACCUSER_LAWYER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAccuserLawyerNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where accuserLawyer does not contain DEFAULT_ACCUSER_LAWYER
        defaultCourtCaseShouldNotBeFound("accuserLawyer.doesNotContain=" + DEFAULT_ACCUSER_LAWYER);

        // Get all the courtCaseList where accuserLawyer does not contain UPDATED_ACCUSER_LAWYER
        defaultCourtCaseShouldBeFound("accuserLawyer.doesNotContain=" + UPDATED_ACCUSER_LAWYER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDefendantLawyerIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where defendantLawyer equals to DEFAULT_DEFENDANT_LAWYER
        defaultCourtCaseShouldBeFound("defendantLawyer.equals=" + DEFAULT_DEFENDANT_LAWYER);

        // Get all the courtCaseList where defendantLawyer equals to UPDATED_DEFENDANT_LAWYER
        defaultCourtCaseShouldNotBeFound("defendantLawyer.equals=" + UPDATED_DEFENDANT_LAWYER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDefendantLawyerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where defendantLawyer not equals to DEFAULT_DEFENDANT_LAWYER
        defaultCourtCaseShouldNotBeFound("defendantLawyer.notEquals=" + DEFAULT_DEFENDANT_LAWYER);

        // Get all the courtCaseList where defendantLawyer not equals to UPDATED_DEFENDANT_LAWYER
        defaultCourtCaseShouldBeFound("defendantLawyer.notEquals=" + UPDATED_DEFENDANT_LAWYER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDefendantLawyerIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where defendantLawyer in DEFAULT_DEFENDANT_LAWYER or UPDATED_DEFENDANT_LAWYER
        defaultCourtCaseShouldBeFound("defendantLawyer.in=" + DEFAULT_DEFENDANT_LAWYER + "," + UPDATED_DEFENDANT_LAWYER);

        // Get all the courtCaseList where defendantLawyer equals to UPDATED_DEFENDANT_LAWYER
        defaultCourtCaseShouldNotBeFound("defendantLawyer.in=" + UPDATED_DEFENDANT_LAWYER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDefendantLawyerIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where defendantLawyer is not null
        defaultCourtCaseShouldBeFound("defendantLawyer.specified=true");

        // Get all the courtCaseList where defendantLawyer is null
        defaultCourtCaseShouldNotBeFound("defendantLawyer.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByDefendantLawyerContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where defendantLawyer contains DEFAULT_DEFENDANT_LAWYER
        defaultCourtCaseShouldBeFound("defendantLawyer.contains=" + DEFAULT_DEFENDANT_LAWYER);

        // Get all the courtCaseList where defendantLawyer contains UPDATED_DEFENDANT_LAWYER
        defaultCourtCaseShouldNotBeFound("defendantLawyer.contains=" + UPDATED_DEFENDANT_LAWYER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDefendantLawyerNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where defendantLawyer does not contain DEFAULT_DEFENDANT_LAWYER
        defaultCourtCaseShouldNotBeFound("defendantLawyer.doesNotContain=" + DEFAULT_DEFENDANT_LAWYER);

        // Get all the courtCaseList where defendantLawyer does not contain UPDATED_DEFENDANT_LAWYER
        defaultCourtCaseShouldBeFound("defendantLawyer.doesNotContain=" + UPDATED_DEFENDANT_LAWYER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseOfficerIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseOfficer equals to DEFAULT_CASE_OFFICER
        defaultCourtCaseShouldBeFound("caseOfficer.equals=" + DEFAULT_CASE_OFFICER);

        // Get all the courtCaseList where caseOfficer equals to UPDATED_CASE_OFFICER
        defaultCourtCaseShouldNotBeFound("caseOfficer.equals=" + UPDATED_CASE_OFFICER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseOfficerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseOfficer not equals to DEFAULT_CASE_OFFICER
        defaultCourtCaseShouldNotBeFound("caseOfficer.notEquals=" + DEFAULT_CASE_OFFICER);

        // Get all the courtCaseList where caseOfficer not equals to UPDATED_CASE_OFFICER
        defaultCourtCaseShouldBeFound("caseOfficer.notEquals=" + UPDATED_CASE_OFFICER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseOfficerIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseOfficer in DEFAULT_CASE_OFFICER or UPDATED_CASE_OFFICER
        defaultCourtCaseShouldBeFound("caseOfficer.in=" + DEFAULT_CASE_OFFICER + "," + UPDATED_CASE_OFFICER);

        // Get all the courtCaseList where caseOfficer equals to UPDATED_CASE_OFFICER
        defaultCourtCaseShouldNotBeFound("caseOfficer.in=" + UPDATED_CASE_OFFICER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseOfficerIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseOfficer is not null
        defaultCourtCaseShouldBeFound("caseOfficer.specified=true");

        // Get all the courtCaseList where caseOfficer is null
        defaultCourtCaseShouldNotBeFound("caseOfficer.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseOfficerContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseOfficer contains DEFAULT_CASE_OFFICER
        defaultCourtCaseShouldBeFound("caseOfficer.contains=" + DEFAULT_CASE_OFFICER);

        // Get all the courtCaseList where caseOfficer contains UPDATED_CASE_OFFICER
        defaultCourtCaseShouldNotBeFound("caseOfficer.contains=" + UPDATED_CASE_OFFICER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseOfficerNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseOfficer does not contain DEFAULT_CASE_OFFICER
        defaultCourtCaseShouldNotBeFound("caseOfficer.doesNotContain=" + DEFAULT_CASE_OFFICER);

        // Get all the courtCaseList where caseOfficer does not contain UPDATED_CASE_OFFICER
        defaultCourtCaseShouldBeFound("caseOfficer.doesNotContain=" + UPDATED_CASE_OFFICER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseOfficerAddIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseOfficerAdd equals to DEFAULT_CASE_OFFICER_ADD
        defaultCourtCaseShouldBeFound("caseOfficerAdd.equals=" + DEFAULT_CASE_OFFICER_ADD);

        // Get all the courtCaseList where caseOfficerAdd equals to UPDATED_CASE_OFFICER_ADD
        defaultCourtCaseShouldNotBeFound("caseOfficerAdd.equals=" + UPDATED_CASE_OFFICER_ADD);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseOfficerAddIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseOfficerAdd not equals to DEFAULT_CASE_OFFICER_ADD
        defaultCourtCaseShouldNotBeFound("caseOfficerAdd.notEquals=" + DEFAULT_CASE_OFFICER_ADD);

        // Get all the courtCaseList where caseOfficerAdd not equals to UPDATED_CASE_OFFICER_ADD
        defaultCourtCaseShouldBeFound("caseOfficerAdd.notEquals=" + UPDATED_CASE_OFFICER_ADD);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseOfficerAddIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseOfficerAdd in DEFAULT_CASE_OFFICER_ADD or UPDATED_CASE_OFFICER_ADD
        defaultCourtCaseShouldBeFound("caseOfficerAdd.in=" + DEFAULT_CASE_OFFICER_ADD + "," + UPDATED_CASE_OFFICER_ADD);

        // Get all the courtCaseList where caseOfficerAdd equals to UPDATED_CASE_OFFICER_ADD
        defaultCourtCaseShouldNotBeFound("caseOfficerAdd.in=" + UPDATED_CASE_OFFICER_ADD);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseOfficerAddIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseOfficerAdd is not null
        defaultCourtCaseShouldBeFound("caseOfficerAdd.specified=true");

        // Get all the courtCaseList where caseOfficerAdd is null
        defaultCourtCaseShouldNotBeFound("caseOfficerAdd.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseOfficerAddContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseOfficerAdd contains DEFAULT_CASE_OFFICER_ADD
        defaultCourtCaseShouldBeFound("caseOfficerAdd.contains=" + DEFAULT_CASE_OFFICER_ADD);

        // Get all the courtCaseList where caseOfficerAdd contains UPDATED_CASE_OFFICER_ADD
        defaultCourtCaseShouldNotBeFound("caseOfficerAdd.contains=" + UPDATED_CASE_OFFICER_ADD);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseOfficerAddNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseOfficerAdd does not contain DEFAULT_CASE_OFFICER_ADD
        defaultCourtCaseShouldNotBeFound("caseOfficerAdd.doesNotContain=" + DEFAULT_CASE_OFFICER_ADD);

        // Get all the courtCaseList where caseOfficerAdd does not contain UPDATED_CASE_OFFICER_ADD
        defaultCourtCaseShouldBeFound("caseOfficerAdd.doesNotContain=" + UPDATED_CASE_OFFICER_ADD);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTotalAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where totalArea equals to DEFAULT_TOTAL_AREA
        defaultCourtCaseShouldBeFound("totalArea.equals=" + DEFAULT_TOTAL_AREA);

        // Get all the courtCaseList where totalArea equals to UPDATED_TOTAL_AREA
        defaultCourtCaseShouldNotBeFound("totalArea.equals=" + UPDATED_TOTAL_AREA);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTotalAreaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where totalArea not equals to DEFAULT_TOTAL_AREA
        defaultCourtCaseShouldNotBeFound("totalArea.notEquals=" + DEFAULT_TOTAL_AREA);

        // Get all the courtCaseList where totalArea not equals to UPDATED_TOTAL_AREA
        defaultCourtCaseShouldBeFound("totalArea.notEquals=" + UPDATED_TOTAL_AREA);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTotalAreaIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where totalArea in DEFAULT_TOTAL_AREA or UPDATED_TOTAL_AREA
        defaultCourtCaseShouldBeFound("totalArea.in=" + DEFAULT_TOTAL_AREA + "," + UPDATED_TOTAL_AREA);

        // Get all the courtCaseList where totalArea equals to UPDATED_TOTAL_AREA
        defaultCourtCaseShouldNotBeFound("totalArea.in=" + UPDATED_TOTAL_AREA);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTotalAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where totalArea is not null
        defaultCourtCaseShouldBeFound("totalArea.specified=true");

        // Get all the courtCaseList where totalArea is null
        defaultCourtCaseShouldNotBeFound("totalArea.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByTotalAreaContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where totalArea contains DEFAULT_TOTAL_AREA
        defaultCourtCaseShouldBeFound("totalArea.contains=" + DEFAULT_TOTAL_AREA);

        // Get all the courtCaseList where totalArea contains UPDATED_TOTAL_AREA
        defaultCourtCaseShouldNotBeFound("totalArea.contains=" + UPDATED_TOTAL_AREA);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTotalAreaNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where totalArea does not contain DEFAULT_TOTAL_AREA
        defaultCourtCaseShouldNotBeFound("totalArea.doesNotContain=" + DEFAULT_TOTAL_AREA);

        // Get all the courtCaseList where totalArea does not contain UPDATED_TOTAL_AREA
        defaultCourtCaseShouldBeFound("totalArea.doesNotContain=" + UPDATED_TOTAL_AREA);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLandAcquisitionOfficerNoIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where landAcquisitionOfficerNo equals to DEFAULT_LAND_ACQUISITION_OFFICER_NO
        defaultCourtCaseShouldBeFound("landAcquisitionOfficerNo.equals=" + DEFAULT_LAND_ACQUISITION_OFFICER_NO);

        // Get all the courtCaseList where landAcquisitionOfficerNo equals to UPDATED_LAND_ACQUISITION_OFFICER_NO
        defaultCourtCaseShouldNotBeFound("landAcquisitionOfficerNo.equals=" + UPDATED_LAND_ACQUISITION_OFFICER_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLandAcquisitionOfficerNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where landAcquisitionOfficerNo not equals to DEFAULT_LAND_ACQUISITION_OFFICER_NO
        defaultCourtCaseShouldNotBeFound("landAcquisitionOfficerNo.notEquals=" + DEFAULT_LAND_ACQUISITION_OFFICER_NO);

        // Get all the courtCaseList where landAcquisitionOfficerNo not equals to UPDATED_LAND_ACQUISITION_OFFICER_NO
        defaultCourtCaseShouldBeFound("landAcquisitionOfficerNo.notEquals=" + UPDATED_LAND_ACQUISITION_OFFICER_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLandAcquisitionOfficerNoIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where landAcquisitionOfficerNo in DEFAULT_LAND_ACQUISITION_OFFICER_NO or UPDATED_LAND_ACQUISITION_OFFICER_NO
        defaultCourtCaseShouldBeFound(
            "landAcquisitionOfficerNo.in=" + DEFAULT_LAND_ACQUISITION_OFFICER_NO + "," + UPDATED_LAND_ACQUISITION_OFFICER_NO
        );

        // Get all the courtCaseList where landAcquisitionOfficerNo equals to UPDATED_LAND_ACQUISITION_OFFICER_NO
        defaultCourtCaseShouldNotBeFound("landAcquisitionOfficerNo.in=" + UPDATED_LAND_ACQUISITION_OFFICER_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLandAcquisitionOfficerNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where landAcquisitionOfficerNo is not null
        defaultCourtCaseShouldBeFound("landAcquisitionOfficerNo.specified=true");

        // Get all the courtCaseList where landAcquisitionOfficerNo is null
        defaultCourtCaseShouldNotBeFound("landAcquisitionOfficerNo.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByLandAcquisitionOfficerNoContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where landAcquisitionOfficerNo contains DEFAULT_LAND_ACQUISITION_OFFICER_NO
        defaultCourtCaseShouldBeFound("landAcquisitionOfficerNo.contains=" + DEFAULT_LAND_ACQUISITION_OFFICER_NO);

        // Get all the courtCaseList where landAcquisitionOfficerNo contains UPDATED_LAND_ACQUISITION_OFFICER_NO
        defaultCourtCaseShouldNotBeFound("landAcquisitionOfficerNo.contains=" + UPDATED_LAND_ACQUISITION_OFFICER_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLandAcquisitionOfficerNoNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where landAcquisitionOfficerNo does not contain DEFAULT_LAND_ACQUISITION_OFFICER_NO
        defaultCourtCaseShouldNotBeFound("landAcquisitionOfficerNo.doesNotContain=" + DEFAULT_LAND_ACQUISITION_OFFICER_NO);

        // Get all the courtCaseList where landAcquisitionOfficerNo does not contain UPDATED_LAND_ACQUISITION_OFFICER_NO
        defaultCourtCaseShouldBeFound("landAcquisitionOfficerNo.doesNotContain=" + UPDATED_LAND_ACQUISITION_OFFICER_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySection11JudgmentNoIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where section11JudgmentNo equals to DEFAULT_SECTION_11_JUDGMENT_NO
        defaultCourtCaseShouldBeFound("section11JudgmentNo.equals=" + DEFAULT_SECTION_11_JUDGMENT_NO);

        // Get all the courtCaseList where section11JudgmentNo equals to UPDATED_SECTION_11_JUDGMENT_NO
        defaultCourtCaseShouldNotBeFound("section11JudgmentNo.equals=" + UPDATED_SECTION_11_JUDGMENT_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySection11JudgmentNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where section11JudgmentNo not equals to DEFAULT_SECTION_11_JUDGMENT_NO
        defaultCourtCaseShouldNotBeFound("section11JudgmentNo.notEquals=" + DEFAULT_SECTION_11_JUDGMENT_NO);

        // Get all the courtCaseList where section11JudgmentNo not equals to UPDATED_SECTION_11_JUDGMENT_NO
        defaultCourtCaseShouldBeFound("section11JudgmentNo.notEquals=" + UPDATED_SECTION_11_JUDGMENT_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySection11JudgmentNoIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where section11JudgmentNo in DEFAULT_SECTION_11_JUDGMENT_NO or UPDATED_SECTION_11_JUDGMENT_NO
        defaultCourtCaseShouldBeFound("section11JudgmentNo.in=" + DEFAULT_SECTION_11_JUDGMENT_NO + "," + UPDATED_SECTION_11_JUDGMENT_NO);

        // Get all the courtCaseList where section11JudgmentNo equals to UPDATED_SECTION_11_JUDGMENT_NO
        defaultCourtCaseShouldNotBeFound("section11JudgmentNo.in=" + UPDATED_SECTION_11_JUDGMENT_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySection11JudgmentNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where section11JudgmentNo is not null
        defaultCourtCaseShouldBeFound("section11JudgmentNo.specified=true");

        // Get all the courtCaseList where section11JudgmentNo is null
        defaultCourtCaseShouldNotBeFound("section11JudgmentNo.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesBySection11JudgmentNoContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where section11JudgmentNo contains DEFAULT_SECTION_11_JUDGMENT_NO
        defaultCourtCaseShouldBeFound("section11JudgmentNo.contains=" + DEFAULT_SECTION_11_JUDGMENT_NO);

        // Get all the courtCaseList where section11JudgmentNo contains UPDATED_SECTION_11_JUDGMENT_NO
        defaultCourtCaseShouldNotBeFound("section11JudgmentNo.contains=" + UPDATED_SECTION_11_JUDGMENT_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySection11JudgmentNoNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where section11JudgmentNo does not contain DEFAULT_SECTION_11_JUDGMENT_NO
        defaultCourtCaseShouldNotBeFound("section11JudgmentNo.doesNotContain=" + DEFAULT_SECTION_11_JUDGMENT_NO);

        // Get all the courtCaseList where section11JudgmentNo does not contain UPDATED_SECTION_11_JUDGMENT_NO
        defaultCourtCaseShouldBeFound("section11JudgmentNo.doesNotContain=" + UPDATED_SECTION_11_JUDGMENT_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySection4NoticeDateIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where section4NoticeDate equals to DEFAULT_SECTION_4_NOTICE_DATE
        defaultCourtCaseShouldBeFound("section4NoticeDate.equals=" + DEFAULT_SECTION_4_NOTICE_DATE);

        // Get all the courtCaseList where section4NoticeDate equals to UPDATED_SECTION_4_NOTICE_DATE
        defaultCourtCaseShouldNotBeFound("section4NoticeDate.equals=" + UPDATED_SECTION_4_NOTICE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySection4NoticeDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where section4NoticeDate not equals to DEFAULT_SECTION_4_NOTICE_DATE
        defaultCourtCaseShouldNotBeFound("section4NoticeDate.notEquals=" + DEFAULT_SECTION_4_NOTICE_DATE);

        // Get all the courtCaseList where section4NoticeDate not equals to UPDATED_SECTION_4_NOTICE_DATE
        defaultCourtCaseShouldBeFound("section4NoticeDate.notEquals=" + UPDATED_SECTION_4_NOTICE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySection4NoticeDateIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where section4NoticeDate in DEFAULT_SECTION_4_NOTICE_DATE or UPDATED_SECTION_4_NOTICE_DATE
        defaultCourtCaseShouldBeFound("section4NoticeDate.in=" + DEFAULT_SECTION_4_NOTICE_DATE + "," + UPDATED_SECTION_4_NOTICE_DATE);

        // Get all the courtCaseList where section4NoticeDate equals to UPDATED_SECTION_4_NOTICE_DATE
        defaultCourtCaseShouldNotBeFound("section4NoticeDate.in=" + UPDATED_SECTION_4_NOTICE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySection4NoticeDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where section4NoticeDate is not null
        defaultCourtCaseShouldBeFound("section4NoticeDate.specified=true");

        // Get all the courtCaseList where section4NoticeDate is null
        defaultCourtCaseShouldNotBeFound("section4NoticeDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesBySection4NoticeDateContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where section4NoticeDate contains DEFAULT_SECTION_4_NOTICE_DATE
        defaultCourtCaseShouldBeFound("section4NoticeDate.contains=" + DEFAULT_SECTION_4_NOTICE_DATE);

        // Get all the courtCaseList where section4NoticeDate contains UPDATED_SECTION_4_NOTICE_DATE
        defaultCourtCaseShouldNotBeFound("section4NoticeDate.contains=" + UPDATED_SECTION_4_NOTICE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySection4NoticeDateNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where section4NoticeDate does not contain DEFAULT_SECTION_4_NOTICE_DATE
        defaultCourtCaseShouldNotBeFound("section4NoticeDate.doesNotContain=" + DEFAULT_SECTION_4_NOTICE_DATE);

        // Get all the courtCaseList where section4NoticeDate does not contain UPDATED_SECTION_4_NOTICE_DATE
        defaultCourtCaseShouldBeFound("section4NoticeDate.doesNotContain=" + UPDATED_SECTION_4_NOTICE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJudgementDateIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where judgementDate equals to DEFAULT_JUDGEMENT_DATE
        defaultCourtCaseShouldBeFound("judgementDate.equals=" + DEFAULT_JUDGEMENT_DATE);

        // Get all the courtCaseList where judgementDate equals to UPDATED_JUDGEMENT_DATE
        defaultCourtCaseShouldNotBeFound("judgementDate.equals=" + UPDATED_JUDGEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJudgementDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where judgementDate not equals to DEFAULT_JUDGEMENT_DATE
        defaultCourtCaseShouldNotBeFound("judgementDate.notEquals=" + DEFAULT_JUDGEMENT_DATE);

        // Get all the courtCaseList where judgementDate not equals to UPDATED_JUDGEMENT_DATE
        defaultCourtCaseShouldBeFound("judgementDate.notEquals=" + UPDATED_JUDGEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJudgementDateIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where judgementDate in DEFAULT_JUDGEMENT_DATE or UPDATED_JUDGEMENT_DATE
        defaultCourtCaseShouldBeFound("judgementDate.in=" + DEFAULT_JUDGEMENT_DATE + "," + UPDATED_JUDGEMENT_DATE);

        // Get all the courtCaseList where judgementDate equals to UPDATED_JUDGEMENT_DATE
        defaultCourtCaseShouldNotBeFound("judgementDate.in=" + UPDATED_JUDGEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJudgementDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where judgementDate is not null
        defaultCourtCaseShouldBeFound("judgementDate.specified=true");

        // Get all the courtCaseList where judgementDate is null
        defaultCourtCaseShouldNotBeFound("judgementDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByJudgementDateContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where judgementDate contains DEFAULT_JUDGEMENT_DATE
        defaultCourtCaseShouldBeFound("judgementDate.contains=" + DEFAULT_JUDGEMENT_DATE);

        // Get all the courtCaseList where judgementDate contains UPDATED_JUDGEMENT_DATE
        defaultCourtCaseShouldNotBeFound("judgementDate.contains=" + UPDATED_JUDGEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJudgementDateNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where judgementDate does not contain DEFAULT_JUDGEMENT_DATE
        defaultCourtCaseShouldNotBeFound("judgementDate.doesNotContain=" + DEFAULT_JUDGEMENT_DATE);

        // Get all the courtCaseList where judgementDate does not contain UPDATED_JUDGEMENT_DATE
        defaultCourtCaseShouldBeFound("judgementDate.doesNotContain=" + UPDATED_JUDGEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDateOfDecisionIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dateOfDecision equals to DEFAULT_DATE_OF_DECISION
        defaultCourtCaseShouldBeFound("dateOfDecision.equals=" + DEFAULT_DATE_OF_DECISION);

        // Get all the courtCaseList where dateOfDecision equals to UPDATED_DATE_OF_DECISION
        defaultCourtCaseShouldNotBeFound("dateOfDecision.equals=" + UPDATED_DATE_OF_DECISION);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDateOfDecisionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dateOfDecision not equals to DEFAULT_DATE_OF_DECISION
        defaultCourtCaseShouldNotBeFound("dateOfDecision.notEquals=" + DEFAULT_DATE_OF_DECISION);

        // Get all the courtCaseList where dateOfDecision not equals to UPDATED_DATE_OF_DECISION
        defaultCourtCaseShouldBeFound("dateOfDecision.notEquals=" + UPDATED_DATE_OF_DECISION);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDateOfDecisionIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dateOfDecision in DEFAULT_DATE_OF_DECISION or UPDATED_DATE_OF_DECISION
        defaultCourtCaseShouldBeFound("dateOfDecision.in=" + DEFAULT_DATE_OF_DECISION + "," + UPDATED_DATE_OF_DECISION);

        // Get all the courtCaseList where dateOfDecision equals to UPDATED_DATE_OF_DECISION
        defaultCourtCaseShouldNotBeFound("dateOfDecision.in=" + UPDATED_DATE_OF_DECISION);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDateOfDecisionIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dateOfDecision is not null
        defaultCourtCaseShouldBeFound("dateOfDecision.specified=true");

        // Get all the courtCaseList where dateOfDecision is null
        defaultCourtCaseShouldNotBeFound("dateOfDecision.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByDateOfDecisionContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dateOfDecision contains DEFAULT_DATE_OF_DECISION
        defaultCourtCaseShouldBeFound("dateOfDecision.contains=" + DEFAULT_DATE_OF_DECISION);

        // Get all the courtCaseList where dateOfDecision contains UPDATED_DATE_OF_DECISION
        defaultCourtCaseShouldNotBeFound("dateOfDecision.contains=" + UPDATED_DATE_OF_DECISION);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDateOfDecisionNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dateOfDecision does not contain DEFAULT_DATE_OF_DECISION
        defaultCourtCaseShouldNotBeFound("dateOfDecision.doesNotContain=" + DEFAULT_DATE_OF_DECISION);

        // Get all the courtCaseList where dateOfDecision does not contain UPDATED_DATE_OF_DECISION
        defaultCourtCaseShouldBeFound("dateOfDecision.doesNotContain=" + UPDATED_DATE_OF_DECISION);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseFilingDateIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseFilingDate equals to DEFAULT_CASE_FILING_DATE
        defaultCourtCaseShouldBeFound("caseFilingDate.equals=" + DEFAULT_CASE_FILING_DATE);

        // Get all the courtCaseList where caseFilingDate equals to UPDATED_CASE_FILING_DATE
        defaultCourtCaseShouldNotBeFound("caseFilingDate.equals=" + UPDATED_CASE_FILING_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseFilingDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseFilingDate not equals to DEFAULT_CASE_FILING_DATE
        defaultCourtCaseShouldNotBeFound("caseFilingDate.notEquals=" + DEFAULT_CASE_FILING_DATE);

        // Get all the courtCaseList where caseFilingDate not equals to UPDATED_CASE_FILING_DATE
        defaultCourtCaseShouldBeFound("caseFilingDate.notEquals=" + UPDATED_CASE_FILING_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseFilingDateIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseFilingDate in DEFAULT_CASE_FILING_DATE or UPDATED_CASE_FILING_DATE
        defaultCourtCaseShouldBeFound("caseFilingDate.in=" + DEFAULT_CASE_FILING_DATE + "," + UPDATED_CASE_FILING_DATE);

        // Get all the courtCaseList where caseFilingDate equals to UPDATED_CASE_FILING_DATE
        defaultCourtCaseShouldNotBeFound("caseFilingDate.in=" + UPDATED_CASE_FILING_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseFilingDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseFilingDate is not null
        defaultCourtCaseShouldBeFound("caseFilingDate.specified=true");

        // Get all the courtCaseList where caseFilingDate is null
        defaultCourtCaseShouldNotBeFound("caseFilingDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseFilingDateContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseFilingDate contains DEFAULT_CASE_FILING_DATE
        defaultCourtCaseShouldBeFound("caseFilingDate.contains=" + DEFAULT_CASE_FILING_DATE);

        // Get all the courtCaseList where caseFilingDate contains UPDATED_CASE_FILING_DATE
        defaultCourtCaseShouldNotBeFound("caseFilingDate.contains=" + UPDATED_CASE_FILING_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseFilingDateNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseFilingDate does not contain DEFAULT_CASE_FILING_DATE
        defaultCourtCaseShouldNotBeFound("caseFilingDate.doesNotContain=" + DEFAULT_CASE_FILING_DATE);

        // Get all the courtCaseList where caseFilingDate does not contain UPDATED_CASE_FILING_DATE
        defaultCourtCaseShouldBeFound("caseFilingDate.doesNotContain=" + UPDATED_CASE_FILING_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseStatus equals to DEFAULT_CASE_STATUS
        defaultCourtCaseShouldBeFound("caseStatus.equals=" + DEFAULT_CASE_STATUS);

        // Get all the courtCaseList where caseStatus equals to UPDATED_CASE_STATUS
        defaultCourtCaseShouldNotBeFound("caseStatus.equals=" + UPDATED_CASE_STATUS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseStatus not equals to DEFAULT_CASE_STATUS
        defaultCourtCaseShouldNotBeFound("caseStatus.notEquals=" + DEFAULT_CASE_STATUS);

        // Get all the courtCaseList where caseStatus not equals to UPDATED_CASE_STATUS
        defaultCourtCaseShouldBeFound("caseStatus.notEquals=" + UPDATED_CASE_STATUS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseStatusIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseStatus in DEFAULT_CASE_STATUS or UPDATED_CASE_STATUS
        defaultCourtCaseShouldBeFound("caseStatus.in=" + DEFAULT_CASE_STATUS + "," + UPDATED_CASE_STATUS);

        // Get all the courtCaseList where caseStatus equals to UPDATED_CASE_STATUS
        defaultCourtCaseShouldNotBeFound("caseStatus.in=" + UPDATED_CASE_STATUS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseStatus is not null
        defaultCourtCaseShouldBeFound("caseStatus.specified=true");

        // Get all the courtCaseList where caseStatus is null
        defaultCourtCaseShouldNotBeFound("caseStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastHearingDateIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastHearingDate equals to DEFAULT_LAST_HEARING_DATE
        defaultCourtCaseShouldBeFound("lastHearingDate.equals=" + DEFAULT_LAST_HEARING_DATE);

        // Get all the courtCaseList where lastHearingDate equals to UPDATED_LAST_HEARING_DATE
        defaultCourtCaseShouldNotBeFound("lastHearingDate.equals=" + UPDATED_LAST_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastHearingDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastHearingDate not equals to DEFAULT_LAST_HEARING_DATE
        defaultCourtCaseShouldNotBeFound("lastHearingDate.notEquals=" + DEFAULT_LAST_HEARING_DATE);

        // Get all the courtCaseList where lastHearingDate not equals to UPDATED_LAST_HEARING_DATE
        defaultCourtCaseShouldBeFound("lastHearingDate.notEquals=" + UPDATED_LAST_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastHearingDateIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastHearingDate in DEFAULT_LAST_HEARING_DATE or UPDATED_LAST_HEARING_DATE
        defaultCourtCaseShouldBeFound("lastHearingDate.in=" + DEFAULT_LAST_HEARING_DATE + "," + UPDATED_LAST_HEARING_DATE);

        // Get all the courtCaseList where lastHearingDate equals to UPDATED_LAST_HEARING_DATE
        defaultCourtCaseShouldNotBeFound("lastHearingDate.in=" + UPDATED_LAST_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastHearingDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastHearingDate is not null
        defaultCourtCaseShouldBeFound("lastHearingDate.specified=true");

        // Get all the courtCaseList where lastHearingDate is null
        defaultCourtCaseShouldNotBeFound("lastHearingDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastHearingDateContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastHearingDate contains DEFAULT_LAST_HEARING_DATE
        defaultCourtCaseShouldBeFound("lastHearingDate.contains=" + DEFAULT_LAST_HEARING_DATE);

        // Get all the courtCaseList where lastHearingDate contains UPDATED_LAST_HEARING_DATE
        defaultCourtCaseShouldNotBeFound("lastHearingDate.contains=" + UPDATED_LAST_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastHearingDateNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastHearingDate does not contain DEFAULT_LAST_HEARING_DATE
        defaultCourtCaseShouldNotBeFound("lastHearingDate.doesNotContain=" + DEFAULT_LAST_HEARING_DATE);

        // Get all the courtCaseList where lastHearingDate does not contain UPDATED_LAST_HEARING_DATE
        defaultCourtCaseShouldBeFound("lastHearingDate.doesNotContain=" + UPDATED_LAST_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByNextHearingDateIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where nextHearingDate equals to DEFAULT_NEXT_HEARING_DATE
        defaultCourtCaseShouldBeFound("nextHearingDate.equals=" + DEFAULT_NEXT_HEARING_DATE);

        // Get all the courtCaseList where nextHearingDate equals to UPDATED_NEXT_HEARING_DATE
        defaultCourtCaseShouldNotBeFound("nextHearingDate.equals=" + UPDATED_NEXT_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByNextHearingDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where nextHearingDate not equals to DEFAULT_NEXT_HEARING_DATE
        defaultCourtCaseShouldNotBeFound("nextHearingDate.notEquals=" + DEFAULT_NEXT_HEARING_DATE);

        // Get all the courtCaseList where nextHearingDate not equals to UPDATED_NEXT_HEARING_DATE
        defaultCourtCaseShouldBeFound("nextHearingDate.notEquals=" + UPDATED_NEXT_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByNextHearingDateIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where nextHearingDate in DEFAULT_NEXT_HEARING_DATE or UPDATED_NEXT_HEARING_DATE
        defaultCourtCaseShouldBeFound("nextHearingDate.in=" + DEFAULT_NEXT_HEARING_DATE + "," + UPDATED_NEXT_HEARING_DATE);

        // Get all the courtCaseList where nextHearingDate equals to UPDATED_NEXT_HEARING_DATE
        defaultCourtCaseShouldNotBeFound("nextHearingDate.in=" + UPDATED_NEXT_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByNextHearingDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where nextHearingDate is not null
        defaultCourtCaseShouldBeFound("nextHearingDate.specified=true");

        // Get all the courtCaseList where nextHearingDate is null
        defaultCourtCaseShouldNotBeFound("nextHearingDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByNextHearingDateContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where nextHearingDate contains DEFAULT_NEXT_HEARING_DATE
        defaultCourtCaseShouldBeFound("nextHearingDate.contains=" + DEFAULT_NEXT_HEARING_DATE);

        // Get all the courtCaseList where nextHearingDate contains UPDATED_NEXT_HEARING_DATE
        defaultCourtCaseShouldNotBeFound("nextHearingDate.contains=" + UPDATED_NEXT_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByNextHearingDateNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where nextHearingDate does not contain DEFAULT_NEXT_HEARING_DATE
        defaultCourtCaseShouldNotBeFound("nextHearingDate.doesNotContain=" + DEFAULT_NEXT_HEARING_DATE);

        // Get all the courtCaseList where nextHearingDate does not contain UPDATED_NEXT_HEARING_DATE
        defaultCourtCaseShouldBeFound("nextHearingDate.doesNotContain=" + UPDATED_NEXT_HEARING_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByNatureResultIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where natureResult equals to DEFAULT_NATURE_RESULT
        defaultCourtCaseShouldBeFound("natureResult.equals=" + DEFAULT_NATURE_RESULT);

        // Get all the courtCaseList where natureResult equals to UPDATED_NATURE_RESULT
        defaultCourtCaseShouldNotBeFound("natureResult.equals=" + UPDATED_NATURE_RESULT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByNatureResultIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where natureResult not equals to DEFAULT_NATURE_RESULT
        defaultCourtCaseShouldNotBeFound("natureResult.notEquals=" + DEFAULT_NATURE_RESULT);

        // Get all the courtCaseList where natureResult not equals to UPDATED_NATURE_RESULT
        defaultCourtCaseShouldBeFound("natureResult.notEquals=" + UPDATED_NATURE_RESULT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByNatureResultIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where natureResult in DEFAULT_NATURE_RESULT or UPDATED_NATURE_RESULT
        defaultCourtCaseShouldBeFound("natureResult.in=" + DEFAULT_NATURE_RESULT + "," + UPDATED_NATURE_RESULT);

        // Get all the courtCaseList where natureResult equals to UPDATED_NATURE_RESULT
        defaultCourtCaseShouldNotBeFound("natureResult.in=" + UPDATED_NATURE_RESULT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByNatureResultIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where natureResult is not null
        defaultCourtCaseShouldBeFound("natureResult.specified=true");

        // Get all the courtCaseList where natureResult is null
        defaultCourtCaseShouldNotBeFound("natureResult.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByAmountDepositeInCourtIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where amountDepositeInCourt equals to DEFAULT_AMOUNT_DEPOSITE_IN_COURT
        defaultCourtCaseShouldBeFound("amountDepositeInCourt.equals=" + DEFAULT_AMOUNT_DEPOSITE_IN_COURT);

        // Get all the courtCaseList where amountDepositeInCourt equals to UPDATED_AMOUNT_DEPOSITE_IN_COURT
        defaultCourtCaseShouldNotBeFound("amountDepositeInCourt.equals=" + UPDATED_AMOUNT_DEPOSITE_IN_COURT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAmountDepositeInCourtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where amountDepositeInCourt not equals to DEFAULT_AMOUNT_DEPOSITE_IN_COURT
        defaultCourtCaseShouldNotBeFound("amountDepositeInCourt.notEquals=" + DEFAULT_AMOUNT_DEPOSITE_IN_COURT);

        // Get all the courtCaseList where amountDepositeInCourt not equals to UPDATED_AMOUNT_DEPOSITE_IN_COURT
        defaultCourtCaseShouldBeFound("amountDepositeInCourt.notEquals=" + UPDATED_AMOUNT_DEPOSITE_IN_COURT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAmountDepositeInCourtIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where amountDepositeInCourt in DEFAULT_AMOUNT_DEPOSITE_IN_COURT or UPDATED_AMOUNT_DEPOSITE_IN_COURT
        defaultCourtCaseShouldBeFound(
            "amountDepositeInCourt.in=" + DEFAULT_AMOUNT_DEPOSITE_IN_COURT + "," + UPDATED_AMOUNT_DEPOSITE_IN_COURT
        );

        // Get all the courtCaseList where amountDepositeInCourt equals to UPDATED_AMOUNT_DEPOSITE_IN_COURT
        defaultCourtCaseShouldNotBeFound("amountDepositeInCourt.in=" + UPDATED_AMOUNT_DEPOSITE_IN_COURT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAmountDepositeInCourtIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where amountDepositeInCourt is not null
        defaultCourtCaseShouldBeFound("amountDepositeInCourt.specified=true");

        // Get all the courtCaseList where amountDepositeInCourt is null
        defaultCourtCaseShouldNotBeFound("amountDepositeInCourt.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByAmountDepositeInCourtContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where amountDepositeInCourt contains DEFAULT_AMOUNT_DEPOSITE_IN_COURT
        defaultCourtCaseShouldBeFound("amountDepositeInCourt.contains=" + DEFAULT_AMOUNT_DEPOSITE_IN_COURT);

        // Get all the courtCaseList where amountDepositeInCourt contains UPDATED_AMOUNT_DEPOSITE_IN_COURT
        defaultCourtCaseShouldNotBeFound("amountDepositeInCourt.contains=" + UPDATED_AMOUNT_DEPOSITE_IN_COURT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAmountDepositeInCourtNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where amountDepositeInCourt does not contain DEFAULT_AMOUNT_DEPOSITE_IN_COURT
        defaultCourtCaseShouldNotBeFound("amountDepositeInCourt.doesNotContain=" + DEFAULT_AMOUNT_DEPOSITE_IN_COURT);

        // Get all the courtCaseList where amountDepositeInCourt does not contain UPDATED_AMOUNT_DEPOSITE_IN_COURT
        defaultCourtCaseShouldBeFound("amountDepositeInCourt.doesNotContain=" + UPDATED_AMOUNT_DEPOSITE_IN_COURT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByClaimAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where claimAmount equals to DEFAULT_CLAIM_AMOUNT
        defaultCourtCaseShouldBeFound("claimAmount.equals=" + DEFAULT_CLAIM_AMOUNT);

        // Get all the courtCaseList where claimAmount equals to UPDATED_CLAIM_AMOUNT
        defaultCourtCaseShouldNotBeFound("claimAmount.equals=" + UPDATED_CLAIM_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByClaimAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where claimAmount not equals to DEFAULT_CLAIM_AMOUNT
        defaultCourtCaseShouldNotBeFound("claimAmount.notEquals=" + DEFAULT_CLAIM_AMOUNT);

        // Get all the courtCaseList where claimAmount not equals to UPDATED_CLAIM_AMOUNT
        defaultCourtCaseShouldBeFound("claimAmount.notEquals=" + UPDATED_CLAIM_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByClaimAmountIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where claimAmount in DEFAULT_CLAIM_AMOUNT or UPDATED_CLAIM_AMOUNT
        defaultCourtCaseShouldBeFound("claimAmount.in=" + DEFAULT_CLAIM_AMOUNT + "," + UPDATED_CLAIM_AMOUNT);

        // Get all the courtCaseList where claimAmount equals to UPDATED_CLAIM_AMOUNT
        defaultCourtCaseShouldNotBeFound("claimAmount.in=" + UPDATED_CLAIM_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByClaimAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where claimAmount is not null
        defaultCourtCaseShouldBeFound("claimAmount.specified=true");

        // Get all the courtCaseList where claimAmount is null
        defaultCourtCaseShouldNotBeFound("claimAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByClaimAmountContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where claimAmount contains DEFAULT_CLAIM_AMOUNT
        defaultCourtCaseShouldBeFound("claimAmount.contains=" + DEFAULT_CLAIM_AMOUNT);

        // Get all the courtCaseList where claimAmount contains UPDATED_CLAIM_AMOUNT
        defaultCourtCaseShouldNotBeFound("claimAmount.contains=" + UPDATED_CLAIM_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByClaimAmountNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where claimAmount does not contain DEFAULT_CLAIM_AMOUNT
        defaultCourtCaseShouldNotBeFound("claimAmount.doesNotContain=" + DEFAULT_CLAIM_AMOUNT);

        // Get all the courtCaseList where claimAmount does not contain UPDATED_CLAIM_AMOUNT
        defaultCourtCaseShouldBeFound("claimAmount.doesNotContain=" + UPDATED_CLAIM_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDepositedChequeNoIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where depositedChequeNo equals to DEFAULT_DEPOSITED_CHEQUE_NO
        defaultCourtCaseShouldBeFound("depositedChequeNo.equals=" + DEFAULT_DEPOSITED_CHEQUE_NO);

        // Get all the courtCaseList where depositedChequeNo equals to UPDATED_DEPOSITED_CHEQUE_NO
        defaultCourtCaseShouldNotBeFound("depositedChequeNo.equals=" + UPDATED_DEPOSITED_CHEQUE_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDepositedChequeNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where depositedChequeNo not equals to DEFAULT_DEPOSITED_CHEQUE_NO
        defaultCourtCaseShouldNotBeFound("depositedChequeNo.notEquals=" + DEFAULT_DEPOSITED_CHEQUE_NO);

        // Get all the courtCaseList where depositedChequeNo not equals to UPDATED_DEPOSITED_CHEQUE_NO
        defaultCourtCaseShouldBeFound("depositedChequeNo.notEquals=" + UPDATED_DEPOSITED_CHEQUE_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDepositedChequeNoIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where depositedChequeNo in DEFAULT_DEPOSITED_CHEQUE_NO or UPDATED_DEPOSITED_CHEQUE_NO
        defaultCourtCaseShouldBeFound("depositedChequeNo.in=" + DEFAULT_DEPOSITED_CHEQUE_NO + "," + UPDATED_DEPOSITED_CHEQUE_NO);

        // Get all the courtCaseList where depositedChequeNo equals to UPDATED_DEPOSITED_CHEQUE_NO
        defaultCourtCaseShouldNotBeFound("depositedChequeNo.in=" + UPDATED_DEPOSITED_CHEQUE_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDepositedChequeNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where depositedChequeNo is not null
        defaultCourtCaseShouldBeFound("depositedChequeNo.specified=true");

        // Get all the courtCaseList where depositedChequeNo is null
        defaultCourtCaseShouldNotBeFound("depositedChequeNo.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByDepositedChequeNoContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where depositedChequeNo contains DEFAULT_DEPOSITED_CHEQUE_NO
        defaultCourtCaseShouldBeFound("depositedChequeNo.contains=" + DEFAULT_DEPOSITED_CHEQUE_NO);

        // Get all the courtCaseList where depositedChequeNo contains UPDATED_DEPOSITED_CHEQUE_NO
        defaultCourtCaseShouldNotBeFound("depositedChequeNo.contains=" + UPDATED_DEPOSITED_CHEQUE_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDepositedChequeNoNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where depositedChequeNo does not contain DEFAULT_DEPOSITED_CHEQUE_NO
        defaultCourtCaseShouldNotBeFound("depositedChequeNo.doesNotContain=" + DEFAULT_DEPOSITED_CHEQUE_NO);

        // Get all the courtCaseList where depositedChequeNo does not contain UPDATED_DEPOSITED_CHEQUE_NO
        defaultCourtCaseShouldBeFound("depositedChequeNo.doesNotContain=" + UPDATED_DEPOSITED_CHEQUE_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDepositedchequeDateIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where depositedchequeDate equals to DEFAULT_DEPOSITEDCHEQUE_DATE
        defaultCourtCaseShouldBeFound("depositedchequeDate.equals=" + DEFAULT_DEPOSITEDCHEQUE_DATE);

        // Get all the courtCaseList where depositedchequeDate equals to UPDATED_DEPOSITEDCHEQUE_DATE
        defaultCourtCaseShouldNotBeFound("depositedchequeDate.equals=" + UPDATED_DEPOSITEDCHEQUE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDepositedchequeDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where depositedchequeDate not equals to DEFAULT_DEPOSITEDCHEQUE_DATE
        defaultCourtCaseShouldNotBeFound("depositedchequeDate.notEquals=" + DEFAULT_DEPOSITEDCHEQUE_DATE);

        // Get all the courtCaseList where depositedchequeDate not equals to UPDATED_DEPOSITEDCHEQUE_DATE
        defaultCourtCaseShouldBeFound("depositedchequeDate.notEquals=" + UPDATED_DEPOSITEDCHEQUE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDepositedchequeDateIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where depositedchequeDate in DEFAULT_DEPOSITEDCHEQUE_DATE or UPDATED_DEPOSITEDCHEQUE_DATE
        defaultCourtCaseShouldBeFound("depositedchequeDate.in=" + DEFAULT_DEPOSITEDCHEQUE_DATE + "," + UPDATED_DEPOSITEDCHEQUE_DATE);

        // Get all the courtCaseList where depositedchequeDate equals to UPDATED_DEPOSITEDCHEQUE_DATE
        defaultCourtCaseShouldNotBeFound("depositedchequeDate.in=" + UPDATED_DEPOSITEDCHEQUE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDepositedchequeDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where depositedchequeDate is not null
        defaultCourtCaseShouldBeFound("depositedchequeDate.specified=true");

        // Get all the courtCaseList where depositedchequeDate is null
        defaultCourtCaseShouldNotBeFound("depositedchequeDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByDepositedchequeDateContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where depositedchequeDate contains DEFAULT_DEPOSITEDCHEQUE_DATE
        defaultCourtCaseShouldBeFound("depositedchequeDate.contains=" + DEFAULT_DEPOSITEDCHEQUE_DATE);

        // Get all the courtCaseList where depositedchequeDate contains UPDATED_DEPOSITEDCHEQUE_DATE
        defaultCourtCaseShouldNotBeFound("depositedchequeDate.contains=" + UPDATED_DEPOSITEDCHEQUE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDepositedchequeDateNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where depositedchequeDate does not contain DEFAULT_DEPOSITEDCHEQUE_DATE
        defaultCourtCaseShouldNotBeFound("depositedchequeDate.doesNotContain=" + DEFAULT_DEPOSITEDCHEQUE_DATE);

        // Get all the courtCaseList where depositedchequeDate does not contain UPDATED_DEPOSITEDCHEQUE_DATE
        defaultCourtCaseShouldBeFound("depositedchequeDate.doesNotContain=" + UPDATED_DEPOSITEDCHEQUE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddInterestAmountDistCourtIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where addInterestAmountDistCourt equals to DEFAULT_ADD_INTEREST_AMOUNT_DIST_COURT
        defaultCourtCaseShouldBeFound("addInterestAmountDistCourt.equals=" + DEFAULT_ADD_INTEREST_AMOUNT_DIST_COURT);

        // Get all the courtCaseList where addInterestAmountDistCourt equals to UPDATED_ADD_INTEREST_AMOUNT_DIST_COURT
        defaultCourtCaseShouldNotBeFound("addInterestAmountDistCourt.equals=" + UPDATED_ADD_INTEREST_AMOUNT_DIST_COURT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddInterestAmountDistCourtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where addInterestAmountDistCourt not equals to DEFAULT_ADD_INTEREST_AMOUNT_DIST_COURT
        defaultCourtCaseShouldNotBeFound("addInterestAmountDistCourt.notEquals=" + DEFAULT_ADD_INTEREST_AMOUNT_DIST_COURT);

        // Get all the courtCaseList where addInterestAmountDistCourt not equals to UPDATED_ADD_INTEREST_AMOUNT_DIST_COURT
        defaultCourtCaseShouldBeFound("addInterestAmountDistCourt.notEquals=" + UPDATED_ADD_INTEREST_AMOUNT_DIST_COURT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddInterestAmountDistCourtIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where addInterestAmountDistCourt in DEFAULT_ADD_INTEREST_AMOUNT_DIST_COURT or UPDATED_ADD_INTEREST_AMOUNT_DIST_COURT
        defaultCourtCaseShouldBeFound(
            "addInterestAmountDistCourt.in=" + DEFAULT_ADD_INTEREST_AMOUNT_DIST_COURT + "," + UPDATED_ADD_INTEREST_AMOUNT_DIST_COURT
        );

        // Get all the courtCaseList where addInterestAmountDistCourt equals to UPDATED_ADD_INTEREST_AMOUNT_DIST_COURT
        defaultCourtCaseShouldNotBeFound("addInterestAmountDistCourt.in=" + UPDATED_ADD_INTEREST_AMOUNT_DIST_COURT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddInterestAmountDistCourtIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where addInterestAmountDistCourt is not null
        defaultCourtCaseShouldBeFound("addInterestAmountDistCourt.specified=true");

        // Get all the courtCaseList where addInterestAmountDistCourt is null
        defaultCourtCaseShouldNotBeFound("addInterestAmountDistCourt.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddInterestAmountDistCourtContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where addInterestAmountDistCourt contains DEFAULT_ADD_INTEREST_AMOUNT_DIST_COURT
        defaultCourtCaseShouldBeFound("addInterestAmountDistCourt.contains=" + DEFAULT_ADD_INTEREST_AMOUNT_DIST_COURT);

        // Get all the courtCaseList where addInterestAmountDistCourt contains UPDATED_ADD_INTEREST_AMOUNT_DIST_COURT
        defaultCourtCaseShouldNotBeFound("addInterestAmountDistCourt.contains=" + UPDATED_ADD_INTEREST_AMOUNT_DIST_COURT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddInterestAmountDistCourtNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where addInterestAmountDistCourt does not contain DEFAULT_ADD_INTEREST_AMOUNT_DIST_COURT
        defaultCourtCaseShouldNotBeFound("addInterestAmountDistCourt.doesNotContain=" + DEFAULT_ADD_INTEREST_AMOUNT_DIST_COURT);

        // Get all the courtCaseList where addInterestAmountDistCourt does not contain UPDATED_ADD_INTEREST_AMOUNT_DIST_COURT
        defaultCourtCaseShouldBeFound("addInterestAmountDistCourt.doesNotContain=" + UPDATED_ADD_INTEREST_AMOUNT_DIST_COURT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddInterestApplicationNoIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where addInterestApplicationNo equals to DEFAULT_ADD_INTEREST_APPLICATION_NO
        defaultCourtCaseShouldBeFound("addInterestApplicationNo.equals=" + DEFAULT_ADD_INTEREST_APPLICATION_NO);

        // Get all the courtCaseList where addInterestApplicationNo equals to UPDATED_ADD_INTEREST_APPLICATION_NO
        defaultCourtCaseShouldNotBeFound("addInterestApplicationNo.equals=" + UPDATED_ADD_INTEREST_APPLICATION_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddInterestApplicationNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where addInterestApplicationNo not equals to DEFAULT_ADD_INTEREST_APPLICATION_NO
        defaultCourtCaseShouldNotBeFound("addInterestApplicationNo.notEquals=" + DEFAULT_ADD_INTEREST_APPLICATION_NO);

        // Get all the courtCaseList where addInterestApplicationNo not equals to UPDATED_ADD_INTEREST_APPLICATION_NO
        defaultCourtCaseShouldBeFound("addInterestApplicationNo.notEquals=" + UPDATED_ADD_INTEREST_APPLICATION_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddInterestApplicationNoIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where addInterestApplicationNo in DEFAULT_ADD_INTEREST_APPLICATION_NO or UPDATED_ADD_INTEREST_APPLICATION_NO
        defaultCourtCaseShouldBeFound(
            "addInterestApplicationNo.in=" + DEFAULT_ADD_INTEREST_APPLICATION_NO + "," + UPDATED_ADD_INTEREST_APPLICATION_NO
        );

        // Get all the courtCaseList where addInterestApplicationNo equals to UPDATED_ADD_INTEREST_APPLICATION_NO
        defaultCourtCaseShouldNotBeFound("addInterestApplicationNo.in=" + UPDATED_ADD_INTEREST_APPLICATION_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddInterestApplicationNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where addInterestApplicationNo is not null
        defaultCourtCaseShouldBeFound("addInterestApplicationNo.specified=true");

        // Get all the courtCaseList where addInterestApplicationNo is null
        defaultCourtCaseShouldNotBeFound("addInterestApplicationNo.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddInterestApplicationNoContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where addInterestApplicationNo contains DEFAULT_ADD_INTEREST_APPLICATION_NO
        defaultCourtCaseShouldBeFound("addInterestApplicationNo.contains=" + DEFAULT_ADD_INTEREST_APPLICATION_NO);

        // Get all the courtCaseList where addInterestApplicationNo contains UPDATED_ADD_INTEREST_APPLICATION_NO
        defaultCourtCaseShouldNotBeFound("addInterestApplicationNo.contains=" + UPDATED_ADD_INTEREST_APPLICATION_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddInterestApplicationNoNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where addInterestApplicationNo does not contain DEFAULT_ADD_INTEREST_APPLICATION_NO
        defaultCourtCaseShouldNotBeFound("addInterestApplicationNo.doesNotContain=" + DEFAULT_ADD_INTEREST_APPLICATION_NO);

        // Get all the courtCaseList where addInterestApplicationNo does not contain UPDATED_ADD_INTEREST_APPLICATION_NO
        defaultCourtCaseShouldBeFound("addInterestApplicationNo.doesNotContain=" + UPDATED_ADD_INTEREST_APPLICATION_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddIntChequeNoIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where addIntChequeNo equals to DEFAULT_ADD_INT_CHEQUE_NO
        defaultCourtCaseShouldBeFound("addIntChequeNo.equals=" + DEFAULT_ADD_INT_CHEQUE_NO);

        // Get all the courtCaseList where addIntChequeNo equals to UPDATED_ADD_INT_CHEQUE_NO
        defaultCourtCaseShouldNotBeFound("addIntChequeNo.equals=" + UPDATED_ADD_INT_CHEQUE_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddIntChequeNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where addIntChequeNo not equals to DEFAULT_ADD_INT_CHEQUE_NO
        defaultCourtCaseShouldNotBeFound("addIntChequeNo.notEquals=" + DEFAULT_ADD_INT_CHEQUE_NO);

        // Get all the courtCaseList where addIntChequeNo not equals to UPDATED_ADD_INT_CHEQUE_NO
        defaultCourtCaseShouldBeFound("addIntChequeNo.notEquals=" + UPDATED_ADD_INT_CHEQUE_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddIntChequeNoIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where addIntChequeNo in DEFAULT_ADD_INT_CHEQUE_NO or UPDATED_ADD_INT_CHEQUE_NO
        defaultCourtCaseShouldBeFound("addIntChequeNo.in=" + DEFAULT_ADD_INT_CHEQUE_NO + "," + UPDATED_ADD_INT_CHEQUE_NO);

        // Get all the courtCaseList where addIntChequeNo equals to UPDATED_ADD_INT_CHEQUE_NO
        defaultCourtCaseShouldNotBeFound("addIntChequeNo.in=" + UPDATED_ADD_INT_CHEQUE_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddIntChequeNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where addIntChequeNo is not null
        defaultCourtCaseShouldBeFound("addIntChequeNo.specified=true");

        // Get all the courtCaseList where addIntChequeNo is null
        defaultCourtCaseShouldNotBeFound("addIntChequeNo.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddIntChequeNoContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where addIntChequeNo contains DEFAULT_ADD_INT_CHEQUE_NO
        defaultCourtCaseShouldBeFound("addIntChequeNo.contains=" + DEFAULT_ADD_INT_CHEQUE_NO);

        // Get all the courtCaseList where addIntChequeNo contains UPDATED_ADD_INT_CHEQUE_NO
        defaultCourtCaseShouldNotBeFound("addIntChequeNo.contains=" + UPDATED_ADD_INT_CHEQUE_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddIntChequeNoNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where addIntChequeNo does not contain DEFAULT_ADD_INT_CHEQUE_NO
        defaultCourtCaseShouldNotBeFound("addIntChequeNo.doesNotContain=" + DEFAULT_ADD_INT_CHEQUE_NO);

        // Get all the courtCaseList where addIntChequeNo does not contain UPDATED_ADD_INT_CHEQUE_NO
        defaultCourtCaseShouldBeFound("addIntChequeNo.doesNotContain=" + UPDATED_ADD_INT_CHEQUE_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddIntChequeDateIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where addIntChequeDate equals to DEFAULT_ADD_INT_CHEQUE_DATE
        defaultCourtCaseShouldBeFound("addIntChequeDate.equals=" + DEFAULT_ADD_INT_CHEQUE_DATE);

        // Get all the courtCaseList where addIntChequeDate equals to UPDATED_ADD_INT_CHEQUE_DATE
        defaultCourtCaseShouldNotBeFound("addIntChequeDate.equals=" + UPDATED_ADD_INT_CHEQUE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddIntChequeDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where addIntChequeDate not equals to DEFAULT_ADD_INT_CHEQUE_DATE
        defaultCourtCaseShouldNotBeFound("addIntChequeDate.notEquals=" + DEFAULT_ADD_INT_CHEQUE_DATE);

        // Get all the courtCaseList where addIntChequeDate not equals to UPDATED_ADD_INT_CHEQUE_DATE
        defaultCourtCaseShouldBeFound("addIntChequeDate.notEquals=" + UPDATED_ADD_INT_CHEQUE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddIntChequeDateIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where addIntChequeDate in DEFAULT_ADD_INT_CHEQUE_DATE or UPDATED_ADD_INT_CHEQUE_DATE
        defaultCourtCaseShouldBeFound("addIntChequeDate.in=" + DEFAULT_ADD_INT_CHEQUE_DATE + "," + UPDATED_ADD_INT_CHEQUE_DATE);

        // Get all the courtCaseList where addIntChequeDate equals to UPDATED_ADD_INT_CHEQUE_DATE
        defaultCourtCaseShouldNotBeFound("addIntChequeDate.in=" + UPDATED_ADD_INT_CHEQUE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddIntChequeDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where addIntChequeDate is not null
        defaultCourtCaseShouldBeFound("addIntChequeDate.specified=true");

        // Get all the courtCaseList where addIntChequeDate is null
        defaultCourtCaseShouldNotBeFound("addIntChequeDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddIntChequeDateContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where addIntChequeDate contains DEFAULT_ADD_INT_CHEQUE_DATE
        defaultCourtCaseShouldBeFound("addIntChequeDate.contains=" + DEFAULT_ADD_INT_CHEQUE_DATE);

        // Get all the courtCaseList where addIntChequeDate contains UPDATED_ADD_INT_CHEQUE_DATE
        defaultCourtCaseShouldNotBeFound("addIntChequeDate.contains=" + UPDATED_ADD_INT_CHEQUE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddIntChequeDateNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where addIntChequeDate does not contain DEFAULT_ADD_INT_CHEQUE_DATE
        defaultCourtCaseShouldNotBeFound("addIntChequeDate.doesNotContain=" + DEFAULT_ADD_INT_CHEQUE_DATE);

        // Get all the courtCaseList where addIntChequeDate does not contain UPDATED_ADD_INT_CHEQUE_DATE
        defaultCourtCaseShouldBeFound("addIntChequeDate.doesNotContain=" + UPDATED_ADD_INT_CHEQUE_DATE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByBankGuaranteeAppNoIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where bankGuaranteeAppNo equals to DEFAULT_BANK_GUARANTEE_APP_NO
        defaultCourtCaseShouldBeFound("bankGuaranteeAppNo.equals=" + DEFAULT_BANK_GUARANTEE_APP_NO);

        // Get all the courtCaseList where bankGuaranteeAppNo equals to UPDATED_BANK_GUARANTEE_APP_NO
        defaultCourtCaseShouldNotBeFound("bankGuaranteeAppNo.equals=" + UPDATED_BANK_GUARANTEE_APP_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByBankGuaranteeAppNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where bankGuaranteeAppNo not equals to DEFAULT_BANK_GUARANTEE_APP_NO
        defaultCourtCaseShouldNotBeFound("bankGuaranteeAppNo.notEquals=" + DEFAULT_BANK_GUARANTEE_APP_NO);

        // Get all the courtCaseList where bankGuaranteeAppNo not equals to UPDATED_BANK_GUARANTEE_APP_NO
        defaultCourtCaseShouldBeFound("bankGuaranteeAppNo.notEquals=" + UPDATED_BANK_GUARANTEE_APP_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByBankGuaranteeAppNoIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where bankGuaranteeAppNo in DEFAULT_BANK_GUARANTEE_APP_NO or UPDATED_BANK_GUARANTEE_APP_NO
        defaultCourtCaseShouldBeFound("bankGuaranteeAppNo.in=" + DEFAULT_BANK_GUARANTEE_APP_NO + "," + UPDATED_BANK_GUARANTEE_APP_NO);

        // Get all the courtCaseList where bankGuaranteeAppNo equals to UPDATED_BANK_GUARANTEE_APP_NO
        defaultCourtCaseShouldNotBeFound("bankGuaranteeAppNo.in=" + UPDATED_BANK_GUARANTEE_APP_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByBankGuaranteeAppNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where bankGuaranteeAppNo is not null
        defaultCourtCaseShouldBeFound("bankGuaranteeAppNo.specified=true");

        // Get all the courtCaseList where bankGuaranteeAppNo is null
        defaultCourtCaseShouldNotBeFound("bankGuaranteeAppNo.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByBankGuaranteeAppNoContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where bankGuaranteeAppNo contains DEFAULT_BANK_GUARANTEE_APP_NO
        defaultCourtCaseShouldBeFound("bankGuaranteeAppNo.contains=" + DEFAULT_BANK_GUARANTEE_APP_NO);

        // Get all the courtCaseList where bankGuaranteeAppNo contains UPDATED_BANK_GUARANTEE_APP_NO
        defaultCourtCaseShouldNotBeFound("bankGuaranteeAppNo.contains=" + UPDATED_BANK_GUARANTEE_APP_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByBankGuaranteeAppNoNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where bankGuaranteeAppNo does not contain DEFAULT_BANK_GUARANTEE_APP_NO
        defaultCourtCaseShouldNotBeFound("bankGuaranteeAppNo.doesNotContain=" + DEFAULT_BANK_GUARANTEE_APP_NO);

        // Get all the courtCaseList where bankGuaranteeAppNo does not contain UPDATED_BANK_GUARANTEE_APP_NO
        defaultCourtCaseShouldBeFound("bankGuaranteeAppNo.doesNotContain=" + UPDATED_BANK_GUARANTEE_APP_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCourtNameIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where courtName equals to DEFAULT_COURT_NAME
        defaultCourtCaseShouldBeFound("courtName.equals=" + DEFAULT_COURT_NAME);

        // Get all the courtCaseList where courtName equals to UPDATED_COURT_NAME
        defaultCourtCaseShouldNotBeFound("courtName.equals=" + UPDATED_COURT_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCourtNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where courtName not equals to DEFAULT_COURT_NAME
        defaultCourtCaseShouldNotBeFound("courtName.notEquals=" + DEFAULT_COURT_NAME);

        // Get all the courtCaseList where courtName not equals to UPDATED_COURT_NAME
        defaultCourtCaseShouldBeFound("courtName.notEquals=" + UPDATED_COURT_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCourtNameIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where courtName in DEFAULT_COURT_NAME or UPDATED_COURT_NAME
        defaultCourtCaseShouldBeFound("courtName.in=" + DEFAULT_COURT_NAME + "," + UPDATED_COURT_NAME);

        // Get all the courtCaseList where courtName equals to UPDATED_COURT_NAME
        defaultCourtCaseShouldNotBeFound("courtName.in=" + UPDATED_COURT_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCourtNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where courtName is not null
        defaultCourtCaseShouldBeFound("courtName.specified=true");

        // Get all the courtCaseList where courtName is null
        defaultCourtCaseShouldNotBeFound("courtName.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByCourtNameContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where courtName contains DEFAULT_COURT_NAME
        defaultCourtCaseShouldBeFound("courtName.contains=" + DEFAULT_COURT_NAME);

        // Get all the courtCaseList where courtName contains UPDATED_COURT_NAME
        defaultCourtCaseShouldNotBeFound("courtName.contains=" + UPDATED_COURT_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCourtNameNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where courtName does not contain DEFAULT_COURT_NAME
        defaultCourtCaseShouldNotBeFound("courtName.doesNotContain=" + DEFAULT_COURT_NAME);

        // Get all the courtCaseList where courtName does not contain UPDATED_COURT_NAME
        defaultCourtCaseShouldBeFound("courtName.doesNotContain=" + UPDATED_COURT_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCourtTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where courtType equals to DEFAULT_COURT_TYPE
        defaultCourtCaseShouldBeFound("courtType.equals=" + DEFAULT_COURT_TYPE);

        // Get all the courtCaseList where courtType equals to UPDATED_COURT_TYPE
        defaultCourtCaseShouldNotBeFound("courtType.equals=" + UPDATED_COURT_TYPE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCourtTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where courtType not equals to DEFAULT_COURT_TYPE
        defaultCourtCaseShouldNotBeFound("courtType.notEquals=" + DEFAULT_COURT_TYPE);

        // Get all the courtCaseList where courtType not equals to UPDATED_COURT_TYPE
        defaultCourtCaseShouldBeFound("courtType.notEquals=" + UPDATED_COURT_TYPE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCourtTypeIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where courtType in DEFAULT_COURT_TYPE or UPDATED_COURT_TYPE
        defaultCourtCaseShouldBeFound("courtType.in=" + DEFAULT_COURT_TYPE + "," + UPDATED_COURT_TYPE);

        // Get all the courtCaseList where courtType equals to UPDATED_COURT_TYPE
        defaultCourtCaseShouldNotBeFound("courtType.in=" + UPDATED_COURT_TYPE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCourtTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where courtType is not null
        defaultCourtCaseShouldBeFound("courtType.specified=true");

        // Get all the courtCaseList where courtType is null
        defaultCourtCaseShouldNotBeFound("courtType.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseType equals to DEFAULT_CASE_TYPE
        defaultCourtCaseShouldBeFound("caseType.equals=" + DEFAULT_CASE_TYPE);

        // Get all the courtCaseList where caseType equals to UPDATED_CASE_TYPE
        defaultCourtCaseShouldNotBeFound("caseType.equals=" + UPDATED_CASE_TYPE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseType not equals to DEFAULT_CASE_TYPE
        defaultCourtCaseShouldNotBeFound("caseType.notEquals=" + DEFAULT_CASE_TYPE);

        // Get all the courtCaseList where caseType not equals to UPDATED_CASE_TYPE
        defaultCourtCaseShouldBeFound("caseType.notEquals=" + UPDATED_CASE_TYPE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseTypeIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseType in DEFAULT_CASE_TYPE or UPDATED_CASE_TYPE
        defaultCourtCaseShouldBeFound("caseType.in=" + DEFAULT_CASE_TYPE + "," + UPDATED_CASE_TYPE);

        // Get all the courtCaseList where caseType equals to UPDATED_CASE_TYPE
        defaultCourtCaseShouldNotBeFound("caseType.in=" + UPDATED_CASE_TYPE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseType is not null
        defaultCourtCaseShouldBeFound("caseType.specified=true");

        // Get all the courtCaseList where caseType is null
        defaultCourtCaseShouldNotBeFound("caseType.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByIsActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where isActivated equals to DEFAULT_IS_ACTIVATED
        defaultCourtCaseShouldBeFound("isActivated.equals=" + DEFAULT_IS_ACTIVATED);

        // Get all the courtCaseList where isActivated equals to UPDATED_IS_ACTIVATED
        defaultCourtCaseShouldNotBeFound("isActivated.equals=" + UPDATED_IS_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllCourtCasesByIsActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where isActivated not equals to DEFAULT_IS_ACTIVATED
        defaultCourtCaseShouldNotBeFound("isActivated.notEquals=" + DEFAULT_IS_ACTIVATED);

        // Get all the courtCaseList where isActivated not equals to UPDATED_IS_ACTIVATED
        defaultCourtCaseShouldBeFound("isActivated.notEquals=" + UPDATED_IS_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllCourtCasesByIsActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where isActivated in DEFAULT_IS_ACTIVATED or UPDATED_IS_ACTIVATED
        defaultCourtCaseShouldBeFound("isActivated.in=" + DEFAULT_IS_ACTIVATED + "," + UPDATED_IS_ACTIVATED);

        // Get all the courtCaseList where isActivated equals to UPDATED_IS_ACTIVATED
        defaultCourtCaseShouldNotBeFound("isActivated.in=" + UPDATED_IS_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllCourtCasesByIsActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where isActivated is not null
        defaultCourtCaseShouldBeFound("isActivated.specified=true");

        // Get all the courtCaseList where isActivated is null
        defaultCourtCaseShouldNotBeFound("isActivated.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield1IsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield1 equals to DEFAULT_FREEFIELD_1
        defaultCourtCaseShouldBeFound("freefield1.equals=" + DEFAULT_FREEFIELD_1);

        // Get all the courtCaseList where freefield1 equals to UPDATED_FREEFIELD_1
        defaultCourtCaseShouldNotBeFound("freefield1.equals=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield1 not equals to DEFAULT_FREEFIELD_1
        defaultCourtCaseShouldNotBeFound("freefield1.notEquals=" + DEFAULT_FREEFIELD_1);

        // Get all the courtCaseList where freefield1 not equals to UPDATED_FREEFIELD_1
        defaultCourtCaseShouldBeFound("freefield1.notEquals=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield1IsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield1 in DEFAULT_FREEFIELD_1 or UPDATED_FREEFIELD_1
        defaultCourtCaseShouldBeFound("freefield1.in=" + DEFAULT_FREEFIELD_1 + "," + UPDATED_FREEFIELD_1);

        // Get all the courtCaseList where freefield1 equals to UPDATED_FREEFIELD_1
        defaultCourtCaseShouldNotBeFound("freefield1.in=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield1IsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield1 is not null
        defaultCourtCaseShouldBeFound("freefield1.specified=true");

        // Get all the courtCaseList where freefield1 is null
        defaultCourtCaseShouldNotBeFound("freefield1.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield1ContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield1 contains DEFAULT_FREEFIELD_1
        defaultCourtCaseShouldBeFound("freefield1.contains=" + DEFAULT_FREEFIELD_1);

        // Get all the courtCaseList where freefield1 contains UPDATED_FREEFIELD_1
        defaultCourtCaseShouldNotBeFound("freefield1.contains=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield1NotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield1 does not contain DEFAULT_FREEFIELD_1
        defaultCourtCaseShouldNotBeFound("freefield1.doesNotContain=" + DEFAULT_FREEFIELD_1);

        // Get all the courtCaseList where freefield1 does not contain UPDATED_FREEFIELD_1
        defaultCourtCaseShouldBeFound("freefield1.doesNotContain=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield2IsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield2 equals to DEFAULT_FREEFIELD_2
        defaultCourtCaseShouldBeFound("freefield2.equals=" + DEFAULT_FREEFIELD_2);

        // Get all the courtCaseList where freefield2 equals to UPDATED_FREEFIELD_2
        defaultCourtCaseShouldNotBeFound("freefield2.equals=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield2 not equals to DEFAULT_FREEFIELD_2
        defaultCourtCaseShouldNotBeFound("freefield2.notEquals=" + DEFAULT_FREEFIELD_2);

        // Get all the courtCaseList where freefield2 not equals to UPDATED_FREEFIELD_2
        defaultCourtCaseShouldBeFound("freefield2.notEquals=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield2IsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield2 in DEFAULT_FREEFIELD_2 or UPDATED_FREEFIELD_2
        defaultCourtCaseShouldBeFound("freefield2.in=" + DEFAULT_FREEFIELD_2 + "," + UPDATED_FREEFIELD_2);

        // Get all the courtCaseList where freefield2 equals to UPDATED_FREEFIELD_2
        defaultCourtCaseShouldNotBeFound("freefield2.in=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield2IsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield2 is not null
        defaultCourtCaseShouldBeFound("freefield2.specified=true");

        // Get all the courtCaseList where freefield2 is null
        defaultCourtCaseShouldNotBeFound("freefield2.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield2ContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield2 contains DEFAULT_FREEFIELD_2
        defaultCourtCaseShouldBeFound("freefield2.contains=" + DEFAULT_FREEFIELD_2);

        // Get all the courtCaseList where freefield2 contains UPDATED_FREEFIELD_2
        defaultCourtCaseShouldNotBeFound("freefield2.contains=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield2NotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield2 does not contain DEFAULT_FREEFIELD_2
        defaultCourtCaseShouldNotBeFound("freefield2.doesNotContain=" + DEFAULT_FREEFIELD_2);

        // Get all the courtCaseList where freefield2 does not contain UPDATED_FREEFIELD_2
        defaultCourtCaseShouldBeFound("freefield2.doesNotContain=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield3IsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield3 equals to DEFAULT_FREEFIELD_3
        defaultCourtCaseShouldBeFound("freefield3.equals=" + DEFAULT_FREEFIELD_3);

        // Get all the courtCaseList where freefield3 equals to UPDATED_FREEFIELD_3
        defaultCourtCaseShouldNotBeFound("freefield3.equals=" + UPDATED_FREEFIELD_3);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield3 not equals to DEFAULT_FREEFIELD_3
        defaultCourtCaseShouldNotBeFound("freefield3.notEquals=" + DEFAULT_FREEFIELD_3);

        // Get all the courtCaseList where freefield3 not equals to UPDATED_FREEFIELD_3
        defaultCourtCaseShouldBeFound("freefield3.notEquals=" + UPDATED_FREEFIELD_3);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield3IsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield3 in DEFAULT_FREEFIELD_3 or UPDATED_FREEFIELD_3
        defaultCourtCaseShouldBeFound("freefield3.in=" + DEFAULT_FREEFIELD_3 + "," + UPDATED_FREEFIELD_3);

        // Get all the courtCaseList where freefield3 equals to UPDATED_FREEFIELD_3
        defaultCourtCaseShouldNotBeFound("freefield3.in=" + UPDATED_FREEFIELD_3);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield3IsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield3 is not null
        defaultCourtCaseShouldBeFound("freefield3.specified=true");

        // Get all the courtCaseList where freefield3 is null
        defaultCourtCaseShouldNotBeFound("freefield3.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield3ContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield3 contains DEFAULT_FREEFIELD_3
        defaultCourtCaseShouldBeFound("freefield3.contains=" + DEFAULT_FREEFIELD_3);

        // Get all the courtCaseList where freefield3 contains UPDATED_FREEFIELD_3
        defaultCourtCaseShouldNotBeFound("freefield3.contains=" + UPDATED_FREEFIELD_3);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield3NotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield3 does not contain DEFAULT_FREEFIELD_3
        defaultCourtCaseShouldNotBeFound("freefield3.doesNotContain=" + DEFAULT_FREEFIELD_3);

        // Get all the courtCaseList where freefield3 does not contain UPDATED_FREEFIELD_3
        defaultCourtCaseShouldBeFound("freefield3.doesNotContain=" + UPDATED_FREEFIELD_3);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield4IsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield4 equals to DEFAULT_FREEFIELD_4
        defaultCourtCaseShouldBeFound("freefield4.equals=" + DEFAULT_FREEFIELD_4);

        // Get all the courtCaseList where freefield4 equals to UPDATED_FREEFIELD_4
        defaultCourtCaseShouldNotBeFound("freefield4.equals=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield4IsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield4 not equals to DEFAULT_FREEFIELD_4
        defaultCourtCaseShouldNotBeFound("freefield4.notEquals=" + DEFAULT_FREEFIELD_4);

        // Get all the courtCaseList where freefield4 not equals to UPDATED_FREEFIELD_4
        defaultCourtCaseShouldBeFound("freefield4.notEquals=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield4IsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield4 in DEFAULT_FREEFIELD_4 or UPDATED_FREEFIELD_4
        defaultCourtCaseShouldBeFound("freefield4.in=" + DEFAULT_FREEFIELD_4 + "," + UPDATED_FREEFIELD_4);

        // Get all the courtCaseList where freefield4 equals to UPDATED_FREEFIELD_4
        defaultCourtCaseShouldNotBeFound("freefield4.in=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield4IsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield4 is not null
        defaultCourtCaseShouldBeFound("freefield4.specified=true");

        // Get all the courtCaseList where freefield4 is null
        defaultCourtCaseShouldNotBeFound("freefield4.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield4ContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield4 contains DEFAULT_FREEFIELD_4
        defaultCourtCaseShouldBeFound("freefield4.contains=" + DEFAULT_FREEFIELD_4);

        // Get all the courtCaseList where freefield4 contains UPDATED_FREEFIELD_4
        defaultCourtCaseShouldNotBeFound("freefield4.contains=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield4NotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield4 does not contain DEFAULT_FREEFIELD_4
        defaultCourtCaseShouldNotBeFound("freefield4.doesNotContain=" + DEFAULT_FREEFIELD_4);

        // Get all the courtCaseList where freefield4 does not contain UPDATED_FREEFIELD_4
        defaultCourtCaseShouldBeFound("freefield4.doesNotContain=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield5IsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield5 equals to DEFAULT_FREEFIELD_5
        defaultCourtCaseShouldBeFound("freefield5.equals=" + DEFAULT_FREEFIELD_5);

        // Get all the courtCaseList where freefield5 equals to UPDATED_FREEFIELD_5
        defaultCourtCaseShouldNotBeFound("freefield5.equals=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield5IsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield5 not equals to DEFAULT_FREEFIELD_5
        defaultCourtCaseShouldNotBeFound("freefield5.notEquals=" + DEFAULT_FREEFIELD_5);

        // Get all the courtCaseList where freefield5 not equals to UPDATED_FREEFIELD_5
        defaultCourtCaseShouldBeFound("freefield5.notEquals=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield5IsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield5 in DEFAULT_FREEFIELD_5 or UPDATED_FREEFIELD_5
        defaultCourtCaseShouldBeFound("freefield5.in=" + DEFAULT_FREEFIELD_5 + "," + UPDATED_FREEFIELD_5);

        // Get all the courtCaseList where freefield5 equals to UPDATED_FREEFIELD_5
        defaultCourtCaseShouldNotBeFound("freefield5.in=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield5IsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield5 is not null
        defaultCourtCaseShouldBeFound("freefield5.specified=true");

        // Get all the courtCaseList where freefield5 is null
        defaultCourtCaseShouldNotBeFound("freefield5.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield5ContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield5 contains DEFAULT_FREEFIELD_5
        defaultCourtCaseShouldBeFound("freefield5.contains=" + DEFAULT_FREEFIELD_5);

        // Get all the courtCaseList where freefield5 contains UPDATED_FREEFIELD_5
        defaultCourtCaseShouldNotBeFound("freefield5.contains=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield5NotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield5 does not contain DEFAULT_FREEFIELD_5
        defaultCourtCaseShouldNotBeFound("freefield5.doesNotContain=" + DEFAULT_FREEFIELD_5);

        // Get all the courtCaseList where freefield5 does not contain UPDATED_FREEFIELD_5
        defaultCourtCaseShouldBeFound("freefield5.doesNotContain=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield6IsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield6 equals to DEFAULT_FREEFIELD_6
        defaultCourtCaseShouldBeFound("freefield6.equals=" + DEFAULT_FREEFIELD_6);

        // Get all the courtCaseList where freefield6 equals to UPDATED_FREEFIELD_6
        defaultCourtCaseShouldNotBeFound("freefield6.equals=" + UPDATED_FREEFIELD_6);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield6IsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield6 not equals to DEFAULT_FREEFIELD_6
        defaultCourtCaseShouldNotBeFound("freefield6.notEquals=" + DEFAULT_FREEFIELD_6);

        // Get all the courtCaseList where freefield6 not equals to UPDATED_FREEFIELD_6
        defaultCourtCaseShouldBeFound("freefield6.notEquals=" + UPDATED_FREEFIELD_6);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield6IsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield6 in DEFAULT_FREEFIELD_6 or UPDATED_FREEFIELD_6
        defaultCourtCaseShouldBeFound("freefield6.in=" + DEFAULT_FREEFIELD_6 + "," + UPDATED_FREEFIELD_6);

        // Get all the courtCaseList where freefield6 equals to UPDATED_FREEFIELD_6
        defaultCourtCaseShouldNotBeFound("freefield6.in=" + UPDATED_FREEFIELD_6);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield6IsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield6 is not null
        defaultCourtCaseShouldBeFound("freefield6.specified=true");

        // Get all the courtCaseList where freefield6 is null
        defaultCourtCaseShouldNotBeFound("freefield6.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield6ContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield6 contains DEFAULT_FREEFIELD_6
        defaultCourtCaseShouldBeFound("freefield6.contains=" + DEFAULT_FREEFIELD_6);

        // Get all the courtCaseList where freefield6 contains UPDATED_FREEFIELD_6
        defaultCourtCaseShouldNotBeFound("freefield6.contains=" + UPDATED_FREEFIELD_6);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield6NotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield6 does not contain DEFAULT_FREEFIELD_6
        defaultCourtCaseShouldNotBeFound("freefield6.doesNotContain=" + DEFAULT_FREEFIELD_6);

        // Get all the courtCaseList where freefield6 does not contain UPDATED_FREEFIELD_6
        defaultCourtCaseShouldBeFound("freefield6.doesNotContain=" + UPDATED_FREEFIELD_6);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield7IsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield7 equals to DEFAULT_FREEFIELD_7
        defaultCourtCaseShouldBeFound("freefield7.equals=" + DEFAULT_FREEFIELD_7);

        // Get all the courtCaseList where freefield7 equals to UPDATED_FREEFIELD_7
        defaultCourtCaseShouldNotBeFound("freefield7.equals=" + UPDATED_FREEFIELD_7);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield7IsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield7 not equals to DEFAULT_FREEFIELD_7
        defaultCourtCaseShouldNotBeFound("freefield7.notEquals=" + DEFAULT_FREEFIELD_7);

        // Get all the courtCaseList where freefield7 not equals to UPDATED_FREEFIELD_7
        defaultCourtCaseShouldBeFound("freefield7.notEquals=" + UPDATED_FREEFIELD_7);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield7IsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield7 in DEFAULT_FREEFIELD_7 or UPDATED_FREEFIELD_7
        defaultCourtCaseShouldBeFound("freefield7.in=" + DEFAULT_FREEFIELD_7 + "," + UPDATED_FREEFIELD_7);

        // Get all the courtCaseList where freefield7 equals to UPDATED_FREEFIELD_7
        defaultCourtCaseShouldNotBeFound("freefield7.in=" + UPDATED_FREEFIELD_7);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield7IsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield7 is not null
        defaultCourtCaseShouldBeFound("freefield7.specified=true");

        // Get all the courtCaseList where freefield7 is null
        defaultCourtCaseShouldNotBeFound("freefield7.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield7ContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield7 contains DEFAULT_FREEFIELD_7
        defaultCourtCaseShouldBeFound("freefield7.contains=" + DEFAULT_FREEFIELD_7);

        // Get all the courtCaseList where freefield7 contains UPDATED_FREEFIELD_7
        defaultCourtCaseShouldNotBeFound("freefield7.contains=" + UPDATED_FREEFIELD_7);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield7NotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield7 does not contain DEFAULT_FREEFIELD_7
        defaultCourtCaseShouldNotBeFound("freefield7.doesNotContain=" + DEFAULT_FREEFIELD_7);

        // Get all the courtCaseList where freefield7 does not contain UPDATED_FREEFIELD_7
        defaultCourtCaseShouldBeFound("freefield7.doesNotContain=" + UPDATED_FREEFIELD_7);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield8IsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield8 equals to DEFAULT_FREEFIELD_8
        defaultCourtCaseShouldBeFound("freefield8.equals=" + DEFAULT_FREEFIELD_8);

        // Get all the courtCaseList where freefield8 equals to UPDATED_FREEFIELD_8
        defaultCourtCaseShouldNotBeFound("freefield8.equals=" + UPDATED_FREEFIELD_8);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield8IsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield8 not equals to DEFAULT_FREEFIELD_8
        defaultCourtCaseShouldNotBeFound("freefield8.notEquals=" + DEFAULT_FREEFIELD_8);

        // Get all the courtCaseList where freefield8 not equals to UPDATED_FREEFIELD_8
        defaultCourtCaseShouldBeFound("freefield8.notEquals=" + UPDATED_FREEFIELD_8);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield8IsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield8 in DEFAULT_FREEFIELD_8 or UPDATED_FREEFIELD_8
        defaultCourtCaseShouldBeFound("freefield8.in=" + DEFAULT_FREEFIELD_8 + "," + UPDATED_FREEFIELD_8);

        // Get all the courtCaseList where freefield8 equals to UPDATED_FREEFIELD_8
        defaultCourtCaseShouldNotBeFound("freefield8.in=" + UPDATED_FREEFIELD_8);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield8IsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield8 is not null
        defaultCourtCaseShouldBeFound("freefield8.specified=true");

        // Get all the courtCaseList where freefield8 is null
        defaultCourtCaseShouldNotBeFound("freefield8.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield8ContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield8 contains DEFAULT_FREEFIELD_8
        defaultCourtCaseShouldBeFound("freefield8.contains=" + DEFAULT_FREEFIELD_8);

        // Get all the courtCaseList where freefield8 contains UPDATED_FREEFIELD_8
        defaultCourtCaseShouldNotBeFound("freefield8.contains=" + UPDATED_FREEFIELD_8);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield8NotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield8 does not contain DEFAULT_FREEFIELD_8
        defaultCourtCaseShouldNotBeFound("freefield8.doesNotContain=" + DEFAULT_FREEFIELD_8);

        // Get all the courtCaseList where freefield8 does not contain UPDATED_FREEFIELD_8
        defaultCourtCaseShouldBeFound("freefield8.doesNotContain=" + UPDATED_FREEFIELD_8);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield9IsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield9 equals to DEFAULT_FREEFIELD_9
        defaultCourtCaseShouldBeFound("freefield9.equals=" + DEFAULT_FREEFIELD_9);

        // Get all the courtCaseList where freefield9 equals to UPDATED_FREEFIELD_9
        defaultCourtCaseShouldNotBeFound("freefield9.equals=" + UPDATED_FREEFIELD_9);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield9IsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield9 not equals to DEFAULT_FREEFIELD_9
        defaultCourtCaseShouldNotBeFound("freefield9.notEquals=" + DEFAULT_FREEFIELD_9);

        // Get all the courtCaseList where freefield9 not equals to UPDATED_FREEFIELD_9
        defaultCourtCaseShouldBeFound("freefield9.notEquals=" + UPDATED_FREEFIELD_9);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield9IsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield9 in DEFAULT_FREEFIELD_9 or UPDATED_FREEFIELD_9
        defaultCourtCaseShouldBeFound("freefield9.in=" + DEFAULT_FREEFIELD_9 + "," + UPDATED_FREEFIELD_9);

        // Get all the courtCaseList where freefield9 equals to UPDATED_FREEFIELD_9
        defaultCourtCaseShouldNotBeFound("freefield9.in=" + UPDATED_FREEFIELD_9);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield9IsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield9 is not null
        defaultCourtCaseShouldBeFound("freefield9.specified=true");

        // Get all the courtCaseList where freefield9 is null
        defaultCourtCaseShouldNotBeFound("freefield9.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield9ContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield9 contains DEFAULT_FREEFIELD_9
        defaultCourtCaseShouldBeFound("freefield9.contains=" + DEFAULT_FREEFIELD_9);

        // Get all the courtCaseList where freefield9 contains UPDATED_FREEFIELD_9
        defaultCourtCaseShouldNotBeFound("freefield9.contains=" + UPDATED_FREEFIELD_9);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield9NotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield9 does not contain DEFAULT_FREEFIELD_9
        defaultCourtCaseShouldNotBeFound("freefield9.doesNotContain=" + DEFAULT_FREEFIELD_9);

        // Get all the courtCaseList where freefield9 does not contain UPDATED_FREEFIELD_9
        defaultCourtCaseShouldBeFound("freefield9.doesNotContain=" + UPDATED_FREEFIELD_9);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield10IsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield10 equals to DEFAULT_FREEFIELD_10
        defaultCourtCaseShouldBeFound("freefield10.equals=" + DEFAULT_FREEFIELD_10);

        // Get all the courtCaseList where freefield10 equals to UPDATED_FREEFIELD_10
        defaultCourtCaseShouldNotBeFound("freefield10.equals=" + UPDATED_FREEFIELD_10);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield10IsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield10 not equals to DEFAULT_FREEFIELD_10
        defaultCourtCaseShouldNotBeFound("freefield10.notEquals=" + DEFAULT_FREEFIELD_10);

        // Get all the courtCaseList where freefield10 not equals to UPDATED_FREEFIELD_10
        defaultCourtCaseShouldBeFound("freefield10.notEquals=" + UPDATED_FREEFIELD_10);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield10IsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield10 in DEFAULT_FREEFIELD_10 or UPDATED_FREEFIELD_10
        defaultCourtCaseShouldBeFound("freefield10.in=" + DEFAULT_FREEFIELD_10 + "," + UPDATED_FREEFIELD_10);

        // Get all the courtCaseList where freefield10 equals to UPDATED_FREEFIELD_10
        defaultCourtCaseShouldNotBeFound("freefield10.in=" + UPDATED_FREEFIELD_10);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield10IsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield10 is not null
        defaultCourtCaseShouldBeFound("freefield10.specified=true");

        // Get all the courtCaseList where freefield10 is null
        defaultCourtCaseShouldNotBeFound("freefield10.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield10ContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield10 contains DEFAULT_FREEFIELD_10
        defaultCourtCaseShouldBeFound("freefield10.contains=" + DEFAULT_FREEFIELD_10);

        // Get all the courtCaseList where freefield10 contains UPDATED_FREEFIELD_10
        defaultCourtCaseShouldNotBeFound("freefield10.contains=" + UPDATED_FREEFIELD_10);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFreefield10NotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where freefield10 does not contain DEFAULT_FREEFIELD_10
        defaultCourtCaseShouldNotBeFound("freefield10.doesNotContain=" + DEFAULT_FREEFIELD_10);

        // Get all the courtCaseList where freefield10 does not contain UPDATED_FREEFIELD_10
        defaultCourtCaseShouldBeFound("freefield10.doesNotContain=" + UPDATED_FREEFIELD_10);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultCourtCaseShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the courtCaseList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultCourtCaseShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultCourtCaseShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the courtCaseList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultCourtCaseShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultCourtCaseShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the courtCaseList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultCourtCaseShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastModifiedBy is not null
        defaultCourtCaseShouldBeFound("lastModifiedBy.specified=true");

        // Get all the courtCaseList where lastModifiedBy is null
        defaultCourtCaseShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultCourtCaseShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the courtCaseList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultCourtCaseShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultCourtCaseShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the courtCaseList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultCourtCaseShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultCourtCaseShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the courtCaseList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultCourtCaseShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultCourtCaseShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the courtCaseList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultCourtCaseShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultCourtCaseShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the courtCaseList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultCourtCaseShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastModified is not null
        defaultCourtCaseShouldBeFound("lastModified.specified=true");

        // Get all the courtCaseList where lastModified is null
        defaultCourtCaseShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultCourtCaseShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the courtCaseList where lastModified contains UPDATED_LAST_MODIFIED
        defaultCourtCaseShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultCourtCaseShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the courtCaseList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultCourtCaseShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCourtCasesByOrganizationIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);
        Organization organization;
        if (TestUtil.findAll(em, Organization.class).isEmpty()) {
            organization = OrganizationResourceIT.createEntity(em);
            em.persist(organization);
            em.flush();
        } else {
            organization = TestUtil.findAll(em, Organization.class).get(0);
        }
        em.persist(organization);
        em.flush();
        courtCase.setOrganization(organization);
        courtCaseRepository.saveAndFlush(courtCase);
        Long organizationId = organization.getId();

        // Get all the courtCaseList where organization equals to organizationId
        defaultCourtCaseShouldBeFound("organizationId.equals=" + organizationId);

        // Get all the courtCaseList where organization equals to (organizationId + 1)
        defaultCourtCaseShouldNotBeFound("organizationId.equals=" + (organizationId + 1));
    }

    @Test
    @Transactional
    void getAllCourtCasesByLawyerDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);
        LawyerDetails lawyerDetails;
        if (TestUtil.findAll(em, LawyerDetails.class).isEmpty()) {
            lawyerDetails = LawyerDetailsResourceIT.createEntity(em);
            em.persist(lawyerDetails);
            em.flush();
        } else {
            lawyerDetails = TestUtil.findAll(em, LawyerDetails.class).get(0);
        }
        em.persist(lawyerDetails);
        em.flush();
        courtCase.addLawyerDetails(lawyerDetails);
        courtCaseRepository.saveAndFlush(courtCase);
        Long lawyerDetailsId = lawyerDetails.getId();

        // Get all the courtCaseList where lawyerDetails equals to lawyerDetailsId
        defaultCourtCaseShouldBeFound("lawyerDetailsId.equals=" + lawyerDetailsId);

        // Get all the courtCaseList where lawyerDetails equals to (lawyerDetailsId + 1)
        defaultCourtCaseShouldNotBeFound("lawyerDetailsId.equals=" + (lawyerDetailsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCourtCaseShouldBeFound(String filter) throws Exception {
        restCourtCaseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courtCase.getId().intValue())))
            .andExpect(jsonPath("$.[*].landReferenceNo").value(hasItem(DEFAULT_LAND_REFERENCE_NO)))
            .andExpect(jsonPath("$.[*].caseNo").value(hasItem(DEFAULT_CASE_NO)))
            .andExpect(jsonPath("$.[*].villageName").value(hasItem(DEFAULT_VILLAGE_NAME)))
            .andExpect(jsonPath("$.[*].accuserName").value(hasItem(DEFAULT_ACCUSER_NAME)))
            .andExpect(jsonPath("$.[*].defendantName").value(hasItem(DEFAULT_DEFENDANT_NAME)))
            .andExpect(jsonPath("$.[*].accuserLawyer").value(hasItem(DEFAULT_ACCUSER_LAWYER)))
            .andExpect(jsonPath("$.[*].defendantLawyer").value(hasItem(DEFAULT_DEFENDANT_LAWYER)))
            .andExpect(jsonPath("$.[*].caseOfficer").value(hasItem(DEFAULT_CASE_OFFICER)))
            .andExpect(jsonPath("$.[*].caseOfficerAdd").value(hasItem(DEFAULT_CASE_OFFICER_ADD)))
            .andExpect(jsonPath("$.[*].totalArea").value(hasItem(DEFAULT_TOTAL_AREA)))
            .andExpect(jsonPath("$.[*].landAcquisitionOfficerNo").value(hasItem(DEFAULT_LAND_ACQUISITION_OFFICER_NO)))
            .andExpect(jsonPath("$.[*].section11JudgmentNo").value(hasItem(DEFAULT_SECTION_11_JUDGMENT_NO)))
            .andExpect(jsonPath("$.[*].section4NoticeDate").value(hasItem(DEFAULT_SECTION_4_NOTICE_DATE)))
            .andExpect(jsonPath("$.[*].judgementDate").value(hasItem(DEFAULT_JUDGEMENT_DATE)))
            .andExpect(jsonPath("$.[*].dateOfDecision").value(hasItem(DEFAULT_DATE_OF_DECISION)))
            .andExpect(jsonPath("$.[*].caseFilingDate").value(hasItem(DEFAULT_CASE_FILING_DATE)))
            .andExpect(jsonPath("$.[*].caseStatus").value(hasItem(DEFAULT_CASE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lastHearingDate").value(hasItem(DEFAULT_LAST_HEARING_DATE)))
            .andExpect(jsonPath("$.[*].nextHearingDate").value(hasItem(DEFAULT_NEXT_HEARING_DATE)))
            .andExpect(jsonPath("$.[*].natureResult").value(hasItem(DEFAULT_NATURE_RESULT.toString())))
            .andExpect(jsonPath("$.[*].amountDepositeInCourt").value(hasItem(DEFAULT_AMOUNT_DEPOSITE_IN_COURT)))
            .andExpect(jsonPath("$.[*].claimAmount").value(hasItem(DEFAULT_CLAIM_AMOUNT)))
            .andExpect(jsonPath("$.[*].depositedChequeNo").value(hasItem(DEFAULT_DEPOSITED_CHEQUE_NO)))
            .andExpect(jsonPath("$.[*].depositedchequeDate").value(hasItem(DEFAULT_DEPOSITEDCHEQUE_DATE)))
            .andExpect(jsonPath("$.[*].addInterestAmountDistCourt").value(hasItem(DEFAULT_ADD_INTEREST_AMOUNT_DIST_COURT)))
            .andExpect(jsonPath("$.[*].addInterestApplicationNo").value(hasItem(DEFAULT_ADD_INTEREST_APPLICATION_NO)))
            .andExpect(jsonPath("$.[*].addIntChequeNo").value(hasItem(DEFAULT_ADD_INT_CHEQUE_NO)))
            .andExpect(jsonPath("$.[*].addIntChequeDate").value(hasItem(DEFAULT_ADD_INT_CHEQUE_DATE)))
            .andExpect(jsonPath("$.[*].bankGuaranteeAppNo").value(hasItem(DEFAULT_BANK_GUARANTEE_APP_NO)))
            .andExpect(jsonPath("$.[*].courtName").value(hasItem(DEFAULT_COURT_NAME)))
            .andExpect(jsonPath("$.[*].courtType").value(hasItem(DEFAULT_COURT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].caseType").value(hasItem(DEFAULT_CASE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isActivated").value(hasItem(DEFAULT_IS_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].freefield1").value(hasItem(DEFAULT_FREEFIELD_1)))
            .andExpect(jsonPath("$.[*].freefield2").value(hasItem(DEFAULT_FREEFIELD_2)))
            .andExpect(jsonPath("$.[*].freefield3").value(hasItem(DEFAULT_FREEFIELD_3)))
            .andExpect(jsonPath("$.[*].freefield4").value(hasItem(DEFAULT_FREEFIELD_4)))
            .andExpect(jsonPath("$.[*].freefield5").value(hasItem(DEFAULT_FREEFIELD_5)))
            .andExpect(jsonPath("$.[*].freefield6").value(hasItem(DEFAULT_FREEFIELD_6)))
            .andExpect(jsonPath("$.[*].freefield7").value(hasItem(DEFAULT_FREEFIELD_7)))
            .andExpect(jsonPath("$.[*].freefield8").value(hasItem(DEFAULT_FREEFIELD_8)))
            .andExpect(jsonPath("$.[*].freefield9").value(hasItem(DEFAULT_FREEFIELD_9)))
            .andExpect(jsonPath("$.[*].freefield10").value(hasItem(DEFAULT_FREEFIELD_10)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)));

        // Check, that the count call also returns 1
        restCourtCaseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCourtCaseShouldNotBeFound(String filter) throws Exception {
        restCourtCaseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCourtCaseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCourtCase() throws Exception {
        // Get the courtCase
        restCourtCaseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCourtCase() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();

        // Update the courtCase
        CourtCase updatedCourtCase = courtCaseRepository.findById(courtCase.getId()).get();
        // Disconnect from session so that the updates on updatedCourtCase are not directly saved in db
        em.detach(updatedCourtCase);
        updatedCourtCase
            .landReferenceNo(UPDATED_LAND_REFERENCE_NO)
            .caseNo(UPDATED_CASE_NO)
            .villageName(UPDATED_VILLAGE_NAME)
            .accuserName(UPDATED_ACCUSER_NAME)
            .defendantName(UPDATED_DEFENDANT_NAME)
            .accuserLawyer(UPDATED_ACCUSER_LAWYER)
            .defendantLawyer(UPDATED_DEFENDANT_LAWYER)
            .caseOfficer(UPDATED_CASE_OFFICER)
            .caseOfficerAdd(UPDATED_CASE_OFFICER_ADD)
            .totalArea(UPDATED_TOTAL_AREA)
            .landAcquisitionOfficerNo(UPDATED_LAND_ACQUISITION_OFFICER_NO)
            .section11JudgmentNo(UPDATED_SECTION_11_JUDGMENT_NO)
            .section4NoticeDate(UPDATED_SECTION_4_NOTICE_DATE)
            .judgementDate(UPDATED_JUDGEMENT_DATE)
            .dateOfDecision(UPDATED_DATE_OF_DECISION)
            .caseFilingDate(UPDATED_CASE_FILING_DATE)
            .caseStatus(UPDATED_CASE_STATUS)
            .lastHearingDate(UPDATED_LAST_HEARING_DATE)
            .nextHearingDate(UPDATED_NEXT_HEARING_DATE)
            .natureResult(UPDATED_NATURE_RESULT)
            .amountDepositeInCourt(UPDATED_AMOUNT_DEPOSITE_IN_COURT)
            .claimAmount(UPDATED_CLAIM_AMOUNT)
            .depositedChequeNo(UPDATED_DEPOSITED_CHEQUE_NO)
            .depositedchequeDate(UPDATED_DEPOSITEDCHEQUE_DATE)
            .addInterestAmountDistCourt(UPDATED_ADD_INTEREST_AMOUNT_DIST_COURT)
            .addInterestApplicationNo(UPDATED_ADD_INTEREST_APPLICATION_NO)
            .addIntChequeNo(UPDATED_ADD_INT_CHEQUE_NO)
            .addIntChequeDate(UPDATED_ADD_INT_CHEQUE_DATE)
            .bankGuaranteeAppNo(UPDATED_BANK_GUARANTEE_APP_NO)
            .courtName(UPDATED_COURT_NAME)
            .courtType(UPDATED_COURT_TYPE)
            .caseType(UPDATED_CASE_TYPE)
            .isActivated(UPDATED_IS_ACTIVATED)
            .freefield1(UPDATED_FREEFIELD_1)
            .freefield2(UPDATED_FREEFIELD_2)
            .freefield3(UPDATED_FREEFIELD_3)
            .freefield4(UPDATED_FREEFIELD_4)
            .freefield5(UPDATED_FREEFIELD_5)
            .freefield6(UPDATED_FREEFIELD_6)
            .freefield7(UPDATED_FREEFIELD_7)
            .freefield8(UPDATED_FREEFIELD_8)
            .freefield9(UPDATED_FREEFIELD_9)
            .freefield10(UPDATED_FREEFIELD_10)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModified(UPDATED_LAST_MODIFIED);
        CourtCaseDTO courtCaseDTO = courtCaseMapper.toDto(updatedCourtCase);

        restCourtCaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courtCaseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courtCaseDTO))
            )
            .andExpect(status().isOk());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
        CourtCase testCourtCase = courtCaseList.get(courtCaseList.size() - 1);
        assertThat(testCourtCase.getLandReferenceNo()).isEqualTo(UPDATED_LAND_REFERENCE_NO);
        assertThat(testCourtCase.getCaseNo()).isEqualTo(UPDATED_CASE_NO);
        assertThat(testCourtCase.getVillageName()).isEqualTo(UPDATED_VILLAGE_NAME);
        assertThat(testCourtCase.getAccuserName()).isEqualTo(UPDATED_ACCUSER_NAME);
        assertThat(testCourtCase.getDefendantName()).isEqualTo(UPDATED_DEFENDANT_NAME);
        assertThat(testCourtCase.getAccuserLawyer()).isEqualTo(UPDATED_ACCUSER_LAWYER);
        assertThat(testCourtCase.getDefendantLawyer()).isEqualTo(UPDATED_DEFENDANT_LAWYER);
        assertThat(testCourtCase.getCaseOfficer()).isEqualTo(UPDATED_CASE_OFFICER);
        assertThat(testCourtCase.getCaseOfficerAdd()).isEqualTo(UPDATED_CASE_OFFICER_ADD);
        assertThat(testCourtCase.getTotalArea()).isEqualTo(UPDATED_TOTAL_AREA);
        assertThat(testCourtCase.getLandAcquisitionOfficerNo()).isEqualTo(UPDATED_LAND_ACQUISITION_OFFICER_NO);
        assertThat(testCourtCase.getSection11JudgmentNo()).isEqualTo(UPDATED_SECTION_11_JUDGMENT_NO);
        assertThat(testCourtCase.getSection4NoticeDate()).isEqualTo(UPDATED_SECTION_4_NOTICE_DATE);
        assertThat(testCourtCase.getJudgementDate()).isEqualTo(UPDATED_JUDGEMENT_DATE);
        assertThat(testCourtCase.getDateOfDecision()).isEqualTo(UPDATED_DATE_OF_DECISION);
        assertThat(testCourtCase.getCaseFilingDate()).isEqualTo(UPDATED_CASE_FILING_DATE);
        assertThat(testCourtCase.getCaseStatus()).isEqualTo(UPDATED_CASE_STATUS);
        assertThat(testCourtCase.getLastHearingDate()).isEqualTo(UPDATED_LAST_HEARING_DATE);
        assertThat(testCourtCase.getNextHearingDate()).isEqualTo(UPDATED_NEXT_HEARING_DATE);
        assertThat(testCourtCase.getNatureResult()).isEqualTo(UPDATED_NATURE_RESULT);
        assertThat(testCourtCase.getAmountDepositeInCourt()).isEqualTo(UPDATED_AMOUNT_DEPOSITE_IN_COURT);
        assertThat(testCourtCase.getClaimAmount()).isEqualTo(UPDATED_CLAIM_AMOUNT);
        assertThat(testCourtCase.getDepositedChequeNo()).isEqualTo(UPDATED_DEPOSITED_CHEQUE_NO);
        assertThat(testCourtCase.getDepositedchequeDate()).isEqualTo(UPDATED_DEPOSITEDCHEQUE_DATE);
        assertThat(testCourtCase.getAddInterestAmountDistCourt()).isEqualTo(UPDATED_ADD_INTEREST_AMOUNT_DIST_COURT);
        assertThat(testCourtCase.getAddInterestApplicationNo()).isEqualTo(UPDATED_ADD_INTEREST_APPLICATION_NO);
        assertThat(testCourtCase.getAddIntChequeNo()).isEqualTo(UPDATED_ADD_INT_CHEQUE_NO);
        assertThat(testCourtCase.getAddIntChequeDate()).isEqualTo(UPDATED_ADD_INT_CHEQUE_DATE);
        assertThat(testCourtCase.getBankGuaranteeAppNo()).isEqualTo(UPDATED_BANK_GUARANTEE_APP_NO);
        assertThat(testCourtCase.getCourtName()).isEqualTo(UPDATED_COURT_NAME);
        assertThat(testCourtCase.getCourtType()).isEqualTo(UPDATED_COURT_TYPE);
        assertThat(testCourtCase.getCaseType()).isEqualTo(UPDATED_CASE_TYPE);
        assertThat(testCourtCase.getIsActivated()).isEqualTo(UPDATED_IS_ACTIVATED);
        assertThat(testCourtCase.getFreefield1()).isEqualTo(UPDATED_FREEFIELD_1);
        assertThat(testCourtCase.getFreefield2()).isEqualTo(UPDATED_FREEFIELD_2);
        assertThat(testCourtCase.getFreefield3()).isEqualTo(UPDATED_FREEFIELD_3);
        assertThat(testCourtCase.getFreefield4()).isEqualTo(UPDATED_FREEFIELD_4);
        assertThat(testCourtCase.getFreefield5()).isEqualTo(UPDATED_FREEFIELD_5);
        assertThat(testCourtCase.getFreefield6()).isEqualTo(UPDATED_FREEFIELD_6);
        assertThat(testCourtCase.getFreefield7()).isEqualTo(UPDATED_FREEFIELD_7);
        assertThat(testCourtCase.getFreefield8()).isEqualTo(UPDATED_FREEFIELD_8);
        assertThat(testCourtCase.getFreefield9()).isEqualTo(UPDATED_FREEFIELD_9);
        assertThat(testCourtCase.getFreefield10()).isEqualTo(UPDATED_FREEFIELD_10);
        assertThat(testCourtCase.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testCourtCase.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void putNonExistingCourtCase() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();
        courtCase.setId(count.incrementAndGet());

        // Create the CourtCase
        CourtCaseDTO courtCaseDTO = courtCaseMapper.toDto(courtCase);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourtCaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courtCaseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courtCaseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourtCase() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();
        courtCase.setId(count.incrementAndGet());

        // Create the CourtCase
        CourtCaseDTO courtCaseDTO = courtCaseMapper.toDto(courtCase);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourtCaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courtCaseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourtCase() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();
        courtCase.setId(count.incrementAndGet());

        // Create the CourtCase
        CourtCaseDTO courtCaseDTO = courtCaseMapper.toDto(courtCase);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourtCaseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courtCaseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCourtCaseWithPatch() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();

        // Update the courtCase using partial update
        CourtCase partialUpdatedCourtCase = new CourtCase();
        partialUpdatedCourtCase.setId(courtCase.getId());

        partialUpdatedCourtCase
            .accuserName(UPDATED_ACCUSER_NAME)
            .caseOfficerAdd(UPDATED_CASE_OFFICER_ADD)
            .totalArea(UPDATED_TOTAL_AREA)
            .landAcquisitionOfficerNo(UPDATED_LAND_ACQUISITION_OFFICER_NO)
            .section11JudgmentNo(UPDATED_SECTION_11_JUDGMENT_NO)
            .dateOfDecision(UPDATED_DATE_OF_DECISION)
            .nextHearingDate(UPDATED_NEXT_HEARING_DATE)
            .natureResult(UPDATED_NATURE_RESULT)
            .amountDepositeInCourt(UPDATED_AMOUNT_DEPOSITE_IN_COURT)
            .depositedchequeDate(UPDATED_DEPOSITEDCHEQUE_DATE)
            .addInterestAmountDistCourt(UPDATED_ADD_INTEREST_AMOUNT_DIST_COURT)
            .addIntChequeNo(UPDATED_ADD_INT_CHEQUE_NO)
            .addIntChequeDate(UPDATED_ADD_INT_CHEQUE_DATE)
            .courtName(UPDATED_COURT_NAME)
            .caseType(UPDATED_CASE_TYPE)
            .freefield1(UPDATED_FREEFIELD_1)
            .freefield3(UPDATED_FREEFIELD_3)
            .freefield4(UPDATED_FREEFIELD_4)
            .freefield5(UPDATED_FREEFIELD_5)
            .freefield7(UPDATED_FREEFIELD_7)
            .freefield8(UPDATED_FREEFIELD_8);

        restCourtCaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourtCase.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourtCase))
            )
            .andExpect(status().isOk());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
        CourtCase testCourtCase = courtCaseList.get(courtCaseList.size() - 1);
        assertThat(testCourtCase.getLandReferenceNo()).isEqualTo(DEFAULT_LAND_REFERENCE_NO);
        assertThat(testCourtCase.getCaseNo()).isEqualTo(DEFAULT_CASE_NO);
        assertThat(testCourtCase.getVillageName()).isEqualTo(DEFAULT_VILLAGE_NAME);
        assertThat(testCourtCase.getAccuserName()).isEqualTo(UPDATED_ACCUSER_NAME);
        assertThat(testCourtCase.getDefendantName()).isEqualTo(DEFAULT_DEFENDANT_NAME);
        assertThat(testCourtCase.getAccuserLawyer()).isEqualTo(DEFAULT_ACCUSER_LAWYER);
        assertThat(testCourtCase.getDefendantLawyer()).isEqualTo(DEFAULT_DEFENDANT_LAWYER);
        assertThat(testCourtCase.getCaseOfficer()).isEqualTo(DEFAULT_CASE_OFFICER);
        assertThat(testCourtCase.getCaseOfficerAdd()).isEqualTo(UPDATED_CASE_OFFICER_ADD);
        assertThat(testCourtCase.getTotalArea()).isEqualTo(UPDATED_TOTAL_AREA);
        assertThat(testCourtCase.getLandAcquisitionOfficerNo()).isEqualTo(UPDATED_LAND_ACQUISITION_OFFICER_NO);
        assertThat(testCourtCase.getSection11JudgmentNo()).isEqualTo(UPDATED_SECTION_11_JUDGMENT_NO);
        assertThat(testCourtCase.getSection4NoticeDate()).isEqualTo(DEFAULT_SECTION_4_NOTICE_DATE);
        assertThat(testCourtCase.getJudgementDate()).isEqualTo(DEFAULT_JUDGEMENT_DATE);
        assertThat(testCourtCase.getDateOfDecision()).isEqualTo(UPDATED_DATE_OF_DECISION);
        assertThat(testCourtCase.getCaseFilingDate()).isEqualTo(DEFAULT_CASE_FILING_DATE);
        assertThat(testCourtCase.getCaseStatus()).isEqualTo(DEFAULT_CASE_STATUS);
        assertThat(testCourtCase.getLastHearingDate()).isEqualTo(DEFAULT_LAST_HEARING_DATE);
        assertThat(testCourtCase.getNextHearingDate()).isEqualTo(UPDATED_NEXT_HEARING_DATE);
        assertThat(testCourtCase.getNatureResult()).isEqualTo(UPDATED_NATURE_RESULT);
        assertThat(testCourtCase.getAmountDepositeInCourt()).isEqualTo(UPDATED_AMOUNT_DEPOSITE_IN_COURT);
        assertThat(testCourtCase.getClaimAmount()).isEqualTo(DEFAULT_CLAIM_AMOUNT);
        assertThat(testCourtCase.getDepositedChequeNo()).isEqualTo(DEFAULT_DEPOSITED_CHEQUE_NO);
        assertThat(testCourtCase.getDepositedchequeDate()).isEqualTo(UPDATED_DEPOSITEDCHEQUE_DATE);
        assertThat(testCourtCase.getAddInterestAmountDistCourt()).isEqualTo(UPDATED_ADD_INTEREST_AMOUNT_DIST_COURT);
        assertThat(testCourtCase.getAddInterestApplicationNo()).isEqualTo(DEFAULT_ADD_INTEREST_APPLICATION_NO);
        assertThat(testCourtCase.getAddIntChequeNo()).isEqualTo(UPDATED_ADD_INT_CHEQUE_NO);
        assertThat(testCourtCase.getAddIntChequeDate()).isEqualTo(UPDATED_ADD_INT_CHEQUE_DATE);
        assertThat(testCourtCase.getBankGuaranteeAppNo()).isEqualTo(DEFAULT_BANK_GUARANTEE_APP_NO);
        assertThat(testCourtCase.getCourtName()).isEqualTo(UPDATED_COURT_NAME);
        assertThat(testCourtCase.getCourtType()).isEqualTo(DEFAULT_COURT_TYPE);
        assertThat(testCourtCase.getCaseType()).isEqualTo(UPDATED_CASE_TYPE);
        assertThat(testCourtCase.getIsActivated()).isEqualTo(DEFAULT_IS_ACTIVATED);
        assertThat(testCourtCase.getFreefield1()).isEqualTo(UPDATED_FREEFIELD_1);
        assertThat(testCourtCase.getFreefield2()).isEqualTo(DEFAULT_FREEFIELD_2);
        assertThat(testCourtCase.getFreefield3()).isEqualTo(UPDATED_FREEFIELD_3);
        assertThat(testCourtCase.getFreefield4()).isEqualTo(UPDATED_FREEFIELD_4);
        assertThat(testCourtCase.getFreefield5()).isEqualTo(UPDATED_FREEFIELD_5);
        assertThat(testCourtCase.getFreefield6()).isEqualTo(DEFAULT_FREEFIELD_6);
        assertThat(testCourtCase.getFreefield7()).isEqualTo(UPDATED_FREEFIELD_7);
        assertThat(testCourtCase.getFreefield8()).isEqualTo(UPDATED_FREEFIELD_8);
        assertThat(testCourtCase.getFreefield9()).isEqualTo(DEFAULT_FREEFIELD_9);
        assertThat(testCourtCase.getFreefield10()).isEqualTo(DEFAULT_FREEFIELD_10);
        assertThat(testCourtCase.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testCourtCase.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void fullUpdateCourtCaseWithPatch() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();

        // Update the courtCase using partial update
        CourtCase partialUpdatedCourtCase = new CourtCase();
        partialUpdatedCourtCase.setId(courtCase.getId());

        partialUpdatedCourtCase
            .landReferenceNo(UPDATED_LAND_REFERENCE_NO)
            .caseNo(UPDATED_CASE_NO)
            .villageName(UPDATED_VILLAGE_NAME)
            .accuserName(UPDATED_ACCUSER_NAME)
            .defendantName(UPDATED_DEFENDANT_NAME)
            .accuserLawyer(UPDATED_ACCUSER_LAWYER)
            .defendantLawyer(UPDATED_DEFENDANT_LAWYER)
            .caseOfficer(UPDATED_CASE_OFFICER)
            .caseOfficerAdd(UPDATED_CASE_OFFICER_ADD)
            .totalArea(UPDATED_TOTAL_AREA)
            .landAcquisitionOfficerNo(UPDATED_LAND_ACQUISITION_OFFICER_NO)
            .section11JudgmentNo(UPDATED_SECTION_11_JUDGMENT_NO)
            .section4NoticeDate(UPDATED_SECTION_4_NOTICE_DATE)
            .judgementDate(UPDATED_JUDGEMENT_DATE)
            .dateOfDecision(UPDATED_DATE_OF_DECISION)
            .caseFilingDate(UPDATED_CASE_FILING_DATE)
            .caseStatus(UPDATED_CASE_STATUS)
            .lastHearingDate(UPDATED_LAST_HEARING_DATE)
            .nextHearingDate(UPDATED_NEXT_HEARING_DATE)
            .natureResult(UPDATED_NATURE_RESULT)
            .amountDepositeInCourt(UPDATED_AMOUNT_DEPOSITE_IN_COURT)
            .claimAmount(UPDATED_CLAIM_AMOUNT)
            .depositedChequeNo(UPDATED_DEPOSITED_CHEQUE_NO)
            .depositedchequeDate(UPDATED_DEPOSITEDCHEQUE_DATE)
            .addInterestAmountDistCourt(UPDATED_ADD_INTEREST_AMOUNT_DIST_COURT)
            .addInterestApplicationNo(UPDATED_ADD_INTEREST_APPLICATION_NO)
            .addIntChequeNo(UPDATED_ADD_INT_CHEQUE_NO)
            .addIntChequeDate(UPDATED_ADD_INT_CHEQUE_DATE)
            .bankGuaranteeAppNo(UPDATED_BANK_GUARANTEE_APP_NO)
            .courtName(UPDATED_COURT_NAME)
            .courtType(UPDATED_COURT_TYPE)
            .caseType(UPDATED_CASE_TYPE)
            .isActivated(UPDATED_IS_ACTIVATED)
            .freefield1(UPDATED_FREEFIELD_1)
            .freefield2(UPDATED_FREEFIELD_2)
            .freefield3(UPDATED_FREEFIELD_3)
            .freefield4(UPDATED_FREEFIELD_4)
            .freefield5(UPDATED_FREEFIELD_5)
            .freefield6(UPDATED_FREEFIELD_6)
            .freefield7(UPDATED_FREEFIELD_7)
            .freefield8(UPDATED_FREEFIELD_8)
            .freefield9(UPDATED_FREEFIELD_9)
            .freefield10(UPDATED_FREEFIELD_10)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModified(UPDATED_LAST_MODIFIED);

        restCourtCaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourtCase.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourtCase))
            )
            .andExpect(status().isOk());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
        CourtCase testCourtCase = courtCaseList.get(courtCaseList.size() - 1);
        assertThat(testCourtCase.getLandReferenceNo()).isEqualTo(UPDATED_LAND_REFERENCE_NO);
        assertThat(testCourtCase.getCaseNo()).isEqualTo(UPDATED_CASE_NO);
        assertThat(testCourtCase.getVillageName()).isEqualTo(UPDATED_VILLAGE_NAME);
        assertThat(testCourtCase.getAccuserName()).isEqualTo(UPDATED_ACCUSER_NAME);
        assertThat(testCourtCase.getDefendantName()).isEqualTo(UPDATED_DEFENDANT_NAME);
        assertThat(testCourtCase.getAccuserLawyer()).isEqualTo(UPDATED_ACCUSER_LAWYER);
        assertThat(testCourtCase.getDefendantLawyer()).isEqualTo(UPDATED_DEFENDANT_LAWYER);
        assertThat(testCourtCase.getCaseOfficer()).isEqualTo(UPDATED_CASE_OFFICER);
        assertThat(testCourtCase.getCaseOfficerAdd()).isEqualTo(UPDATED_CASE_OFFICER_ADD);
        assertThat(testCourtCase.getTotalArea()).isEqualTo(UPDATED_TOTAL_AREA);
        assertThat(testCourtCase.getLandAcquisitionOfficerNo()).isEqualTo(UPDATED_LAND_ACQUISITION_OFFICER_NO);
        assertThat(testCourtCase.getSection11JudgmentNo()).isEqualTo(UPDATED_SECTION_11_JUDGMENT_NO);
        assertThat(testCourtCase.getSection4NoticeDate()).isEqualTo(UPDATED_SECTION_4_NOTICE_DATE);
        assertThat(testCourtCase.getJudgementDate()).isEqualTo(UPDATED_JUDGEMENT_DATE);
        assertThat(testCourtCase.getDateOfDecision()).isEqualTo(UPDATED_DATE_OF_DECISION);
        assertThat(testCourtCase.getCaseFilingDate()).isEqualTo(UPDATED_CASE_FILING_DATE);
        assertThat(testCourtCase.getCaseStatus()).isEqualTo(UPDATED_CASE_STATUS);
        assertThat(testCourtCase.getLastHearingDate()).isEqualTo(UPDATED_LAST_HEARING_DATE);
        assertThat(testCourtCase.getNextHearingDate()).isEqualTo(UPDATED_NEXT_HEARING_DATE);
        assertThat(testCourtCase.getNatureResult()).isEqualTo(UPDATED_NATURE_RESULT);
        assertThat(testCourtCase.getAmountDepositeInCourt()).isEqualTo(UPDATED_AMOUNT_DEPOSITE_IN_COURT);
        assertThat(testCourtCase.getClaimAmount()).isEqualTo(UPDATED_CLAIM_AMOUNT);
        assertThat(testCourtCase.getDepositedChequeNo()).isEqualTo(UPDATED_DEPOSITED_CHEQUE_NO);
        assertThat(testCourtCase.getDepositedchequeDate()).isEqualTo(UPDATED_DEPOSITEDCHEQUE_DATE);
        assertThat(testCourtCase.getAddInterestAmountDistCourt()).isEqualTo(UPDATED_ADD_INTEREST_AMOUNT_DIST_COURT);
        assertThat(testCourtCase.getAddInterestApplicationNo()).isEqualTo(UPDATED_ADD_INTEREST_APPLICATION_NO);
        assertThat(testCourtCase.getAddIntChequeNo()).isEqualTo(UPDATED_ADD_INT_CHEQUE_NO);
        assertThat(testCourtCase.getAddIntChequeDate()).isEqualTo(UPDATED_ADD_INT_CHEQUE_DATE);
        assertThat(testCourtCase.getBankGuaranteeAppNo()).isEqualTo(UPDATED_BANK_GUARANTEE_APP_NO);
        assertThat(testCourtCase.getCourtName()).isEqualTo(UPDATED_COURT_NAME);
        assertThat(testCourtCase.getCourtType()).isEqualTo(UPDATED_COURT_TYPE);
        assertThat(testCourtCase.getCaseType()).isEqualTo(UPDATED_CASE_TYPE);
        assertThat(testCourtCase.getIsActivated()).isEqualTo(UPDATED_IS_ACTIVATED);
        assertThat(testCourtCase.getFreefield1()).isEqualTo(UPDATED_FREEFIELD_1);
        assertThat(testCourtCase.getFreefield2()).isEqualTo(UPDATED_FREEFIELD_2);
        assertThat(testCourtCase.getFreefield3()).isEqualTo(UPDATED_FREEFIELD_3);
        assertThat(testCourtCase.getFreefield4()).isEqualTo(UPDATED_FREEFIELD_4);
        assertThat(testCourtCase.getFreefield5()).isEqualTo(UPDATED_FREEFIELD_5);
        assertThat(testCourtCase.getFreefield6()).isEqualTo(UPDATED_FREEFIELD_6);
        assertThat(testCourtCase.getFreefield7()).isEqualTo(UPDATED_FREEFIELD_7);
        assertThat(testCourtCase.getFreefield8()).isEqualTo(UPDATED_FREEFIELD_8);
        assertThat(testCourtCase.getFreefield9()).isEqualTo(UPDATED_FREEFIELD_9);
        assertThat(testCourtCase.getFreefield10()).isEqualTo(UPDATED_FREEFIELD_10);
        assertThat(testCourtCase.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testCourtCase.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void patchNonExistingCourtCase() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();
        courtCase.setId(count.incrementAndGet());

        // Create the CourtCase
        CourtCaseDTO courtCaseDTO = courtCaseMapper.toDto(courtCase);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourtCaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, courtCaseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courtCaseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCourtCase() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();
        courtCase.setId(count.incrementAndGet());

        // Create the CourtCase
        CourtCaseDTO courtCaseDTO = courtCaseMapper.toDto(courtCase);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourtCaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courtCaseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCourtCase() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();
        courtCase.setId(count.incrementAndGet());

        // Create the CourtCase
        CourtCaseDTO courtCaseDTO = courtCaseMapper.toDto(courtCase);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourtCaseMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(courtCaseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourtCase() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        int databaseSizeBeforeDelete = courtCaseRepository.findAll().size();

        // Delete the courtCase
        restCourtCaseMockMvc
            .perform(delete(ENTITY_API_URL_ID, courtCase.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
