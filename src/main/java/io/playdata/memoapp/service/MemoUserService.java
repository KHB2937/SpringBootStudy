package io.playdata.memoapp.service;

import io.playdata.memoapp.repository.MemoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // 스프링에 Service임을 등록
public class MemoUserService {
    // DB와의 소통을 위해 Repository
    @Autowired // 의존성 주입
    private MemoUserRepository memoUserRepository;

    // 가입

    // 로그인
}
