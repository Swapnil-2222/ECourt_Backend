package com.mycompany.myapp.ecourt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.ecourt.domain.enumeration.CaseStatus;
import com.mycompany.myapp.ecourt.domain.enumeration.CourtType;
import com.mycompany.myapp.ecourt.domain.enumeration.NatureResult;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CourtCase.
 */
@Entity
@Table(name = "court_case")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CourtCase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "land_reference_no")
    private String landReferenceNo;

    @Column(name = "case_no")
    private String caseNo;

    @Column(name = "village_name")
    private String villageName;

    @Column(name = "accuser_name")
    private String accuserName;

    @Column(name = "defendant_name")
    private String defendantName;

    @Column(name = "accuser_lawyer")
    private String accuserLawyer;

    @Column(name = "defendant_lawyer")
    private String defendantLawyer;

    @Column(name = "case_officer")
    private String caseOfficer;

    @Column(name = "case_officer_add")
    private String caseOfficerAdd;

    @Column(name = "total_area")
    private String totalArea;

    @Column(name = "land_acquisition_officer_no")
    private String landAcquisitionOfficerNo;

    @Column(name = "section_11_judgment_no")
    private String section11JudgmentNo;

    @Column(name = "section_4_notice_date")
    private String section4NoticeDate;

    @Column(name = "judgement_date")
    private String judgementDate;

    @Column(name = "date_of_decision")
    private String dateOfDecision;

    @Column(name = "case_filing_date")
    private String caseFilingDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "case_status")
    private CaseStatus caseStatus;

    @Column(name = "last_hearing_date")
    private String lastHearingDate;

    @Column(name = "next_hearing_date")
    private String nextHearingDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "nature_result")
    private NatureResult natureResult;

    @Column(name = "amount_deposite_in_court")
    private String amountDepositeInCourt;

    @Column(name = "claim_amount")
    private String claimAmount;

    @Column(name = "deposited_cheque_no")
    private String depositedChequeNo;

    @Column(name = "depositedcheque_date")
    private String depositedchequeDate;

    @Column(name = "add_interest_amount_dist_court")
    private String addInterestAmountDistCourt;

    @Column(name = "add_interest_application_no")
    private String addInterestApplicationNo;

    @Column(name = "add_int_cheque_no")
    private String addIntChequeNo;

    @Column(name = "add_int_cheque_date")
    private String addIntChequeDate;

    @Column(name = "bank_guarantee_app_no")
    private String bankGuaranteeAppNo;

    @Column(name = "court_name")
    private String courtName;

    @Enumerated(EnumType.STRING)
    @Column(name = "court_type")
    private CourtType courtType;

    @Column(name = "is_activated")
    private Boolean isActivated;

    @Column(name = "freefield_1")
    private String freefield1;

    @Column(name = "freefield_2")
    private String freefield2;

    @Column(name = "freefield_3")
    private String freefield3;

    @Column(name = "freefield_4")
    private String freefield4;

    @Column(name = "freefield_5")
    private String freefield5;

    @Column(name = "freefield_6")
    private String freefield6;

    @Column(name = "freefield_7")
    private String freefield7;

    @Column(name = "freefield_8")
    private String freefield8;

    @Column(name = "freefield_9")
    private String freefield9;

    @Column(name = "freefield_10")
    private String freefield10;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified")
    private String lastModified;

    @ManyToOne
    private Organization organization;

    @ManyToMany(mappedBy = "courtCases")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "courtCases" }, allowSetters = true)
    private Set<LawyerDetails> lawyerDetails = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CourtCase id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLandReferenceNo() {
        return this.landReferenceNo;
    }

    public CourtCase landReferenceNo(String landReferenceNo) {
        this.setLandReferenceNo(landReferenceNo);
        return this;
    }

    public void setLandReferenceNo(String landReferenceNo) {
        this.landReferenceNo = landReferenceNo;
    }

    public String getCaseNo() {
        return this.caseNo;
    }

    public CourtCase caseNo(String caseNo) {
        this.setCaseNo(caseNo);
        return this;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getVillageName() {
        return this.villageName;
    }

    public CourtCase villageName(String villageName) {
        this.setVillageName(villageName);
        return this;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getAccuserName() {
        return this.accuserName;
    }

    public CourtCase accuserName(String accuserName) {
        this.setAccuserName(accuserName);
        return this;
    }

    public void setAccuserName(String accuserName) {
        this.accuserName = accuserName;
    }

    public String getDefendantName() {
        return this.defendantName;
    }

    public CourtCase defendantName(String defendantName) {
        this.setDefendantName(defendantName);
        return this;
    }

    public void setDefendantName(String defendantName) {
        this.defendantName = defendantName;
    }

    public String getAccuserLawyer() {
        return this.accuserLawyer;
    }

    public CourtCase accuserLawyer(String accuserLawyer) {
        this.setAccuserLawyer(accuserLawyer);
        return this;
    }

    public void setAccuserLawyer(String accuserLawyer) {
        this.accuserLawyer = accuserLawyer;
    }

    public String getDefendantLawyer() {
        return this.defendantLawyer;
    }

    public CourtCase defendantLawyer(String defendantLawyer) {
        this.setDefendantLawyer(defendantLawyer);
        return this;
    }

    public void setDefendantLawyer(String defendantLawyer) {
        this.defendantLawyer = defendantLawyer;
    }

    public String getCaseOfficer() {
        return this.caseOfficer;
    }

    public CourtCase caseOfficer(String caseOfficer) {
        this.setCaseOfficer(caseOfficer);
        return this;
    }

    public void setCaseOfficer(String caseOfficer) {
        this.caseOfficer = caseOfficer;
    }

    public String getCaseOfficerAdd() {
        return this.caseOfficerAdd;
    }

    public CourtCase caseOfficerAdd(String caseOfficerAdd) {
        this.setCaseOfficerAdd(caseOfficerAdd);
        return this;
    }

    public void setCaseOfficerAdd(String caseOfficerAdd) {
        this.caseOfficerAdd = caseOfficerAdd;
    }

    public String getTotalArea() {
        return this.totalArea;
    }

    public CourtCase totalArea(String totalArea) {
        this.setTotalArea(totalArea);
        return this;
    }

    public void setTotalArea(String totalArea) {
        this.totalArea = totalArea;
    }

    public String getLandAcquisitionOfficerNo() {
        return this.landAcquisitionOfficerNo;
    }

    public CourtCase landAcquisitionOfficerNo(String landAcquisitionOfficerNo) {
        this.setLandAcquisitionOfficerNo(landAcquisitionOfficerNo);
        return this;
    }

    public void setLandAcquisitionOfficerNo(String landAcquisitionOfficerNo) {
        this.landAcquisitionOfficerNo = landAcquisitionOfficerNo;
    }

    public String getSection11JudgmentNo() {
        return this.section11JudgmentNo;
    }

    public CourtCase section11JudgmentNo(String section11JudgmentNo) {
        this.setSection11JudgmentNo(section11JudgmentNo);
        return this;
    }

    public void setSection11JudgmentNo(String section11JudgmentNo) {
        this.section11JudgmentNo = section11JudgmentNo;
    }

    public String getSection4NoticeDate() {
        return this.section4NoticeDate;
    }

    public CourtCase section4NoticeDate(String section4NoticeDate) {
        this.setSection4NoticeDate(section4NoticeDate);
        return this;
    }

    public void setSection4NoticeDate(String section4NoticeDate) {
        this.section4NoticeDate = section4NoticeDate;
    }

    public String getJudgementDate() {
        return this.judgementDate;
    }

    public CourtCase judgementDate(String judgementDate) {
        this.setJudgementDate(judgementDate);
        return this;
    }

    public void setJudgementDate(String judgementDate) {
        this.judgementDate = judgementDate;
    }

    public String getDateOfDecision() {
        return this.dateOfDecision;
    }

    public CourtCase dateOfDecision(String dateOfDecision) {
        this.setDateOfDecision(dateOfDecision);
        return this;
    }

    public void setDateOfDecision(String dateOfDecision) {
        this.dateOfDecision = dateOfDecision;
    }

    public String getCaseFilingDate() {
        return this.caseFilingDate;
    }

    public CourtCase caseFilingDate(String caseFilingDate) {
        this.setCaseFilingDate(caseFilingDate);
        return this;
    }

    public void setCaseFilingDate(String caseFilingDate) {
        this.caseFilingDate = caseFilingDate;
    }

    public CaseStatus getCaseStatus() {
        return this.caseStatus;
    }

    public CourtCase caseStatus(CaseStatus caseStatus) {
        this.setCaseStatus(caseStatus);
        return this;
    }

    public void setCaseStatus(CaseStatus caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getLastHearingDate() {
        return this.lastHearingDate;
    }

    public CourtCase lastHearingDate(String lastHearingDate) {
        this.setLastHearingDate(lastHearingDate);
        return this;
    }

    public void setLastHearingDate(String lastHearingDate) {
        this.lastHearingDate = lastHearingDate;
    }

    public String getNextHearingDate() {
        return this.nextHearingDate;
    }

    public CourtCase nextHearingDate(String nextHearingDate) {
        this.setNextHearingDate(nextHearingDate);
        return this;
    }

    public void setNextHearingDate(String nextHearingDate) {
        this.nextHearingDate = nextHearingDate;
    }

    public NatureResult getNatureResult() {
        return this.natureResult;
    }

    public CourtCase natureResult(NatureResult natureResult) {
        this.setNatureResult(natureResult);
        return this;
    }

    public void setNatureResult(NatureResult natureResult) {
        this.natureResult = natureResult;
    }

    public String getAmountDepositeInCourt() {
        return this.amountDepositeInCourt;
    }

    public CourtCase amountDepositeInCourt(String amountDepositeInCourt) {
        this.setAmountDepositeInCourt(amountDepositeInCourt);
        return this;
    }

    public void setAmountDepositeInCourt(String amountDepositeInCourt) {
        this.amountDepositeInCourt = amountDepositeInCourt;
    }

    public String getClaimAmount() {
        return this.claimAmount;
    }

    public CourtCase claimAmount(String claimAmount) {
        this.setClaimAmount(claimAmount);
        return this;
    }

    public void setClaimAmount(String claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getDepositedChequeNo() {
        return this.depositedChequeNo;
    }

    public CourtCase depositedChequeNo(String depositedChequeNo) {
        this.setDepositedChequeNo(depositedChequeNo);
        return this;
    }

    public void setDepositedChequeNo(String depositedChequeNo) {
        this.depositedChequeNo = depositedChequeNo;
    }

    public String getDepositedchequeDate() {
        return this.depositedchequeDate;
    }

    public CourtCase depositedchequeDate(String depositedchequeDate) {
        this.setDepositedchequeDate(depositedchequeDate);
        return this;
    }

    public void setDepositedchequeDate(String depositedchequeDate) {
        this.depositedchequeDate = depositedchequeDate;
    }

    public String getAddInterestAmountDistCourt() {
        return this.addInterestAmountDistCourt;
    }

    public CourtCase addInterestAmountDistCourt(String addInterestAmountDistCourt) {
        this.setAddInterestAmountDistCourt(addInterestAmountDistCourt);
        return this;
    }

    public void setAddInterestAmountDistCourt(String addInterestAmountDistCourt) {
        this.addInterestAmountDistCourt = addInterestAmountDistCourt;
    }

    public String getAddInterestApplicationNo() {
        return this.addInterestApplicationNo;
    }

    public CourtCase addInterestApplicationNo(String addInterestApplicationNo) {
        this.setAddInterestApplicationNo(addInterestApplicationNo);
        return this;
    }

    public void setAddInterestApplicationNo(String addInterestApplicationNo) {
        this.addInterestApplicationNo = addInterestApplicationNo;
    }

    public String getAddIntChequeNo() {
        return this.addIntChequeNo;
    }

    public CourtCase addIntChequeNo(String addIntChequeNo) {
        this.setAddIntChequeNo(addIntChequeNo);
        return this;
    }

    public void setAddIntChequeNo(String addIntChequeNo) {
        this.addIntChequeNo = addIntChequeNo;
    }

    public String getAddIntChequeDate() {
        return this.addIntChequeDate;
    }

    public CourtCase addIntChequeDate(String addIntChequeDate) {
        this.setAddIntChequeDate(addIntChequeDate);
        return this;
    }

    public void setAddIntChequeDate(String addIntChequeDate) {
        this.addIntChequeDate = addIntChequeDate;
    }

    public String getBankGuaranteeAppNo() {
        return this.bankGuaranteeAppNo;
    }

    public CourtCase bankGuaranteeAppNo(String bankGuaranteeAppNo) {
        this.setBankGuaranteeAppNo(bankGuaranteeAppNo);
        return this;
    }

    public void setBankGuaranteeAppNo(String bankGuaranteeAppNo) {
        this.bankGuaranteeAppNo = bankGuaranteeAppNo;
    }

    public String getCourtName() {
        return this.courtName;
    }

    public CourtCase courtName(String courtName) {
        this.setCourtName(courtName);
        return this;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public CourtType getCourtType() {
        return this.courtType;
    }

    public CourtCase courtType(CourtType courtType) {
        this.setCourtType(courtType);
        return this;
    }

    public void setCourtType(CourtType courtType) {
        this.courtType = courtType;
    }

    public Boolean getIsActivated() {
        return this.isActivated;
    }

    public CourtCase isActivated(Boolean isActivated) {
        this.setIsActivated(isActivated);
        return this;
    }

    public void setIsActivated(Boolean isActivated) {
        this.isActivated = isActivated;
    }

    public String getFreefield1() {
        return this.freefield1;
    }

    public CourtCase freefield1(String freefield1) {
        this.setFreefield1(freefield1);
        return this;
    }

    public void setFreefield1(String freefield1) {
        this.freefield1 = freefield1;
    }

    public String getFreefield2() {
        return this.freefield2;
    }

    public CourtCase freefield2(String freefield2) {
        this.setFreefield2(freefield2);
        return this;
    }

    public void setFreefield2(String freefield2) {
        this.freefield2 = freefield2;
    }

    public String getFreefield3() {
        return this.freefield3;
    }

    public CourtCase freefield3(String freefield3) {
        this.setFreefield3(freefield3);
        return this;
    }

    public void setFreefield3(String freefield3) {
        this.freefield3 = freefield3;
    }

    public String getFreefield4() {
        return this.freefield4;
    }

    public CourtCase freefield4(String freefield4) {
        this.setFreefield4(freefield4);
        return this;
    }

    public void setFreefield4(String freefield4) {
        this.freefield4 = freefield4;
    }

    public String getFreefield5() {
        return this.freefield5;
    }

    public CourtCase freefield5(String freefield5) {
        this.setFreefield5(freefield5);
        return this;
    }

    public void setFreefield5(String freefield5) {
        this.freefield5 = freefield5;
    }

    public String getFreefield6() {
        return this.freefield6;
    }

    public CourtCase freefield6(String freefield6) {
        this.setFreefield6(freefield6);
        return this;
    }

    public void setFreefield6(String freefield6) {
        this.freefield6 = freefield6;
    }

    public String getFreefield7() {
        return this.freefield7;
    }

    public CourtCase freefield7(String freefield7) {
        this.setFreefield7(freefield7);
        return this;
    }

    public void setFreefield7(String freefield7) {
        this.freefield7 = freefield7;
    }

    public String getFreefield8() {
        return this.freefield8;
    }

    public CourtCase freefield8(String freefield8) {
        this.setFreefield8(freefield8);
        return this;
    }

    public void setFreefield8(String freefield8) {
        this.freefield8 = freefield8;
    }

    public String getFreefield9() {
        return this.freefield9;
    }

    public CourtCase freefield9(String freefield9) {
        this.setFreefield9(freefield9);
        return this;
    }

    public void setFreefield9(String freefield9) {
        this.freefield9 = freefield9;
    }

    public String getFreefield10() {
        return this.freefield10;
    }

    public CourtCase freefield10(String freefield10) {
        this.setFreefield10(freefield10);
        return this;
    }

    public void setFreefield10(String freefield10) {
        this.freefield10 = freefield10;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public CourtCase lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public CourtCase lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public CourtCase organization(Organization organization) {
        this.setOrganization(organization);
        return this;
    }

    public Set<LawyerDetails> getLawyerDetails() {
        return this.lawyerDetails;
    }

    public void setLawyerDetails(Set<LawyerDetails> lawyerDetails) {
        if (this.lawyerDetails != null) {
            this.lawyerDetails.forEach(i -> i.removeCourtCase(this));
        }
        if (lawyerDetails != null) {
            lawyerDetails.forEach(i -> i.addCourtCase(this));
        }
        this.lawyerDetails = lawyerDetails;
    }

    public CourtCase lawyerDetails(Set<LawyerDetails> lawyerDetails) {
        this.setLawyerDetails(lawyerDetails);
        return this;
    }

    public CourtCase addLawyerDetails(LawyerDetails lawyerDetails) {
        this.lawyerDetails.add(lawyerDetails);
        lawyerDetails.getCourtCases().add(this);
        return this;
    }

    public CourtCase removeLawyerDetails(LawyerDetails lawyerDetails) {
        this.lawyerDetails.remove(lawyerDetails);
        lawyerDetails.getCourtCases().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourtCase)) {
            return false;
        }
        return id != null && id.equals(((CourtCase) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourtCase{" +
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
            "}";
    }
}
