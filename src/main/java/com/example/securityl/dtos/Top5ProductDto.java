package com.example.securityl.dtos;


import com.example.securityl.models.Product;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Top5ProductDto {
    private Product product;
    private Integer totalQuantitySold;
    private Double totalAmountSold;
}
