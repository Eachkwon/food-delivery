package com.example.week05.Dto;

import lombok.*;

@NoArgsConstructor
@Data
public class RestaurantDto {

    private Long id;
    private String name;
    private int minOrderPrice;
    private int deliveryFee;

    public RestaurantDto(Long id, String name, int deliveryFee, int minOrderPrice){
        this.id= id;
        this.name= name;
        this.deliveryFee = deliveryFee;
        this.minOrderPrice = minOrderPrice;
    }
}
