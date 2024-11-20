package com.dlphsolutions.demo_crud.application.service;

import com.dlphsolutions.demo_crud.infrastructure.exception.ItemNotFoundException;
import com.dlphsolutions.demo_crud.infrastructure.exception.ItemServiceException;
import com.dlphsolutions.demo_crud.domain.model.Item;
import com.dlphsolutions.demo_crud.domain.repository.IItemRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.dlphsolutions.demo_crud.application.Constants.generateRandomId;

@Service
@Log4j2
public class ItemService {
    @Autowired
    private IItemRepository repository;

    public List<Item> getAllItems(){
        log.info("Service get all items");
        try {
            return repository.findAll();
        } catch (Exception e){
            log.error("Failed to fetch items", e);
            throw  new ItemServiceException("Failed to fetch items", e);
        }
    }

    public Optional<Item> getItemById(String id){
        log.info("Service get item by id");
        try {
            return repository.findById(id)
                    .or(() -> {
                        log.error("Item not found with id: " + id);
                        throw new ItemNotFoundException(id);
                    });
        } catch (Exception e){
            log.error("Failed to fetch item with id: " + id, e);
            throw new ItemServiceException("Failed to fetch item with id: " + id, e);
        }
    }

    public List<Item> getItemByState(Boolean state){
        log.info("Service get item by state");
        try {
            List<Item> items = repository.findByState(state);
            if(items.isEmpty()){
                log.error("No items found with state: " + state);
                throw new ItemNotFoundException("State: " + state);
            }
            return items;
        } catch (Exception e){
            log.error("Failed to fetch items with state: " + state, e);
            throw new ItemServiceException("Failed to fetch items with state: " + state, e);
        }
    }

    public Item saveItem(Item item){
        log.info("Service save item");
        try {
            if(item.getId() == null || item.getId().isEmpty()) item.setId(String.valueOf(generateRandomId()));
            return repository.save(item);
        } catch (Exception e){
            log.error("Failed to create item", e);
            throw new ItemServiceException("Failed to create item", e);
        }
    }

    public Item updateItem(String id, Item item){
        log.info("Service update item");
        try {
            return repository.findById(id)
                    .map(existingItem ->{
                        item.setId(id);
                        return repository.save(item);
                    })
                    .orElseThrow(() -> {
                        log.error("Item not found with id: " + id);
                        return new ItemNotFoundException(id);
                    });
        } catch (Exception e){
            log.error("Failed to update item with id: " + id);
            throw new ItemServiceException("Failed to update item with id: " + id, e);
        }
    }


    public void deleteItem(String id){
        log.info("Service delete item");
        try {
            repository.findById(id)
                    .ifPresentOrElse(item -> repository.deleteById(id),
                            () -> {
                        log.error("Item not found with id: " + id);
                        throw new ItemNotFoundException(id);
                            });
        } catch (Exception e){
            log.error("Failed to delete item with id: " + id);
            throw new ItemServiceException("Failed to delete item with id: " + id, e);
        }
    }
}
