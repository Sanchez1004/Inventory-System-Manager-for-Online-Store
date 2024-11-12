package com.pow.inv_manager.dto.mapper;

import com.pow.inv_manager.dto.ProductDTO;
import com.pow.inv_manager.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    public ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .build();
    }

    public Product toEntity(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .build();
    }
}
