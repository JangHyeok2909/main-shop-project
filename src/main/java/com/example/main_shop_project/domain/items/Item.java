package com.example.main_shop_project.domain.items;

import com.example.main_shop_project.domain.users.User;
import com.example.main_shop_project.dto.ItemDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "items")
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private User seller;

    public void changeItemName(String itemName){
        this.itemName=itemName;
    }
    public void changePrice(Integer price){
        this.price = price;
    }
    public void changeQuantity(Integer quantity){
        this.quantity = quantity;
    }

    public ItemDto toDto(){
        return ItemDto.builder()
                .id(id)
                .itemName(itemName)
                .price(price)
                .quantity(quantity)
                .seller(seller)
                .build();
    }
}
