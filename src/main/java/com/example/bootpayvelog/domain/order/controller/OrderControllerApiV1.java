package com.example.bootpayvelog.domain.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bootpayvelog.domain.order.dto.ReqOrderSaveDTO;
import com.example.bootpayvelog.domain.order.service.OrderServiceApiV1;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/order")
public class OrderControllerApiV1 {

    @Autowired
    private OrderServiceApiV1 orderServiceApiV1;
    
    @PostMapping()
    public ResponseEntity<?> saveOrder(@Valid @RequestBody ReqOrderSaveDTO dto){
        return orderServiceApiV1.saveOrder(dto);
    }
}
