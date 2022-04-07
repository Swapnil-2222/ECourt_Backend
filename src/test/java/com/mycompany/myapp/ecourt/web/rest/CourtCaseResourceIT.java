package com.mycompany.myapp.ecourt.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.ecourt.IntegrationTest;
import com.mycompany.myapp.ecourt.domain.CourtCase;
import com.mycompany.myapp.ecourt.domain.enumeration.CaseStatus;
import com.mycompany.myapp.ecourt.domain.enumeration.CourtType;
import com.mycompany.myapp.ecourt.domain.enumeration.NatureResult;
import com.mycompany.myapp.ecourt.repository.CourtCaseRepository;
import com.mycompany.myapp.ecourt.service.CourtCaseService;
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
            .isActivated(UPDATED_IS_ACTIVATED)
            .freefield2(UPDATED_FREEFIELD_2)
            .freefield4(UPDATED_FREEFIELD_4)
            .freefield5(UPDATED_FREEFIELD_5)
            .freefield6(UPDATED_FREEFIELD_6)
            .freefield8(UPDATED_FREEFIELD_8)
            .freefield9(UPDATED_FREEFIELD_9);

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
        assertThat(testCourtCase.getIsActivated()).isEqualTo(UPDATED_IS_ACTIVATED);
        assertThat(testCourtCase.getFreefield1()).isEqualTo(DEFAULT_FREEFIELD_1);
        assertThat(testCourtCase.getFreefield2()).isEqualTo(UPDATED_FREEFIELD_2);
        assertThat(testCourtCase.getFreefield3()).isEqualTo(DEFAULT_FREEFIELD_3);
        assertThat(testCourtCase.getFreefield4()).isEqualTo(UPDATED_FREEFIELD_4);
        assertThat(testCourtCase.getFreefield5()).isEqualTo(UPDATED_FREEFIELD_5);
        assertThat(testCourtCase.getFreefield6()).isEqualTo(UPDATED_FREEFIELD_6);
        assertThat(testCourtCase.getFreefield7()).isEqualTo(DEFAULT_FREEFIELD_7);
        assertThat(testCourtCase.getFreefield8()).isEqualTo(UPDATED_FREEFIELD_8);
        assertThat(testCourtCase.getFreefield9()).isEqualTo(UPDATED_FREEFIELD_9);
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
