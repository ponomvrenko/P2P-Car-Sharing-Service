package com.danilponomarenko.p2p.carsharing.repository;

import com.danilponomarenko.p2p.carsharing.model.VerificationDoc;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationDocRepository extends JpaRepository<VerificationDoc, Long> {
    List<VerificationDoc> findByUserId(Long userId);
}
