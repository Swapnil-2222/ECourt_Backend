package com.mycompany.myapp.ecourt.service;

import com.mycompany.myapp.ecourt.domain.*; // for static metamodels
import com.mycompany.myapp.ecourt.domain.LawyerDetails;
import com.mycompany.myapp.ecourt.repository.LawyerDetailsRepository;
import com.mycompany.myapp.ecourt.service.criteria.LawyerDetailsCriteria;
import com.mycompany.myapp.ecourt.service.dto.LawyerDetailsDTO;
import com.mycompany.myapp.ecourt.service.mapper.LawyerDetailsMapper;
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
 * Service for executing complex queries for {@link LawyerDetails} entities in the database.
 * The main input is a {@link LawyerDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LawyerDetailsDTO} or a {@link Page} of {@link LawyerDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LawyerDetailsQueryService extends QueryService<LawyerDetails> {

    private final Logger log = LoggerFactory.getLogger(LawyerDetailsQueryService.class);

    private final LawyerDetailsRepository lawyerDetailsRepository;

    private final LawyerDetailsMapper lawyerDetailsMapper;

    public LawyerDetailsQueryService(LawyerDetailsRepository lawyerDetailsRepository, LawyerDetailsMapper lawyerDetailsMapper) {
        this.lawyerDetailsRepository = lawyerDetailsRepository;
        this.lawyerDetailsMapper = lawyerDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link LawyerDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LawyerDetailsDTO> findByCriteria(LawyerDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LawyerDetails> specification = createSpecification(criteria);
        return lawyerDetailsMapper.toDto(lawyerDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LawyerDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LawyerDetailsDTO> findByCriteria(LawyerDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LawyerDetails> specification = createSpecification(criteria);
        return lawyerDetailsRepository.findAll(specification, page).map(lawyerDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LawyerDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LawyerDetails> specification = createSpecification(criteria);
        return lawyerDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link LawyerDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LawyerDetails> createSpecification(LawyerDetailsCriteria criteria) {
        Specification<LawyerDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LawyerDetails_.id));
            }
            if (criteria.getFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullName(), LawyerDetails_.fullName));
            }
            if (criteria.getContactNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactNo(), LawyerDetails_.contactNo));
            }
            if (criteria.getEmailId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailId(), LawyerDetails_.emailId));
            }
            if (criteria.getBarRegnNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBarRegnNo(), LawyerDetails_.barRegnNo));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), LawyerDetails_.address));
            }
            if (criteria.getLawyerExperience() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLawyerExperience(), LawyerDetails_.lawyerExperience));
            }
            if (criteria.getFreefield1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield1(), LawyerDetails_.freefield1));
            }
            if (criteria.getFreefield2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield2(), LawyerDetails_.freefield2));
            }
            if (criteria.getUserType() != null) {
                specification = specification.and(buildSpecification(criteria.getUserType(), LawyerDetails_.userType));
            }
            if (criteria.getLawyerType() != null) {
                specification = specification.and(buildSpecification(criteria.getLawyerType(), LawyerDetails_.lawyerType));
            }
            if (criteria.getIsActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActivated(), LawyerDetails_.isActivated));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), LawyerDetails_.lastModifiedBy));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), LawyerDetails_.lastModified));
            }
            if (criteria.getCourtCaseId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCourtCaseId(),
                            root -> root.join(LawyerDetails_.courtCases, JoinType.LEFT).get(CourtCase_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
