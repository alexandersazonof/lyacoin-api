package com.lyacoin.api.service;

import com.lyacoin.api.core.dto.UserRegistrationDto;
import com.lyacoin.api.core.model.User;
import com.lyacoin.api.exception.AppException;
import com.lyacoin.api.exception.ExceptionMessageClient;
import com.lyacoin.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InitSeedService initSeedService;

    public User save(UserRegistrationDto user) {
        if (user.getSeed() == null || user.getSeed().size() < 1) {
            throw new AppException(ExceptionMessageClient.INTERNAL_ERROR, "Seed yet exist");
        }

        initSeedService.findBySeed(user.getSeed())
                .orElseThrow(() -> new AppException(ExceptionMessageClient.INTERNAL_ERROR, "Seed doesn't yet begin init phase.."));

        userRepository.findBySeed(user.getSeed())
                .ifPresent(i -> {
                    throw new AppException(ExceptionMessageClient.INTERNAL_ERROR, "Seed yet exist");
                });
        return userRepository.save(
                User.builder()
                .seed(user.getSeed())
                .build());
    }

    public Optional<User> findBySeed(List<String> seed) {
        return userRepository.findBySeed(seed);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }
}
