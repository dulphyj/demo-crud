package com.dlphsolutions.demo_crud.service;

import com.dlphsolutions.demo_crud.application.service.ItemService;
import com.dlphsolutions.demo_crud.infrastructure.exception.ItemServiceException;
import com.dlphsolutions.demo_crud.domain.model.Item;
import com.dlphsolutions.demo_crud.domain.repository.IItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
public class ItemServiceTest {
    @Mock
    private IItemRepository repository;
    @InjectMocks
    private ItemService service;
    private Item item;

    private String id = "1";
    private String title = "Item 1";
    private String content = "Content 1";
    private Integer queantity = 10;
    private Boolean state = true;

    @BeforeEach
    void setUp(){
        log.info("set item");
        MockitoAnnotations.openMocks(this);
        item = new Item();
        item.setId(id);
        item.setTitle(title);
        item.setContent(content);
        item.setQuantity(queantity);
        item.setState(state);
    }

    @Test
    void getItemById_Exists_ReturnItem(){
        when(repository.findById(id)).thenReturn(Optional.of(item));
        Optional<Item> foundItem = service.getItemById(id);
        log.info("getItemById_Exists_ReturnItem: item with id {} found: {}", id, foundItem);
        assertTrue(foundItem.isPresent(), "The item is not present");
        assertEquals(item, foundItem.get(), "the item obtained is not equal to the item sent");
    }

    @Test
    void getItemById_notExists_Exception(){
        when(repository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(ItemServiceException.class, () -> service.getItemById(id));
        log.info("getItemById_notExists_Exception: Item with id {} does not exist: {}", id, exception.getMessage());
        assertEquals("Failed to fetch item with id: 1", exception.getMessage(), "The exception message did not send");
    }

    @Test
    void getItemByState_ReturnItem(){
        when(repository.findByState(state)).thenReturn(List.of(item));
        List<Item> items = service.getItemByState(state);
        log.info("getItemByState_ReturnItem: Item with state {} found {}", state, items);
        assertFalse(items.isEmpty(), "Item does not find by state");
        assertTrue(items.size()!= 0, "There is no item");
    }

    @Test
    void saveItem_new_ReturnsNewItem(){
        when(repository.save(any(Item.class))).thenReturn(item);
        Item savedItem = service.saveItem(item);
        assertNotNull(savedItem);
        log.info("saveItem_new_ReturnsNewItem: New item created; {}", item);
        assertEquals(id, savedItem.getId(), "Item id is not the same");
        assertEquals(title, savedItem.getTitle(), "Item title is not the same");
        assertEquals(content, savedItem.getContent(),"Item content is not the same");
        assertEquals(queantity, savedItem.getQuantity(),"Item queantity is not the same");
        assertTrue(savedItem.getState(),"Item state is not the same");
    }

    @Test
    void updateItem_exists_ReturnsUpdated(){
        Item updateItem = new Item();
        updateItem.setTitle("updated title");
        updateItem.setContent("update content");
        updateItem.setQuantity(20);
        updateItem.setState(false);
        when(repository.findById(id)).thenReturn(Optional.of(item));
        when(repository.save(any(Item.class))).thenReturn(updateItem);
        Item update = service.updateItem(id, updateItem);
        log.info("updateItem_exists_ReturnsUpdated: Item with id {} updated: {}",id, update);
        assertNotNull(update, "Item not found");
        assertEquals(id, update.getId(), "Item id is not the same");
        assertEquals("updated title", update.getTitle(), "Item title was not updated");
        assertEquals("update content", update.getContent(), "Item content was not updated");
        assertEquals(20, update.getQuantity(), "Item quantity was not updated");
        assertFalse(update.getState(),"Item state was not updated");
    }

    @Test
    void updateItem_notExists_exception(){
        Item updateItem = new Item();
        updateItem.setTitle("updated title");
        updateItem.setContent("update content");
        updateItem.setQuantity(20);
        updateItem.setState(false);
        when(repository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(ItemServiceException.class, () -> service.updateItem(id, updateItem));
        log.info("updateItem_notExists_exception: Item id {}, exception {}", id, exception.getMessage());
        assertEquals("Failed to update item with id: 1", exception.getMessage(), "The exception message did not send");
    }

    @Test
    void deleteItem_ItemExists_DeletesItem() {
        when(repository.findById(id)).thenReturn(Optional.of(item));
        service.deleteItem(id);
        verify(repository, times(1)).deleteById(id);
        when(repository.findById(id)).thenReturn(Optional.empty());
        Optional<Item> deletedItem = repository.findById(id);
        log.info("deleteItem_ItemExists_DeletesItem: item deleted {}", deletedItem);
        assertFalse(deletedItem.isPresent(), "Item not deleted");
    }

    @Test
    void deleteItem_ItemDoesNotExist_ThrowsException() {
        when(repository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(ItemServiceException.class, () -> service.deleteItem(id));
        log.info("deleteItem_ItemDoesNotExist_ThrowsException: Exception message: {}", exception.getMessage());
        assertEquals("Failed to delete item with id: 1", exception.getMessage(),"The exception message did not send");
    }
}
