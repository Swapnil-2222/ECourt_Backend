package com.mycompany.myapp.ecourt.repository;

import com.mycompany.myapp.ecourt.domain.Hearing;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Hearing entity.
 */
@Repository
public interface HearingRepository extends JpaRepository<Hearing, Long>, JpaSpecificationExecutor<Hearing> {
    default Optional<Hearing> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Hearing> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Hearing> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct hearing from Hearing hearing left join fetch hearing.courtCase",
        countQuery = "select count(distinct hearing) from Hearing hearing"
    )
    Page<Hearing> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct hearing from Hearing hearing left join fetch hearing.courtCase")
    List<Hearing> findAllWithToOneRelationships();

    @Query("select hearing from Hearing hearing left join fetch hearing.courtCase where hearing.id =:id")
    Optional<Hearing> findOneWithToOneRelationships(@Param("id") Long id);
}
