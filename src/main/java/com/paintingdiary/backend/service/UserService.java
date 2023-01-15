package com.paintingdiary.backend.service;

import com.paintingdiary.backend.exception.CommonException;
import com.paintingdiary.backend.model.entity.User;
import com.paintingdiary.backend.model.enums.UserType;
import com.paintingdiary.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User syncUser(String uid, String nickname) {

        User user = userRepository.findByUid(uid)
                .orElseGet(() -> new User(uid, getUniqueNickname(nickname), UserType.USER));

        return userRepository.save(user);
    }

    public User syncUserWithDuplicateError(String uid, String nickname) {

        userRepository.findByUid(uid)
                .ifPresent((ignored) -> {
                    throw CommonException.ALREADY_REGISTERED_USER;
                });

        userRepository.findByNickname(nickname)
                .ifPresent((ignored) -> {
                    throw CommonException.ALREADY_REGISTERED_NICKNAME;
                });

        return userRepository.save(new User(uid, nickname, UserType.USER));
    }

    public Optional<User> getUserByUid(String uid) {
        return userRepository.findByUid(uid);
    }

    private String getUniqueNickname(String nickname) {
        while (true) {
            if (userRepository.findByNickname(nickname).isEmpty()) {
                return nickname;
            }

            nickname = "USER_" + RandomStringUtils.randomAlphanumeric(11);
        }
    }

    public boolean isDuplicatedNickname(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }
}
