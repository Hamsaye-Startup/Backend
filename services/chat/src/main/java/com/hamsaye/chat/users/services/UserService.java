package com.hamsaye.chat.users.services;

import com.hamsaye.chat.configs.VersionControlConfig;
import com.hamsaye.chat.users.exceptions.NotFoundUserException;
import com.hamsaye.chat.users.models.*;
import com.hamsaye.chat.users.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final VersionControlConfig versionControlConfig;

    public UserEntity saveUser(UserEntity user, UserConnectionState connectionState) {
        user.setVersionControl(VersionControl.builder()
                .appVersion(versionControlConfig.appVersion())
                .build());

        user.setLoyaltyStatus(UserLoyaltyStatus.NEW_USER);
        user.setConnectionState(connectionState);
        return userRepository.save(user);
    }

    public UserEntity saveUser(UserEntity user) {
        user.setVersionControl(VersionControl.builder()
                .appVersion(versionControlConfig.appVersion())
                .build());

        user.setLoyaltyStatus(UserLoyaltyStatus.NEW_USER);
        return userRepository.save(user);
    }

    public void updateUser(UserEntity user) {
        user.setVersionControl(VersionControl.builder()
                .appVersion(versionControlConfig.appVersion())
                .build());

        userRepository.deleteById(user.getUid());
        userRepository.save(user);
    }

    public void deleteUser(UserEntity user) {
        userRepository.delete(user);
    }

    public UserEntity connectUser(UserEntity user, UserConnectionState connectionState) {
        user.setConnectionState(connectionState);
        return userRepository.save(user);
    }

    public UserEntity findUserById(UUID uid) {
        return userRepository.findById(uid)
                .orElseThrow(() -> new NotFoundUserException(uid.toString())); // TODO: implement the exception
    }
}
