package com.example.homemanager.auth.models;

import com.example.homemanager.utils.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(value = "tokens")
public class TokenDocument {

    private String id;
    private String token;
    private TokenType tokenType = TokenType.BEARER;
    private boolean revoked;
    private boolean expired;
    private String user;

}
