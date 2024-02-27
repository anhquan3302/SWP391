package com.example.securityl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
   private Integer productId;
   private String productName;
   private double price;
   private int quantity;
   private double discount;

}
