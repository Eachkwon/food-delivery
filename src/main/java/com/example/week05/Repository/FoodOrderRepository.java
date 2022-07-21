package com.example.week05.Repository;

import com.example.week05.Entity.FoodOrder;
import com.example.week05.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {

    List<FoodOrder> findAllByOrder(Order order);
}
