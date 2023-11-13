package com.project.shopapp.controller;

import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.responses.product.ProductListResponses;
import com.project.shopapp.responses.product.ProductResponses;
import com.project.shopapp.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private  final ProductService productService;
    @GetMapping("")
    public ResponseEntity<ProductListResponses> getProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        PageRequest pageRequest = PageRequest.of(page -1,limit,
                Sort.by("id").ascending());
        Page<ProductResponses> productPage = productService.getAllProduct(pageRequest);
        int totalPages = productPage.getTotalPages();
        int totalContent = productPage.getContent().size();
        List<ProductResponses> products = productPage.getContent();
        return ResponseEntity.ok( ProductListResponses.builder()
                        .products(products)
                        .pageNumber(page)
                        .totalPage(totalPages)
                        .totalContent(totalContent)
                        .build());
    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult result){
        try{
            if (result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return  ResponseEntity.badRequest().body(errorMessages);
            }
            Product newProduct =  productService.CreateProduct(productDTO);
            return ResponseEntity.ok(newProduct);
        }catch (Exception e){
             return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
        @PathVariable long id,
        @RequestBody ProductDTO productDTO
    ){
        try{
            Product updateProduct = productService.updateProduct(id, productDTO);
            return ResponseEntity.ok(productDTO);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(value = "upload/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(@PathVariable("id") Long productId, @ModelAttribute("files") List<MultipartFile> files){
        try {
            Product existingProduct = productService.getProductById(productId);
            files = files == null ? new ArrayList<MultipartFile>():files;
            if (files.size()>ProductImage.MAXiMUM_IMAGE_IN_PRODUCT){
                return ResponseEntity.badRequest().body("You can only upload maximum 5 image");
            }
            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile file:files) {
                if (file.getSize()==0){
                    continue;
                }
                if (file.getSize()>10*1024*1024){
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("file nho hon 10Mb");
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")){
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("Đay k phải file ảnh");
                }
                //Lưu file và cập nhập thumbnail trong DTO
                String fileName = storeFile(file);
                ProductImage productImage = productService.createProductImage(
                        existingProduct.getId(),
                        ProductImageDTO.builder()
                                .imageUrl(fileName)
                                .build()
                );
                productImages.add(productImage);
            }
            return  ResponseEntity.ok().body(productImages);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public String storeFile(MultipartFile file) throws IOException{
//        if (isImageFile(file) || file.getOriginalFilename() == null){
//            throw new IOException("ivalid image format");
//        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        //Them UUID vào trước để tên ảnh là duy nhất
        String uniqueFileName = UUID.randomUUID().toString()+"_"+fileName;

        // Đoạn này là lưu luôn trong chương trình file upload
        // Dường dẫn đến thư mục muốn lưu
//        Path updateDir = Paths.get("uploads");
//        if (!Files.exists(updateDir)){
//            Files.createDirectories(updateDir);
//        }
//        //Đường dãn đến file
//        Path destination = Paths.get(updateDir.toString(),uniqueFileName);
//        // Sao chép file vào thư mục đích
//        Files.copy(file.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);
        //
        return uniqueFileName;
    }
    private boolean isImageFile(MultipartFile file){
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long ProductId){
        try{
            Product existingProduct = productService.getProductById(ProductId);
            return ResponseEntity.ok(ProductResponses.fromProduct(existingProduct));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable("id") Long id){
        return ResponseEntity.ok("product delete is successfully :"+id);
    }
}
