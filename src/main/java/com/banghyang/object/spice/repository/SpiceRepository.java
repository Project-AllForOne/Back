package com.banghyang.object.spice.repository;

import com.banghyang.object.spice.entity.Spice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpiceRepository extends JpaRepository<Spice, Long> {
    Page<Spice> findAll(Pageable pageable);

    Page<Spice> findByLineNameIn(List<String> lineNames, Pageable pageable);

    // 한글명에 해당하는 향료 반환
    Spice findByNameKr(String nameKr);

    // 키워드에 해당하는 향료 검색하여 반환
    @Query(value = "SELECT * FROM spice " +
            "WHERE LOWER(name_en) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(name_kr) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(content_en) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(content_kr) LIKE LOWER(CONCAT('%', :keyword, '%'))",
            countQuery = "SELECT COUNT(*) FROM spice " +
                    "WHERE LOWER(name_en) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(name_kr) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(content_en) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(content_kr) LIKE LOWER(CONCAT('%', :keyword, '%'))",
            nativeQuery = true)
    Page<Spice> searchSpices(String keyword, Pageable pageable);
}
