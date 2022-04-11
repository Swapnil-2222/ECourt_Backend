package com.mycompany.myapp.ecourt.service.criteria;

import com.mycompany.myapp.ecourt.domain.enumeration.CaseStatus;
import com.mycompany.myapp.ecourt.domain.enumeration.CourtType;
import com.mycompany.myapp.ecourt.domain.enumeration.NatureResult;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.ecourt.domain.CourtCase} entity. This class is used
 * in {@link com.mycompany.myapp.ecourt.web.rest.CourtCaseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /court-cases?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class CourtCaseCriteria implements Serializable, Criteria {

    /**
     * Class for filtering CaseStatus
     */
    public static class CaseStatusFilter extends Filter<CaseStatus> {

        public CaseStatusFilter() {}

        public CaseStatusFilter(CaseStatusFilter filter) {
            super(filter);
        }

        @Override
        public CaseStatusFilter copy() {
            return new CaseStatusFilter(this);
        }
    }

    /**
     * Class for filtering NatureResult
     */
    public static class NatureResultFilter extends Filter<NatureResult> {

        public NatureResultFilter() {}

        public NatureResultFilter(NatureResultFilter filter) {
            super(filter);
        }

        @Override
        public NatureResultFilter copy() {
            return new NatureResultFilter(this);
        }
    }

    /**
     * Class for filtering CourtType
     */
    public static class CourtTypeFilter extends Filter<CourtType> {

        public CourtTypeFilter() {}

        public CourtTypeFilter(CourtTypeFilter filter) {
            super(filter);
        }

        @Override
        public CourtTypeFilter copy() {
            return new CourtTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter landReferenceNo;

    private StringFilter caseNo;

    private StringFilter villageName;

    private StringFilter accuserName;

    private StringFilter defendantName;

    private StringFilter accuserLawyer;

    private StringFilter defendantLawyer;

    private StringFilter caseOfficer;

    private StringFilter caseOfficerAdd;

    private StringFilter totalArea;

    private StringFilter landAcquisitionOfficerNo;

    private StringFilter section11JudgmentNo;

    private StringFilter section4NoticeDate;

    private StringFilter judgementDate;

    private StringFilter dateOfDecision;

    private StringFilter caseFilingDate;

    private CaseStatusFilter caseStatus;

    private StringFilter lastHearingDate;

    private StringFilter nextHearingDate;

    private NatureResultFilter natureResult;

    private StringFilter amountDepositeInCourt;

    private StringFilter claimAmount;

    private StringFilter depositedChequeNo;

    private StringFilter depositedchequeDate;

    private StringFilter addInterestAmountDistCourt;

    private StringFilter addInterestApplicationNo;

    private StringFilter addIntChequeNo;

    private StringFilter addIntChequeDate;

    private StringFilter bankGuaranteeAppNo;

    private StringFilter courtName;

    private CourtTypeFilter courtType;

    private BooleanFilter isActivated;

    private StringFilter freefield1;

    private StringFilter freefield2;

    private StringFilter freefield3;

    private StringFilter freefield4;

    private StringFilter freefield5;

    private StringFilter freefield6;

    private StringFilter freefield7;

    private StringFilter freefield8;

    private StringFilter freefield9;

    private StringFilter freefield10;

    private StringFilter lastModifiedBy;

    private StringFilter lastModified;

    private LongFilter organizationId;

    private LongFilter lawyerDetailsId;

    private Boolean distinct;

    public CourtCaseCriteria() {}

    public CourtCaseCriteria(CourtCaseCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.landReferenceNo = other.landReferenceNo == null ? null : other.landReferenceNo.copy();
        this.caseNo = other.caseNo == null ? null : other.caseNo.copy();
        this.villageName = other.villageName == null ? null : other.villageName.copy();
        this.accuserName = other.accuserName == null ? null : other.accuserName.copy();
        this.defendantName = other.defendantName == null ? null : other.defendantName.copy();
        this.accuserLawyer = other.accuserLawyer == null ? null : other.accuserLawyer.copy();
        this.defendantLawyer = other.defendantLawyer == null ? null : other.defendantLawyer.copy();
        this.caseOfficer = other.caseOfficer == null ? null : other.caseOfficer.copy();
        this.caseOfficerAdd = other.caseOfficerAdd == null ? null : other.caseOfficerAdd.copy();
        this.totalArea = other.totalArea == null ? null : other.totalArea.copy();
        this.landAcquisitionOfficerNo = other.landAcquisitionOfficerNo == null ? null : other.landAcquisitionOfficerNo.copy();
        this.section11JudgmentNo = other.section11JudgmentNo == null ? null : other.section11JudgmentNo.copy();
        this.section4NoticeDate = other.section4NoticeDate == null ? null : other.section4NoticeDate.copy();
        this.judgementDate = other.judgementDate == null ? null : other.judgementDate.copy();
        this.dateOfDecision = other.dateOfDecision == null ? null : other.dateOfDecision.copy();
        this.caseFilingDate = other.caseFilingDate == null ? null : other.caseFilingDate.copy();
        this.caseStatus = other.caseStatus == null ? null : other.caseStatus.copy();
        this.lastHearingDate = other.lastHearingDate == null ? null : other.lastHearingDate.copy();
        this.nextHearingDate = other.nextHearingDate == null ? null : other.nextHearingDate.copy();
        this.natureResult = other.natureResult == null ? null : other.natureResult.copy();
        this.amountDepositeInCourt = other.amountDepositeInCourt == null ? null : other.amountDepositeInCourt.copy();
        this.claimAmount = other.claimAmount == null ? null : other.claimAmount.copy();
        this.depositedChequeNo = other.depositedChequeNo == null ? null : other.depositedChequeNo.copy();
        this.depositedchequeDate = other.depositedchequeDate == null ? null : other.depositedchequeDate.copy();
        this.addInterestAmountDistCourt = other.addInterestAmountDistCourt == null ? null : other.addInterestAmountDistCourt.copy();
        this.addInterestApplicationNo = other.addInterestApplicationNo == null ? null : other.addInterestApplicationNo.copy();
        this.addIntChequeNo = other.addIntChequeNo == null ? null : other.addIntChequeNo.copy();
        this.addIntChequeDate = other.addIntChequeDate == null ? null : other.addIntChequeDate.copy();
        this.bankGuaranteeAppNo = other.bankGuaranteeAppNo == null ? null : other.bankGuaranteeAppNo.copy();
        this.courtName = other.courtName == null ? null : other.courtName.copy();
        this.courtType = other.courtType == null ? null : other.courtType.copy();
        this.isActivated = other.isActivated == null ? null : other.isActivated.copy();
        this.freefield1 = other.freefield1 == null ? null : other.freefield1.copy();
        this.freefield2 = other.freefield2 == null ? null : other.freefield2.copy();
        this.freefield3 = other.freefield3 == null ? null : other.freefield3.copy();
        this.freefield4 = other.freefield4 == null ? null : other.freefield4.copy();
        this.freefield5 = other.freefield5 == null ? null : other.freefield5.copy();
        this.freefield6 = other.freefield6 == null ? null : other.freefield6.copy();
        this.freefield7 = other.freefield7 == null ? null : other.freefield7.copy();
        this.freefield8 = other.freefield8 == null ? null : other.freefield8.copy();
        this.freefield9 = other.freefield9 == null ? null : other.freefield9.copy();
        this.freefield10 = other.freefield10 == null ? null : other.freefield10.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.organizationId = other.organizationId == null ? null : other.organizationId.copy();
        this.lawyerDetailsId = other.lawyerDetailsId == null ? null : other.lawyerDetailsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CourtCaseCriteria copy() {
        return new CourtCaseCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLandReferenceNo() {
        return landReferenceNo;
    }

    public StringFilter landReferenceNo() {
        if (landReferenceNo == null) {
            landReferenceNo = new StringFilter();
        }
        return landReferenceNo;
    }

    public void setLandReferenceNo(StringFilter landReferenceNo) {
        this.landReferenceNo = landReferenceNo;
    }

    public StringFilter getCaseNo() {
        return caseNo;
    }

    public StringFilter caseNo() {
        if (caseNo == null) {
            caseNo = new StringFilter();
        }
        return caseNo;
    }

    public void setCaseNo(StringFilter caseNo) {
        this.caseNo = caseNo;
    }

    public StringFilter getVillageName() {
        return villageName;
    }

    public StringFilter villageName() {
        if (villageName == null) {
            villageName = new StringFilter();
        }
        return villageName;
    }

    public void setVillageName(StringFilter villageName) {
        this.villageName = villageName;
    }

    public StringFilter getAccuserName() {
        return accuserName;
    }

    public StringFilter accuserName() {
        if (accuserName == null) {
            accuserName = new StringFilter();
        }
        return accuserName;
    }

    public void setAccuserName(StringFilter accuserName) {
        this.accuserName = accuserName;
    }

    public StringFilter getDefendantName() {
        return defendantName;
    }

    public StringFilter defendantName() {
        if (defendantName == null) {
            defendantName = new StringFilter();
        }
        return defendantName;
    }

    public void setDefendantName(StringFilter defendantName) {
        this.defendantName = defendantName;
    }

    public StringFilter getAccuserLawyer() {
        return accuserLawyer;
    }

    public StringFilter accuserLawyer() {
        if (accuserLawyer == null) {
            accuserLawyer = new StringFilter();
        }
        return accuserLawyer;
    }

    public void setAccuserLawyer(StringFilter accuserLawyer) {
        this.accuserLawyer = accuserLawyer;
    }

    public StringFilter getDefendantLawyer() {
        return defendantLawyer;
    }

    public StringFilter defendantLawyer() {
        if (defendantLawyer == null) {
            defendantLawyer = new StringFilter();
        }
        return defendantLawyer;
    }

    public void setDefendantLawyer(StringFilter defendantLawyer) {
        this.defendantLawyer = defendantLawyer;
    }

    public StringFilter getCaseOfficer() {
        return caseOfficer;
    }

    public StringFilter caseOfficer() {
        if (caseOfficer == null) {
            caseOfficer = new StringFilter();
        }
        return caseOfficer;
    }

    public void setCaseOfficer(StringFilter caseOfficer) {
        this.caseOfficer = caseOfficer;
    }

    public StringFilter getCaseOfficerAdd() {
        return caseOfficerAdd;
    }

    public StringFilter caseOfficerAdd() {
        if (caseOfficerAdd == null) {
            caseOfficerAdd = new StringFilter();
        }
        return caseOfficerAdd;
    }

    public void setCaseOfficerAdd(StringFilter caseOfficerAdd) {
        this.caseOfficerAdd = caseOfficerAdd;
    }

    public StringFilter getTotalArea() {
        return totalArea;
    }

    public StringFilter totalArea() {
        if (totalArea == null) {
            totalArea = new StringFilter();
        }
        return totalArea;
    }

    public void setTotalArea(StringFilter totalArea) {
        this.totalArea = totalArea;
    }

    public StringFilter getLandAcquisitionOfficerNo() {
        return landAcquisitionOfficerNo;
    }

    public StringFilter landAcquisitionOfficerNo() {
        if (landAcquisitionOfficerNo == null) {
            landAcquisitionOfficerNo = new StringFilter();
        }
        return landAcquisitionOfficerNo;
    }

    public void setLandAcquisitionOfficerNo(StringFilter landAcquisitionOfficerNo) {
        this.landAcquisitionOfficerNo = landAcquisitionOfficerNo;
    }

    public StringFilter getSection11JudgmentNo() {
        return section11JudgmentNo;
    }

    public StringFilter section11JudgmentNo() {
        if (section11JudgmentNo == null) {
            section11JudgmentNo = new StringFilter();
        }
        return section11JudgmentNo;
    }

    public void setSection11JudgmentNo(StringFilter section11JudgmentNo) {
        this.section11JudgmentNo = section11JudgmentNo;
    }

    public StringFilter getSection4NoticeDate() {
        return section4NoticeDate;
    }

    public StringFilter section4NoticeDate() {
        if (section4NoticeDate == null) {
            section4NoticeDate = new StringFilter();
        }
        return section4NoticeDate;
    }

    public void setSection4NoticeDate(StringFilter section4NoticeDate) {
        this.section4NoticeDate = section4NoticeDate;
    }

    public StringFilter getJudgementDate() {
        return judgementDate;
    }

    public StringFilter judgementDate() {
        if (judgementDate == null) {
            judgementDate = new StringFilter();
        }
        return judgementDate;
    }

    public void setJudgementDate(StringFilter judgementDate) {
        this.judgementDate = judgementDate;
    }

    public StringFilter getDateOfDecision() {
        return dateOfDecision;
    }

    public StringFilter dateOfDecision() {
        if (dateOfDecision == null) {
            dateOfDecision = new StringFilter();
        }
        return dateOfDecision;
    }

    public void setDateOfDecision(StringFilter dateOfDecision) {
        this.dateOfDecision = dateOfDecision;
    }

    public StringFilter getCaseFilingDate() {
        return caseFilingDate;
    }

    public StringFilter caseFilingDate() {
        if (caseFilingDate == null) {
            caseFilingDate = new StringFilter();
        }
        return caseFilingDate;
    }

    public void setCaseFilingDate(StringFilter caseFilingDate) {
        this.caseFilingDate = caseFilingDate;
    }

    public CaseStatusFilter getCaseStatus() {
        return caseStatus;
    }

    public CaseStatusFilter caseStatus() {
        if (caseStatus == null) {
            caseStatus = new CaseStatusFilter();
        }
        return caseStatus;
    }

    public void setCaseStatus(CaseStatusFilter caseStatus) {
        this.caseStatus = caseStatus;
    }

    public StringFilter getLastHearingDate() {
        return lastHearingDate;
    }

    public StringFilter lastHearingDate() {
        if (lastHearingDate == null) {
            lastHearingDate = new StringFilter();
        }
        return lastHearingDate;
    }

    public void setLastHearingDate(StringFilter lastHearingDate) {
        this.lastHearingDate = lastHearingDate;
    }

    public StringFilter getNextHearingDate() {
        return nextHearingDate;
    }

    public StringFilter nextHearingDate() {
        if (nextHearingDate == null) {
            nextHearingDate = new StringFilter();
        }
        return nextHearingDate;
    }

    public void setNextHearingDate(StringFilter nextHearingDate) {
        this.nextHearingDate = nextHearingDate;
    }

    public NatureResultFilter getNatureResult() {
        return natureResult;
    }

    public NatureResultFilter natureResult() {
        if (natureResult == null) {
            natureResult = new NatureResultFilter();
        }
        return natureResult;
    }

    public void setNatureResult(NatureResultFilter natureResult) {
        this.natureResult = natureResult;
    }

    public StringFilter getAmountDepositeInCourt() {
        return amountDepositeInCourt;
    }

    public StringFilter amountDepositeInCourt() {
        if (amountDepositeInCourt == null) {
            amountDepositeInCourt = new StringFilter();
        }
        return amountDepositeInCourt;
    }

    public void setAmountDepositeInCourt(StringFilter amountDepositeInCourt) {
        this.amountDepositeInCourt = amountDepositeInCourt;
    }

    public StringFilter getClaimAmount() {
        return claimAmount;
    }

    public StringFilter claimAmount() {
        if (claimAmount == null) {
            claimAmount = new StringFilter();
        }
        return claimAmount;
    }

    public void setClaimAmount(StringFilter claimAmount) {
        this.claimAmount = claimAmount;
    }

    public StringFilter getDepositedChequeNo() {
        return depositedChequeNo;
    }

    public StringFilter depositedChequeNo() {
        if (depositedChequeNo == null) {
            depositedChequeNo = new StringFilter();
        }
        return depositedChequeNo;
    }

    public void setDepositedChequeNo(StringFilter depositedChequeNo) {
        this.depositedChequeNo = depositedChequeNo;
    }

    public StringFilter getDepositedchequeDate() {
        return depositedchequeDate;
    }

    public StringFilter depositedchequeDate() {
        if (depositedchequeDate == null) {
            depositedchequeDate = new StringFilter();
        }
        return depositedchequeDate;
    }

    public void setDepositedchequeDate(StringFilter depositedchequeDate) {
        this.depositedchequeDate = depositedchequeDate;
    }

    public StringFilter getAddInterestAmountDistCourt() {
        return addInterestAmountDistCourt;
    }

    public StringFilter addInterestAmountDistCourt() {
        if (addInterestAmountDistCourt == null) {
            addInterestAmountDistCourt = new StringFilter();
        }
        return addInterestAmountDistCourt;
    }

    public void setAddInterestAmountDistCourt(StringFilter addInterestAmountDistCourt) {
        this.addInterestAmountDistCourt = addInterestAmountDistCourt;
    }

    public StringFilter getAddInterestApplicationNo() {
        return addInterestApplicationNo;
    }

    public StringFilter addInterestApplicationNo() {
        if (addInterestApplicationNo == null) {
            addInterestApplicationNo = new StringFilter();
        }
        return addInterestApplicationNo;
    }

    public void setAddInterestApplicationNo(StringFilter addInterestApplicationNo) {
        this.addInterestApplicationNo = addInterestApplicationNo;
    }

    public StringFilter getAddIntChequeNo() {
        return addIntChequeNo;
    }

    public StringFilter addIntChequeNo() {
        if (addIntChequeNo == null) {
            addIntChequeNo = new StringFilter();
        }
        return addIntChequeNo;
    }

    public void setAddIntChequeNo(StringFilter addIntChequeNo) {
        this.addIntChequeNo = addIntChequeNo;
    }

    public StringFilter getAddIntChequeDate() {
        return addIntChequeDate;
    }

    public StringFilter addIntChequeDate() {
        if (addIntChequeDate == null) {
            addIntChequeDate = new StringFilter();
        }
        return addIntChequeDate;
    }

    public void setAddIntChequeDate(StringFilter addIntChequeDate) {
        this.addIntChequeDate = addIntChequeDate;
    }

    public StringFilter getBankGuaranteeAppNo() {
        return bankGuaranteeAppNo;
    }

    public StringFilter bankGuaranteeAppNo() {
        if (bankGuaranteeAppNo == null) {
            bankGuaranteeAppNo = new StringFilter();
        }
        return bankGuaranteeAppNo;
    }

    public void setBankGuaranteeAppNo(StringFilter bankGuaranteeAppNo) {
        this.bankGuaranteeAppNo = bankGuaranteeAppNo;
    }

    public StringFilter getCourtName() {
        return courtName;
    }

    public StringFilter courtName() {
        if (courtName == null) {
            courtName = new StringFilter();
        }
        return courtName;
    }

    public void setCourtName(StringFilter courtName) {
        this.courtName = courtName;
    }

    public CourtTypeFilter getCourtType() {
        return courtType;
    }

    public CourtTypeFilter courtType() {
        if (courtType == null) {
            courtType = new CourtTypeFilter();
        }
        return courtType;
    }

    public void setCourtType(CourtTypeFilter courtType) {
        this.courtType = courtType;
    }

    public BooleanFilter getIsActivated() {
        return isActivated;
    }

    public BooleanFilter isActivated() {
        if (isActivated == null) {
            isActivated = new BooleanFilter();
        }
        return isActivated;
    }

    public void setIsActivated(BooleanFilter isActivated) {
        this.isActivated = isActivated;
    }

    public StringFilter getFreefield1() {
        return freefield1;
    }

    public StringFilter freefield1() {
        if (freefield1 == null) {
            freefield1 = new StringFilter();
        }
        return freefield1;
    }

    public void setFreefield1(StringFilter freefield1) {
        this.freefield1 = freefield1;
    }

    public StringFilter getFreefield2() {
        return freefield2;
    }

    public StringFilter freefield2() {
        if (freefield2 == null) {
            freefield2 = new StringFilter();
        }
        return freefield2;
    }

    public void setFreefield2(StringFilter freefield2) {
        this.freefield2 = freefield2;
    }

    public StringFilter getFreefield3() {
        return freefield3;
    }

    public StringFilter freefield3() {
        if (freefield3 == null) {
            freefield3 = new StringFilter();
        }
        return freefield3;
    }

    public void setFreefield3(StringFilter freefield3) {
        this.freefield3 = freefield3;
    }

    public StringFilter getFreefield4() {
        return freefield4;
    }

    public StringFilter freefield4() {
        if (freefield4 == null) {
            freefield4 = new StringFilter();
        }
        return freefield4;
    }

    public void setFreefield4(StringFilter freefield4) {
        this.freefield4 = freefield4;
    }

    public StringFilter getFreefield5() {
        return freefield5;
    }

    public StringFilter freefield5() {
        if (freefield5 == null) {
            freefield5 = new StringFilter();
        }
        return freefield5;
    }

    public void setFreefield5(StringFilter freefield5) {
        this.freefield5 = freefield5;
    }

    public StringFilter getFreefield6() {
        return freefield6;
    }

    public StringFilter freefield6() {
        if (freefield6 == null) {
            freefield6 = new StringFilter();
        }
        return freefield6;
    }

    public void setFreefield6(StringFilter freefield6) {
        this.freefield6 = freefield6;
    }

    public StringFilter getFreefield7() {
        return freefield7;
    }

    public StringFilter freefield7() {
        if (freefield7 == null) {
            freefield7 = new StringFilter();
        }
        return freefield7;
    }

    public void setFreefield7(StringFilter freefield7) {
        this.freefield7 = freefield7;
    }

    public StringFilter getFreefield8() {
        return freefield8;
    }

    public StringFilter freefield8() {
        if (freefield8 == null) {
            freefield8 = new StringFilter();
        }
        return freefield8;
    }

    public void setFreefield8(StringFilter freefield8) {
        this.freefield8 = freefield8;
    }

    public StringFilter getFreefield9() {
        return freefield9;
    }

    public StringFilter freefield9() {
        if (freefield9 == null) {
            freefield9 = new StringFilter();
        }
        return freefield9;
    }

    public void setFreefield9(StringFilter freefield9) {
        this.freefield9 = freefield9;
    }

    public StringFilter getFreefield10() {
        return freefield10;
    }

    public StringFilter freefield10() {
        if (freefield10 == null) {
            freefield10 = new StringFilter();
        }
        return freefield10;
    }

    public void setFreefield10(StringFilter freefield10) {
        this.freefield10 = freefield10;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public StringFilter getLastModified() {
        return lastModified;
    }

    public StringFilter lastModified() {
        if (lastModified == null) {
            lastModified = new StringFilter();
        }
        return lastModified;
    }

    public void setLastModified(StringFilter lastModified) {
        this.lastModified = lastModified;
    }

    public LongFilter getOrganizationId() {
        return organizationId;
    }

    public LongFilter organizationId() {
        if (organizationId == null) {
            organizationId = new LongFilter();
        }
        return organizationId;
    }

    public void setOrganizationId(LongFilter organizationId) {
        this.organizationId = organizationId;
    }

    public LongFilter getLawyerDetailsId() {
        return lawyerDetailsId;
    }

    public LongFilter lawyerDetailsId() {
        if (lawyerDetailsId == null) {
            lawyerDetailsId = new LongFilter();
        }
        return lawyerDetailsId;
    }

    public void setLawyerDetailsId(LongFilter lawyerDetailsId) {
        this.lawyerDetailsId = lawyerDetailsId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CourtCaseCriteria that = (CourtCaseCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(landReferenceNo, that.landReferenceNo) &&
            Objects.equals(caseNo, that.caseNo) &&
            Objects.equals(villageName, that.villageName) &&
            Objects.equals(accuserName, that.accuserName) &&
            Objects.equals(defendantName, that.defendantName) &&
            Objects.equals(accuserLawyer, that.accuserLawyer) &&
            Objects.equals(defendantLawyer, that.defendantLawyer) &&
            Objects.equals(caseOfficer, that.caseOfficer) &&
            Objects.equals(caseOfficerAdd, that.caseOfficerAdd) &&
            Objects.equals(totalArea, that.totalArea) &&
            Objects.equals(landAcquisitionOfficerNo, that.landAcquisitionOfficerNo) &&
            Objects.equals(section11JudgmentNo, that.section11JudgmentNo) &&
            Objects.equals(section4NoticeDate, that.section4NoticeDate) &&
            Objects.equals(judgementDate, that.judgementDate) &&
            Objects.equals(dateOfDecision, that.dateOfDecision) &&
            Objects.equals(caseFilingDate, that.caseFilingDate) &&
            Objects.equals(caseStatus, that.caseStatus) &&
            Objects.equals(lastHearingDate, that.lastHearingDate) &&
            Objects.equals(nextHearingDate, that.nextHearingDate) &&
            Objects.equals(natureResult, that.natureResult) &&
            Objects.equals(amountDepositeInCourt, that.amountDepositeInCourt) &&
            Objects.equals(claimAmount, that.claimAmount) &&
            Objects.equals(depositedChequeNo, that.depositedChequeNo) &&
            Objects.equals(depositedchequeDate, that.depositedchequeDate) &&
            Objects.equals(addInterestAmountDistCourt, that.addInterestAmountDistCourt) &&
            Objects.equals(addInterestApplicationNo, that.addInterestApplicationNo) &&
            Objects.equals(addIntChequeNo, that.addIntChequeNo) &&
            Objects.equals(addIntChequeDate, that.addIntChequeDate) &&
            Objects.equals(bankGuaranteeAppNo, that.bankGuaranteeAppNo) &&
            Objects.equals(courtName, that.courtName) &&
            Objects.equals(courtType, that.courtType) &&
            Objects.equals(isActivated, that.isActivated) &&
            Objects.equals(freefield1, that.freefield1) &&
            Objects.equals(freefield2, that.freefield2) &&
            Objects.equals(freefield3, that.freefield3) &&
            Objects.equals(freefield4, that.freefield4) &&
            Objects.equals(freefield5, that.freefield5) &&
            Objects.equals(freefield6, that.freefield6) &&
            Objects.equals(freefield7, that.freefield7) &&
            Objects.equals(freefield8, that.freefield8) &&
            Objects.equals(freefield9, that.freefield9) &&
            Objects.equals(freefield10, that.freefield10) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(organizationId, that.organizationId) &&
            Objects.equals(lawyerDetailsId, that.lawyerDetailsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            landReferenceNo,
            caseNo,
            villageName,
            accuserName,
            defendantName,
            accuserLawyer,
            defendantLawyer,
            caseOfficer,
            caseOfficerAdd,
            totalArea,
            landAcquisitionOfficerNo,
            section11JudgmentNo,
            section4NoticeDate,
            judgementDate,
            dateOfDecision,
            caseFilingDate,
            caseStatus,
            lastHearingDate,
            nextHearingDate,
            natureResult,
            amountDepositeInCourt,
            claimAmount,
            depositedChequeNo,
            depositedchequeDate,
            addInterestAmountDistCourt,
            addInterestApplicationNo,
            addIntChequeNo,
            addIntChequeDate,
            bankGuaranteeAppNo,
            courtName,
            courtType,
            isActivated,
            freefield1,
            freefield2,
            freefield3,
            freefield4,
            freefield5,
            freefield6,
            freefield7,
            freefield8,
            freefield9,
            freefield10,
            lastModifiedBy,
            lastModified,
            organizationId,
            lawyerDetailsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourtCaseCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (landReferenceNo != null ? "landReferenceNo=" + landReferenceNo + ", " : "") +
            (caseNo != null ? "caseNo=" + caseNo + ", " : "") +
            (villageName != null ? "villageName=" + villageName + ", " : "") +
            (accuserName != null ? "accuserName=" + accuserName + ", " : "") +
            (defendantName != null ? "defendantName=" + defendantName + ", " : "") +
            (accuserLawyer != null ? "accuserLawyer=" + accuserLawyer + ", " : "") +
            (defendantLawyer != null ? "defendantLawyer=" + defendantLawyer + ", " : "") +
            (caseOfficer != null ? "caseOfficer=" + caseOfficer + ", " : "") +
            (caseOfficerAdd != null ? "caseOfficerAdd=" + caseOfficerAdd + ", " : "") +
            (totalArea != null ? "totalArea=" + totalArea + ", " : "") +
            (landAcquisitionOfficerNo != null ? "landAcquisitionOfficerNo=" + landAcquisitionOfficerNo + ", " : "") +
            (section11JudgmentNo != null ? "section11JudgmentNo=" + section11JudgmentNo + ", " : "") +
            (section4NoticeDate != null ? "section4NoticeDate=" + section4NoticeDate + ", " : "") +
            (judgementDate != null ? "judgementDate=" + judgementDate + ", " : "") +
            (dateOfDecision != null ? "dateOfDecision=" + dateOfDecision + ", " : "") +
            (caseFilingDate != null ? "caseFilingDate=" + caseFilingDate + ", " : "") +
            (caseStatus != null ? "caseStatus=" + caseStatus + ", " : "") +
            (lastHearingDate != null ? "lastHearingDate=" + lastHearingDate + ", " : "") +
            (nextHearingDate != null ? "nextHearingDate=" + nextHearingDate + ", " : "") +
            (natureResult != null ? "natureResult=" + natureResult + ", " : "") +
            (amountDepositeInCourt != null ? "amountDepositeInCourt=" + amountDepositeInCourt + ", " : "") +
            (claimAmount != null ? "claimAmount=" + claimAmount + ", " : "") +
            (depositedChequeNo != null ? "depositedChequeNo=" + depositedChequeNo + ", " : "") +
            (depositedchequeDate != null ? "depositedchequeDate=" + depositedchequeDate + ", " : "") +
            (addInterestAmountDistCourt != null ? "addInterestAmountDistCourt=" + addInterestAmountDistCourt + ", " : "") +
            (addInterestApplicationNo != null ? "addInterestApplicationNo=" + addInterestApplicationNo + ", " : "") +
            (addIntChequeNo != null ? "addIntChequeNo=" + addIntChequeNo + ", " : "") +
            (addIntChequeDate != null ? "addIntChequeDate=" + addIntChequeDate + ", " : "") +
            (bankGuaranteeAppNo != null ? "bankGuaranteeAppNo=" + bankGuaranteeAppNo + ", " : "") +
            (courtName != null ? "courtName=" + courtName + ", " : "") +
            (courtType != null ? "courtType=" + courtType + ", " : "") +
            (isActivated != null ? "isActivated=" + isActivated + ", " : "") +
            (freefield1 != null ? "freefield1=" + freefield1 + ", " : "") +
            (freefield2 != null ? "freefield2=" + freefield2 + ", " : "") +
            (freefield3 != null ? "freefield3=" + freefield3 + ", " : "") +
            (freefield4 != null ? "freefield4=" + freefield4 + ", " : "") +
            (freefield5 != null ? "freefield5=" + freefield5 + ", " : "") +
            (freefield6 != null ? "freefield6=" + freefield6 + ", " : "") +
            (freefield7 != null ? "freefield7=" + freefield7 + ", " : "") +
            (freefield8 != null ? "freefield8=" + freefield8 + ", " : "") +
            (freefield9 != null ? "freefield9=" + freefield9 + ", " : "") +
            (freefield10 != null ? "freefield10=" + freefield10 + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (organizationId != null ? "organizationId=" + organizationId + ", " : "") +
            (lawyerDetailsId != null ? "lawyerDetailsId=" + lawyerDetailsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
