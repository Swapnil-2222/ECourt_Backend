package com.mycompany.myapp.ecourt.service.mapper;

import com.mycompany.myapp.ecourt.domain.Organization;
import com.mycompany.myapp.ecourt.service.dto.OrganizationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Organization} and its DTO {@link OrganizationDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrganizationMapper extends EntityMapper<OrganizationDTO, Organization> {}
