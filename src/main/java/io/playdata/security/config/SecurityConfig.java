package io.playdata.security.config;

import io.playdata.security.model.Account;
import io.playdata.security.repository.AccountRepository;
import io.playdata.security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration // 밑의 코드를 바탕으로 설정 진행
@EnableWebSecurity // 웹 설정
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private PasswordEncoder passwordEncoder;
    @Bean // 스프링 등록
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 해싱 (특정한 문구 -> 대응되는 문자열)
        // 로그인 기능 구현했습니다 -> Spring Security -> Bcrypt 해싱 -> Bcrypt (???)
    } // 순환 참조 2 : passwordEncoder를 다른 곳으로 옮기던가...

    // The dependencies of some of the beans in the application context form a cycle
    // 순환 참조
    // SecurityConfig -> LoginService
    // LoginService -> passwordEncoder(SecurityConfig)
//    @Autowired
//    private LoginService loginService;
    @Autowired // Service 대신에 Repository를 써서 순환 참조 방지
    private AccountRepository accountRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 로그인 시에 로직 처리
        // 우리의 DB에 있는 유저(Account)를 쓰도록 연결
        auth.userDetailsService(
                // 유저를 구분할 수 있는 인증정보
                username -> {
//                    Account account = loginService.findUserByUsername(username);
                    Account account = accountRepository.findByUsername(username);
                    // 유저의 존재를 파악
                    if (account == null) { // 존재하지 않는다면 (Username에 맞는)
                        // UsernameNotFoundException <- Security가 가진 자체 에러
                        throw new UsernameNotFoundException("해당 유저가 없습니다");
                        // throw == return -> 처리되지 않으면 그 즉시 상위 메소드
                    }
                    // 유저가 있다
                    // 유저의 정보를 찾아서 return
                    // 우리가 만든 User가 아닌 Spring Security의 유저
                    return User.withUsername(username)
                            .password(account.getPassword()) // Lombok이 만들어준 getPassword
                            .roles(account.getRole())
                            .build(); // 위의 조건을 만족시키는 User를 만들어서 돌려주겠다 -> Session.
                }
        ).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll() // 로그인 페이지는 모든 사용자에게 허용
                .antMatchers("/join").permitAll() // 가입 페이지는 모든 사용자에게 허용
                .anyRequest().authenticated() // 나머지 요청은 인증이 필요
                .and()
                .formLogin()
                .loginPage("/login") // 커스텀 로그인 페이지 경로
                .defaultSuccessUrl("/home") // 로그인 성공 후 이동할 페이지 경로
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login") // 로그아웃 성공 후 이동할 페이지 경로
                .permitAll();
    }
}
