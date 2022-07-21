package com.example.week05.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class FoodOrder {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @JoinColumn(name= "food_id")
    @ManyToOne
    private Food foods;

    private int quantity;

    private int price;

    @JoinColumn(name="order_id")
    @ManyToOne
    private Order order;
}
