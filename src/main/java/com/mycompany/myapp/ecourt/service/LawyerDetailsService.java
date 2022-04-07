package com.mycompany.myapp.ecourt.service;

import com.mycompany.myapp.ecourt.service.dto.LawyerDetailsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.ecourt.domain.LawyerDetails}.
 */
public interface LawyerDetailsService {
    /**
     * Save a lawyerDetails.
     *
     * @param lawyerDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    LawyerDetailsDTO save(LawyerDetailsDTO lawyerDetailsDTO);

    /**
     * Updates a lawyerDetails.
     *
     * @param lawyerDetailsDTO the entity to update.
     * @return the persisted entity.
     */
    LawyerDetailsDTO update(LawyerDetailsDTO lawyerDetailsDTO);

    /**
     * Partially updates a lawyerDetails.
     *
     * @param lawyerDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LawyerDetailsDTO> partialUpdate(LawyerDetailsDTO lawyerDetailsDTO);

    /**
     * Get all the lawyerDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LawyerDetailsDTO> findAll(Pageable pageable);

    /**
     * Get all the lawyerDetails with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LawyerDetailsDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" lawyerDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LawyerDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" lawyerDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
