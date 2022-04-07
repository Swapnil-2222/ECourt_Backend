package com.mycompany.myapp.ecourt.repository;

import com.mycompany.myapp.ecourt.domain.LawyerDetails;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class LawyerDetailsRepositoryWithBagRelationshipsImpl implements LawyerDetailsRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<LawyerDetails> fetchBagRelationships(Optional<LawyerDetails> lawyerDetails) {
        return lawyerDetails.map(this::fetchCourtCases);
    }

    @Override
    public Page<LawyerDetails> fetchBagRelationships(Page<LawyerDetails> lawyerDetails) {
        return new PageImpl<>(
            fetchBagRelationships(lawyerDetails.getContent()),
            lawyerDetails.getPageable(),
            lawyerDetails.getTotalElements()
        );
    }

    @Override
    public List<LawyerDetails> fetchBagRelationships(List<LawyerDetails> lawyerDetails) {
        return Optional.of(lawyerDetails).map(this::fetchCourtCases).orElse(Collections.emptyList());
    }

    LawyerDetails fetchCourtCases(LawyerDetails result) {
        return entityManager
            .createQuery(
                "select lawyerDetails from LawyerDetails lawyerDetails left join fetch lawyerDetails.courtCases where lawyerDetails is :lawyerDetails",
                LawyerDetails.class
            )
            .setParameter("lawyerDetails", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<LawyerDetails> fetchCourtCases(List<LawyerDetails> lawyerDetails) {
        return entityManager
            .createQuery(
                "select distinct lawyerDetails from LawyerDetails lawyerDetails left join fetch lawyerDetails.courtCases where lawyerDetails in :lawyerDetails",
                LawyerDetails.class
            )
            .setParameter("lawyerDetails", lawyerDetails)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
