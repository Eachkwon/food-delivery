package com.example.week05.Service;

import com.example.week05.Dto.FoodRequestDto;
import com.example.week05.Dto.FoodResponseDto;
import com.example.week05.Entity.Food;
import com.example.week05.Entity.Restaurant;
import com.example.week05.Repository.FoodRepository;
import com.example.week05.Repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FoodService {

    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public void createFood(List<FoodRequestDto> requestDtos, Long restaurantId){

        //레스토랑 아이디 확인
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                ()-> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
        );

        List<String> savedFoodNameList = foodRepository.findNameByRestaurantId((restaurantId));

        for(FoodRequestDto foodRequestDto: requestDtos){
           String requestDtoName = foodRequestDto.getName();
           int requestDtoPrice = foodRequestDto.getPrice();
           boolean priceChk = requestDtoPrice>=100&&requestDtoPrice<=1000000&&requestDtoPrice%100==0;

           if(!foodNameChk(savedFoodNameList, requestDtoName)){
               throw new IllegalArgumentException("이미 등록되어 있는 음식입니다!");
           } else if(!priceChk) {
               throw new IllegalArgumentException("가격을 수정해주세요!");
           } else if(!isDuplicate(requestDtos)) {
               throw new IllegalArgumentException("중복된 음식을 확인해주세요!");
           }

           Food food = Food.builder()
                   .name(foodRequestDto.getName())
                   .price(foodRequestDto.getPrice())
                   .restaurant(restaurant)
                   .build();

           foodRepository.save(food);
        }
    }

    private boolean foodNameChk(List<String> savedFoodNameList, String requestDtoName){
        for(String name : savedFoodNameList){
            if(name.equals(requestDtoName))
                return false;
        }
        return true;
    }

    private boolean isDuplicate(List<FoodRequestDto> requestDtos){
        for (int i=0; i< requestDtos.size(); i++) {
            for(int j=0; j<i; j++) {
                if(requestDtos.get(i).equals(requestDtos.get(j))) {
                    return false;
                }
            }
        }
    return true;
    }


    @Transactional
    public List<FoodResponseDto> getAllFoods(@PathVariable Long restaurantId) {
        List<Food> foodList = foodRepository.findAllByRestaurantId(restaurantId);

        List<FoodResponseDto> foodResponseDtos = new ArrayList<>();
        for(Food food : foodList) {
            FoodResponseDto foodResponseDto = new FoodResponseDto().toEntity(food);
            foodResponseDtos.add(foodResponseDto);
        }
        return foodResponseDtos;
    }
}
