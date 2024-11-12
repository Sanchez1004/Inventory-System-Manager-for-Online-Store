package com.pow.inv_manager.service;

import com.pow.inv_manager.dto.ProductDTO;
import com.pow.inv_manager.exception.ProductException;
import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO) throws ProductException;

    ProductDTO updateProduct(Long productId, ProductDTO productDTO) throws ProductException;

    ProductDTO getProduct(Long productId) throws ProductException;

    List<ProductDTO> getAllProducts() throws ProductException;
}
