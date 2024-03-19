package com.example.reactspringtodo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class UserEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id; //유저에게 고유하게 부여되는 id.

    @Column(nullable = false)
    private String username;

    // 패스워드. OAuth를 사용할 경우에는 null이다. null을 비허용할 경우 OAuth 구현에 문제가 발생한다.
    // DB에 null이 입력되는 대신, 컨트롤러에서 password를 반드시 입력하도록 만들어야 한다.
    private String password;

    //사용자의 롤. 예: 어드민, 일반 사용자
    private String role;

    // 이후 OAuth에서 사용할 유저 정보 제공자: github
    private String authProvider;

}