package com.example.bootpayvelog.model.order.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bootpayvelog.model.order.entity.OrderEntity;


@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    // 나중에 orderIdx로 DB에서 검색하기 위함
    Optional<OrderEntity> findByIdx(Long orderIdx);
}
