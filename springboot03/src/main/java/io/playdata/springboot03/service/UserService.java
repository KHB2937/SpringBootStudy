package io.playdata.springboot03.service;

import io.playdata.springboot03.model.User;
import io.playdata.springboot03.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User update(Long userId, User user) {
        // optional -> null이 나올 수 있다
        Optional<User> optionalUser = userRepository.findById(userId); // 이미 저장되었던 유저가 있는지를 확인
        // optionUser가 비어있다 - 존재하지 않는다 -> 해당 id에 대응하는 유저가 없다
        if (optionalUser.isEmpty()) { // isEmpty() -> 존재하지 않는다면
            return null;
        }
        // 기존에 저장되었던 유저가 있다
        user.setId(userId);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
