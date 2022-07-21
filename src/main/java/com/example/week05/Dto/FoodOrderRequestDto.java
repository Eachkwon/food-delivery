package com.example.week05.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FoodOrderRequestDto {
    private Long id;
    private int quantity;
}
