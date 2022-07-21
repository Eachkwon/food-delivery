package com.example.week05.Entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name = "ORDER_TABLE")
public class Order {

    @JoinColumn(name= "restaurant_id")
    @ManyToOne
    private Restaurant restaurant;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private int totalPrice;


}
