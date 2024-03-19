package com.example.reactspringtodo.controller;

import com.example.reactspringtodo.dto.ResponseDTO;
import com.example.reactspringtodo.dto.UserDTO;
import com.example.reactspringtodo.entity.UserEntity;
import com.example.reactspringtodo.security.TokenProvider;
import com.example.reactspringtodo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

    private UserService userService;
    private TokenProvider tokenProvider;
    //BCrypt암호화 유틸
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserController (UserService userService, TokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        System.out.println("################################# signup#################################");

        try {//DB는 nullable이기 때문에, 비밀번호가 존재하는지를 컨트롤러에서 반드시 확인해야 한다.
            if(userDTO == null || userDTO.getPassword() == null) {
                throw new RuntimeException("Invalid Password value");
            }

            UserEntity user = UserEntity.builder()
                    .username(userDTO.getUsername())
                    //.password(userDTO.getPassword())
                    //암호화한 비밀번호로 엔티티 생성
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .build();

            //UserEntity 값을 DB에 등록하고 등록한 값을 반환함.
            UserEntity registeredUser = userService.create(user);

            UserDTO responseUserDTO = UserDTO.builder()
                    .id(registeredUser.getId())
                    .username(registeredUser.getUsername())
                    .build();

            return ResponseEntity.ok().body(responseUserDTO);

        }catch (Exception e) {
            //유저 정보는 항상 하나이므로 리스트로 만들어야 하는 ResponseDTO를 사용하지 않는다.
            //그냥 UserDTO를 리턴한다.

            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error(e.getMessage())
                    .build();

            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
        System.out.println("################################# signin#################################");

        //DB에 등록되 값이 존재하는지 확인하고 존재하는 경우 UserEntity 값을 반환함.
        UserEntity user = userService.getByCredentials(
                userDTO.getUsername(),
                userDTO.getPassword(),
                passwordEncoder //암호화 유틸 넘기기
        );

        //값이 존재하는 경우 UserDTO를 만들어 반환
        if(user != null) {
            //토큰 생성
            final String token = tokenProvider.create(user);
            System.out.println("###created Token: " + token);

            final UserDTO responseUserDTO = UserDTO.builder()
                    .username(user.getUsername())
                    .id(user.getId())
                    .token(token) //tokenProvider를 사용해 생성한 토큰을 UserDTO에 넣어준다.
                    .build();

            return ResponseEntity.ok().body(responseUserDTO);

        } else {
            // 로그인 정보가 존재하지 않는 경우 responseDTO를 생성하여 ResponseEntity의 body 값에 담아 반환한다.
            ResponseDTO<?> responseDTO = ResponseDTO.builder()
                    .error("Login failed.")
                    .build();

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}