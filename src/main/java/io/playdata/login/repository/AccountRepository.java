package io.playdata.login.repository;

import io.playdata.login.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    public Account findByLoginIDAndPassword(String loginID, String password);
    List<Account> findAllByOrderByIdAsc();
}
