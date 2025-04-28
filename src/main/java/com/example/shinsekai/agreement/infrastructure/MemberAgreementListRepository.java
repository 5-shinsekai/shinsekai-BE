package com.example.shinsekai.agreement.infrastructure;

import com.example.shinsekai.agreement.entity.Agreement;
import com.example.shinsekai.agreement.entity.MemberAgreementList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberAgreementListRepository extends JpaRepository<MemberAgreementList, Long> {

    List<MemberAgreementList> findMemberAgreementListByMemberUuidAndIsAgreeTrue(String memberUuid);

    Optional<MemberAgreementList> findMemberAgreementListByMemberUuidAndAgreementIdAndIsAgreeTrue(String memberUuid,
                                                                                                  Long agreementId);

    List<MemberAgreementList> findByAgreement(Agreement agreement);
}
