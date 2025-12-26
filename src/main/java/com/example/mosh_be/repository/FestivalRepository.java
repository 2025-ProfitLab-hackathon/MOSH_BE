package com.example.mosh_be.repository;

import com.example.mosh_be.domain.entity.Festival;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface FestivalRepository extends JpaRepository<Festival, Long> {

    @Query("SELECT f FROM Festival f WHERE " +
           "(:q IS NULL OR f.title LIKE %:q% OR f.place LIKE %:q%) AND " +
           "(:startFrom IS NULL OR f.startAt >= :startFrom) AND " +
           "(:startTo IS NULL OR f.startAt <= :startTo)")
    Page<Festival> searchFestivals(@Param("q") String q,
                                   @Param("startFrom") LocalDateTime startFrom,
                                   @Param("startTo") LocalDateTime startTo,
                                   Pageable pageable);
}
