package com.mycompany.myapp.ecourt.service.impl;

import com.mycompany.myapp.ecourt.domain.Hearing;
import com.mycompany.myapp.ecourt.repository.HearingRepository;
import com.mycompany.myapp.ecourt.service.HearingService;
import com.mycompany.myapp.ecourt.service.dto.HearingDTO;
import com.mycompany.myapp.ecourt.service.mapper.HearingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Hearing}.
 */
@Service
@Transactional
public class HearingServiceImpl implements HearingService {

    private final Logger log = LoggerFactory.getLogger(HearingServiceImpl.class);

    private final HearingRepository hearingRepository;

    private final HearingMapper hearingMapper;

    public HearingServiceImpl(HearingRepository hearingRepository, HearingMapper hearingMapper) {
        this.hearingRepository = hearingRepository;
        this.hearingMapper = hearingMapper;
    }

    @Override
    public HearingDTO save(HearingDTO hearingDTO) {
        log.debug("Request to save Hearing : {}", hearingDTO);
        Hearing hearing = hearingMapper.toEntity(hearingDTO);
        hearing = hearingRepository.save(hearing);
        return hearingMapper.toDto(hearing);
    }

    @Override
    public HearingDTO update(HearingDTO hearingDTO) {
        log.debug("Request to save Hearing : {}", hearingDTO);
        Hearing hearing = hearingMapper.toEntity(hearingDTO);
        hearing = hearingRepository.save(hearing);
        return hearingMapper.toDto(hearing);
    }

    @Override
    public Optional<HearingDTO> partialUpdate(HearingDTO hearingDTO) {
        log.debug("Request to partially update Hearing : {}", hearingDTO);

        return hearingRepository
            .findById(hearingDTO.getId())
            .map(existingHearing -> {
                hearingMapper.partialUpdate(existingHearing, hearingDTO);

                return existingHearing;
            })
            .map(hearingRepository::save)
            .map(hearingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HearingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Hearings");
        return hearingRepository.findAll(pageable).map(hearingMapper::toDto);
    }

    public Page<HearingDTO> findAllWithEagerRelationships(Pageable pageable) {
        return hearingRepository.findAllWithEagerRelationships(pageable).map(hearingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HearingDTO> findOne(Long id) {
        log.debug("Request to get Hearing : {}", id);
        return hearingRepository.findOneWithEagerRelationships(id).map(hearingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Hearing : {}", id);
        hearingRepository.deleteById(id);
    }
}
