package com.crosscert.firewall.config.security;

import com.crosscert.firewall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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

@Log4j2
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    //스프링 시큐리티 설정 (prod)
    @Bean
    @Profile("prod")
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        log.info("운영용 Spring Security 설정");
        //TODO : 유저 관리는 LEADER 권한만 가능하도록 설정 수정하기
        return httpSecurity
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/assets/**").permitAll()  //정적 리소스
                    .antMatchers("/css/**").permitAll()
                    .antMatchers("/js/**").permitAll()
                    .antMatchers("/").permitAll()           //홈화면
                    .antMatchers("/signup/**").anonymous()     //회원가입
                    .antMatchers("/login").anonymous()      //로그인
//                    .antMatchers("/leader/**").hasRole("LEADER")
                .anyRequest().authenticated() //그 외 로그인 필요
                .and()
                .formLogin()
                    .loginPage("/login")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .deleteCookies("JESSIONID")
                    .logoutSuccessUrl("/")
                .and().build();
    }

    //스프링 시큐리티 설정 (개발용)
    @Bean
    @Profile("!prod")
    public SecurityFilterChain filterChainForDev(HttpSecurity httpSecurity) throws Exception {
        log.info("개발용 Spring Security 설정");
        return httpSecurity
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/assets/**").permitAll()  //정적 리소스
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/").permitAll()           //홈화면
                .antMatchers("/signup/**").anonymous()     //회원가입
                .antMatchers("/login").anonymous()      //로그인
                .anyRequest().permitAll() //개발용 임시 모두허용
                .and()
                .formLogin()
                    .loginPage("/login")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .deleteCookies("JESSIONID")
                    .logoutSuccessUrl("/")
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
