package io.playdata.security.confg;

import io.playdata.security.login.model.AccountDTO;
import io.playdata.security.login.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // 설정을 등록해주는 어노테이션
@EnableWebSecurity // 웹 보안 설정
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccountRepository accountRepository; // **

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(
                // username을 사용한 자바 람다식
          username -> {
              AccountDTO accountDTO = accountRepository.findByUsername(username); // **
              // 특정한 유저가 존재하는지 검증
              if (accountDTO != null) { // 유저가 존재한다면 // **
                  // User <- 스프링 security에서 일반적인 사용자를 관리할 때 쓰는 클래스
                  // 우리가 정의한 엔터티(DTO)를 연결해서 사용
                  return User.withUsername(accountDTO.getUsername()) // **
                          .password(accountDTO.getPassword()) // **
                          .roles(accountDTO.getRole()) // **
                          .build(); // 스프링 Security에서 관리하는 유저를 생성 (세션)
              } else {
                  // 스프링 Security에서 이미 가지고 있는 에러
                  throw new UsernameNotFoundException("해당 유저가 없습니다");
              }
          }).passwordEncoder(passwordEncoder()); // 암호를 복호화 (bcrypt)
        // 암호가 그대로 저장이 되면 해킹등의 문제가 있을 수 있기 때문에 인간이 알아보지 못하게 변형해서 저장
        // 암호를 DB 저장
    }

    @Bean // 스프링에 등록
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Bcrypt를 사용해서 복호화하는 기능이 지원
    }

    // -> 스프링 Security를 위한 사전 작업

    // configure -> http 요청이 들어왔을 때의 설정

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll() // 인증없이 login 페이지 사용 가능
                .antMatchers("/register").permitAll() // 인증없이 register 페이지 사용 가능
                .anyRequest().authenticated() // 나머지 요청은 인증 필요
                .and()
                .formLogin() // form 사용해서 로그인할 것이다
                .loginPage("/login") // 로그인 페이지 URL
                .defaultSuccessUrl("/home") // 로그인 성공 후 이용할 URL
                .permitAll();
        // TODO : ???
    }
}
