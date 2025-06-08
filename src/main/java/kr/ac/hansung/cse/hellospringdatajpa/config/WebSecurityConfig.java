package kr.ac.hansung.cse.hellospringdatajpa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    // 사용자 인증 정보를 로드하는 데 사용
    @Autowired
    private UserDetailsService customUserDetailsService; // customUserDetailsService 빈 주입

    // 비밀번호 암호화를 위한 PasswordEncoder 빈
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder는 비밀번호를 암호화하고 검증하는 데 사용
        return new BCryptPasswordEncoder();
    }

    // 인증 매니저 빈을 생성하여 Spring Security가 인증을 처리할 수 있도록 설정
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }

    // 공개적으로 접근 가능한 URL 패턴을 정의
    private static final String[] PUBLIC_MATCHERS = {
        "/webjars/**",  
        "/css/**",
        "/js/**",       // JavaScript 파일 접근 허용
        "/images/**",
        "/about/**",
        "/login",       // 로그인 페이지 접근 허용
        "/",            // 홈 페이지 접근 허용
        "/error/**",    // 에러 페이지 접근 허용
        "/console/**"   // 콘솔 접근 허용 (개발용)
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Authorization 설정: HTTP 요청에 대한 권한을 정의
                .authorizeHttpRequests(authz -> authz
                        // 공개적으로 접근 가능한 URL 패턴 정의
                        .requestMatchers(PUBLIC_MATCHERS).permitAll()

                        // 홈 페이지, 회원가입, 제품 목록, 로그인 페이지 등은 인증 없이 접근 허용
                        .requestMatchers("/", "/home", "/signup", "/products", "login", "/products/").permitAll()

                        // 상품 CUD와 admin 페이지는 ADMIN 권한 필요
                        .requestMatchers("/products/new", "/products/save", "/products/edit/**", "/products/delete/**", "/admin/**").hasRole("ADMIN")

                        // 기타 모든 요청은 인증이 필요
                        .anyRequest().authenticated()
                )
                // 로그인 설정: 사용자 인증을 위한 로그인 페이지와 성공/실패 URL 정의
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/products")
                        .failureUrl("/login?error")
                        .permitAll()
                )
                // 로그아웃 설정: 로그아웃 URL과 성공 시 리다이렉트 URL 정의
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                // 접근 거부 처리: 접근 거부 시 접근 거부 페이지로 리다이렉트
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedPage("/accessDenied")
                )

                // 사용자 정보 및 CSRF 보호 설정
                .userDetailsService(customUserDetailsService);   // 사용자 인증 정보를 로드하는 서비스 설정
                // .csrf(AbstractHttpConfigurer::disable); // CSRF 보호 비활성화 (API 서버에서는 보통 비활성화)

        return http.build();    // SecurityFilterChain 객체를 빌드하여 반환
    }
} 