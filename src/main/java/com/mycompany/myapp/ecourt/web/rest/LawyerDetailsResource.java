package com.mycompany.myapp.ecourt.web.rest;

import com.mycompany.myapp.ecourt.repository.LawyerDetailsRepository;
import com.mycompany.myapp.ecourt.service.LawyerDetailsQueryService;
import com.mycompany.myapp.ecourt.service.LawyerDetailsService;
import com.mycompany.myapp.ecourt.service.criteria.LawyerDetailsCriteria;
import com.mycompany.myapp.ecourt.service.dto.LawyerDetailsDTO;
import com.mycompany.myapp.ecourt.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.ecourt.domain.LawyerDetails}.
 */
@RestController
@RequestMapping("/api")
public class LawyerDetailsResource {

    private final Logger log = LoggerFactory.getLogger(LawyerDetailsResource.class);

    private static final String ENTITY_NAME = "lawyerDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LawyerDetailsService lawyerDetailsService;

    private final LawyerDetailsRepository lawyerDetailsRepository;

    private final LawyerDetailsQueryService lawyerDetailsQueryService;

    public LawyerDetailsResource(
        LawyerDetailsService lawyerDetailsService,
        LawyerDetailsRepository lawyerDetailsRepository,
        LawyerDetailsQueryService lawyerDetailsQueryService
    ) {
        this.lawyerDetailsService = lawyerDetailsService;
        this.lawyerDetailsRepository = lawyerDetailsRepository;
        this.lawyerDetailsQueryService = lawyerDetailsQueryService;
    }

    /**
     * {@code POST  /lawyer-details} : Create a new lawyerDetails.
     *
     * @param lawyerDetailsDTO the lawyerDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lawyerDetailsDTO, or with status {@code 400 (Bad Request)} if the lawyerDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lawyer-details")
    public ResponseEntity<LawyerDetailsDTO> createLawyerDetails(@RequestBody LawyerDetailsDTO lawyerDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save LawyerDetails : {}", lawyerDetailsDTO);
        if (lawyerDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new lawyerDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LawyerDetailsDTO result = lawyerDetailsService.save(lawyerDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/lawyer-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lawyer-details/:id} : Updates an existing lawyerDetails.
     *
     * @param id the id of the lawyerDetailsDTO to save.
     * @param lawyerDetailsDTO the lawyerDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lawyerDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the lawyerDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lawyerDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lawyer-details/{id}")
    public ResponseEntity<LawyerDetailsDTO> updateLawyerDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LawyerDetailsDTO lawyerDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LawyerDetails : {}, {}", id, lawyerDetailsDTO);
        if (lawyerDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lawyerDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lawyerDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LawyerDetailsDTO result = lawyerDetailsService.update(lawyerDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lawyerDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /lawyer-details/:id} : Partial updates given fields of an existing lawyerDetails, field will ignore if it is null
     *
     * @param id the id of the lawyerDetailsDTO to save.
     * @param lawyerDetailsDTO the lawyerDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lawyerDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the lawyerDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the lawyerDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the lawyerDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lawyer-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LawyerDetailsDTO> partialUpdateLawyerDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LawyerDetailsDTO lawyerDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LawyerDetails partially : {}, {}", id, lawyerDetailsDTO);
        if (lawyerDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lawyerDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lawyerDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LawyerDetailsDTO> result = lawyerDetailsService.partialUpdate(lawyerDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lawyerDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /lawyer-details} : get all the lawyerDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lawyerDetails in body.
     */
    @GetMapping("/lawyer-details")
    public ResponseEntity<List<LawyerDetailsDTO>> getAllLawyerDetails(
        LawyerDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LawyerDetails by criteria: {}", criteria);
        Page<LawyerDetailsDTO> page = lawyerDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lawyer-details/count} : count all the lawyerDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/lawyer-details/count")
    public ResponseEntity<Long> countLawyerDetails(LawyerDetailsCriteria criteria) {
        log.debug("REST request to count LawyerDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(lawyerDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /lawyer-details/:id} : get the "id" lawyerDetails.
     *
     * @param id the id of the lawyerDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lawyerDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lawyer-details/{id}")
    public ResponseEntity<LawyerDetailsDTO> getLawyerDetails(@PathVariable Long id) {
        log.debug("REST request to get LawyerDetails : {}", id);
        Optional<LawyerDetailsDTO> lawyerDetailsDTO = lawyerDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lawyerDetailsDTO);
    }

    /**
     * {@code DELETE  /lawyer-details/:id} : delete the "id" lawyerDetails.
     *
     * @param id the id of the lawyerDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lawyer-details/{id}")
    public ResponseEntity<Void> deleteLawyerDetails(@PathVariable Long id) {
        log.debug("REST request to delete LawyerDetails : {}", id);
        lawyerDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
