package io.playdata.tshirts.service;

import io.playdata.tshirts.model.TShirt;
import io.playdata.tshirts.repository.TShirtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TShirtService {

    @Autowired
    private TShirtRepository tShirtRepository;

    public List<TShirt> getAllTShirts() {
        return tShirtRepository.findAll(); // Repository를 사용해서 TShirt를 모두 DB 조회
    }

    public TShirt getTShirtById(Long id) {
        return tShirtRepository.findById(id).orElse(null);
        // ID를 통해서 TShirt를 검색하고, 해당 ID에 대응하는게 있으면, TShirt로 아니면 null로.
    }

    public TShirt addTShirt(TShirt tShirt) {
        return tShirtRepository.save(tShirt); // 새로운 TShirt 객체를 DB에 저장
        // ID는 자동생성
    }

    public TShirt updateTShirt(TShirt tShirt) {
        TShirt existingTShirt = tShirtRepository.findById(tShirt.getId()).orElse(null);
        // 전달 받은 TShirt 객체의 ID로 해당 ID에 대응하는 DB 데이터가 있는지 체크
        if (existingTShirt != null) { // 없으면 null
            existingTShirt.setColor(tShirt.getColor());
            existingTShirt.setLogo(tShirt.getLogo());
            existingTShirt.setSize(tShirt.getSize());
            existingTShirt.setPrice(tShirt.getPrice());
            return tShirtRepository.save(existingTShirt); // 새로운 데이터를 반영해서 저장
        } else {
            return null; // 그대로 null 리턴해서 controller가 알 수 있게 해줌
        }
    }

    public void deleteTShirtById(Long id) {
        tShirtRepository.deleteById(id); // id를 전달받아서 대응되는 DB에 있는 티셔츠를 삭제
    }

}