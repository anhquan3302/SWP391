package com.example.securityl.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private Long id;
    @NotEmpty(message = "Category's name cannot be empty")
    private String name;
    private String code;
}
