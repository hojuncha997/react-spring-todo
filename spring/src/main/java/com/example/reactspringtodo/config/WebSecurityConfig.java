package com.example.reactspringtodo.config;

import com.example.reactspringtodo.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/*
서블릿 필터를 작성하였으면 서블릿 컨테이너가 해당 필터를 사용하라고 알려주는 설정작업을 해야 한다.
서블릿 필터를 작성한 후 web.xml에 필터를 등록해 주는 작업이다.

여기서는 스프링 시큐리티를 사요하므로,
JwtAuthenticationFilter라는 서블릿 필터를 작성하였으면 여기서는 스프링 시큐리티에게 이 필터를 사용하라고 알려줘야 한다.
 */
@EnableWebSecurity //안 적어주면 작동 안 함
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*HttpSecurity 빌더
        HttpSecurity는 시큐리티 설정을 위한 오브젝트이다. 이 오브젝트가 제공하는 빌더를 이용해 다양한 설정을 할 수 있다.
        말하자면 web.xml 대신 HttpSecurity를 이용하여 시큐리티 관련 설정을 하는 것이다.
         */
        http.cors() //WebMvcConfig.java에서 설정했으므로 기본 cors 설정.
                .and()
                .csrf() //현재는 사용하지 않으므로 disable
                    .disable()
                .httpBasic() //token을 사용하므로 basic 인증 disable
                    .disable()
                .sessionManagement() //Session기반이 아님을 선언
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests() // "/"와 "/auth/**" 경로는 인증하지 않아도 된다
                    .antMatchers("/", "/auth/**").permitAll()
                .anyRequest() // "/"와 "/auth/**" 이외의 모든 경로는 인증해야 한다.
                .authenticated();
        /*
         filter 등록

         매 요청마다 CorsFilter를 실행한 후에
         jwtAuthenticationFilter를 실행한다.
         반드시 이 순서로 해야하는 것은 아니다.

         */
        http.addFilterAfter( //(나중에 실행될 필터, 먼저 실행될 필터)
                jwtAuthenticationFilter,
                // 반드시 org.springframework.web.filter.CorsFilter를 임포트 해야한다.
                CorsFilter.class
        );


    }

}
