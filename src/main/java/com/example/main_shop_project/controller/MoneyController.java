package com.example.main_shop_project.controller;


import com.example.main_shop_project.domain.orders.Order;
import com.example.main_shop_project.domain.orders.OrderRepository;
import com.example.main_shop_project.domain.users.User;
import com.example.main_shop_project.domain.users.UserRepository;
import com.example.main_shop_project.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.main_shop_project.session.SessionConst.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/money")
public class MoneyController {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @GetMapping("/transHis")
    public String getTransHistoryForm(@SessionAttribute(name = LOGIN_SESSION_ID)Long userId,
                                      Model model){
        User user = userRepository.findById(userId).get();
        List<Order> buyOrders = orderRepository.findByBuyer(userId);
        List<Order> sellOrders = orderRepository.findBySeller(userId);
//        for(Order order:buyOrders){
//            if(order.getSeller()==null) order.setSeller("탈퇴한 회원");
//        }
//        for(Order order:sellOrders){
//            if(order.getBuyer()==null) order.setBuyer("탈퇴한 회원");
//        }
        Integer sellMoney=0;
        Integer buyMoney=0;
        for (Order order:sellOrders) {
            sellMoney+=order.getTotalAmount();
        }
        for (Order order:buyOrders) {
            buyMoney+=order.getTotalAmount();
        }
        List<Integer> moneyRecord=new ArrayList();
        moneyRecord.add(sellMoney);
        moneyRecord.add(buyMoney);
        model.addAttribute("nickname",user.getNickname());
        model.addAttribute("moneyRecord",moneyRecord);
        model.addAttribute("sellOrders",sellOrders);
        model.addAttribute("buyOrders",buyOrders);
        return "/orders/TransHistoryForm";
    }
//    @GetMapping("/charge")
//    public String chargeMoneyForm(@SessionAttribute(name=LOGIN_SESSION_ID)Integer loginMemberId,
//                                  Model model){
//        Member loginMember = sessionManager.getMember(loginMemberId);
//        model.addAttribute("loginMember",loginMember);
//        return "/mypage/chargeMoneyForm";
//    }
//    @PostMapping("/charge")
//    public String chargeMoney(@SessionAttribute(name=LOGIN_SESSION_ID)Integer loginMemberId,
//                              @RequestParam(name = "rechargeAmount")Integer chargeAmount){
//        Member loginMember = sessionManager.getMember(loginMemberId);
//        int beforeMoney = loginMember.getMoney();
//        loginMember.setMoney(beforeMoney+chargeAmount);
//        log.info("charge Money, MemberLoginId={}, before money={} +{} ->after money={}",loginMember.getLoginId(),beforeMoney,chargeAmount,loginMember.getMoney());
//        memberRepository.update(loginMember);
//        return "redirect:/members/memberInfo";
//    }
}
