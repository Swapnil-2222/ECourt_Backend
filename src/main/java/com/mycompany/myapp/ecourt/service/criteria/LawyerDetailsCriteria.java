package com.mycompany.myapp.ecourt.service.criteria;

import com.mycompany.myapp.ecourt.domain.enumeration.LawyerType;
import com.mycompany.myapp.ecourt.domain.enumeration.UserType;
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
 * Criteria class for the {@link com.mycompany.myapp.ecourt.domain.LawyerDetails} entity. This class is used
 * in {@link com.mycompany.myapp.ecourt.web.rest.LawyerDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /lawyer-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class LawyerDetailsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering UserType
     */
    public static class UserTypeFilter extends Filter<UserType> {

        public UserTypeFilter() {}

        public UserTypeFilter(UserTypeFilter filter) {
            super(filter);
        }

        @Override
        public UserTypeFilter copy() {
            return new UserTypeFilter(this);
        }
    }

    /**
     * Class for filtering LawyerType
     */
    public static class LawyerTypeFilter extends Filter<LawyerType> {

        public LawyerTypeFilter() {}

        public LawyerTypeFilter(LawyerTypeFilter filter) {
            super(filter);
        }

        @Override
        public LawyerTypeFilter copy() {
            return new LawyerTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fullName;

    private StringFilter contactNo;

    private StringFilter emailId;

    private StringFilter barRegnNo;

    private StringFilter address;

    private StringFilter lawyerExperience;

    private StringFilter freefield1;

    private StringFilter freefield2;

    private UserTypeFilter userType;

    private LawyerTypeFilter lawyerType;

    private BooleanFilter isActivated;

    private StringFilter lastModifiedBy;

    private StringFilter lastModified;

    private LongFilter courtCaseId;

    private Boolean distinct;

    public LawyerDetailsCriteria() {}

    public LawyerDetailsCriteria(LawyerDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fullName = other.fullName == null ? null : other.fullName.copy();
        this.contactNo = other.contactNo == null ? null : other.contactNo.copy();
        this.emailId = other.emailId == null ? null : other.emailId.copy();
        this.barRegnNo = other.barRegnNo == null ? null : other.barRegnNo.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.lawyerExperience = other.lawyerExperience == null ? null : other.lawyerExperience.copy();
        this.freefield1 = other.freefield1 == null ? null : other.freefield1.copy();
        this.freefield2 = other.freefield2 == null ? null : other.freefield2.copy();
        this.userType = other.userType == null ? null : other.userType.copy();
        this.lawyerType = other.lawyerType == null ? null : other.lawyerType.copy();
        this.isActivated = other.isActivated == null ? null : other.isActivated.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.courtCaseId = other.courtCaseId == null ? null : other.courtCaseId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LawyerDetailsCriteria copy() {
        return new LawyerDetailsCriteria(this);
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

    public StringFilter getFullName() {
        return fullName;
    }

    public StringFilter fullName() {
        if (fullName == null) {
            fullName = new StringFilter();
        }
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public StringFilter getContactNo() {
        return contactNo;
    }

    public StringFilter contactNo() {
        if (contactNo == null) {
            contactNo = new StringFilter();
        }
        return contactNo;
    }

    public void setContactNo(StringFilter contactNo) {
        this.contactNo = contactNo;
    }

    public StringFilter getEmailId() {
        return emailId;
    }

    public StringFilter emailId() {
        if (emailId == null) {
            emailId = new StringFilter();
        }
        return emailId;
    }

    public void setEmailId(StringFilter emailId) {
        this.emailId = emailId;
    }

    public StringFilter getBarRegnNo() {
        return barRegnNo;
    }

    public StringFilter barRegnNo() {
        if (barRegnNo == null) {
            barRegnNo = new StringFilter();
        }
        return barRegnNo;
    }

    public void setBarRegnNo(StringFilter barRegnNo) {
        this.barRegnNo = barRegnNo;
    }

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getLawyerExperience() {
        return lawyerExperience;
    }

    public StringFilter lawyerExperience() {
        if (lawyerExperience == null) {
            lawyerExperience = new StringFilter();
        }
        return lawyerExperience;
    }

    public void setLawyerExperience(StringFilter lawyerExperience) {
        this.lawyerExperience = lawyerExperience;
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

    public UserTypeFilter getUserType() {
        return userType;
    }

    public UserTypeFilter userType() {
        if (userType == null) {
            userType = new UserTypeFilter();
        }
        return userType;
    }

    public void setUserType(UserTypeFilter userType) {
        this.userType = userType;
    }

    public LawyerTypeFilter getLawyerType() {
        return lawyerType;
    }

    public LawyerTypeFilter lawyerType() {
        if (lawyerType == null) {
            lawyerType = new LawyerTypeFilter();
        }
        return lawyerType;
    }

    public void setLawyerType(LawyerTypeFilter lawyerType) {
        this.lawyerType = lawyerType;
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

    public LongFilter getCourtCaseId() {
        return courtCaseId;
    }

    public LongFilter courtCaseId() {
        if (courtCaseId == null) {
            courtCaseId = new LongFilter();
        }
        return courtCaseId;
    }

    public void setCourtCaseId(LongFilter courtCaseId) {
        this.courtCaseId = courtCaseId;
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
        final LawyerDetailsCriteria that = (LawyerDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(contactNo, that.contactNo) &&
            Objects.equals(emailId, that.emailId) &&
            Objects.equals(barRegnNo, that.barRegnNo) &&
            Objects.equals(address, that.address) &&
            Objects.equals(lawyerExperience, that.lawyerExperience) &&
            Objects.equals(freefield1, that.freefield1) &&
            Objects.equals(freefield2, that.freefield2) &&
            Objects.equals(userType, that.userType) &&
            Objects.equals(lawyerType, that.lawyerType) &&
            Objects.equals(isActivated, that.isActivated) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(courtCaseId, that.courtCaseId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            fullName,
            contactNo,
            emailId,
            barRegnNo,
            address,
            lawyerExperience,
            freefield1,
            freefield2,
            userType,
            lawyerType,
            isActivated,
            lastModifiedBy,
            lastModified,
            courtCaseId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LawyerDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (fullName != null ? "fullName=" + fullName + ", " : "") +
            (contactNo != null ? "contactNo=" + contactNo + ", " : "") +
            (emailId != null ? "emailId=" + emailId + ", " : "") +
            (barRegnNo != null ? "barRegnNo=" + barRegnNo + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (lawyerExperience != null ? "lawyerExperience=" + lawyerExperience + ", " : "") +
            (freefield1 != null ? "freefield1=" + freefield1 + ", " : "") +
            (freefield2 != null ? "freefield2=" + freefield2 + ", " : "") +
            (userType != null ? "userType=" + userType + ", " : "") +
            (lawyerType != null ? "lawyerType=" + lawyerType + ", " : "") +
            (isActivated != null ? "isActivated=" + isActivated + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (courtCaseId != null ? "courtCaseId=" + courtCaseId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
