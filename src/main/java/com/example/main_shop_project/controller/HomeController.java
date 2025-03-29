
package com.example.main_shop_project.controller;

import com.example.main_shop_project.domain.users.User;
import com.example.main_shop_project.domain.users.UserRepository;
import com.example.main_shop_project.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping
public class HomeController {
    private final UserRepository userRepository;
    @GetMapping
    public String redirectHome(){
        return "redirect:/home";
    }
    @GetMapping("/home")
    public String home(@SessionAttribute(name=SessionConst.LOGIN_SESSION_ID,required = false)Long userId,
                       Model model){
        if(userId==null){
            return "home";
        }
        User user = userRepository.findById(userId).get();
        model.addAttribute("nickname",user.getNickname());
        return "loginHome";
    }
}
