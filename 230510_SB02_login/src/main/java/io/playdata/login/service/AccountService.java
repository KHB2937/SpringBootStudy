package io.playdata.login.service;

import io.playdata.login.model.Account;
import io.playdata.login.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    // join
    public void join(Account account) {
        account.setRole("user");
        accountRepository.save(account);
    }

    // login
    public Account login(String loginID, String password) {
        return accountRepository.findByLoginIDAndPassword(loginID, password);
    }

    // getAllAccounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAllByOrderByIdAsc();
    }
    // 회원 정보 업데이트
    public void updateUser(Account updatedAccount) {
        // 업데이트할 회원 정보 조회
        Account account = accountRepository.findById(updatedAccount.getId()).orElse(null);
        if (account == null) {
            // 업데이트할 회원 정보가 없으면 예외 처리 또는 적절한 에러 처리
            throw new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
        }
        // 회원 정보 업데이트
        account.setLoginID(updatedAccount.getLoginID());
        account.setName(updatedAccount.getName());
        account.setRole(updatedAccount.getRole());

        accountRepository.save(account);
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public void deleteAccountById(Long id) {
        accountRepository.deleteById(id);
    }
}
