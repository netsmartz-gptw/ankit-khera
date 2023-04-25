package com.g1app.engine.services;

import com.g1app.engine.models.FamilyMember;
import com.g1app.engine.models.User;
import com.g1app.engine.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }


    @Override
    public Optional<User> findUserMobileFromOtp (String mobile_number) {
        return repository.findById(mobile_number);
    }

    @Override
    public User findBymobileNumber(String mobile_number) {
        return repository.findBymobileNumber(mobile_number);
    }

    @Override
    public User save(User entity) {
        return null;
    }

    @Override
    public FamilyMember getById(Serializable id) {
        return null;
    }

    @Override
    public List<FamilyMember> getAll() {
        return null;
    }

    @Override
    public void delete(Serializable id) {
    }
}
