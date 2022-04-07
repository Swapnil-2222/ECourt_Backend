package com.mycompany.myapp.ecourt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.ecourt.domain.enumeration.LawyerType;
import com.mycompany.myapp.ecourt.domain.enumeration.UserType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LawyerDetails.
 */
@Entity
@Table(name = "lawyer_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LawyerDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "bar_regn_no")
    private String barRegnNo;

    @Column(name = "address")
    private String address;

    @Column(name = "lawyer_experience")
    private String lawyerExperience;

    @Column(name = "freefield_1")
    private String freefield1;

    @Column(name = "freefield_2")
    private String freefield2;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

    @Enumerated(EnumType.STRING)
    @Column(name = "lawyer_type")
    private LawyerType lawyerType;

    @Column(name = "is_activated")
    private Boolean isActivated;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified")
    private String lastModified;

    @ManyToMany
    @JoinTable(
        name = "rel_lawyer_details__court_case",
        joinColumns = @JoinColumn(name = "lawyer_details_id"),
        inverseJoinColumns = @JoinColumn(name = "court_case_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "organization", "lawyerDetails" }, allowSetters = true)
    private Set<CourtCase> courtCases = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LawyerDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public LawyerDetails fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContactNo() {
        return this.contactNo;
    }

    public LawyerDetails contactNo(String contactNo) {
        this.setContactNo(contactNo);
        return this;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmailId() {
        return this.emailId;
    }

    public LawyerDetails emailId(String emailId) {
        this.setEmailId(emailId);
        return this;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getBarRegnNo() {
        return this.barRegnNo;
    }

    public LawyerDetails barRegnNo(String barRegnNo) {
        this.setBarRegnNo(barRegnNo);
        return this;
    }

    public void setBarRegnNo(String barRegnNo) {
        this.barRegnNo = barRegnNo;
    }

    public String getAddress() {
        return this.address;
    }

    public LawyerDetails address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLawyerExperience() {
        return this.lawyerExperience;
    }

    public LawyerDetails lawyerExperience(String lawyerExperience) {
        this.setLawyerExperience(lawyerExperience);
        return this;
    }

    public void setLawyerExperience(String lawyerExperience) {
        this.lawyerExperience = lawyerExperience;
    }

    public String getFreefield1() {
        return this.freefield1;
    }

    public LawyerDetails freefield1(String freefield1) {
        this.setFreefield1(freefield1);
        return this;
    }

    public void setFreefield1(String freefield1) {
        this.freefield1 = freefield1;
    }

    public String getFreefield2() {
        return this.freefield2;
    }

    public LawyerDetails freefield2(String freefield2) {
        this.setFreefield2(freefield2);
        return this;
    }

    public void setFreefield2(String freefield2) {
        this.freefield2 = freefield2;
    }

    public UserType getUserType() {
        return this.userType;
    }

    public LawyerDetails userType(UserType userType) {
        this.setUserType(userType);
        return this;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public LawyerType getLawyerType() {
        return this.lawyerType;
    }

    public LawyerDetails lawyerType(LawyerType lawyerType) {
        this.setLawyerType(lawyerType);
        return this;
    }

    public void setLawyerType(LawyerType lawyerType) {
        this.lawyerType = lawyerType;
    }

    public Boolean getIsActivated() {
        return this.isActivated;
    }

    public LawyerDetails isActivated(Boolean isActivated) {
        this.setIsActivated(isActivated);
        return this;
    }

    public void setIsActivated(Boolean isActivated) {
        this.isActivated = isActivated;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public LawyerDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public LawyerDetails lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public Set<CourtCase> getCourtCases() {
        return this.courtCases;
    }

    public void setCourtCases(Set<CourtCase> courtCases) {
        this.courtCases = courtCases;
    }

    public LawyerDetails courtCases(Set<CourtCase> courtCases) {
        this.setCourtCases(courtCases);
        return this;
    }

    public LawyerDetails addCourtCase(CourtCase courtCase) {
        this.courtCases.add(courtCase);
        courtCase.getLawyerDetails().add(this);
        return this;
    }

    public LawyerDetails removeCourtCase(CourtCase courtCase) {
        this.courtCases.remove(courtCase);
        courtCase.getLawyerDetails().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LawyerDetails)) {
            return false;
        }
        return id != null && id.equals(((LawyerDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LawyerDetails{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", contactNo='" + getContactNo() + "'" +
            ", emailId='" + getEmailId() + "'" +
            ", barRegnNo='" + getBarRegnNo() + "'" +
            ", address='" + getAddress() + "'" +
            ", lawyerExperience='" + getLawyerExperience() + "'" +
            ", freefield1='" + getFreefield1() + "'" +
            ", freefield2='" + getFreefield2() + "'" +
            ", userType='" + getUserType() + "'" +
            ", lawyerType='" + getLawyerType() + "'" +
            ", isActivated='" + getIsActivated() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            "}";
    }
}
