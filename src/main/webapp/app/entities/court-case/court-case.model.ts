import { IOrganization } from 'app/entities/organization/organization.model';
import { ILawyerDetails } from 'app/entities/lawyer-details/lawyer-details.model';
import { CaseStatus } from 'app/entities/enumerations/case-status.model';
import { NatureResult } from 'app/entities/enumerations/nature-result.model';
import { CourtType } from 'app/entities/enumerations/court-type.model';

export interface ICourtCase {
  id?: number;
  landReferenceNo?: string | null;
  caseNo?: string | null;
  villageName?: string | null;
  accuserName?: string | null;
  defendantName?: string | null;
  accuserLawyer?: string | null;
  defendantLawyer?: string | null;
  caseOfficer?: string | null;
  caseOfficerAdd?: string | null;
  totalArea?: string | null;
  landAcquisitionOfficerNo?: string | null;
  section11JudgmentNo?: string | null;
  section4NoticeDate?: string | null;
  judgementDate?: string | null;
  dateOfDecision?: string | null;
  caseFilingDate?: string | null;
  caseStatus?: CaseStatus | null;
  lastHearingDate?: string | null;
  nextHearingDate?: string | null;
  natureResult?: NatureResult | null;
  amountDepositeInCourt?: string | null;
  claimAmount?: string | null;
  depositedChequeNo?: string | null;
  depositedchequeDate?: string | null;
  addInterestAmountDistCourt?: string | null;
  addInterestApplicationNo?: string | null;
  addIntChequeNo?: string | null;
  addIntChequeDate?: string | null;
  bankGuaranteeAppNo?: string | null;
  courtName?: string | null;
  courtType?: CourtType | null;
  isActivated?: boolean | null;
  freefield1?: string | null;
  freefield2?: string | null;
  freefield3?: string | null;
  freefield4?: string | null;
  freefield5?: string | null;
  freefield6?: string | null;
  freefield7?: string | null;
  freefield8?: string | null;
  freefield9?: string | null;
  freefield10?: string | null;
  lastModifiedBy?: string | null;
  lastModified?: string | null;
  organization?: IOrganization | null;
  lawyerDetails?: ILawyerDetails[] | null;
}

export class CourtCase implements ICourtCase {
  constructor(
    public id?: number,
    public landReferenceNo?: string | null,
    public caseNo?: string | null,
    public villageName?: string | null,
    public accuserName?: string | null,
    public defendantName?: string | null,
    public accuserLawyer?: string | null,
    public defendantLawyer?: string | null,
    public caseOfficer?: string | null,
    public caseOfficerAdd?: string | null,
    public totalArea?: string | null,
    public landAcquisitionOfficerNo?: string | null,
    public section11JudgmentNo?: string | null,
    public section4NoticeDate?: string | null,
    public judgementDate?: string | null,
    public dateOfDecision?: string | null,
    public caseFilingDate?: string | null,
    public caseStatus?: CaseStatus | null,
    public lastHearingDate?: string | null,
    public nextHearingDate?: string | null,
    public natureResult?: NatureResult | null,
    public amountDepositeInCourt?: string | null,
    public claimAmount?: string | null,
    public depositedChequeNo?: string | null,
    public depositedchequeDate?: string | null,
    public addInterestAmountDistCourt?: string | null,
    public addInterestApplicationNo?: string | null,
    public addIntChequeNo?: string | null,
    public addIntChequeDate?: string | null,
    public bankGuaranteeAppNo?: string | null,
    public courtName?: string | null,
    public courtType?: CourtType | null,
    public isActivated?: boolean | null,
    public freefield1?: string | null,
    public freefield2?: string | null,
    public freefield3?: string | null,
    public freefield4?: string | null,
    public freefield5?: string | null,
    public freefield6?: string | null,
    public freefield7?: string | null,
    public freefield8?: string | null,
    public freefield9?: string | null,
    public freefield10?: string | null,
    public lastModifiedBy?: string | null,
    public lastModified?: string | null,
    public organization?: IOrganization | null,
    public lawyerDetails?: ILawyerDetails[] | null
  ) {
    this.isActivated = this.isActivated ?? false;
  }
}

export function getCourtCaseIdentifier(courtCase: ICourtCase): number | undefined {
  return courtCase.id;
}
