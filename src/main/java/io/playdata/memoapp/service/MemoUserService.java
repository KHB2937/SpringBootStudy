package io.playdata.memoapp.service;

import io.playdata.memoapp.model.MemoUserDTO;
import io.playdata.memoapp.repository.MemoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

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

    /*
    *     @Bean // 등록 시켜주겠다
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(() -> AwsBasicCredentials.create(accessKey, secretKey))
                .build();
    }
    * */
    @Autowired
    private S3Client s3Client;

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

            // 로컬 경로로 파일을 저장해주는 코드
//            File file = new File(uploadPath + "/" + newFileName); // 새롭게 만들 파일의 경로
//            imageFile.transferTo(file);

            // 객체 스토리지로 저장하는 코드
            PutObjectRequest request = PutObjectRequest.builder()
                            .bucket("test") // 파일을 집어넣을 버킷(폴더)
                            .key(newFileName) // 파일명
                            .build(); // 요청을 작성 (어느 버켓에, 어떤 이름으로 저장할지)
            // 객체 스토리지 클라이언트에게 요청과 파일(바이트)을 보내는 명령
            s3Client.putObject(request, RequestBody.fromBytes(imageFile.getBytes()));
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
//        Path filePath = Path.of(uploadPath + "/" + fileName); // 파일 경로
//        return Files.readAllBytes(filePath);
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket("test")
                .key(fileName)
                .build(); // 파일을 받아오기 위한 요청 설정
        return s3Client.getObject(request).readAllBytes(); // 바이트 형태로 변환
    }

}
