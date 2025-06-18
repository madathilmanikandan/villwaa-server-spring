package com.villwaa.backend.Service;

import com.villwaa.backend.DTO.ProductDTO;
import com.villwaa.backend.DTO.ProductImageDTO;
import com.villwaa.backend.DTO.VariantDTO;
import com.villwaa.backend.Model.Product;
import com.villwaa.backend.Model.ProductImage;
import com.villwaa.backend.Model.Variant;
import com.villwaa.backend.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

     private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private ProductDTO toProductDTO(Product product){
        return ProductDTO.builder()
                .id(product.getId())
                .isAvailable(product.getIsAvailable())
                .mrp(product.getMrp())
                .name(product.getName())
                .price(product.getPrice())
                .category(product.getCategory())
                .description(product.getDescription())
                .discount(10)
                .isNew(product.getIsNew())
                .specifications(product.getSpecifications())
                .isBestSeller(product.getIsBestSeller())
                .stockQuantity(product.getQuantity())
                .thumbnails(new HashSet<>(product.getThumbnails()))
                .variants(product.getVariants().stream().map(this::toVariantDTO).collect(Collectors.toList())).build();
    }

    private VariantDTO toVariantDTO(Variant variant){
        return VariantDTO.builder()
                .id(variant.getId())
                .color(variant.getColor())
                .is_primary_variant(variant.isPrimaryVariant())
                .images(variant.getImages().stream()
                        .map(this::toProductImageDTO).collect(Collectors.toList())).build();
    }

    private ProductImageDTO toProductImageDTO(ProductImage productImage){
        return ProductImageDTO.builder()
                .id(productImage.getId())
                .url(productImage.getUrl()).build();
    }

    private Product toProduct(ProductDTO productDto){
        return Product.builder()
                .isAvailable(productDto.getIsAvailable())
                .mrp(productDto.getMrp())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .category(productDto.getCategory())
                .description(productDto.getDescription())
                .isNew(productDto.getIsNew())
                .specifications(productDto.getSpecifications())
                .isBestSeller(productDto.getIsBestSeller())
                .quantity(productDto.getStockQuantity())
                .thumbnails(new ArrayList<>(productDto.getThumbnails()) {
                })
                .variants(productDto.getVariants().stream().map(this::toVariant).collect(Collectors.toList())).build();
    }

    private Variant toVariant(VariantDTO variantDto){
        Variant variant =  Variant.builder()
                .color(variantDto.getColor())
                .isPrimaryVariant(variantDto.is_primary_variant())
                .images(variantDto.getImages().stream()
                        .map(this::toProductImage).collect(Collectors.toList())).build();
        variant.getImages().forEach(productImage -> productImage.setVariant(variant));
        return variant;
    }

    private ProductImage toProductImage(ProductImageDTO productImageDto){
        return ProductImage.builder()
                .url(productImageDto.getUrl()).build();

    }

    @Transactional
    public ProductDTO createProduct(ProductDTO productDto){
        Product product = Product.builder()
                .name(productDto.getName())
                .mrp(productDto.getMrp())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .isAvailable(productDto.getIsAvailable())
                .quantity(productDto.getStockQuantity())
                .category(productDto.getCategory())
                .thumbnails(new ArrayList<>(productDto.getThumbnails()))
                .washCare(productDto.getWashCare())
                .isBestSeller(productDto.getIsBestSeller())
                .isNew(productDto.getIsNew())
                .specifications(productDto.getSpecifications())
                .build();

        if (productDto.getVariants() !=null){
            List<Variant> variants = productDto.getVariants().stream().map(this::toVariant).collect(Collectors.toList());
            variants.forEach(variant -> variant.setProduct(product));
            product.setVariants(variants);
        }
        Product savedProduct  = productRepository.save(product);
        return toProductDTO(savedProduct);
    }

    public ProductDTO updateProduct(Long id,ProductDTO productDto){
        Product product = productRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Product not found with id: "+id));

        if(productDto.getMrp().compareTo(productDto.getPrice())<0){
            throw new IllegalArgumentException("MRP must be greater than or equal to price");
        }
        if(productDto.getVariants().stream().filter(VariantDTO::is_primary_variant).count()>1){
            throw new IllegalArgumentException("Only one variant can be primary");
        }
        product.setMrp(productDto.getMrp());
        product.setCategory(productDto.getCategory());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getStockQuantity());
        product.setIsAvailable(productDto.getIsAvailable());
        product.setCategory(productDto.getCategory());
        product.setThumbnails(new ArrayList<>(productDto.getThumbnails()));
        product.setWashCare(productDto.getWashCare());
        product.setIsBestSeller(productDto.getIsBestSeller());
        product.setIsNew(productDto.getIsNew());
        product.setSpecifications(productDto.getSpecifications());

        if (productDto.getVariants() !=null){
            List<Variant> variants = productDto.getVariants().stream().map(this::toVariant).toList();
            product.setVariants(variants);
        }
        Product updateddProduct  = productRepository.save(product);
        return toProductDTO(updateddProduct);


    }

    public List<ProductDTO> getProducts(){
        return  productRepository.findAll().stream().map(this::toProductDTO).collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id){
        Product product =  productRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Product not found with id: "+id));
        return toProductDTO(product);
    }

    @Transactional
    public void deleteProduct(Long id){
       productRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Product not found with id: "+id));
       productRepository.deleteById(id);
    }


}
