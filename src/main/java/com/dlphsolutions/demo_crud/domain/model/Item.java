package com.dlphsolutions.demo_crud.domain.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "items")
@Data
public class Item {
    @Id
    private String id;
    @NotBlank
    @Size(min = 5, max = 20)
    private String title;
    @NotBlank
    @Size(min = 10, max = 100)
    private String content;
    @NotNull
    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private Integer quantity;
    @NotNull
    private Boolean state;
}
