package com.example.shinsekai.agreement.infrastructure;

import com.example.shinsekai.agreement.entity.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgreementRepository extends JpaRepository<Agreement, Long> {
}
