package com.example.bootpayvelog.domain.bootpay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bootpayvelog.domain.bootpay.dto.ReqBootpayConfirmDTO;
import com.example.bootpayvelog.domain.bootpay.service.BootpayServiceApiV1;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/bootpay")
public class BootpayCotnrollerApiV1 {

    @Autowired
    private BootpayServiceApiV1 bootpayServiceApiV1;
    
    @PostMapping("/check")
    public ResponseEntity<?> priceCheck(@Valid @RequestBody ReqBootpayConfirmDTO dto){
        return bootpayServiceApiV1.priceCheck(dto);
    }
}
