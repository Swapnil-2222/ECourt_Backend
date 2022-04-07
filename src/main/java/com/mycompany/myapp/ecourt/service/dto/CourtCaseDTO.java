package com.mycompany.myapp.ecourt.service.dto;

import com.mycompany.myapp.ecourt.domain.enumeration.CaseStatus;
import com.mycompany.myapp.ecourt.domain.enumeration.CourtType;
import com.mycompany.myapp.ecourt.domain.enumeration.NatureResult;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.ecourt.domain.CourtCase} entity.
 */
public class CourtCaseDTO implements Serializable {

    private Long id;

    private String landReferenceNo;

    private String caseNo;

    private String villageName;

    private String accuserName;

    private String defendantName;

    private String accuserLawyer;

    private String defendantLawyer;

    private String caseOfficer;

    private String caseOfficerAdd;

    private String totalArea;

    private String landAcquisitionOfficerNo;

    private String section11JudgmentNo;

    private String section4NoticeDate;

    private String judgementDate;

    private String dateOfDecision;

    private String caseFilingDate;

    private CaseStatus caseStatus;

    private String lastHearingDate;

    private String nextHearingDate;

    private NatureResult natureResult;

    private String amountDepositeInCourt;

    private String claimAmount;

    private String depositedChequeNo;

    private String depositedchequeDate;

    private String addInterestAmountDistCourt;

    private String addInterestApplicationNo;

    private String addIntChequeNo;

    private String addIntChequeDate;

    private String bankGuaranteeAppNo;

    private String courtName;

    private CourtType courtType;

    private Boolean isActivated;

    private String freefield1;

    private String freefield2;

    private String freefield3;

    private String freefield4;

    private String freefield5;

    private String freefield6;

    private String freefield7;

    private String freefield8;

    private String freefield9;

    private String freefield10;

    private String lastModifiedBy;

    private String lastModified;

    private OrganizationDTO organization;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLandReferenceNo() {
        return landReferenceNo;
    }

    public void setLandReferenceNo(String landReferenceNo) {
        this.landReferenceNo = landReferenceNo;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getAccuserName() {
        return accuserName;
    }

    public void setAccuserName(String accuserName) {
        this.accuserName = accuserName;
    }

    public String getDefendantName() {
        return defendantName;
    }

    public void setDefendantName(String defendantName) {
        this.defendantName = defendantName;
    }

    public String getAccuserLawyer() {
        return accuserLawyer;
    }

    public void setAccuserLawyer(String accuserLawyer) {
        this.accuserLawyer = accuserLawyer;
    }

    public String getDefendantLawyer() {
        return defendantLawyer;
    }

    public void setDefendantLawyer(String defendantLawyer) {
        this.defendantLawyer = defendantLawyer;
    }

    public String getCaseOfficer() {
        return caseOfficer;
    }

    public void setCaseOfficer(String caseOfficer) {
        this.caseOfficer = caseOfficer;
    }

    public String getCaseOfficerAdd() {
        return caseOfficerAdd;
    }

    public void setCaseOfficerAdd(String caseOfficerAdd) {
        this.caseOfficerAdd = caseOfficerAdd;
    }

    public String getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(String totalArea) {
        this.totalArea = totalArea;
    }

    public String getLandAcquisitionOfficerNo() {
        return landAcquisitionOfficerNo;
    }

    public void setLandAcquisitionOfficerNo(String landAcquisitionOfficerNo) {
        this.landAcquisitionOfficerNo = landAcquisitionOfficerNo;
    }

    public String getSection11JudgmentNo() {
        return section11JudgmentNo;
    }

    public void setSection11JudgmentNo(String section11JudgmentNo) {
        this.section11JudgmentNo = section11JudgmentNo;
    }

    public String getSection4NoticeDate() {
        return section4NoticeDate;
    }

    public void setSection4NoticeDate(String section4NoticeDate) {
        this.section4NoticeDate = section4NoticeDate;
    }

    public String getJudgementDate() {
        return judgementDate;
    }

    public void setJudgementDate(String judgementDate) {
        this.judgementDate = judgementDate;
    }

    public String getDateOfDecision() {
        return dateOfDecision;
    }

    public void setDateOfDecision(String dateOfDecision) {
        this.dateOfDecision = dateOfDecision;
    }

    public String getCaseFilingDate() {
        return caseFilingDate;
    }

    public void setCaseFilingDate(String caseFilingDate) {
        this.caseFilingDate = caseFilingDate;
    }

    public CaseStatus getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(CaseStatus caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getLastHearingDate() {
        return lastHearingDate;
    }

    public void setLastHearingDate(String lastHearingDate) {
        this.lastHearingDate = lastHearingDate;
    }

    public String getNextHearingDate() {
        return nextHearingDate;
    }

    public void setNextHearingDate(String nextHearingDate) {
        this.nextHearingDate = nextHearingDate;
    }

    public NatureResult getNatureResult() {
        return natureResult;
    }

    public void setNatureResult(NatureResult natureResult) {
        this.natureResult = natureResult;
    }

    public String getAmountDepositeInCourt() {
        return amountDepositeInCourt;
    }

    public void setAmountDepositeInCourt(String amountDepositeInCourt) {
        this.amountDepositeInCourt = amountDepositeInCourt;
    }

    public String getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(String claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getDepositedChequeNo() {
        return depositedChequeNo;
    }

    public void setDepositedChequeNo(String depositedChequeNo) {
        this.depositedChequeNo = depositedChequeNo;
    }

    public String getDepositedchequeDate() {
        return depositedchequeDate;
    }

    public void setDepositedchequeDate(String depositedchequeDate) {
        this.depositedchequeDate = depositedchequeDate;
    }

    public String getAddInterestAmountDistCourt() {
        return addInterestAmountDistCourt;
    }

    public void setAddInterestAmountDistCourt(String addInterestAmountDistCourt) {
        this.addInterestAmountDistCourt = addInterestAmountDistCourt;
    }

    public String getAddInterestApplicationNo() {
        return addInterestApplicationNo;
    }

    public void setAddInterestApplicationNo(String addInterestApplicationNo) {
        this.addInterestApplicationNo = addInterestApplicationNo;
    }

    public String getAddIntChequeNo() {
        return addIntChequeNo;
    }

    public void setAddIntChequeNo(String addIntChequeNo) {
        this.addIntChequeNo = addIntChequeNo;
    }

    public String getAddIntChequeDate() {
        return addIntChequeDate;
    }

    public void setAddIntChequeDate(String addIntChequeDate) {
        this.addIntChequeDate = addIntChequeDate;
    }

    public String getBankGuaranteeAppNo() {
        return bankGuaranteeAppNo;
    }

    public void setBankGuaranteeAppNo(String bankGuaranteeAppNo) {
        this.bankGuaranteeAppNo = bankGuaranteeAppNo;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public CourtType getCourtType() {
        return courtType;
    }

    public void setCourtType(CourtType courtType) {
        this.courtType = courtType;
    }

    public Boolean getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(Boolean isActivated) {
        this.isActivated = isActivated;
    }

    public String getFreefield1() {
        return freefield1;
    }

    public void setFreefield1(String freefield1) {
        this.freefield1 = freefield1;
    }

    public String getFreefield2() {
        return freefield2;
    }

    public void setFreefield2(String freefield2) {
        this.freefield2 = freefield2;
    }

    public String getFreefield3() {
        return freefield3;
    }

    public void setFreefield3(String freefield3) {
        this.freefield3 = freefield3;
    }

    public String getFreefield4() {
        return freefield4;
    }

    public void setFreefield4(String freefield4) {
        this.freefield4 = freefield4;
    }

    public String getFreefield5() {
        return freefield5;
    }

    public void setFreefield5(String freefield5) {
        this.freefield5 = freefield5;
    }

    public String getFreefield6() {
        return freefield6;
    }

    public void setFreefield6(String freefield6) {
        this.freefield6 = freefield6;
    }

    public String getFreefield7() {
        return freefield7;
    }

    public void setFreefield7(String freefield7) {
        this.freefield7 = freefield7;
    }

    public String getFreefield8() {
        return freefield8;
    }

    public void setFreefield8(String freefield8) {
        this.freefield8 = freefield8;
    }

    public String getFreefield9() {
        return freefield9;
    }

    public void setFreefield9(String freefield9) {
        this.freefield9 = freefield9;
    }

    public String getFreefield10() {
        return freefield10;
    }

    public void setFreefield10(String freefield10) {
        this.freefield10 = freefield10;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public OrganizationDTO getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDTO organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourtCaseDTO)) {
            return false;
        }

        CourtCaseDTO courtCaseDTO = (CourtCaseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, courtCaseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourtCaseDTO{" +
            "id=" + getId() +
            ", landReferenceNo='" + getLandReferenceNo() + "'" +
            ", caseNo='" + getCaseNo() + "'" +
            ", villageName='" + getVillageName() + "'" +
            ", accuserName='" + getAccuserName() + "'" +
            ", defendantName='" + getDefendantName() + "'" +
            ", accuserLawyer='" + getAccuserLawyer() + "'" +
            ", defendantLawyer='" + getDefendantLawyer() + "'" +
            ", caseOfficer='" + getCaseOfficer() + "'" +
            ", caseOfficerAdd='" + getCaseOfficerAdd() + "'" +
            ", totalArea='" + getTotalArea() + "'" +
            ", landAcquisitionOfficerNo='" + getLandAcquisitionOfficerNo() + "'" +
            ", section11JudgmentNo='" + getSection11JudgmentNo() + "'" +
            ", section4NoticeDate='" + getSection4NoticeDate() + "'" +
            ", judgementDate='" + getJudgementDate() + "'" +
            ", dateOfDecision='" + getDateOfDecision() + "'" +
            ", caseFilingDate='" + getCaseFilingDate() + "'" +
            ", caseStatus='" + getCaseStatus() + "'" +
            ", lastHearingDate='" + getLastHearingDate() + "'" +
            ", nextHearingDate='" + getNextHearingDate() + "'" +
            ", natureResult='" + getNatureResult() + "'" +
            ", amountDepositeInCourt='" + getAmountDepositeInCourt() + "'" +
            ", claimAmount='" + getClaimAmount() + "'" +
            ", depositedChequeNo='" + getDepositedChequeNo() + "'" +
            ", depositedchequeDate='" + getDepositedchequeDate() + "'" +
            ", addInterestAmountDistCourt='" + getAddInterestAmountDistCourt() + "'" +
            ", addInterestApplicationNo='" + getAddInterestApplicationNo() + "'" +
            ", addIntChequeNo='" + getAddIntChequeNo() + "'" +
            ", addIntChequeDate='" + getAddIntChequeDate() + "'" +
            ", bankGuaranteeAppNo='" + getBankGuaranteeAppNo() + "'" +
            ", courtName='" + getCourtName() + "'" +
            ", courtType='" + getCourtType() + "'" +
            ", isActivated='" + getIsActivated() + "'" +
            ", freefield1='" + getFreefield1() + "'" +
            ", freefield2='" + getFreefield2() + "'" +
            ", freefield3='" + getFreefield3() + "'" +
            ", freefield4='" + getFreefield4() + "'" +
            ", freefield5='" + getFreefield5() + "'" +
            ", freefield6='" + getFreefield6() + "'" +
            ", freefield7='" + getFreefield7() + "'" +
            ", freefield8='" + getFreefield8() + "'" +
            ", freefield9='" + getFreefield9() + "'" +
            ", freefield10='" + getFreefield10() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", organization=" + getOrganization() +
            "}";
    }
}
