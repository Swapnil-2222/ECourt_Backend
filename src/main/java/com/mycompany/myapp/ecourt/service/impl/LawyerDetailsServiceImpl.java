package com.mycompany.myapp.ecourt.service.impl;

import com.mycompany.myapp.ecourt.domain.LawyerDetails;
import com.mycompany.myapp.ecourt.repository.LawyerDetailsRepository;
import com.mycompany.myapp.ecourt.service.LawyerDetailsService;
import com.mycompany.myapp.ecourt.service.dto.LawyerDetailsDTO;
import com.mycompany.myapp.ecourt.service.mapper.LawyerDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LawyerDetails}.
 */
@Service
@Transactional
public class LawyerDetailsServiceImpl implements LawyerDetailsService {

    private final Logger log = LoggerFactory.getLogger(LawyerDetailsServiceImpl.class);

    private final LawyerDetailsRepository lawyerDetailsRepository;

    private final LawyerDetailsMapper lawyerDetailsMapper;

    public LawyerDetailsServiceImpl(LawyerDetailsRepository lawyerDetailsRepository, LawyerDetailsMapper lawyerDetailsMapper) {
        this.lawyerDetailsRepository = lawyerDetailsRepository;
        this.lawyerDetailsMapper = lawyerDetailsMapper;
    }

    @Override
    public LawyerDetailsDTO save(LawyerDetailsDTO lawyerDetailsDTO) {
        log.debug("Request to save LawyerDetails : {}", lawyerDetailsDTO);
        LawyerDetails lawyerDetails = lawyerDetailsMapper.toEntity(lawyerDetailsDTO);
        lawyerDetails = lawyerDetailsRepository.save(lawyerDetails);
        return lawyerDetailsMapper.toDto(lawyerDetails);
    }

    @Override
    public LawyerDetailsDTO update(LawyerDetailsDTO lawyerDetailsDTO) {
        log.debug("Request to save LawyerDetails : {}", lawyerDetailsDTO);
        LawyerDetails lawyerDetails = lawyerDetailsMapper.toEntity(lawyerDetailsDTO);
        lawyerDetails = lawyerDetailsRepository.save(lawyerDetails);
        return lawyerDetailsMapper.toDto(lawyerDetails);
    }

    @Override
    public Optional<LawyerDetailsDTO> partialUpdate(LawyerDetailsDTO lawyerDetailsDTO) {
        log.debug("Request to partially update LawyerDetails : {}", lawyerDetailsDTO);

        return lawyerDetailsRepository
            .findById(lawyerDetailsDTO.getId())
            .map(existingLawyerDetails -> {
                lawyerDetailsMapper.partialUpdate(existingLawyerDetails, lawyerDetailsDTO);

                return existingLawyerDetails;
            })
            .map(lawyerDetailsRepository::save)
            .map(lawyerDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LawyerDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LawyerDetails");
        return lawyerDetailsRepository.findAll(pageable).map(lawyerDetailsMapper::toDto);
    }

    public Page<LawyerDetailsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return lawyerDetailsRepository.findAllWithEagerRelationships(pageable).map(lawyerDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LawyerDetailsDTO> findOne(Long id) {
        log.debug("Request to get LawyerDetails : {}", id);
        return lawyerDetailsRepository.findOneWithEagerRelationships(id).map(lawyerDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LawyerDetails : {}", id);
        lawyerDetailsRepository.deleteById(id);
    }
}
