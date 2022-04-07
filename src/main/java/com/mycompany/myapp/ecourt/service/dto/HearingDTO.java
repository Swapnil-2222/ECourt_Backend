package com.mycompany.myapp.ecourt.service.dto;

import com.mycompany.myapp.ecourt.domain.enumeration.CaseStatus;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.ecourt.domain.Hearing} entity.
 */
public class HearingDTO implements Serializable {

    private Long id;

    private String hearingDate;

    private String nextHearingDate;

    private String description;

    private String previousHearingDate;

    private String conclusion;

    private String comment;

    private CaseStatus caseStatus;

    private String freefield1;

    private String freefield2;

    private String freefield3;

    private String freefield4;

    private String freefield5;

    private String lastModifiedBy;

    private String lastModified;

    private CourtCaseDTO courtCase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHearingDate() {
        return hearingDate;
    }

    public void setHearingDate(String hearingDate) {
        this.hearingDate = hearingDate;
    }

    public String getNextHearingDate() {
        return nextHearingDate;
    }

    public void setNextHearingDate(String nextHearingDate) {
        this.nextHearingDate = nextHearingDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreviousHearingDate() {
        return previousHearingDate;
    }

    public void setPreviousHearingDate(String previousHearingDate) {
        this.previousHearingDate = previousHearingDate;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CaseStatus getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(CaseStatus caseStatus) {
        this.caseStatus = caseStatus;
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

    public CourtCaseDTO getCourtCase() {
        return courtCase;
    }

    public void setCourtCase(CourtCaseDTO courtCase) {
        this.courtCase = courtCase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HearingDTO)) {
            return false;
        }

        HearingDTO hearingDTO = (HearingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hearingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HearingDTO{" +
            "id=" + getId() +
            ", hearingDate='" + getHearingDate() + "'" +
            ", nextHearingDate='" + getNextHearingDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", previousHearingDate='" + getPreviousHearingDate() + "'" +
            ", conclusion='" + getConclusion() + "'" +
            ", comment='" + getComment() + "'" +
            ", caseStatus='" + getCaseStatus() + "'" +
            ", freefield1='" + getFreefield1() + "'" +
            ", freefield2='" + getFreefield2() + "'" +
            ", freefield3='" + getFreefield3() + "'" +
            ", freefield4='" + getFreefield4() + "'" +
            ", freefield5='" + getFreefield5() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", courtCase=" + getCourtCase() +
            "}";
    }
}
