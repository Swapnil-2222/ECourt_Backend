package com.mycompany.myapp.ecourt.repository;

import com.mycompany.myapp.ecourt.domain.LawyerDetails;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface LawyerDetailsRepositoryWithBagRelationships {
    Optional<LawyerDetails> fetchBagRelationships(Optional<LawyerDetails> lawyerDetails);

    List<LawyerDetails> fetchBagRelationships(List<LawyerDetails> lawyerDetails);

    Page<LawyerDetails> fetchBagRelationships(Page<LawyerDetails> lawyerDetails);
}
