package com.example.main_shop_project.domain.items;

import com.example.main_shop_project.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    @Query("select i from Item i where i.seller = :seller")
    public List<Item> findBySeller(User seller);
}
