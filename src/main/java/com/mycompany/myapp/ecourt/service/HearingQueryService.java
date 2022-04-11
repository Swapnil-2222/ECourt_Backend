package com.mycompany.myapp.ecourt.service;

import com.mycompany.myapp.ecourt.domain.*; // for static metamodels
import com.mycompany.myapp.ecourt.domain.Hearing;
import com.mycompany.myapp.ecourt.repository.HearingRepository;
import com.mycompany.myapp.ecourt.service.criteria.HearingCriteria;
import com.mycompany.myapp.ecourt.service.dto.HearingDTO;
import com.mycompany.myapp.ecourt.service.mapper.HearingMapper;
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
 * Service for executing complex queries for {@link Hearing} entities in the database.
 * The main input is a {@link HearingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HearingDTO} or a {@link Page} of {@link HearingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HearingQueryService extends QueryService<Hearing> {

    private final Logger log = LoggerFactory.getLogger(HearingQueryService.class);

    private final HearingRepository hearingRepository;

    private final HearingMapper hearingMapper;

    public HearingQueryService(HearingRepository hearingRepository, HearingMapper hearingMapper) {
        this.hearingRepository = hearingRepository;
        this.hearingMapper = hearingMapper;
    }

    /**
     * Return a {@link List} of {@link HearingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HearingDTO> findByCriteria(HearingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Hearing> specification = createSpecification(criteria);
        return hearingMapper.toDto(hearingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link HearingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HearingDTO> findByCriteria(HearingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Hearing> specification = createSpecification(criteria);
        return hearingRepository.findAll(specification, page).map(hearingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HearingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Hearing> specification = createSpecification(criteria);
        return hearingRepository.count(specification);
    }

    /**
     * Function to convert {@link HearingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Hearing> createSpecification(HearingCriteria criteria) {
        Specification<Hearing> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Hearing_.id));
            }
            if (criteria.getHearingDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHearingDate(), Hearing_.hearingDate));
            }
            if (criteria.getNextHearingDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNextHearingDate(), Hearing_.nextHearingDate));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Hearing_.description));
            }
            if (criteria.getPreviousHearingDate() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPreviousHearingDate(), Hearing_.previousHearingDate));
            }
            if (criteria.getConclusion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConclusion(), Hearing_.conclusion));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), Hearing_.comment));
            }
            if (criteria.getCaseStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getCaseStatus(), Hearing_.caseStatus));
            }
            if (criteria.getFreefield1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield1(), Hearing_.freefield1));
            }
            if (criteria.getFreefield2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield2(), Hearing_.freefield2));
            }
            if (criteria.getFreefield3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield3(), Hearing_.freefield3));
            }
            if (criteria.getFreefield4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield4(), Hearing_.freefield4));
            }
            if (criteria.getFreefield5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield5(), Hearing_.freefield5));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Hearing_.lastModifiedBy));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), Hearing_.lastModified));
            }
            if (criteria.getCourtCaseId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCourtCaseId(),
                            root -> root.join(Hearing_.courtCase, JoinType.LEFT).get(CourtCase_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
