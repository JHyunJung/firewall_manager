package com.crosscert.firewall.config.security;

import com.crosscert.firewall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    //스프링 시큐리티 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/assets/**").permitAll()  //정적 리소스
                    .antMatchers("/").permitAll()//홈화면
                    .antMatchers("/signup").anonymous()     //회원가입
                    .antMatchers("/login").anonymous()      //로그인
//                    .antMatchers("/leader/**").hasRole("LEADER")
//                .anyRequest().authenticated() //그 외 로그인 필요
                .anyRequest().permitAll() //개발용 임시 모두허용
                .and()
                .formLogin()
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .deleteCookies("JESSIONID")
                    .logoutSuccessUrl("/login")
                .and().build();
    }


    @Bean   //비밀번호 암호화, 비밀번호 검증용
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean   //타임리프에서 sec속성 사용
    public SpringSecurityDialect securityDialect(){
        return new SpringSecurityDialect();
    }

    

}
