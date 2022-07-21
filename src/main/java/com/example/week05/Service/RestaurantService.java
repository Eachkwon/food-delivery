package com.example.week05.Service;

import com.example.week05.Entity.Restaurant;
import com.example.week05.Repository.RestaurantRepository;
import com.example.week05.Dto.RestaurantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Restaurant createRestaurant(RestaurantDto dto) {
            int minPrice = dto.getMinOrderPrice();
            boolean minPriceChk = minPrice>=1000&&minPrice<=100000&&minPrice%100==0;
            int deliveryFee = dto.getDeliveryFee();
            boolean deliveryFeeChk = deliveryFee>=0&&deliveryFee<=10000&&deliveryFee%500==0;

            if(!minPriceChk){
                throw new IllegalArgumentException("최소주문 가격을 수정해주세요!");
            } else if(!deliveryFeeChk){
                throw new IllegalArgumentException("기본 배달비를 수정해주세요!");
            }

            Restaurant restaurant = Restaurant.builder()
                            .name(dto.getName())
                            .minOrderPrice(dto.getMinOrderPrice())
                            .deliveryFee(dto.getDeliveryFee())
                            .build();

            restaurantRepository.save(restaurant);
            return restaurant;
    }

    @Transactional
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }
}
