package com.example.main_shop_project.dto;


import com.example.main_shop_project.domain.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;
    private String nickname;
    private User seller;
}
