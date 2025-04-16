package com.example.shinsekai.like.infrastructure;

import com.example.shinsekai.like.entity.ProductLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {

    Optional<ProductLike> findById(Long likeId);
    List<ProductLike> findByMemberUuid(String memberUuid);
}
