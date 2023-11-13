package com.project.shopapp.service;

import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.responses.product.ProductResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IProductService {
    Product CreateProduct(ProductDTO productDTO) throws Exception;
    Product getProductById(long id);
    Page<ProductResponses> getAllProduct(PageRequest pageRequest);
    Product updateProduct(long id,ProductDTO productDTO);
    void deleteProduct(long id);
    boolean existByName(String name);
    ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception;
}
