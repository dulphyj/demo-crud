package com.dlphsolutions.demo_crud.domain.repository;

import com.dlphsolutions.demo_crud.domain.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IUserRepository extends MongoRepository<User, String > {
    User findByUser(String user);
}
