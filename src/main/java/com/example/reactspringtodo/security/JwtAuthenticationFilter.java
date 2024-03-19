// 스프링 시큐리티에서 사용할 필터이다.

package com.example.reactspringtodo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//
//@Slf4j
//@Component
////OncePerRequestFilter 필터는 한 요청당 반드시 한 번만 실행된다. 여기서는 인증을 한 번만 하기 때문에 한 번만 한다.
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private TokenProvider tokenProvider;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        try {
//            //1.요청 헤더에서 토큰 가져오기
//            String token = parseBearerToken(request);
//            log.info("Filter is running ...");
//
//            //토큰 검사하기. JWT이므로 인가 서버에 요청하지 않고도 검증 가능
//            if (token != null && !token.equalsIgnoreCase("null")) {
//                //2. 토큰을 이용해 토큰을 인증한다. 토큰에서 userId 가져온다. 위조된 경우 예외처리된다.
//                String userId = tokenProvider.validateAndGetUserId(token);
//                log.info("Authenticated user ID : " + userId);
//
//                //인증 완료; SecurityContextHolder에 등록해야 인증된 사용자라고 생각한다.
//                //3. UsernamePasswordAuthenticationToken을 작성한다. 이것은 인증정보이다. 이 오브젝트에 사용자 인증 정보를 저장하고 SecurityContext에 인증된 사용자를 등록한다.
//                //서버가 요청이 끝나기 전까지 방금 인증한 사용자의 정보를 가지고 있어야 하기 때문이다. 요청을 처리하는 과정에서 사용자 인증 여부, 또는 인증된 사용자가 누구인지 알아야 할 떄가 있다.
//                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                        userId, //인증된 사용자의 정보. 문자열이 아니더라도 아무 거나 넣을 수 있다. 보통 UserDetails라는 오브젝트를 넣는다. 여기서는 사용하지 않았다.
//                        null, //
//                        AuthorityUtils.NO_AUTHORITIES
//                );
//
//                //4. 빌드 디테일 추가?
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                //5. SecurityContext 생성. SecurityContextHolder의 createEmptyContext() 메서드를 이용해 생성 가능하다.
//                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//                //6. 생성한 컨텍스트에 인증정보인 authentication을 넣는다.
//                securityContext.setAuthentication(authentication);
//                //7. securityContext를 다시 SecurityContextHolder에 등록한다.
//                SecurityContextHolder.setContext(securityContext);
//
//                //SecurityContextHolder는 기본적으로 ThreadLocal에 저장된다. 따라서 각 스레드마다 하나의 컨텍스트를 관리할 수 있다. 같은 스레드 내라면 어디에서든 접근 가능하다.
//                //ThreadLocal은 멀티 스레드 기반의 애플리케이션에서 주로 사용한다. ThreadLocal에 저장된 오브젝트는 각 스레드별로 저장되고, 불러올 때도 내 스레드에서 지정한 오브젝트만 불러올 수 있다.
//            }
//
//
//
//        } catch (Exception exception) {
//            log.error("Could not set user authentication in security context", exception);
//        }
//    }
//
//    //요청 헤더에서 Bearer 토큰을 가져오는 메서드.
//    private String parseBearerToken(HttpServletRequest request) {
//
//        //Http 요청의 헤더를 파싱해 Bearer 토큰을 리턴한다.
//        String bearerToken = request.getHeader("Authorization");
//        log.info("request.getHeader('Authorization') : " + bearerToken);
//
//        //StringUtils.hasText(bearerToken): StringUtils 유틸리티 클래스의 hasText 메서드는 주어진 문자열이 비어 있지 않은지 확인한다. 비어 있지 않으면 true를 반환하고, 그렇지 않으면 false를 반환한다. 따라서 bearerToken이 비어 있지 않은지 확인하는 조건이다.
//        //bearerToken.startsWith("Bearer "): bearerToken 문자열이 "Bearer "로 시작하는지 확인한다
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//
//            //bearerToken 문자열에서 인덱스 7부터 끝까지의 부분 문자열을 반환합니다. 따라서 "Bearer "
//            //예를 들어, bearerToken이 "Bearer ABC123"인 경우, substring(7)은 "ABC123"을 반환
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//
//}
//
///* 스레드 로컬의 예
//
//final class ThreadLocalSecurityContextHolderStrategy implements SecurityContextHolderStrategy {
//    private static final ThreadLocal<SecurityContext> contextHolder = new ThreadLocal<>();
//    //...
//}
//
//
// */

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 리퀘스트에서 토큰 가져오기.
            String token = parseBearerToken(request);
            log.info("Filter is running...");
            // 토큰 검사하기. JWT이므로 인가 서버에 요청 하지 않고도 검증 가능.
            if (token != null && !token.equalsIgnoreCase("null")) {
                // userId 가져오기. 위조 된 경우 예외 처리 된다.
                String userId = tokenProvider.validateAndGetUserId(token);
                log.info("Authenticated user ID : " + userId );
                // 인증 완료; SecurityContextHolder에 등록해야 인증된 사용자라고 생각한다.
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userId, // 인증된 사용자의 정보. 문자열이 아니어도 아무거나 넣을 수 있다.
                        null, //
                        AuthorityUtils.NO_AUTHORITIES
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        // Http 리퀘스트의 헤더를 파싱해 Bearer 토큰을 리턴한다.
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}