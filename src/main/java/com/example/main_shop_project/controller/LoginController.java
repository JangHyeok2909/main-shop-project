package com.example.main_shop_project.controller;


import com.example.main_shop_project.domain.users.User;
import com.example.main_shop_project.domain.users.UserRepository;
import com.example.main_shop_project.dto.login.LoginDto;
import com.example.main_shop_project.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserRepository userRepository;
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false); // 현재 세션이 없으면 새로 만들지 않음
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/home";
    }
    @GetMapping("/login")
    public String loginForm(Model model, HttpServletRequest request){
        LoginDto loginDto = new LoginDto();
        model.addAttribute(loginDto);
        return "users/login/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginDto loginDto, HttpServletRequest request){
        User loginUser = userRepository.findByLoginId(loginDto.getLoginId()).get();
        if(!loginDto.getPassword().equals(loginUser.getPassword())){
            return "redirect:/login";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_SESSION_ID,loginUser.getId());
        return "redirect:/items";
    }
}
