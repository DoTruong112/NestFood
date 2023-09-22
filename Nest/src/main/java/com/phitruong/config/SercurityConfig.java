package com.phitruong.config;


import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;

import com.phitruong.repository.AccountRepository;
import com.phitruong.service.AccountDetailService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
 class SecurityConfig  {

    private final AccountDetailService accountDetailService;

    public SecurityConfig(AccountDetailService accountDetailService) {
        this.accountDetailService = accountDetailService;
    }
      
    //Mã hóa mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    //Xác thực người dùng
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(accountDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    // Cấu hình bảo mật
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Tắt CSRF để cho phép gửi yêu cầu từ các nguồn không đáng tin cậy.
        http.csrf().disable()
            .authorizeRequests()
                // Cho phép tất cả mọi người truy cập các URL mà không cần xác thực.
                .requestMatchers("/home", "/product/**","/register/**","/register?**").permitAll()
             // Cho phép tất cả mọi người truy cập tệp CSS, JS.
                .requestMatchers("/user/assets/js/**", "/user/assets/css/**","/user/assets/imgs/**","/user/assets/fonts/**","/user/assets/sass/**").permitAll()
                // Yêu cầu xác thực cho các URL bắt đầu bằng "/order/".
                .requestMatchers("/order/**","/myaccount/**").authenticated()
                // Yêu cầu xác thực cho tất cả các URL khác.
                .requestMatchers("/admin/**").hasAnyRole("Staffs", "Directors")
                .anyRequest().authenticated()
            .and()
            .formLogin()
                // Cấu hình trang đăng nhập "/login".
                .loginPage("/login")
                // Cấu hình URL mặc định sau khi đăng nhập thành công.
                .defaultSuccessUrl("/home")
                .successHandler((request, response, authentication) -> {
                    // Lấy danh sách các vai trò của người dùng sau khi đăng nhập
                    List<String> roles = authentication.getAuthorities().stream()
                                            .map(GrantedAuthority::getAuthority)
                                            .collect(Collectors.toList());

                    // Nếu người dùng có vai trò "Staffs" hoặc "Directors", chuyển hướng đến "/admin"
                    if (roles.contains("ROLE_Staffs") || roles.contains("ROLE_Directors")) {
                        response.sendRedirect("/admin");
                    } else {
                        response.sendRedirect("/home");
                    }
                })
                // Cho phép tất cả mọi người truy cập vào trang đăng nhập.
                .permitAll()
            .and()
            .logout()
                // Cấu hình URL đăng xuất "/logout".
                .logoutUrl("/logout")
                // Cấu hình URL sau khi đăng xuất thành công.
//                .logoutSuccessUrl("/home")
                .logoutSuccessHandler((request, response, authentication) -> {
                    List<String> roles = authentication.getAuthorities().stream()
                                            .map(GrantedAuthority::getAuthority)
                                            .collect(Collectors.toList());

                    // Kiểm tra nếu người dùng có vai trò "Staffs" hoặc "Directors"
                    if (roles.contains("ROLE_Staffs") || roles.contains("ROLE_Directors")) {
                        response.sendRedirect("/login"); // Chuyển hướng đến trang đăng nhập
                    } else {
                        response.sendRedirect("/home"); // Chuyển hướng đến trang chính sau khi logout
                    }
                })
                // Cho phép tất cả mọi người truy cập vào trang đăng xuất.
                .permitAll()
        	.and()
	        .rememberMe()
		        .key("JfjakjlYjfldjfljOjflajlj24850")
		        .tokenValiditySeconds(604800) // Thời gian sống của cookie (đơn vị: giây), ở đây là 7 ngày
		        .rememberMeParameter("remember-me") // Tên của trường checkbox "Remember me" trong form đăng nhập
		        .userDetailsService(accountDetailService) // Cung cấp implementation của accountDetailService
	        .and()
	        .sessionManagement()
	        .maximumSessions(1) // Số lượng phiên đăng nhập tối đa của một người dùng (ở đây là 1)
	        .expiredUrl("/login"); // URL chuyển hướng khi session hết hạn
        // Trả về đối tượng SecurityFilterChain đã cấu hình.
        return http.build();
    }
}