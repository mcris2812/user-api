package com.company.api.user.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {
    @Id
    private String id;
    private String username;
}
