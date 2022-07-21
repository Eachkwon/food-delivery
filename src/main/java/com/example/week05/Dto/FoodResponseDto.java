package com.example.week05.Dto;

import com.example.week05.Entity.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FoodResponseDto {

    private Long id;
    private String name;
    private int price;

    public FoodResponseDto toEntity(Food food) {
        return FoodResponseDto.builder()
                .id(food.getId())
                .name(food.getName())
                .price(food.getPrice())
                .build();
    }
}
