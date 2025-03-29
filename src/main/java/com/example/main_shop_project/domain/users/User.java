package com.example.main_shop_project.domain.users;

import com.example.main_shop_project.domain.orders.Order;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "users")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private String nickname;
    private Integer money;

    @OneToMany(mappedBy = "buyer")
    private List<Order> boughtOrders;
    @OneToMany(mappedBy = "seller")
    private List<Order> soldOrders;

    public void changeNickname(String nickname){
        this.nickname = nickname;
    }
    public void changeName(String name){
        this.name = name;
    }
    public void changePassword(String password){
        this.password = password;
    }
    public void withdrawal(Integer money){
        this.money = this.money-money;
    }
    public void deposit(Integer money){
        this.money = this.money+money;
    }

}
