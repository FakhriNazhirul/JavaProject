package com.project.organix.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {

    private Long id;
    private String name;
    private String email;
    private int points;
    private String role;
    private LocalDateTime createdAt;
}