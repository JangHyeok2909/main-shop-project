package com.example.main_shop_project.controller;

import com.example.main_shop_project.domain.users.User;
import com.example.main_shop_project.domain.users.UserRepository;
import com.example.main_shop_project.dto.login.LoginDto;
import com.example.main_shop_project.dto.login.UserDto;
import com.example.main_shop_project.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.example.main_shop_project.session.SessionConst.LOGIN_SESSION_ID;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping
    public String registeUserForm(Model model){
        model.addAttribute("user",new User());
        return "users/registeUserForm";
    }
    @PostMapping
    public String registeUser(@ModelAttribute UserDto userDto, HttpServletRequest request){
        userService.registeUser(userDto,request);
        return "redirect:/home";
    }

    @GetMapping("/mypage")
    public String mypageForm(@SessionAttribute(name = LOGIN_SESSION_ID,required = false)Long userId,
                             Model model){
        if(userId == null){
            return "redirect:/login";
        }
        User loginUser = userRepository.findById(userId).get();
        model.addAttribute("loginUser",loginUser);
        return "mypages/mypage";
    }
    @GetMapping("/info")
    public String userInfoForm(@SessionAttribute(name = LOGIN_SESSION_ID,required = false)Long userId,
                               Model model){
        User user = userRepository.findById(userId).get();
        model.addAttribute("loginUser",user);
        return "/mypages/userInfo";
    }
    @GetMapping("/edit")
    public String userEditForm(@SessionAttribute(name = LOGIN_SESSION_ID,required = false)Long userId,
                               Model model){
        User user = userRepository.findById(userId).get();
        model.addAttribute("loginUser",user);
        return "/mypages/editUser";
    }
    @PutMapping
    public String editUser(@SessionAttribute(name=LOGIN_SESSION_ID)Long userId,
                           @RequestParam(name="nickname")String nickname,
                           @RequestParam(name="name")String name,
                           @RequestParam(name="password")String password){
        UserDto dto = UserDto.builder()
                .nickname(nickname)
                .name(name)
                .password(password)
                .build();
        userService.editUser(userId,dto);
//        log.info("editMember before={}, after={}",loginMember,edittedMember);
//        memberRepository.update(edittedMember);
        return "redirect:/users/mypage";
    }
    @GetMapping("/delete")
    public String deleteUserForm(Model model){
        model.addAttribute("loginDto",new LoginDto());
        return "/users/deleteUserForm";
    }
    @DeleteMapping
    public String deleteUser(@SessionAttribute(name=LOGIN_SESSION_ID)Long userId,
                             @ModelAttribute(name = "loginDto")LoginDto dto){
        User dbUser = userRepository.findById(userId).get();
        if(!dbUser.getPassword().equals(dto.getPassword())){
            log.info("비밀번호 잘못입력, loginid = {}, pw = {}",dto.getLoginId(),dto.getPassword());
            return "redirect:/delete";
        }
        userRepository.delete(dbUser);
        return "redirect:/logout";
    }
}
