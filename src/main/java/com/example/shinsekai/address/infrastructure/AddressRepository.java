package com.example.shinsekai.address.infrastructure;


import com.example.shinsekai.address.dto.out.AddressResponseDto;
import com.example.shinsekai.address.entity.Address;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

    int countByMemberUuid(String memberUuid);

    Optional<Address> findByMemberUuidAndAddressUuid(String memberUuid, String addressUuid);

    Optional<Address> findByAddressUuid(String addressUuid);

    Optional<Address> findByMemberUuidAndIsMainAddressIsTrue(String memberUuid);

    @Query("SELECT a FROM Address a WHERE a.memberUuid = :memberUuid AND (a.isDeleted = false OR a.isDeleted IS NULL)")
    List<Address> findActiveByMemberUuid(@Param("memberUuid") String memberUuid, Sort sort);

}
