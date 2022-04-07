import { ICourtCase } from 'app/entities/court-case/court-case.model';
import { UserType } from 'app/entities/enumerations/user-type.model';
import { LawyerType } from 'app/entities/enumerations/lawyer-type.model';

export interface ILawyerDetails {
  id?: number;
  fullName?: string | null;
  contactNo?: string | null;
  emailId?: string | null;
  barRegnNo?: string | null;
  address?: string | null;
  lawyerExperience?: string | null;
  freefield1?: string | null;
  freefield2?: string | null;
  userType?: UserType | null;
  lawyerType?: LawyerType | null;
  isActivated?: boolean | null;
  lastModifiedBy?: string | null;
  lastModified?: string | null;
  courtCases?: ICourtCase[] | null;
}

export class LawyerDetails implements ILawyerDetails {
  constructor(
    public id?: number,
    public fullName?: string | null,
    public contactNo?: string | null,
    public emailId?: string | null,
    public barRegnNo?: string | null,
    public address?: string | null,
    public lawyerExperience?: string | null,
    public freefield1?: string | null,
    public freefield2?: string | null,
    public userType?: UserType | null,
    public lawyerType?: LawyerType | null,
    public isActivated?: boolean | null,
    public lastModifiedBy?: string | null,
    public lastModified?: string | null,
    public courtCases?: ICourtCase[] | null
  ) {
    this.isActivated = this.isActivated ?? false;
  }
}

export function getLawyerDetailsIdentifier(lawyerDetails: ILawyerDetails): number | undefined {
  return lawyerDetails.id;
}
