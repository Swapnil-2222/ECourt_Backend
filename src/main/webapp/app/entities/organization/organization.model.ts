export interface IOrganization {
  id?: number;
  organizationName?: string | null;
  contactNo?: string | null;
  address?: string | null;
  emailAddress?: string | null;
  pincode?: string | null;
  website?: string | null;
  freefield1?: string | null;
  freefield2?: string | null;
  isActivated?: boolean | null;
  lastModifiedBy?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
}

export class Organization implements IOrganization {
  constructor(
    public id?: number,
    public organizationName?: string | null,
    public contactNo?: string | null,
    public address?: string | null,
    public emailAddress?: string | null,
    public pincode?: string | null,
    public website?: string | null,
    public freefield1?: string | null,
    public freefield2?: string | null,
    public isActivated?: boolean | null,
    public lastModifiedBy?: string | null,
    public lastModified?: string | null,
    public createdOn?: string | null
  ) {
    this.isActivated = this.isActivated ?? false;
  }
}

export function getOrganizationIdentifier(organization: IOrganization): number | undefined {
  return organization.id;
}
