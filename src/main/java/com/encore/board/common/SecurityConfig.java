package com.encore.board.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //해당 어노테이션은 Spring Security 설정을 customizing 하기 위함이다
//    WebSecurityConfigurerAdapter를 상속하는 방식은 deprecated! 되었음
@EnableGlobalMethodSecurity(prePostEnabled = true) // 사전 / 사후에 인증 / 권한 검사 어노테이션 사용 가능
public class SecurityConfig {

    @Bean
    public SecurityFilterChain myFilter(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
////                csrf 보안 공격에 대한 설정(대비)은 하지 않겠다라는 의미
                .csrf().disable()
//                특정 url에 대해서는 인증처리 하지 않고, 특정 url에 대해서는 인증처리 하겠다라는 설정
                .authorizeRequests()
//                인증 미적용 url 패턴
                .antMatchers("/", "/author/create", "/author/login-page")
                .permitAll()
//                그 외 요청은 모두 인증 필요
                .anyRequest().authenticated()
                .and()
//                만약 세션 방식을 사용하지 않으면 아래 내용대로 설정해야함
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .formLogin()
                .loginPage("/author/login-page")
//                spring security 내장 메서드를 사용하기 위해 /doLogin url 사용
                .loginProcessingUrl("/doLogin")
                .usernameParameter("email")
                .passwordParameter("pw")
                .successHandler(new LoginSuccessHandler())
                .and()
                .logout()
//                spring security 의 doLogout 기능 그대로 사용
                .logoutUrl("/doLogout")
                .and()
                .build();
    }
}
