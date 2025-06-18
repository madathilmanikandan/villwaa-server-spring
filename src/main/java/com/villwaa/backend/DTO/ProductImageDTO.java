package com.villwaa.backend.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ProductImageDTO {
    private Long id;

    @NotBlank(message = "URL is required")
    private String url;
}
