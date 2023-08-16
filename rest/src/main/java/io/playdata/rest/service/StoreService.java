package io.playdata.rest.service;

import io.playdata.rest.model.StoreDTO;
import io.playdata.rest.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    public StoreDTO createStore(StoreDTO store) {
        return storeRepository.save(store);
    }

    public List<StoreDTO> findAll() {
        return storeRepository.findAll();
    }

    public Optional<StoreDTO> findById(Long id) {
        return storeRepository.findById(id);
    }

    public List<StoreDTO> findByNameKwd(String kwd) {
        // findBy속성명 : 완전 일치
        // findBy속성명Containing : 부분 일치 (포함)
        return storeRepository.findByNameContaining(kwd); // 생성 규칙
    }

    public List<StoreDTO> findAll(boolean price) {
        return price ? storeRepository.findAllByOrderByPriceAsc()
                : storeRepository.findAllByOrderByPriceDesc();
    }

    public void deleteById(Long id) {
        storeRepository.deleteById(id);
    }

    public StoreDTO updateById(Long id, StoreDTO store) {
        // TODO : 존재 여부를 검증
        store.setId(id);
        storeRepository.save(store);
        return store;
    }

    public StoreDTO changePriceById(Long id, int price) {
        StoreDTO store = storeRepository.findById(id).orElseThrow(); // DB에 저장된 값
        store.setPrice(price);
        storeRepository.save(store);
        return store;
    }
}