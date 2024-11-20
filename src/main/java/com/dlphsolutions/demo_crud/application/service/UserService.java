package com.dlphsolutions.demo_crud.application.service;

import com.dlphsolutions.demo_crud.infrastructure.exception.UserServiceException;
import com.dlphsolutions.demo_crud.domain.model.User;
import com.dlphsolutions.demo_crud.domain.repository.IUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.dlphsolutions.demo_crud.application.Constants.generateRandomId;

@Service
@Log4j2
public class UserService {
    @Autowired
    private IUserRepository repository;

    public User saveUser(User user){
        log.info("Service save user");
        try {
            if(repository.findByUser(user.getUser()) != null){
                throw new UserServiceException("User with the same username already exists", null);
            }
            if(user.getId() == null || user.getUser().isEmpty())
                user.setId(String.valueOf(generateRandomId()));
            return  repository.save(user);
        } catch (Exception e){
            log.error("Failed to crate user. ", e);
            throw new UserServiceException("Failed to create user", e);
        }
    }

    public User findByUsername(String user){
        log.info("Service get user by username");
        try {
            if(repository.findByUser(user) == null){
                log.error("User not found");
                throw new UserServiceException("User not found", null);
            }
            return repository.findByUser(user);
        } catch (Exception e){
            log.error("User not found", e);
            throw new UserServiceException("User not found", e);
        }
    }
}
