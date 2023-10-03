package com.example.bootpayvelog.model.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name= "`ORDER`")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false, unique = true)
    private Long idx;

    @Column(name = "item_idx", nullable = false, unique = false)
    private Long itemIdx;

    @Column(name = "item_name", nullable = false, unique = false)
    private String itemName;

    @Column(name = "item_amount", nullable = false, unique = false)
    private Long itemAmount;

    @Column(name = "item_price", nullable = false, unique = false)
    private Long itemPrice;

    @Column(name = "order_status", nullable = false, unique = false)
    private String orderStatus;
}