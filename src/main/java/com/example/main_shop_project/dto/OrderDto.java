package com.example.main_shop_project.dto;

import com.example.main_shop_project.domain.items.Item;
import com.example.main_shop_project.domain.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private Item item;
    private User buyer;
    private User seller;
    private Integer totalAmount;
    private Integer quantity;
    private LocalDate orderDate;
}
