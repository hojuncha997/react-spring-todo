//WebMvcConfig.java

package com.example.reactspringtodo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //모든 경로에 대해
        registry.addMapping("/**")
                //Origin이 http://localhost:3000에 대해
                .allowedOrigins("http://localhost:3000") //1h : 60초 * 60
                //GET, POST, PUT, PATCH, DELETE, OPTIONS 메서드를 허용한다.
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*") //모든 헤더에 대해 허용
                .allowCredentials(true) //모든 인증정보를 허용
                .maxAge(MAX_AGE_SECS);

    }
    /*
    CORS
    :
    CORS는 웹 애플리케이션이 다른 도메인의 리소스에 접근할 수 있도록 하는 보안 메커니즘이다. 
    브라우저에서 실행되는 JavaScript를 사용하여 웹 애플리케이션이 다른 도메인의 리소스에 AJAX 요청을 보낼 때 CORS가 적용된다. 
    이는 웹 애플리케이션이 자신이 호스팅되는 도메인과 다른 도메인 간의 리소스 공유를 허용하는지 여부를 결정한다.
    */

    /*
    allowCredentials()
    :
    allowCredentials() 메서드는 Access-Control-Allow-Credentials 헤더 값을 설정하는 데 사용된다.
    이 헤더는 브라우저에게 요청 시에 사용자 인증 정보를 포함할 수 있는지 여부를 알려주는 역할을 한다.
    기본적으로 브라우저는 CORS 요청 시에는 인증 정보를 포함하지 않으며, 이를 Same-Origin Policy로 인해 접근할 수 없다.
    그러나 allowCredentials(true)를 사용하여 이 헤더를 설정하면 브라우저는 요청 시에 사용자 인증 정보를 포함할 수 있도록 허용된다.
    주의할 점은 Access-Control-Allow-Credentials 헤더를 사용하려면 Access-Control-Allow-Origin 헤더도 동일한 도메인 또는 *와 같은 와일드카드를 사용하여 설정되어야 한다.
    그렇지 않으면 브라우저는 Access-Control-Allow-Credentials 헤더를 무시하고 요청을 거부할 것이다.
    */

    /*
    maxAge()
    :
    maxAge() 메서드는 Access-Control-Max-Age 헤더의 값을 설정하는 데 사용된다.
    이 헤더는 브라우저에게 미리 요청을 캐시할 수 있는 최대 시간을 알려주는 역할을 한다.
    설정된 시간 동안은 브라우저는 동일한 요청을 반복해서 서버에 보내지 않고 캐시된 응답을 사용할 수 있다.
    이는 서버의 부하를 줄이고 응답 속도를 향상시키는 데 도움이 된다.

    maxAge() 메서드를 사용하여 Access-Control-Max-Age 헤더 값을 설정하면 브라우저는 해당 시간 동안 요청을 캐시하고, 그 이후에는 다시 서버에 요청을 보내야 한다.
    메서드의 인자로는 시간(초 단위)을 지정하게 되며, 0보다 큰 정수 값을 사용하여 지정한다.
    0이나 음수를 사용하면 캐시 시간이 없음을 의미하며, 매번 요청을 서버로 보내야 한다.
    * */

}
