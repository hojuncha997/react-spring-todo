//서블릿 필터 예제
/*이 프로젝트에서는 스프링 시큐리티를 사용하기 때문에 사용하지 않음*/
package com.example.reactspringtodo.security;

import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExampleServletFilter extends HttpFilter {
    private TokenProvider tokenProvider;

    @Override
    protected void doFilter(HttpServletRequest request,
                            HttpServletResponse response,
                            FilterChain filterChain) throws IOException, ServletException {

        try {
            final String token = parseBearerToken(request);

            if (token != null && !token.equalsIgnoreCase("null")) {
                //userId 가져오기. 위조된 경우 예외 처리됨
                String userId = tokenProvider.validateAndGetUserId(token);

                //다음 ServletFilter 실행
                //한 파일에 필터가 한 개일 필요는 없다. 그러나 크기가 커져서 좋을 것이 없기 때문에 FilterChain을 사용하여 연쇄적 순서로 필터를 사용한다.
                filterChain.doFilter(request, response);
            }

        } catch (Exception e) {
            //예외 발생 시 response를 403 Forbidden으로 설정.
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

    };

    private String parseBearerToken(HttpServletRequest request) {
        // Http 요청의 헤더를 파싱해 Bearer 토큰을 리턴한다.
        String bearerToken =  request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }



}
//스프링 부트를 사용하지 않는 경우 web.xml 파일에 아래와 같이 설정해 줄 수 있다. 필터를 어느 경로(예 /todo)에 적용해야 하는지 알려줘야 한다.
/*
<filter>
    <filter-name>ExampleServletFilter</filter-name>
    <filter-class>com.example.reactspringtodo.security.ExampleServletFilter</filter-class>
    ..다른 매개변수들..
</filter>
<filter-mapping>
    <filter-name>ExampleServletName</filter-name>
    <url-pattern>/todo</url-pattern>
</filter-mapping>

 */
