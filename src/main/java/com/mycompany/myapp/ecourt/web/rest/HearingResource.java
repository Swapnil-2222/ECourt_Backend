package com.mycompany.myapp.ecourt.web.rest;

import com.mycompany.myapp.ecourt.repository.HearingRepository;
import com.mycompany.myapp.ecourt.service.HearingService;
import com.mycompany.myapp.ecourt.service.dto.HearingDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.ecourt.domain.Hearing}.
 */
@RestController
@RequestMapping("/api")
public class HearingResource {

    private final Logger log = LoggerFactory.getLogger(HearingResource.class);

    private static final String ENTITY_NAME = "hearing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HearingService hearingService;

    private final HearingRepository hearingRepository;

    public HearingResource(HearingService hearingService, HearingRepository hearingRepository) {
        this.hearingService = hearingService;
        this.hearingRepository = hearingRepository;
    }

    /**
     * {@code POST  /hearings} : Create a new hearing.
     *
     * @param hearingDTO the hearingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hearingDTO, or with status {@code 400 (Bad Request)} if the hearing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hearings")
    public ResponseEntity<HearingDTO> createHearing(@RequestBody HearingDTO hearingDTO) throws URISyntaxException {
        log.debug("REST request to save Hearing : {}", hearingDTO);
        if (hearingDTO.getId() != null) {
            throw new BadRequestAlertException("A new hearing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HearingDTO result = hearingService.save(hearingDTO);
        return ResponseEntity
            .created(new URI("/api/hearings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hearings/:id} : Updates an existing hearing.
     *
     * @param id the id of the hearingDTO to save.
     * @param hearingDTO the hearingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hearingDTO,
     * or with status {@code 400 (Bad Request)} if the hearingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hearingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hearings/{id}")
    public ResponseEntity<HearingDTO> updateHearing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HearingDTO hearingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Hearing : {}, {}", id, hearingDTO);
        if (hearingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hearingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hearingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HearingDTO result = hearingService.update(hearingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hearingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hearings/:id} : Partial updates given fields of an existing hearing, field will ignore if it is null
     *
     * @param id the id of the hearingDTO to save.
     * @param hearingDTO the hearingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hearingDTO,
     * or with status {@code 400 (Bad Request)} if the hearingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hearingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hearingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hearings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HearingDTO> partialUpdateHearing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HearingDTO hearingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Hearing partially : {}, {}", id, hearingDTO);
        if (hearingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hearingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hearingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HearingDTO> result = hearingService.partialUpdate(hearingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hearingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hearings} : get all the hearings.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hearings in body.
     */
    @GetMapping("/hearings")
    public ResponseEntity<List<HearingDTO>> getAllHearings(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Hearings");
        Page<HearingDTO> page;
        if (eagerload) {
            page = hearingService.findAllWithEagerRelationships(pageable);
        } else {
            page = hearingService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hearings/:id} : get the "id" hearing.
     *
     * @param id the id of the hearingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hearingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hearings/{id}")
    public ResponseEntity<HearingDTO> getHearing(@PathVariable Long id) {
        log.debug("REST request to get Hearing : {}", id);
        Optional<HearingDTO> hearingDTO = hearingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hearingDTO);
    }

    /**
     * {@code DELETE  /hearings/:id} : delete the "id" hearing.
     *
     * @param id the id of the hearingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hearings/{id}")
    public ResponseEntity<Void> deleteHearing(@PathVariable Long id) {
        log.debug("REST request to delete Hearing : {}", id);
        hearingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
