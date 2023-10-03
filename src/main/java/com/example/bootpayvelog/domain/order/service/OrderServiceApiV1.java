package com.example.bootpayvelog.domain.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.bootpayvelog.common.dto.ResponseDTO;
import com.example.bootpayvelog.domain.order.dto.ReqOrderSaveDTO;
import com.example.bootpayvelog.model.order.entity.OrderEntity;
import com.example.bootpayvelog.model.order.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceApiV1 {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public ResponseEntity<?> saveOrder(ReqOrderSaveDTO dto){

        // 엔티티를 만들고,
        OrderEntity entityForSaving = OrderEntity.builder()
        .itemIdx(dto.getItem().getItemIdx())
        .itemPrice(dto.getItem().getItemPrice())
        .itemName(dto.getItem().getItemName())
        .itemAmount(dto.getItem().getItemAmount())
        .orderStatus("결제 미승인")
        .build();

        // DB에 저장한다.
        // 이 때 entity에는 DB에 저장된 entity 정보가 들어감(idx가 자동 생성된 데이터)
        OrderEntity entity = orderRepository.save(entityForSaving);

        // 저장이 성공했음을 알리는 code와 message,
        // data에 위에서 저장한 entity의 idx를 넘겨준다.
        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .code(0)
                        .message("주문 저장 성공")
                        .data(entity.getIdx())
                        .build(),
                HttpStatus.OK);
    }
}
