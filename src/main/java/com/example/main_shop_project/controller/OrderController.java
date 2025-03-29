package com.example.main_shop_project.controller;

import com.example.main_shop_project.domain.items.Item;
import com.example.main_shop_project.domain.items.ItemRepository;
import com.example.main_shop_project.domain.users.User;
import com.example.main_shop_project.domain.users.UserRepository;
import com.example.main_shop_project.domain.orders.Order;
import com.example.main_shop_project.domain.orders.OrderRepository;
import com.example.main_shop_project.dto.ItemDto;
import com.example.main_shop_project.dto.OrderDto;
import com.example.main_shop_project.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.main_shop_project.session.SessionConst.LOGIN_SESSION_ID;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final OrderService orderService;


    @GetMapping
    public String boughtOrdersForm(@SessionAttribute(name = LOGIN_SESSION_ID)Long userId,
                            Model model){
//        String buyer = sessionManager.getNickname(loginMemberId);
//        List<Order> findOrders = orderRepository.findByBuyer(buyer);
//        for(Order order:findOrders){
//            if(order.getBuyer()==null) order.setBuyer("탈퇴한 회원");
//            if(order.getSeller()==null) order.setSeller("탈퇴한 회원");
//        }
//        log.info("find Order={}",findOrders);
        User buyer = userRepository.findById(userId).get();
        List<Order> boughtOrders = orderRepository.findByBuyer(buyer);
        model.addAttribute("nickname",buyer.getNickname());
        model.addAttribute("orders",boughtOrders);
        return "/orders/orderList";
    }
    @PostMapping("/{itemId}")
    public String buyItem(@SessionAttribute(name= LOGIN_SESSION_ID)Long userId,
                          @RequestParam(name="selectedQuantity")Integer selectQuantity,
                          @PathVariable Long itemId){
        User buyer = userRepository.findById(userId).get();
        Item item = itemRepository.findById(itemId).get();
        Order order = orderService.buyItemService(item, buyer, selectQuantity);

        return "redirect:/orders/finish/"+order.getId();
    }

    @GetMapping("/finish/{orderId}")
    public String buyFinished(@PathVariable Long orderId,Model model){
        Order order = orderRepository.findById(orderId).get();
        OrderDto orderDto = order.toDto();
        ItemDto itemDto = order.getItem().toDto();
//        log.info("buyFished method run, order={}, item={}",order,item);
        model.addAttribute("nickname",order.getBuyer().getNickname());
        model.addAttribute("order",orderDto);
        model.addAttribute("item",itemDto);
        return "/orders/buyfinish";
    }
}
