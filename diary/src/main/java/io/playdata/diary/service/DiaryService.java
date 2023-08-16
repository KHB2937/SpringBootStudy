package io.playdata.diary.service;

import io.playdata.diary.model.Diary;
import io.playdata.diary.repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class DiaryService {
    @Autowired
    private DiaryRepository diaryRepository;

    /**
     * 새로운 다이어리를 생성한다.
     *
     * @param diary 생성할 다이어리의 정보
     * @return 생성된 다이어리
     */
    public Diary createDiary(Diary diary) {
//        diary.setCreateAt(LocalDateTime.now()); // 생성시의 시간 입력
        return diaryRepository.save(diary);
    }

    /**
     * ID에 해당하는 다이어리를 조회한다.
     *
     * @param id 조회할 다이어리의 ID
     * @return 조회된 다이어리, 없으면 null
     */
    public Diary getDiary(Long id) {
        return diaryRepository.findById(id).orElse(null);
    }

    /**
     * 모든 다이어리를 조회한다.
     *
     * @return 조회된 다이어리 목록
     */
    public List<Diary> getAllDiaries() {
        return diaryRepository.findAll();
    }

    /**
     * ID에 해당하는 다이어리를 삭제한다.
     *
     * @param id 삭제할 다이어리의 ID
     */
    public void deleteDiary(Long id) {
        diaryRepository.deleteById(id);
    }

    /**
     * ID에 해당하는 다이어리의 정보를 변경한다.
     *
     * @param id 변경할 다이어리의 ID
     * @param newDiary 변경할 다이어리의 정보
     * @return 변경된 다이어리
     */
    public Diary updateDiary(Long id, Diary newDiary) {
        Diary diary = diaryRepository.findById(id).orElse(null);
        if (diary == null) {
            return null;
        }
        diary.setTitle(newDiary.getTitle());
        diary.setContent(newDiary.getContent());
        diary.setImage(newDiary.getImage());
        diary.setSound(newDiary.getSound());
        return diaryRepository.save(diary);
    }

//    @Value("${upload.path}")
//    private String uploadPath;

    @Autowired
    private S3Client s3Client; // @Bean

    public Diary createDiary(Diary diary, MultipartFile imageFile, MultipartFile soundFile) throws IOException {
        String bucketName = "test";
        if (imageFile != null) {
            // 새로운 파일 이름 - 파일이 겹칠 수 있으니까
            String newFileName = System.currentTimeMillis() + "-" + imageFile.getOriginalFilename();
            // 업로드를 받을 폴더 이름 -> 새로운 파일이 들어갈 최종 경로
//            File file = new File(uploadPath + "/" + newFileName);
//            imageFile.transferTo(file); // 파일 전송
            // S3(R2)로 전달
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(newFileName)
                    .build();
            s3Client.putObject(request, RequestBody.fromBytes(imageFile.getBytes()));
            diary.setImage(newFileName); // db에 저장하기 위한 새로운 파일 이름
        }
        if (soundFile != null) {
            // 새로운 파일 이름 - 파일이 겹칠 수 있으니까
            String newFileName = System.currentTimeMillis() + "-" + soundFile.getOriginalFilename();
            // 업로드를 받을 폴더 이름 -> 새로운 파일이 들어갈 최종 경로
//            File file = new File(uploadPath + "/" + newFileName);
//            soundFile.transferTo(file); // 파일 전송
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(newFileName)
                    .build();
            s3Client.putObject(request, RequestBody.fromBytes(soundFile.getBytes()));
            diary.setSound(newFileName); // db에 저장하기 위한 새로운 파일 이름
        }
//        diary.setCreateAt(LocalDateTime.now()); // 생성시의 시간 입력
        return diaryRepository.save(diary);
    }

    public Diary updateDiary(Long id,
                             Diary newDiary,
                             MultipartFile imageFile,
                             MultipartFile soundFile) throws IOException {
        Diary diary = diaryRepository.findById(id).orElse(null);
        if (diary == null) {
            return null;
        }
        diary.setTitle(newDiary.getTitle());
        diary.setContent(newDiary.getContent());
        String bucketName = "test";
        if (imageFile != null) {
            // 새로운 파일 이름 - 파일이 겹칠 수 있으니까
            String newFileName = System.currentTimeMillis() + "-" + imageFile.getOriginalFilename();
            // 업로드를 받을 폴더 이름 -> 새로운 파일이 들어갈 최종 경로
//            File file = new File(uploadPath + "/" + newFileName);
//            imageFile.transferTo(file); // 파일 전송
            // S3(R2)로 전달
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(newFileName)
                    .build();
            s3Client.putObject(request, RequestBody.fromBytes(imageFile.getBytes()));
            diary.setImage(newFileName); // db에 저장하기 위한 새로운 파일 이름
        }
        if (soundFile != null) {
            // 새로운 파일 이름 - 파일이 겹칠 수 있으니까
            String newFileName = System.currentTimeMillis() + "-" + soundFile.getOriginalFilename();
            // 업로드를 받을 폴더 이름 -> 새로운 파일이 들어갈 최종 경로
//            File file = new File(uploadPath + "/" + newFileName);
//            soundFile.transferTo(file); // 파일 전송
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(newFileName)
                    .build();
            s3Client.putObject(request, RequestBody.fromBytes(soundFile.getBytes()));
            diary.setSound(newFileName); // db에 저장하기 위한 새로운 파일 이름
        }
        return diaryRepository.save(diary);
    }

    public byte[] loadFile(String key) throws IOException {
        String bucketName = "test";
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        return s3Client.getObject(request).readAllBytes();
    }
}