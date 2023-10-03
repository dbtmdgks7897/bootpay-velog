package com.example.bootpayvelog.domain.bootpay.service;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.bootpayvelog.common.dto.ResponseDTO;
import com.example.bootpayvelog.domain.bootpay.dto.ReqBootpayConfirmDTO;
import com.example.bootpayvelog.model.order.entity.OrderEntity;
import com.example.bootpayvelog.model.order.repository.OrderRepository;

import jakarta.transaction.Transactional;
import kr.co.bootpay.Bootpay;

@Service
public class BootpayServiceApiV1 {

    @Autowired
    private OrderRepository orderRepository;

    private Bootpay bootpay;

    // 부트페이 서버에서 토큰을 가져올 수 있는지 확인.
    // 중복되는 코드라 빼봤습니다.
    public void getBootpayToken() {
        try {
            bootpay = new Bootpay("", "");
            HashMap token = bootpay.getAccessToken();
            if (token.get("error_code") != null) { // failed
                System.out.println("getAccessToken false: " + token);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 단건 조회
    public HashMap getBootpayReceipt(String receiptId) {
        try {
            getBootpayToken();
            HashMap res = bootpay.getReceipt(receiptId);
            if (res.get("error_code") == null) { // success
                System.out.println("getReceipt success: " + res);
            } else {
                System.out.println("getReceipt false: " + res);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // 결제 승인
    @Transactional
    public HashMap confirm(String receiptId){
        try {
            getBootpayToken();
            HashMap res = bootpay.confirm(receiptId);
            if(res.get("error_code") == null) { //success
                System.out.println("confirm success: " + res);

                // order테이블의 status column 데이터를 바꿔준다.
                Long orderIdx = Long.valueOf(res.get("order_id").toString());
                Optional<OrderEntity> orderEntityOptional = orderRepository.findByIdx(orderIdx);
                if(!orderEntityOptional.isPresent()){
                    System.out.println("주문 번호에 해당하는 주문 정보가 없음.");
                    return null;
                }
                OrderEntity orderEntity = orderEntityOptional.get();
                orderEntity.setOrderStatus("결제 승인");

            } else {
                System.out.println("confirm false: " + res);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Transactional
    public ResponseEntity<?> priceCheck(ReqBootpayConfirmDTO dto) {
        // 조회해서 영수증 받아오기
        HashMap res = getBootpayReceipt(dto.getReceiptId());

        // 영수증의 price와 order table의 price 가져오기
        Long receiptPrice = Long.valueOf(res.get("price").toString());
        
        Optional<OrderEntity> orderEntityOptional = orderRepository.findByIdx(Long.valueOf(res.get("order_id").toString()));
        // order 테이블에 해당 정보가 있는 지 확인
        if(!orderEntityOptional.isPresent()){
            return new ResponseEntity<>(
                ResponseDTO.builder()
                        .code(1)
                        .message("해당 주문이 존재하지 않습니다.")
                        .build(),
                HttpStatus.BAD_REQUEST);
        }
        OrderEntity entity = orderEntityOptional.get();

        Long orderPrice = entity.getItemPrice();

        System.out.println("orderPrice : " + orderPrice);
        System.out.println("receiptPrice : " + receiptPrice);

        // 두 값이 같으면
        // Long은 equals로 비교해야 정확히 비교가 되더라.
        if(receiptPrice.equals(orderPrice)){
            // confirm()
            HashMap resData = confirm(dto.getReceiptId());
            return new ResponseEntity<>(
                ResponseDTO.builder()
                        .code(0)
                        .message("결제 승인")
                        .data(resData)
                        .build(),
                HttpStatus.OK);
        }
        // 아니면
        else {
            // order table에 status를 취소 상태로
            entity.setOrderStatus("결제 취소");
            return new ResponseEntity<>(
                ResponseDTO.builder()
                        .code(2)
                        .message("결제가 취소되었습니다.")
                        .build(),
                HttpStatus.BAD_REQUEST);
        }
    }
}
