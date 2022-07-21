package com.example.week05.Controller;

import com.example.week05.Dto.FoodRequestDto;
import com.example.week05.Dto.FoodResponseDto;
import com.example.week05.Service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FoodController {

    private final FoodService foodService;

    @PostMapping("/restaurant/{restaurantId}/food/register")
    public ResponseEntity<Void> createFood(@RequestBody List<FoodRequestDto> requestDtos, @PathVariable Long restaurantId){
        foodService.createFood(requestDtos, restaurantId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/restaurant/{restaurantId}/foods")
    public ResponseEntity<List<FoodResponseDto>> getFoods(@PathVariable Long restaurantId){
        return ResponseEntity.ok(foodService.getAllFoods(restaurantId));}

}
