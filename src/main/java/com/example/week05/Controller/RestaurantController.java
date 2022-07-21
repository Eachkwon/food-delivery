package com.example.week05.Controller;

import com.example.week05.Entity.Restaurant;
import com.example.week05.Dto.RestaurantDto;
import com.example.week05.Service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/restaurant/register")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody RestaurantDto dto) {

        Restaurant restaurant = restaurantService.createRestaurant(dto);
        return ResponseEntity.ok(restaurant);
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> getRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }
}
