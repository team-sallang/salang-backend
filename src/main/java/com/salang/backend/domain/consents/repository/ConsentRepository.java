package com.salang.backend.domain.consents.repository;

import com.salang.backend.domain.consents.entity.Consent;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsentRepository extends JpaRepository<Consent, Long> {

    List<Consent> findByUserId(Long userId);

    Optional<Consent> findByUserIdAndTypeAndVersion(Long id, String type, String version);

}
