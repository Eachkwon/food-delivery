package com.example.week05.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class OrderResponseDto {

    private String restaurantName;

    private List<FoodOrderResponseDto> foods;

    private int deliveryFee;

    private int totalPrice;
}
