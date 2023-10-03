package com.example.bootpayvelog.domain.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ReqOrderSaveDTO {
    
    @NotNull(message = "item 정보가 없습니다.")
    private Item item;

    @Valid
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class Item{
        @NotNull(message = "itemIdx가 잘못되었습니다.")
        private Long itemIdx;
        @NotNull(message = "itemPirce가 잘못되었습니다.")
        private Long itemPrice;
        @NotNull(message = "itemName이 잘못되었습니다.")
        private String itemName;
        @NotNull(message = "itemAmount가 잘못되었습니다.")
        private Long itemAmount;
    }
}
