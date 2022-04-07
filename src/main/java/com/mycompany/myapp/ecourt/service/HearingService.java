package com.mycompany.myapp.ecourt.service;

import com.mycompany.myapp.ecourt.service.dto.HearingDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.ecourt.domain.Hearing}.
 */
public interface HearingService {
    /**
     * Save a hearing.
     *
     * @param hearingDTO the entity to save.
     * @return the persisted entity.
     */
    HearingDTO save(HearingDTO hearingDTO);

    /**
     * Updates a hearing.
     *
     * @param hearingDTO the entity to update.
     * @return the persisted entity.
     */
    HearingDTO update(HearingDTO hearingDTO);

    /**
     * Partially updates a hearing.
     *
     * @param hearingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HearingDTO> partialUpdate(HearingDTO hearingDTO);

    /**
     * Get all the hearings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HearingDTO> findAll(Pageable pageable);

    /**
     * Get all the hearings with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HearingDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" hearing.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HearingDTO> findOne(Long id);

    /**
     * Delete the "id" hearing.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
