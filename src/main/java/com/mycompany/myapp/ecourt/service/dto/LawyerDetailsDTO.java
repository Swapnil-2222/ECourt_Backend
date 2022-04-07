package com.mycompany.myapp.ecourt.service.dto;

import com.mycompany.myapp.ecourt.domain.enumeration.LawyerType;
import com.mycompany.myapp.ecourt.domain.enumeration.UserType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.myapp.ecourt.domain.LawyerDetails} entity.
 */
public class LawyerDetailsDTO implements Serializable {

    private Long id;

    private String fullName;

    private String contactNo;

    private String emailId;

    private String barRegnNo;

    private String address;

    private String lawyerExperience;

    private String freefield1;

    private String freefield2;

    private UserType userType;

    private LawyerType lawyerType;

    private Boolean isActivated;

    private String lastModifiedBy;

    private String lastModified;

    private Set<CourtCaseDTO> courtCases = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getBarRegnNo() {
        return barRegnNo;
    }

    public void setBarRegnNo(String barRegnNo) {
        this.barRegnNo = barRegnNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLawyerExperience() {
        return lawyerExperience;
    }

    public void setLawyerExperience(String lawyerExperience) {
        this.lawyerExperience = lawyerExperience;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public LawyerType getLawyerType() {
        return lawyerType;
    }

    public void setLawyerType(LawyerType lawyerType) {
        this.lawyerType = lawyerType;
    }

    public Boolean getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(Boolean isActivated) {
        this.isActivated = isActivated;
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

    public Set<CourtCaseDTO> getCourtCases() {
        return courtCases;
    }

    public void setCourtCases(Set<CourtCaseDTO> courtCases) {
        this.courtCases = courtCases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LawyerDetailsDTO)) {
            return false;
        }

        LawyerDetailsDTO lawyerDetailsDTO = (LawyerDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, lawyerDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LawyerDetailsDTO{" +
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
            ", courtCases=" + getCourtCases() +
            "}";
    }
}
