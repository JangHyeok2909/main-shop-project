package com.example.main_shop_project.service;

import com.example.main_shop_project.domain.users.User;
import com.example.main_shop_project.domain.users.UserRepository;
import com.example.main_shop_project.dto.login.UserDto;
import com.example.main_shop_project.session.SessionConst;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    @PersistenceContext
    private EntityManager em;
    @Transactional
    public User registeUser(UserDto dto, HttpServletRequest request){
        //유저 저장
        User user = User.builder()
                .loginId(dto.getLoginId())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .name(dto.getName())
                .money(0)
                .build();
        User savedUser = userRepository.save(user);
        //세션 생성(세션 id:유저 id), 클라에 쿠키 전달
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_SESSION_ID,savedUser.getId());
        return user;
    }
    @Transactional
    public User editUser(Long userId, UserDto userDto){
        User user = userRepository.findById(userId).get();
        user.changeNickname(userDto.getNickname());
        user.changeName(userDto.getName());
        user.changePassword(userDto.getPassword());
        return user;
    }
}
