package com.example.main_shop_project.controller;


import com.example.main_shop_project.domain.items.Item;
import com.example.main_shop_project.domain.items.ItemRepository;
import com.example.main_shop_project.domain.users.User;
import com.example.main_shop_project.domain.users.UserRepository;
import com.example.main_shop_project.dto.ItemDto;
import com.example.main_shop_project.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


import static com.example.main_shop_project.session.SessionConst.LOGIN_SESSION_ID;

@Controller
@RequiredArgsConstructor
@RequestMapping("items")
public class ItemController {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemService itemService;
    @GetMapping
    public String itemListForm(@SessionAttribute(name = LOGIN_SESSION_ID,required = false)Long userId
            , Model model){
        List<Item> itemList = itemRepository.findAll();
        model.addAttribute(itemList);
        if(userId == null){
            return "items/login/notLoginItemList";
        }
        return "items/login/loginItemList";
    }
//    @GetMapping("/search")
//    public String searchItems(@SessionAttribute(name = LOGIN_SESSION_ID,required = false)Integer loginMemberId,
//                              @RequestParam(name="searchType") String searchType,
//                              @RequestParam(name = "searchValue",required = false)String searchValue,
//                              Model model){
//        List<Item> findItems;
//        log.info("searchType={},searchValue={}",searchType,searchValue);
//        if(searchType.equals("itemName")){
//            findItems = itemRepository.findByItemName(searchValue);
//            log.info("search by itemName={}, findItems={}",searchValue,findItems);
//            model.addAttribute("items",findItems);
//        }
//        else if(searchType.equals("nickname")){
//            findItems=itemRepository.findByNickname(searchValue);
//            log.info("search by nickname={}, findItems={}",searchValue,findItems);
//            model.addAttribute("items",findItems);
//        }
//        return loginInteractivePage(loginMemberId);
//    }


    @GetMapping("/myItems")
    public String manageItemsForm(@SessionAttribute(name= LOGIN_SESSION_ID)Long userId, Model model){
        User seller = userRepository.findById(userId).get();
        List<Item> sellerItems = itemRepository.findBySeller(seller);
        model.addAttribute("loginUser",seller);
        model.addAttribute("userItems",sellerItems);
        return "/items/login/myItems";
    }
    @GetMapping("/{itemId}")
    public String itemBuyForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId).get();
        ItemDto itemDto = item.toDto();
        model.addAttribute("item",itemDto);
        return "/items/buyForm";
    }

    @GetMapping("/add")
    public String addItemForm(@SessionAttribute(name = LOGIN_SESSION_ID,required = false)Long userId,
                              Model model){
        model.addAttribute("itemDto",new ItemDto());
        return "/items/addForm";
    }
    @PostMapping
    public String addItem(@SessionAttribute(name = LOGIN_SESSION_ID,required = false)Long userId,
                          @ModelAttribute ItemDto dto){
        User seller = userRepository.findById(userId).get();
        Item item = Item.builder()
                        .itemName(dto.getItemName())
                        .price(dto.getPrice())
                        .quantity(dto.getQuantity())
                        .seller(seller)
                        .build();
        itemRepository.save(item);
        return "redirect:/items/myItems";
    }

    @GetMapping("/edit/{itemId}")
    public String editItemForm(@PathVariable Long itemId,Model model){
        Item item = itemRepository.findById(itemId).get();
        ItemDto itemDto = item.toDto();
        model.addAttribute("itemDto",itemDto);
        return "/items/editForm";
    }
    @PutMapping("/{itemId}")
    public String editItem(@PathVariable Long itemId,@ModelAttribute("itemDto")ItemDto changeDto){
        Item item = itemRepository.findById(itemId).get();
        itemService.editItem(item,changeDto);
        return "redirect:/items/myItems";
    }

    @GetMapping("/delete")
    public String deleteItemForm(@SessionAttribute(name= LOGIN_SESSION_ID)Long userId,
                                 Model model){
        User seller = userRepository.findById(userId).get();
        List<Item> items = itemRepository.findBySeller(seller);
        List<ItemDto> itemDtos = new ArrayList<>();
        for(Item item:items){
            itemDtos.add(item.toDto());
        }
        model.addAttribute("itemDtos",itemDtos);
        return "/items/deleteForm";
    }
    @DeleteMapping
    public String deleteItem(@RequestParam(name = "selectedItemId[]")List<Long> selectedItemId,
                             @SessionAttribute(name=LOGIN_SESSION_ID)Long userId){
        for (Long itemId:selectedItemId) {
//            Item item = itemRepository.findById(itemId);
//            String loginId = sessionManager.getLoginId(loginMemberId);
//            if(!Item.getLoginId().equals(loginId)) {
//                log.info("일치하지 않는 사용자, deleteItem(), loginId={}, but Item={}",loginId,Item);
//                return "redirect:/items";
//            }
            itemService.deleteItems(selectedItemId);
        }
        return "redirect:/items/myItems";
    }
}
