package com.dlphsolutions.demo_crud.service;

import com.dlphsolutions.demo_crud.application.service.ItemService;
import com.dlphsolutions.demo_crud.infrastructure.exception.ItemServiceException;
import com.dlphsolutions.demo_crud.domain.model.Item;
import com.dlphsolutions.demo_crud.domain.repository.IItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class ItemServiceIntegrationTest {

    @Autowired
    private ItemService service;

    @Autowired
    private IItemRepository repository;
    private Item item;
    private String id = "non_existent_id";
    private String title = "Item";
    private String content = "Content";
    private Integer queantity = 10;
    private Boolean state = true;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        item = new Item();
        item.setTitle(title);
        item.setContent(content);
        item.setQuantity(queantity);
        item.setState(state);
    }

    @Test
    void whenValidItem_thenSaveItem() {
        Item savedItem = service.saveItem(item);
        log.info("whenValidItem_thenSaveItem: Item saved: {}", item);
        assertNotNull(savedItem, "Item was not save");
        assertEquals(title, savedItem.getTitle(), "Item title not found");
        repository.deleteById(item.getId());
        log.info("Item was removed");
    }

    @Test
    void whenGetAllItems_thenReturnAllItems() {
        String title2 = "Item 2";
        String content2 = "Content 2";
        Integer quantity2 = 20;
        Boolean state2 = false;
        Item item2 = new Item();
        item2.setTitle(title2);
        item2.setContent(content2);
        item2.setQuantity(quantity2);
        item2.setState(state2);
        service.saveItem(item);
        service.saveItem(item2);
        List<Item> items = service.getAllItems();
        log.info("whenGetAllItems_thenReturnAllItems: items added: item {} and item2 {}", item, item2);
        assertNotNull(items, "Item list is empty");
        assertTrue(items.size() > 1, "The list of item does not have more than one element");
        assertTrue(items.stream().anyMatch(item -> title.equals(item.getTitle())), "The title of item is not the same");
        assertTrue(items.stream().anyMatch(item -> title2.equals(item.getTitle())), "The title of item2 is not the same");
        repository.deleteById(item.getId());
        repository.deleteById(item2.getId());
        log.info("Items were removed");

    }

    @Test
    void whenGetItemById_thenReturnItem() {;
        Item savedItem = service.saveItem(item);
        Optional<Item> foundItem = service.getItemById(savedItem.getId());
        log.info("whenGetItemById_thenReturnItem: Item with id {} found {}", savedItem.getId(), foundItem);
        assertTrue(foundItem.isPresent(), "Item not found");
        assertEquals(title, foundItem.get().getTitle(), "The title of the item is not the same");
        repository.deleteById(item.getId());
        log.info("Item was removed");
    }

    @Test
    void whenGetItemById_thenReturnEmpty() {
        log.info("whenGetItemById_thenReturnEmpty");
        assertThrows(ItemServiceException.class, () -> service.getItemById(id), "The exception message did not send");
    }

    @Test
    void whenUpdateItem_thenReturnUpdatedItem() {
        Item savedItem = service.saveItem(item);
        String updateTitle = "Updated Item";
        savedItem.setTitle(updateTitle);
        Item updatedItem = service.updateItem(savedItem.getId(), savedItem);
        log.info("whenUpdateItem_thenReturnUpdatedItem: Item with id {} update title for {} to {}", updatedItem.getId(), title, updatedItem.getTitle());
        assertNotNull(updatedItem, "Item not updated");
        assertEquals(updateTitle, updatedItem.getTitle(), "Title item was not update");
        repository.deleteById(item.getId());
        log.info("Item was removed");
    }

    @Test
    void whenUpdateItem_thenThrowException() {
        Item updatedItem = new Item();
        updatedItem.setTitle(title);
        updatedItem.setContent(content);
        updatedItem.setQuantity(queantity);
        updatedItem.setState(state);
        log.info("whenUpdateItem_thenThrowException: Invalid item id {}", updatedItem);
        assertThrows(ItemServiceException.class, () -> service.updateItem(id, updatedItem),"The exception message did not send");
    }

    @Test
    void whenDeleteItem_thenItemIsDeleted() {
        Item savedItem = service.saveItem(item);
        log.info("whenDeleteItem_thenItemIsDeleted: item {}", item);
        assertNotNull(savedItem.getId(), "Saved item should have an ID");
        service.deleteItem(savedItem.getId());
        log.info("whenDeleteItem_thenItemIsDeleted: item {}", item.getId().isEmpty());
        assertThrows(ItemServiceException.class, () -> service.deleteItem(savedItem.getId()),"The exception message did not send");
    }

    @Test
    void whenDeleteItem_thenThrowException() {
        assertThrows(ItemServiceException.class, () -> service.deleteItem(id), "The exception message did not send");
    }
}

