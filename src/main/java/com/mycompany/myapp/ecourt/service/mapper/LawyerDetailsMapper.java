package com.mycompany.myapp.ecourt.service.mapper;

import com.mycompany.myapp.ecourt.domain.CourtCase;
import com.mycompany.myapp.ecourt.domain.LawyerDetails;
import com.mycompany.myapp.ecourt.service.dto.CourtCaseDTO;
import com.mycompany.myapp.ecourt.service.dto.LawyerDetailsDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LawyerDetails} and its DTO {@link LawyerDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface LawyerDetailsMapper extends EntityMapper<LawyerDetailsDTO, LawyerDetails> {
    @Mapping(target = "courtCases", source = "courtCases", qualifiedByName = "courtCaseLandReferenceNoSet")
    LawyerDetailsDTO toDto(LawyerDetails s);

    @Mapping(target = "removeCourtCase", ignore = true)
    LawyerDetails toEntity(LawyerDetailsDTO lawyerDetailsDTO);

    @Named("courtCaseLandReferenceNo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "landReferenceNo", source = "landReferenceNo")
    CourtCaseDTO toDtoCourtCaseLandReferenceNo(CourtCase courtCase);

    @Named("courtCaseLandReferenceNoSet")
    default Set<CourtCaseDTO> toDtoCourtCaseLandReferenceNoSet(Set<CourtCase> courtCase) {
        return courtCase.stream().map(this::toDtoCourtCaseLandReferenceNo).collect(Collectors.toSet());
    }
}
