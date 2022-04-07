package com.mycompany.myapp.ecourt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.ecourt.domain.enumeration.CaseStatus;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Hearing.
 */
@Entity
@Table(name = "hearing")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Hearing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "hearing_date")
    private String hearingDate;

    @Column(name = "next_hearing_date")
    private String nextHearingDate;

    @Column(name = "description")
    private String description;

    @Column(name = "previous_hearing_date")
    private String previousHearingDate;

    @Column(name = "conclusion")
    private String conclusion;

    @Column(name = "comment")
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "case_status")
    private CaseStatus caseStatus;

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

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified")
    private String lastModified;

    @ManyToOne
    @JsonIgnoreProperties(value = { "organization", "lawyerDetails" }, allowSetters = true)
    private CourtCase courtCase;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Hearing id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHearingDate() {
        return this.hearingDate;
    }

    public Hearing hearingDate(String hearingDate) {
        this.setHearingDate(hearingDate);
        return this;
    }

    public void setHearingDate(String hearingDate) {
        this.hearingDate = hearingDate;
    }

    public String getNextHearingDate() {
        return this.nextHearingDate;
    }

    public Hearing nextHearingDate(String nextHearingDate) {
        this.setNextHearingDate(nextHearingDate);
        return this;
    }

    public void setNextHearingDate(String nextHearingDate) {
        this.nextHearingDate = nextHearingDate;
    }

    public String getDescription() {
        return this.description;
    }

    public Hearing description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreviousHearingDate() {
        return this.previousHearingDate;
    }

    public Hearing previousHearingDate(String previousHearingDate) {
        this.setPreviousHearingDate(previousHearingDate);
        return this;
    }

    public void setPreviousHearingDate(String previousHearingDate) {
        this.previousHearingDate = previousHearingDate;
    }

    public String getConclusion() {
        return this.conclusion;
    }

    public Hearing conclusion(String conclusion) {
        this.setConclusion(conclusion);
        return this;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getComment() {
        return this.comment;
    }

    public Hearing comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CaseStatus getCaseStatus() {
        return this.caseStatus;
    }

    public Hearing caseStatus(CaseStatus caseStatus) {
        this.setCaseStatus(caseStatus);
        return this;
    }

    public void setCaseStatus(CaseStatus caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getFreefield1() {
        return this.freefield1;
    }

    public Hearing freefield1(String freefield1) {
        this.setFreefield1(freefield1);
        return this;
    }

    public void setFreefield1(String freefield1) {
        this.freefield1 = freefield1;
    }

    public String getFreefield2() {
        return this.freefield2;
    }

    public Hearing freefield2(String freefield2) {
        this.setFreefield2(freefield2);
        return this;
    }

    public void setFreefield2(String freefield2) {
        this.freefield2 = freefield2;
    }

    public String getFreefield3() {
        return this.freefield3;
    }

    public Hearing freefield3(String freefield3) {
        this.setFreefield3(freefield3);
        return this;
    }

    public void setFreefield3(String freefield3) {
        this.freefield3 = freefield3;
    }

    public String getFreefield4() {
        return this.freefield4;
    }

    public Hearing freefield4(String freefield4) {
        this.setFreefield4(freefield4);
        return this;
    }

    public void setFreefield4(String freefield4) {
        this.freefield4 = freefield4;
    }

    public String getFreefield5() {
        return this.freefield5;
    }

    public Hearing freefield5(String freefield5) {
        this.setFreefield5(freefield5);
        return this;
    }

    public void setFreefield5(String freefield5) {
        this.freefield5 = freefield5;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Hearing lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public Hearing lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public CourtCase getCourtCase() {
        return this.courtCase;
    }

    public void setCourtCase(CourtCase courtCase) {
        this.courtCase = courtCase;
    }

    public Hearing courtCase(CourtCase courtCase) {
        this.setCourtCase(courtCase);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hearing)) {
            return false;
        }
        return id != null && id.equals(((Hearing) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hearing{" +
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
            "}";
    }
}
