package com.mycompany.myapp.ecourt.service.criteria;

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
 * Criteria class for the {@link com.mycompany.myapp.ecourt.domain.Organization} entity. This class is used
 * in {@link com.mycompany.myapp.ecourt.web.rest.OrganizationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /organizations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class OrganizationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter organizationName;

    private StringFilter contactNo;

    private StringFilter address;

    private StringFilter emailAddress;

    private StringFilter pincode;

    private StringFilter website;

    private StringFilter freefield1;

    private StringFilter freefield2;

    private BooleanFilter isActivated;

    private StringFilter lastModifiedBy;

    private StringFilter lastModified;

    private StringFilter createdOn;

    private Boolean distinct;

    public OrganizationCriteria() {}

    public OrganizationCriteria(OrganizationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.organizationName = other.organizationName == null ? null : other.organizationName.copy();
        this.contactNo = other.contactNo == null ? null : other.contactNo.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.emailAddress = other.emailAddress == null ? null : other.emailAddress.copy();
        this.pincode = other.pincode == null ? null : other.pincode.copy();
        this.website = other.website == null ? null : other.website.copy();
        this.freefield1 = other.freefield1 == null ? null : other.freefield1.copy();
        this.freefield2 = other.freefield2 == null ? null : other.freefield2.copy();
        this.isActivated = other.isActivated == null ? null : other.isActivated.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.distinct = other.distinct;
    }

    @Override
    public OrganizationCriteria copy() {
        return new OrganizationCriteria(this);
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

    public StringFilter getOrganizationName() {
        return organizationName;
    }

    public StringFilter organizationName() {
        if (organizationName == null) {
            organizationName = new StringFilter();
        }
        return organizationName;
    }

    public void setOrganizationName(StringFilter organizationName) {
        this.organizationName = organizationName;
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

    public StringFilter getEmailAddress() {
        return emailAddress;
    }

    public StringFilter emailAddress() {
        if (emailAddress == null) {
            emailAddress = new StringFilter();
        }
        return emailAddress;
    }

    public void setEmailAddress(StringFilter emailAddress) {
        this.emailAddress = emailAddress;
    }

    public StringFilter getPincode() {
        return pincode;
    }

    public StringFilter pincode() {
        if (pincode == null) {
            pincode = new StringFilter();
        }
        return pincode;
    }

    public void setPincode(StringFilter pincode) {
        this.pincode = pincode;
    }

    public StringFilter getWebsite() {
        return website;
    }

    public StringFilter website() {
        if (website == null) {
            website = new StringFilter();
        }
        return website;
    }

    public void setWebsite(StringFilter website) {
        this.website = website;
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

    public StringFilter getCreatedOn() {
        return createdOn;
    }

    public StringFilter createdOn() {
        if (createdOn == null) {
            createdOn = new StringFilter();
        }
        return createdOn;
    }

    public void setCreatedOn(StringFilter createdOn) {
        this.createdOn = createdOn;
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
        final OrganizationCriteria that = (OrganizationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(organizationName, that.organizationName) &&
            Objects.equals(contactNo, that.contactNo) &&
            Objects.equals(address, that.address) &&
            Objects.equals(emailAddress, that.emailAddress) &&
            Objects.equals(pincode, that.pincode) &&
            Objects.equals(website, that.website) &&
            Objects.equals(freefield1, that.freefield1) &&
            Objects.equals(freefield2, that.freefield2) &&
            Objects.equals(isActivated, that.isActivated) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            organizationName,
            contactNo,
            address,
            emailAddress,
            pincode,
            website,
            freefield1,
            freefield2,
            isActivated,
            lastModifiedBy,
            lastModified,
            createdOn,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (organizationName != null ? "organizationName=" + organizationName + ", " : "") +
            (contactNo != null ? "contactNo=" + contactNo + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (emailAddress != null ? "emailAddress=" + emailAddress + ", " : "") +
            (pincode != null ? "pincode=" + pincode + ", " : "") +
            (website != null ? "website=" + website + ", " : "") +
            (freefield1 != null ? "freefield1=" + freefield1 + ", " : "") +
            (freefield2 != null ? "freefield2=" + freefield2 + ", " : "") +
            (isActivated != null ? "isActivated=" + isActivated + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
