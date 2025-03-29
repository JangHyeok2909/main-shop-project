package com.example.main_shop_project.service;

import com.example.main_shop_project.domain.items.Item;
import com.example.main_shop_project.domain.users.User;
import com.example.main_shop_project.domain.orders.Order;
import com.example.main_shop_project.domain.orders.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    @Transactional
    public Order buyItemService(Item item, User buyer, Integer quantity){
        Integer totalAmount = item.getPrice()*quantity;
        User seller = item.getSeller();
        buyer.withdrawal(totalAmount);
        seller.deposit(totalAmount);
        item.changeQuantity(item.getQuantity()-1);

        Order order = Order.builder()
                .item(item)
                .buyer(buyer)
                .seller(item.getSeller())
                .totalAmount(item.getPrice() * quantity)
                .quantity(quantity)
                .orderDate(LocalDate.now())
                .build();

        return orderRepository.save(order);
    }
}
