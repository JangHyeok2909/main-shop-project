package com.example.main_shop_project.service;

import com.example.main_shop_project.domain.items.Item;
import com.example.main_shop_project.domain.items.ItemRepository;
import com.example.main_shop_project.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    @Transactional
    public Item editItem(Item item, ItemDto changeDto){
        item.changeItemName(changeDto.getItemName());
        item.changePrice(changeDto.getPrice());
        item.changeQuantity(changeDto.getQuantity());
        return item;
    }

    @Transactional
    public void deleteItems(List<Long> itemIds){
        for(Long id:itemIds){
            Item item = itemRepository.findById(id).get();
            itemRepository.delete(item);
        }
    }
}
