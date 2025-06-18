package com.villwaa.backend.DTO;

import com.villwaa.backend.Model.ProductCategory;
import com.villwaa.backend.Model.Variant;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Builder
public class ProductDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;

    @NotNull(message = "MRP is required")
    @PositiveOrZero(message = "MRP must be non-negative")
    private BigDecimal mrp;

    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be non-negative")
    private BigDecimal price;

    @Size(max = 2000, message = "Description must be less than 2000 characters")
    private String description;

    @Min(value = 0, message = "Discount percentage must be non-negative")
    @Max(value = 100, message = "Discount percentage must not exceed 100")
    private int discount;

    @NotNull(message = "Availability is required")
    private Boolean isAvailable;

    @PositiveOrZero(message = "Quantity must be non-negative")
    private int stockQuantity;

    @NotNull(message = "Category is required")
    private ProductCategory category;

    private Set<String> thumbnails;

    private String washCare;

    @NotNull
    private Boolean isBestSeller;

    @NotNull
    private Boolean isNew;

    private Map<String, String> specifications;

    private List<VariantDTO> variants;
}
