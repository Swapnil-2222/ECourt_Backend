package com.mycompany.myapp.ecourt.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Organization.
 */
@Entity
@Table(name = "organization")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "address")
    private String address;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "pincode")
    private String pincode;

    @Column(name = "website")
    private String website;

    @Column(name = "freefield_1")
    private String freefield1;

    @Column(name = "freefield_2")
    private String freefield2;

    @Column(name = "is_activated")
    private Boolean isActivated;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified")
    private String lastModified;

    @Column(name = "created_on")
    private String createdOn;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Organization id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public Organization organizationName(String organizationName) {
        this.setOrganizationName(organizationName);
        return this;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getContactNo() {
        return this.contactNo;
    }

    public Organization contactNo(String contactNo) {
        this.setContactNo(contactNo);
        return this;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return this.address;
    }

    public Organization address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public Organization emailAddress(String emailAddress) {
        this.setEmailAddress(emailAddress);
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPincode() {
        return this.pincode;
    }

    public Organization pincode(String pincode) {
        this.setPincode(pincode);
        return this;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getWebsite() {
        return this.website;
    }

    public Organization website(String website) {
        this.setWebsite(website);
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFreefield1() {
        return this.freefield1;
    }

    public Organization freefield1(String freefield1) {
        this.setFreefield1(freefield1);
        return this;
    }

    public void setFreefield1(String freefield1) {
        this.freefield1 = freefield1;
    }

    public String getFreefield2() {
        return this.freefield2;
    }

    public Organization freefield2(String freefield2) {
        this.setFreefield2(freefield2);
        return this;
    }

    public void setFreefield2(String freefield2) {
        this.freefield2 = freefield2;
    }

    public Boolean getIsActivated() {
        return this.isActivated;
    }

    public Organization isActivated(Boolean isActivated) {
        this.setIsActivated(isActivated);
        return this;
    }

    public void setIsActivated(Boolean isActivated) {
        this.isActivated = isActivated;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Organization lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public Organization lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getCreatedOn() {
        return this.createdOn;
    }

    public Organization createdOn(String createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organization)) {
            return false;
        }
        return id != null && id.equals(((Organization) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Organization{" +
            "id=" + getId() +
            ", organizationName='" + getOrganizationName() + "'" +
            ", contactNo='" + getContactNo() + "'" +
            ", address='" + getAddress() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", pincode='" + getPincode() + "'" +
            ", website='" + getWebsite() + "'" +
            ", freefield1='" + getFreefield1() + "'" +
            ", freefield2='" + getFreefield2() + "'" +
            ", isActivated='" + getIsActivated() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            "}";
    }
}
