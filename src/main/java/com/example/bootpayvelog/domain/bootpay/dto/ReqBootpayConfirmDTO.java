package com.example.bootpayvelog.domain.bootpay.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ReqBootpayConfirmDTO {
    @NotNull(message = "receiptId가 없습니다.")
    private String receiptId;
}

