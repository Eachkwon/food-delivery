package com.example.week05.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FoodOrderResponseDto {

    private String name;
    private int quantity;
    private int price;
}
