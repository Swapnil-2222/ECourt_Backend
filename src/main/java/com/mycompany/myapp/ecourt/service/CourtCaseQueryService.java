package com.mycompany.myapp.ecourt.service;

import com.mycompany.myapp.ecourt.domain.*; // for static metamodels
import com.mycompany.myapp.ecourt.domain.CourtCase;
import com.mycompany.myapp.ecourt.repository.CourtCaseRepository;
import com.mycompany.myapp.ecourt.service.criteria.CourtCaseCriteria;
import com.mycompany.myapp.ecourt.service.dto.CourtCaseDTO;
import com.mycompany.myapp.ecourt.service.mapper.CourtCaseMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link CourtCase} entities in the database.
 * The main input is a {@link CourtCaseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourtCaseDTO} or a {@link Page} of {@link CourtCaseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourtCaseQueryService extends QueryService<CourtCase> {

    private final Logger log = LoggerFactory.getLogger(CourtCaseQueryService.class);

    private final CourtCaseRepository courtCaseRepository;

    private final CourtCaseMapper courtCaseMapper;

    public CourtCaseQueryService(CourtCaseRepository courtCaseRepository, CourtCaseMapper courtCaseMapper) {
        this.courtCaseRepository = courtCaseRepository;
        this.courtCaseMapper = courtCaseMapper;
    }

    /**
     * Return a {@link List} of {@link CourtCaseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourtCaseDTO> findByCriteria(CourtCaseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CourtCase> specification = createSpecification(criteria);
        return courtCaseMapper.toDto(courtCaseRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CourtCaseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourtCaseDTO> findByCriteria(CourtCaseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CourtCase> specification = createSpecification(criteria);
        return courtCaseRepository.findAll(specification, page).map(courtCaseMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourtCaseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CourtCase> specification = createSpecification(criteria);
        return courtCaseRepository.count(specification);
    }

    /**
     * Function to convert {@link CourtCaseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CourtCase> createSpecification(CourtCaseCriteria criteria) {
        Specification<CourtCase> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CourtCase_.id));
            }
            if (criteria.getLandReferenceNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLandReferenceNo(), CourtCase_.landReferenceNo));
            }
            if (criteria.getCaseNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCaseNo(), CourtCase_.caseNo));
            }
            if (criteria.getVillageName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVillageName(), CourtCase_.villageName));
            }
            if (criteria.getAccuserName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccuserName(), CourtCase_.accuserName));
            }
            if (criteria.getDefendantName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDefendantName(), CourtCase_.defendantName));
            }
            if (criteria.getAccuserLawyer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccuserLawyer(), CourtCase_.accuserLawyer));
            }
            if (criteria.getDefendantLawyer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDefendantLawyer(), CourtCase_.defendantLawyer));
            }
            if (criteria.getCaseOfficer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCaseOfficer(), CourtCase_.caseOfficer));
            }
            if (criteria.getCaseOfficerAdd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCaseOfficerAdd(), CourtCase_.caseOfficerAdd));
            }
            if (criteria.getTotalArea() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotalArea(), CourtCase_.totalArea));
            }
            if (criteria.getLandAcquisitionOfficerNo() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getLandAcquisitionOfficerNo(), CourtCase_.landAcquisitionOfficerNo)
                    );
            }
            if (criteria.getSection11JudgmentNo() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSection11JudgmentNo(), CourtCase_.section11JudgmentNo));
            }
            if (criteria.getSection4NoticeDate() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSection4NoticeDate(), CourtCase_.section4NoticeDate));
            }
            if (criteria.getJudgementDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJudgementDate(), CourtCase_.judgementDate));
            }
            if (criteria.getDateOfDecision() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDateOfDecision(), CourtCase_.dateOfDecision));
            }
            if (criteria.getCaseFilingDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCaseFilingDate(), CourtCase_.caseFilingDate));
            }
            if (criteria.getCaseStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getCaseStatus(), CourtCase_.caseStatus));
            }
            if (criteria.getLastHearingDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastHearingDate(), CourtCase_.lastHearingDate));
            }
            if (criteria.getNextHearingDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNextHearingDate(), CourtCase_.nextHearingDate));
            }
            if (criteria.getNatureResult() != null) {
                specification = specification.and(buildSpecification(criteria.getNatureResult(), CourtCase_.natureResult));
            }
            if (criteria.getAmountDepositeInCourt() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAmountDepositeInCourt(), CourtCase_.amountDepositeInCourt));
            }
            if (criteria.getClaimAmount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClaimAmount(), CourtCase_.claimAmount));
            }
            if (criteria.getDepositedChequeNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepositedChequeNo(), CourtCase_.depositedChequeNo));
            }
            if (criteria.getDepositedchequeDate() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDepositedchequeDate(), CourtCase_.depositedchequeDate));
            }
            if (criteria.getAddInterestAmountDistCourt() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getAddInterestAmountDistCourt(), CourtCase_.addInterestAmountDistCourt)
                    );
            }
            if (criteria.getAddInterestApplicationNo() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getAddInterestApplicationNo(), CourtCase_.addInterestApplicationNo)
                    );
            }
            if (criteria.getAddIntChequeNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddIntChequeNo(), CourtCase_.addIntChequeNo));
            }
            if (criteria.getAddIntChequeDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddIntChequeDate(), CourtCase_.addIntChequeDate));
            }
            if (criteria.getBankGuaranteeAppNo() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getBankGuaranteeAppNo(), CourtCase_.bankGuaranteeAppNo));
            }
            if (criteria.getCourtName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCourtName(), CourtCase_.courtName));
            }
            if (criteria.getCourtType() != null) {
                specification = specification.and(buildSpecification(criteria.getCourtType(), CourtCase_.courtType));
            }
            if (criteria.getCaseType() != null) {
                specification = specification.and(buildSpecification(criteria.getCaseType(), CourtCase_.caseType));
            }
            if (criteria.getIsActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActivated(), CourtCase_.isActivated));
            }
            if (criteria.getFreefield1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield1(), CourtCase_.freefield1));
            }
            if (criteria.getFreefield2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield2(), CourtCase_.freefield2));
            }
            if (criteria.getFreefield3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield3(), CourtCase_.freefield3));
            }
            if (criteria.getFreefield4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield4(), CourtCase_.freefield4));
            }
            if (criteria.getFreefield5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield5(), CourtCase_.freefield5));
            }
            if (criteria.getFreefield6() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield6(), CourtCase_.freefield6));
            }
            if (criteria.getFreefield7() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield7(), CourtCase_.freefield7));
            }
            if (criteria.getFreefield8() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield8(), CourtCase_.freefield8));
            }
            if (criteria.getFreefield9() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield9(), CourtCase_.freefield9));
            }
            if (criteria.getFreefield10() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield10(), CourtCase_.freefield10));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), CourtCase_.lastModifiedBy));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), CourtCase_.lastModified));
            }
            if (criteria.getOrganizationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrganizationId(),
                            root -> root.join(CourtCase_.organization, JoinType.LEFT).get(Organization_.id)
                        )
                    );
            }
            if (criteria.getLawyerDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLawyerDetailsId(),
                            root -> root.join(CourtCase_.lawyerDetails, JoinType.LEFT).get(LawyerDetails_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
