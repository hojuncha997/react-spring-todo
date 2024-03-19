package com.example.reactspringtodo.security;

import com.example.reactspringtodo.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    /*이 클래스의 객체를 UserController의 /signin에서 토큰 생성 및 반환에 사용한다.
    create()를 사용하여 토큰을 생성하고,
    validateAndGetUserId()를 사용해 위조 여부를 판별한다.
    */

    private static final String SECRET_KEY="FlRpX30pMqDbiAkmlfArbrmVkDD4RqISskGZmBFax5oGVxzXXWUz TR5JyskiHMIV9M1Oicegkpi46AdvrcX1E6CmTUBc6IFbTPiD";

    //create(): jwt 라이브러리를 이용해 jwt 토큰을 생성한다. 이 과정에서 임의로 생성한 SECRETE_KEY를 개인 키로 사용한다.
    public String create(UserEntity userEntity) {
        //기한을 하루로 설정
        Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

        return Jwts.builder()
                //header에 들어갈 내용 및 서명을 기하 위한 Secret Key
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(userEntity.getId()) //sub: 생성할 토큰의 subject에 UserEntity.getId() 값이 들어간다. getSubjetc 하는 경우 UserEntity의 id 값을 가져올 수 있다.
                .setIssuer("demo app") //iss
                .setIssuedAt(new Date()) //iat: 토큰이 발행된 일자가 기록된다.
                .setExpiration(expiryDate) //exp: 토큰의 유효기한을 설정한다.
                .compact();
    }

    //토큰 디코딩, 파싱 및 위조 여부 확인
    public String validateAndGetUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY) //헤더와 페이로드를 setSigningKey로 넘어온 SETCRET_KEY를 이용해 서명 후 token의 서명과 비교
                .parseClaimsJws(token)//parseClaimsJws 메서드가 Base64로 token을 디코딩 및 파싱.
                .getBody(); //userID가 필요하므로 getBody 호출

        //이후 필요한 subject를 반환한다.
        return claims.getSubject();
    }

}