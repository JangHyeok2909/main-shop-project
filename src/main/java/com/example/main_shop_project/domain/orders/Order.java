package com.example.main_shop_project.domain.orders;

import com.example.main_shop_project.domain.items.Item;
import com.example.main_shop_project.domain.users.User;
import com.example.main_shop_project.dto.OrderDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "buyer_id", referencedColumnName = "id")
    private User buyer;
    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private User seller;
    private Integer totalAmount;
    private Integer quantity;
    private LocalDate orderDate;

    public OrderDto toDto(){
        return OrderDto.builder()
                .id(id)
                .item(item)
                .buyer(buyer)
                .seller(seller)
                .totalAmount(totalAmount)
                .quantity(quantity)
                .orderDate(orderDate)
                .build();
    }


}
