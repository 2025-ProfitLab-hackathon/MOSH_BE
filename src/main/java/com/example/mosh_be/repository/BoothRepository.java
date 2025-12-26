package com.example.mosh_be.repository;

import com.example.mosh_be.domain.entity.Booth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BoothRepository extends JpaRepository<Booth, Long> {

    List<Booth> findByFestivalId(Long festivalId);

    @Query("SELECT b FROM Booth b WHERE " +
           "(:festivalId IS NULL OR b.festivalId = :festivalId) AND " +
           "(:type IS NULL OR b.type = :type) AND " +
           "(:q IS NULL OR b.title LIKE %:q% OR b.place LIKE %:q%)")
    Page<Booth> searchBooths(@Param("festivalId") Long festivalId,
                             @Param("type") String type,
                             @Param("q") String q,
                             Pageable pageable);

    @Query("SELECT b FROM Booth b WHERE b.startAt <= :now AND b.endAt >= :now")
    Page<Booth> findOpenBooths(@Param("now") LocalDateTime now, Pageable pageable);
}
