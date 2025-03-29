package com.example.main_shop_project.domain.orders;

import com.example.main_shop_project.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    public List<Order> findByBuyer(User buyer);
    @Query("select o from Order o where o.buyer.id = :userId")
    public List<Order> findByBuyer(Long userId);
    public List<Order> findBySeller(User seller);
    @Query("select o from Order o where o.seller.id = :userId")
    public List<Order> findBySeller(Long userId);

}
