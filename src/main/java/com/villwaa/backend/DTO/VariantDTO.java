package com.villwaa.backend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class VariantDTO
{
    private Long id;

    @NotBlank(message = "Color is required")
    @Size(max = 255, message = "Color must be less than 255 characters")
    private String color;

    private boolean is_primary_variant;

    private List<ProductImageDTO> images;
}
