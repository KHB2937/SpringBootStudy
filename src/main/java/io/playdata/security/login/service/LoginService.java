package io.playdata.security.login.service;

import io.playdata.security.login.model.AccountDTO;
import io.playdata.security.login.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service // 스프링에 등록
public class LoginService {
    // 데이터를 전달받은 Repository를 의존성 주입
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // join, register
    public AccountDTO register(AccountDTO accountDTO) throws Exception {
        // username이 중복되면 어떡해?
        if (accountRepository.findByUsername(accountDTO.getUsername()) != null) {
            throw new Exception("중복 유저 이름"); // -> Controller한테 알려줘서 적절한 응답
            // 예외 처리 1 : try catch 문으로 묶어줘서 처리하는 것
            // 예외 처리 2 : 상위 메소드, 클래스에게 처리를 위임하는 것
            // throws Exception
        }
        // 로그인을 할 때는 bcrypt가 적용이 되었는데
        // 가입을 할 때는 bcrypt가 적용이 안됨
        // Spring Security 관련 작업
        String newPassword = passwordEncoder.encode(accountDTO.getPassword());
        accountDTO.setPassword(newPassword);

        // Controller로부터 accountDTO를 전달 받아서 repository를 사용해서 DB 등록
        return accountRepository.save(accountDTO);
    }
}
