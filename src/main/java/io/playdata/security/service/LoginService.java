package io.playdata.security.service;

import io.playdata.security.model.Account;
import io.playdata.security.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private AccountRepository accountRepository;



    public Account findUserByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    /*
        @Bean // 스프링 등록
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 해싱 (특정한 문구 -> 대응되는 문자열)
        // 로그인 기능 구현했습니다 -> Spring Security -> Bcrypt 해싱 -> Bcrypt (???)
        -> BCryptPasswordEncoder (등록)
    }
     */
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void join(Account account) {
        // 중복이나 기타...
        // Encoded password does not look like BCrypt (인코딩 안했을 때 오류)
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        // 일반적인 텍스트로 들어왔던 비밀번호를 bcrypt로 해싱
        accountRepository.save(account);
    }
}
