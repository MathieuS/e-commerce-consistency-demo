package com.github.mathieus.customerportal.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReturnRequestDto {

    private Long id;
    private String orderId;
    private String status;
    private String shippingLabelId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}