package kr.ac.hansung.cse.hellospringdatajpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

// 컨트롤러 없이 뷰를 렌더링하기 위한 설정
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/products");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/signup").setViewName("signup");
        registry.addViewController("/accessDenied").setViewName("403");
    }

    @Bean
    public SpringSecurityDialect securityDialect() {
        // SecurityDialect는 Thymeleaf에서 사용하는 보안 관련 태그를 처리하는 클래스
        return new SpringSecurityDialect();
    }
}
