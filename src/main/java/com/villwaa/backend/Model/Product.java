package com.villwaa.backend.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull
    @Positive
    @Column(precision = 10,scale = 2)
    private BigDecimal mrp;

    @NotNull
    @Positive
    @Column(precision = 10,scale = 2)
    private BigDecimal price;

    @Size(max = 2000)
    private String description;

    @NotNull
    private Boolean isAvailable;

    @PositiveOrZero
    private int quantity;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @ElementCollection
    @CollectionTable(name="product_thumbnails",joinColumns = @JoinColumn(name="product_id"))
    @Column(name = "thumbnail_url")
    // initialized to avoid NPE
    private List<String> thumbnails = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Variant> variants = new ArrayList<>();

    @NotNull
    private String washCare;

    @NotNull
    private Boolean isBestSeller;

    @NotNull
    private Boolean isNew;

    @ElementCollection
    @CollectionTable(name="product_specifications",joinColumns = @JoinColumn(name="product_id"))
    @MapKeyColumn(name = "spec_key")
    @Column(name = "spec_value")
    private Map<String, String> specifications = new HashMap<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}

