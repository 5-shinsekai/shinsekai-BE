package com.example.shinsekai.payment.infrastructure;

import com.example.shinsekai.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByPaymentCodeAndMemberUuid(String  paymentCode, String memberUuid);
}
