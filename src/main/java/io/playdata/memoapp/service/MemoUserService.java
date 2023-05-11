package io.playdata.memoapp.service;

import io.playdata.memoapp.model.MemoUserDTO;
import io.playdata.memoapp.repository.MemoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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

    @Value("${upload.path}") // @Value : application.properties
    private String uploadPath;

    // 이미지 파일 업로드 기능이 추가된 서비스 메소드
    public MemoUserDTO createMemoUser(MemoUserDTO user, MultipartFile imageFile) throws IOException {
        if (memoUserRepository.findByLoginId(user.getLoginId()) != null) {
            return null;
        }
        // TODO : 이미지가 아니더라도 들어감 ㅋ (이미지만 들어가게!)
        // TODO : 이미지 볼 수가 없음 (업로드한 이미지를 확인할 수 있게)
        if (imageFile != null && !imageFile.isEmpty()) { // ! : not
            // 새로운 파일 이름
            // 시스템상의 현재 밀리초시간 : System.currentTimeMillis()
            // 확장자까지 포함된 파일 이름 : .getOriginalFilename()
            String newFileName = System.currentTimeMillis() + "-" + imageFile.getOriginalFilename();
            File file = new File(uploadPath + "/" + newFileName); // 새롭게 만들 파일의 경로
            imageFile.transferTo(file);
            user.setImage(newFileName);
        }
        return memoUserRepository.save(user);
    }

    // 로그인
    // loginId와 password를 통해서 유저를 찾는 서비스 메소드
    public MemoUserDTO login(String loginId, String password) {
        return memoUserRepository.findByLoginIdAndPassword(loginId, password);
    }

}
