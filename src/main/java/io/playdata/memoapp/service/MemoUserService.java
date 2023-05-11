package io.playdata.memoapp.service;

import io.playdata.memoapp.model.MemoUserDTO;
import io.playdata.memoapp.repository.MemoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // 스프링에 Service임을 등록
public class MemoUserService {
    // DB와의 소통을 위해 Repository
    @Autowired // 의존성 주입
    private MemoUserRepository memoUserRepository;

    // 가입
    public MemoUserDTO createMemoUser(MemoUserDTO user) {
        // 1. 중복 아이디를 거를 수 없음
        // 2. 오류가 났을 때 무슨 오류인지
        // 특정한 loginId로 User가 존재하는지 검색
        // repository -> findByLoginId
        // 기존에 loginId가 같은 User가 있으면...
        if (memoUserRepository.findByLoginId(user.getLoginId()) != null) {
            return null; // null을 리턴
        }
        return memoUserRepository.save(user);
    }

    // 로그인
    // loginId와 password를 통해서 유저를 찾는 서비스 메소드
    public MemoUserDTO login(String loginId, String password) {
        return memoUserRepository.findByLoginIdAndPassword(loginId, password);
    }

}
