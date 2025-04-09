package com.example.shinsekai.cart.infrastructure;

import com.example.shinsekai.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByMemberUuidAndIdIn(String memberUuid, List<Long> ids);
    Optional<Cart> findByMemberUuidAndId(String memberUuid, Long id);

    @Modifying
    @Query("UPDATE Cart c SET c.isDeleted = true, c.updatedAt = CURRENT_TIMESTAMP " +
            "WHERE c.memberUuid = :memberUuid AND c.isDeleted = false")
    void softDeleteAllByMemberUuid(@Param("memberUuid") String memberUuid);
}
