package com.dlphsolutions.demo_crud.service;

import com.dlphsolutions.demo_crud.domain.model.Item;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import jakarta.validation.Validator;


import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ItemValidationTest {

    private Validator validator;

    private String title = "Item";
    private String content = "Content";
    private Integer queantity = 10;
    private Boolean state = true;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenTitleIsNull_thenValidationFails() {
        Item item = new Item();
        item.setContent(content);
        item.setQuantity(queantity);
        item.setState(state);
        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")));
    }

    @Test
    void whenContentIsNull_thenValidationFails() {
        Item item = new Item();
        item.setTitle(title);
        item.setQuantity(queantity);
        item.setState(state);
        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("content")));
    }

    @Test
    void whenQuantityIsNegative_thenValidationFails() {
        Item item = new Item();
        Integer quantity1 = -5;
        item.setTitle(title);
        item.setContent(content);
        item.setQuantity(quantity1);
        item.setState(state);
        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("quantity")));
    }

    @Test
    void whenActiveIsNull_thenValidationFails() {
        Item item = new Item();
        item.setTitle(title);
        item.setContent(content);
        item.setQuantity(queantity);
        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("state")));
    }
}

