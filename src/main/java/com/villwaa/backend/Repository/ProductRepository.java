package com.villwaa.backend.Repository;

import com.villwaa.backend.Model.Product;
import com.villwaa.backend.Model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByCategory(ProductCategory category);

    List<Product> findByIsAvailableTrue();

    List<Product> findByIsBestSellerTrue();

    List<Product> findByIsNewTrue();

}
