package io.playdata.memoapp.service;

import io.playdata.memoapp.model.MemoDTO;
import io.playdata.memoapp.model.MemoUserDTO;
import io.playdata.memoapp.repository.MemoRepository;
import io.playdata.memoapp.repository.MemoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;

@Service // 스프링에 Service임을 등록
public class MemoService {
    // DB와의 소통을 위해 Repository
    @Autowired // 의존성 주입
    private MemoRepository memoUserRepository;

    public void createMemo(String text, long userId) {
        MemoDTO memo = new MemoDTO();
        memo.setText(text);
        memo.setUserId(userId);
        memoUserRepository.save(memo);
    }

    public List<MemoDTO> getMemoByUserId(long userId) {
        // findAllByUserId : userId를 주면 거기에 해당하는 메모들을 찾아달라
        // OrderByIdDesc : 정렬하되, Id를 기준으로 정렬해주고, Desc(내림차순)
        return memoUserRepository.findAllByUserIdOrderByIdDesc(userId);
    }
}
