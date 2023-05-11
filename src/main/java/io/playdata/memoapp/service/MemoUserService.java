package io.playdata.memoapp.service;

import io.playdata.memoapp.model.MemoUserDTO;
import io.playdata.memoapp.repository.MemoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
    public MemoUserDTO createMemoUser(MemoUserDTO user, MultipartFile imageFile) throws Exception {
        if (memoUserRepository.findByLoginId(user.getLoginId()) != null) {
//            return null; // 중복으로 인한 Null
            // "이미 사용중인 ID입니다" : 오류 메시지
            throw new Exception("이미 사용중인 ID입니다"); // throw new 에러 발생
        }
        // 이미지가 아니더라도 들어감 (이미지만 들어가게!) -> 해결
        // 이미지 볼 수가 없음 (업로드한 이미지를 확인할 수 있게)
        if (imageFile != null && !imageFile.isEmpty()) { // ! : not
            String contentType = imageFile.getContentType();
            // contentType 검사를 통해 jpeg, png 타입만 들어가게
            boolean isJPEG = contentType.contains("image/jpeg");
            boolean isPNG = contentType.contains("image/png");
            if (!isJPEG && !isPNG) {
//                return null; // 이미지 타입 오류 인한 Null
                // "잘못된 이미지 타입입니다. (JPG, PNG)" : 오류 메시지
                // 의도적으로 에러 발생 -> 잘못된 접근에 대해서 가이드 메시지
                throw new Exception("잘못된 이미지 타입입니다. (JPG, PNG)"); // throw new 에러 발생
            }
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

    public byte[] loadFile(String fileName) throws IOException { // fileName을 주면 upload 폴더 경로에 있는 파일을 읽어서 전달
        Path filePath = Path.of(uploadPath + "/" + fileName); // 파일 경로
        return Files.readAllBytes(filePath);
    }

}
