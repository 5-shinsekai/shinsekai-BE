package com.example.shinsekai.address.infrastructure;


import com.example.shinsekai.address.dto.out.AddressResponseDto;
import com.example.shinsekai.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    List<Address> findByMemberUuid(String memberUuid);
    int countByMemberUuid(String memberUuid);
    Optional<Address> findByMemberUuidAndAddressUuid(String memberUuid, String addressUuid);
    Optional<Address> findByAddressUuid(String addressUuid);
}
