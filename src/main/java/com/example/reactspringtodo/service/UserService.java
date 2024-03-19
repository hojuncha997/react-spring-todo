package com.example.reactspringtodo.service;

import com.example.reactspringtodo.entity.UserEntity;
import com.example.reactspringtodo.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity create(final UserEntity userEntity) {
        if(userEntity == null || userEntity.getUsername() == null) {
            throw new RuntimeException("Invalid argument");
        }

        final String username = userEntity.getUsername();

        if(userRepository.existsByUsername(username)){ //존재한다면 true 반환
            log.warn("Username already exists {}", username);
            throw new RuntimeException("Username already exists");
        }
        //없으면 DB에 저장하고, 저장된 엔티티 반환
        return userRepository.save(userEntity);
    }

    /*
    public UserEntity getByCredentials(final String username, final String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
    여기까지는 기존에 쓰던 코드. 아래는 인증과 인가 작업 이후의 코드
     */

    public UserEntity getByCredentials(final String username, final String password,
                                       final PasswordEncoder encoder) {
        final UserEntity originalUser = userRepository.findByUsername(username);

        //matches 메서드를 이용해 패스워드가 같은지 확인
        if(originalUser != null && encoder.matches(password, originalUser.getPassword())) {
            return originalUser;
        }
        return null;

    }

}