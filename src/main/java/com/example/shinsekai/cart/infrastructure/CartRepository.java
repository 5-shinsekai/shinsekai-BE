package com.example.shinsekai.cart.infrastructure;

import com.example.shinsekai.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByMemberUuidAndCartUuidInAndIsDeletedFalse(String memberUuid, List<String> cartUuids);
    Optional<Cart> findByMemberUuidAndCartUuidAndIsDeletedFalse(String memberUuid, String cartUuid);
    List<Cart> findAllByMemberUuidAndProductCodeAndIsDeletedFalse(String memberUuid, String productCode);
    List<Cart> findAllByMemberUuidAndIsDeletedFalse(String memberUuid);
    List<Cart> findAllByMemberUuidAndCheckedTrueAndIsDeletedFalse(String memberUuid);


    @Modifying
    @Query("UPDATE Cart c SET c.isDeleted = true, c.updatedAt = CURRENT_TIMESTAMP " +
            "WHERE c.memberUuid = :memberUuid AND c.isDeleted = false")
    void softDeleteAllByMemberUuid(@Param("memberUuid") String memberUuid);

    @Query("SELECT COUNT(DISTINCT c.productCode) " +
            "FROM Cart c " +
            "WHERE c.memberUuid = :memberUuid AND c.isDeleted = false")
    Long countDistinctProductCodeByMemberUuid(@Param("memberUuid") String memberUuid);


    @Query("SELECT c FROM Cart c WHERE c.memberUuid = :memberUuid AND c.productCode = :productCode " +
            "AND c.productOptionListId = :productOptionListId AND c.isDeleted = false")
    Optional<Cart> findCartByMemberAndProductAndOption(@Param("memberUuid") String memberUuid,
                                  @Param("productCode") String productCode,
                                  @Param("productOptionListId") Long productOptionListId);

    @Modifying
    @Query("UPDATE Cart c SET c.checked = :checked " +
            "WHERE c.memberUuid = :memberUuid " +
            "AND (:isFrozen IS NULL OR c.isFrozen = :isFrozen) " +
            "AND c.isDeleted = false")
    void updateCheckedStatusByType(String memberUuid, Boolean checked, Boolean isFrozen);


}
