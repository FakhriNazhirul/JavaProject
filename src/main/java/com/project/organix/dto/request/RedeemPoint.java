package com.project.organix.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RedeemPoint {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Reward item ID is required")
    private Long rewardItemId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity = 1;
}