import { ICourtCase } from 'app/entities/court-case/court-case.model';
import { CaseStatus } from 'app/entities/enumerations/case-status.model';

export interface IHearing {
  id?: number;
  hearingDate?: string | null;
  nextHearingDate?: string | null;
  description?: string | null;
  previousHearingDate?: string | null;
  conclusion?: string | null;
  comment?: string | null;
  caseStatus?: CaseStatus | null;
  freefield1?: string | null;
  freefield2?: string | null;
  freefield3?: string | null;
  freefield4?: string | null;
  freefield5?: string | null;
  lastModifiedBy?: string | null;
  lastModified?: string | null;
  courtCase?: ICourtCase | null;
}

export class Hearing implements IHearing {
  constructor(
    public id?: number,
    public hearingDate?: string | null,
    public nextHearingDate?: string | null,
    public description?: string | null,
    public previousHearingDate?: string | null,
    public conclusion?: string | null,
    public comment?: string | null,
    public caseStatus?: CaseStatus | null,
    public freefield1?: string | null,
    public freefield2?: string | null,
    public freefield3?: string | null,
    public freefield4?: string | null,
    public freefield5?: string | null,
    public lastModifiedBy?: string | null,
    public lastModified?: string | null,
    public courtCase?: ICourtCase | null
  ) {}
}

export function getHearingIdentifier(hearing: IHearing): number | undefined {
  return hearing.id;
}
