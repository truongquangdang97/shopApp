package com.project.shopapp.service;

import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.exception.InvalidParamException;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.repositories.CategoryRepository;
import com.project.shopapp.repositories.ProductImageRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.responses.product.ProductResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;


    @Override
    public Product CreateProduct(ProductDTO productDTO) throws DataNotFoundException {
        // xem lại video bài 30 phút 5.27

         Category categoryId = categoryRepository
                .findById(productDTO.getCategoryId())
                .orElseThrow(()->new DataNotFoundException("can not id_category"));

            Product newProduct = Product.builder()
                    .name(productDTO.getName())
                    .price(productDTO.getPrice())
                    .description(productDTO.getDescription())
                    .thumbnail(productDTO.getThumbnail())
                    .category(categoryId)
                    .build();
            return productRepository.save(newProduct);

    }

    @Override
    public Product getProductById(long productId) {
        try {
            return productRepository.findById(productId)
                    .orElseThrow(()->new DataNotFoundException("cannot product with id" +productId));
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Page<ProductResponses> getAllProduct(PageRequest pageRequest) {
        // để giới hạn page và limit
        return  productRepository.findAll(pageRequest).map(product ->ProductResponses.fromProduct(product));
        // Cách dưới cũng đc k vấn đê gì 
        // return  productRepository.findAll(pageRequest).map(ProductResponses::fromProduct);

    }

    @Override
    public Product updateProduct(long id, ProductDTO productDTO) {

        Product existingProduct = getProductById(id);
        if (existingProduct != null){
            Category existingCategory = null;
            try {
                existingCategory = categoryRepository
                        .findById(productDTO.getCategoryId())
                        .orElseThrow(()->new DataNotFoundException("can not id_category"));
            } catch (DataNotFoundException e) {
                throw new RuntimeException(e);
            }
            // copy các thuộc tính dto => product
            // có thể sử dụng thuộc tính ModelMapper
            existingProduct.setName(productDTO.getName());
            existingProduct.setCategory(existingCategory);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            productRepository.save(existingProduct);
        }
        return  null;
    }

    @Override
    public void deleteProduct(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(productRepository::delete);
    }

    @Override
    public boolean existByName(String name) {
        return productRepository.existsByName(name);
    }
    @Override
    public ProductImage createProductImage(
            Long productId,
            ProductImageDTO productImageDTO) throws Exception
    {
            Product existingProduct = productRepository
                    .findById(productId)
                    .orElseThrow(()->
                            new DataNotFoundException("can not id_product "));
            ProductImage newProductImage = ProductImage.builder()
                    .product(existingProduct)
                    .imageUrl(productImageDTO.getImageUrl())
                    .build();
            // không cho ínert quá 5 ảnh
        int size = productImageRepository.findByProductId(productId).size();
        if (size >= ProductImage.MAXiMUM_IMAGE_IN_PRODUCT){
//            return ResponseEntity.badRequest().body("You can only upload maximum 5 image");
            throw new InvalidParamException("number ò image must be <= "+ ProductImage.MAXiMUM_IMAGE_IN_PRODUCT);
        }
        return productImageRepository.save(newProductImage);
    }
}
