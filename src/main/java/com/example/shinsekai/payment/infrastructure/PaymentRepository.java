package com.example.shinsekai.payment.infrastructure;

import com.example.shinsekai.payment.entity.Payment;
import com.example.shinsekai.payment.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPaymentCodeAndMemberUuidAndStatus(String  paymentCode, String memberUuid, PaymentStatus paymentStatus);
}
