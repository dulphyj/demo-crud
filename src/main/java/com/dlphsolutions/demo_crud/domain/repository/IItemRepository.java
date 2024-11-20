package com.dlphsolutions.demo_crud.domain.repository;

import com.dlphsolutions.demo_crud.domain.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IItemRepository extends MongoRepository<Item, String> {
    List<Item> findByState(Boolean state);
}
