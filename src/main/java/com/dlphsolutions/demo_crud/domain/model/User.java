package com.dlphsolutions.demo_crud.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
public class User {
    @Id
    private String id;
    @NotBlank
    @Size(min = 3, max = 10)
    private String user;
    @NotBlank
    @Size(min = 5, max = 20)
    private String password;
}
