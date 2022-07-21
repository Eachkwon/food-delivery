package com.example.week05.Service;

import com.example.week05.Dto.FoodOrderRequestDto;
import com.example.week05.Dto.FoodOrderResponseDto;
import com.example.week05.Dto.OrderRequestDto;
import com.example.week05.Dto.OrderResponseDto;
import com.example.week05.Entity.Food;
import com.example.week05.Entity.FoodOrder;
import com.example.week05.Entity.Order;
import com.example.week05.Entity.Restaurant;
import com.example.week05.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    final private OrderRepository orderRepository;
    final private RestaurantRepository restaurantRepository;
    final private FoodOrderRepository foodOrderRepository;
    final private FoodRepository foodRepository;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto){
        //음식점(Restaurant)
        Restaurant restaurant = restaurantRepository.findById(requestDto.getRestaurantId()).orElseThrow(
                ()-> new IllegalArgumentException("해당 음식점이 존재하지 않습니다.")
        );

        //총금액(배달비, 음식 가격*수량)
        int totalPrice= 0;
        Order order = Order.builder()
                .restaurant(restaurant)
                .build();

        orderRepository.save(order);

        //음식주문(FoodOrder) DTO
        List<FoodOrderResponseDto> foodOrderResponseDtos = new ArrayList<>();

        //음식주문(FoodOrder)
        for(FoodOrderRequestDto foods: requestDto.getFoods()){
            //음식
            Food food = foodRepository.findById(foods.getId()).orElseThrow(
                    ()-> new IllegalArgumentException("해당 음식이 존재하지 않습니다.")
            );

            //음식 수량 확인
            int quantity = foods.getQuantity();
            boolean foodChk = quantity>=1&&quantity<=100;
            if(!foodChk){
                throw new IllegalArgumentException("음식 주문 수량을 확인해주세요!");
            }

            //가격
            int price = food.getPrice() * foods.getQuantity();

            FoodOrder foodOrder = FoodOrder.builder()
                    .foods(food)
                    .quantity(foods.getQuantity())
                    .order(order)
                    .price(price)
                    .build();

            foodOrderRepository.save(foodOrder);

            FoodOrderResponseDto foodOrderResponseDto = FoodOrderResponseDto.builder()
                            .name(food.getName())
                            .quantity(foods.getQuantity())
                            .price(price)
                            .build();

            foodOrderResponseDtos.add(foodOrderResponseDto);

            //총금액 중 음식 가격*수량
            totalPrice += price;
        }

        boolean minOrderPriceChk = totalPrice> restaurant.getMinOrderPrice();

        if(!minOrderPriceChk){
            throw new IllegalArgumentException("최소 주문금액을 넘지않았습니다!");
        }

        totalPrice+= restaurant.getDeliveryFee();

        order.setTotalPrice(totalPrice);

        return OrderResponseDto.builder()
                .restaurantName(restaurant.getName())
                .foods(foodOrderResponseDtos)
                .deliveryFee(restaurant.getDeliveryFee())
                .totalPrice(totalPrice)
                .build();
    }

    @Transactional
    public List<OrderResponseDto> getOrders(){
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDto> orderList = new ArrayList<>();
        List<FoodOrderResponseDto> foodOrderList = new ArrayList<>();

        for(Order order : orders){
            List<FoodOrder> foodOrders = foodOrderRepository.findAllByOrder(order);


            for(FoodOrder foodOrder : foodOrders) {
                FoodOrderResponseDto foodOrderResponseDto = FoodOrderResponseDto.builder()
                        .name(foodOrder.getFoods().getName())
                        .quantity(foodOrder.getQuantity())
                        .price(foodOrder.getPrice())
                        .build();

                foodOrderList.add(foodOrderResponseDto);
            }

            OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                    .restaurantName(order.getRestaurant().getName())
                    .foods(foodOrderList)
                    .deliveryFee(order.getRestaurant().getDeliveryFee())
                    .totalPrice(order.getTotalPrice())
                    .build();

            orderList.add(orderResponseDto);
        }
        return orderList;
    }



}
