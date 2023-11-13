package com.project.shopapp.controller;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;
import com.project.shopapp.responses.category.CategoryListResponses;
import com.project.shopapp.responses.category.CategoryResponses;
import com.project.shopapp.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Validated
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("")
    public ResponseEntity<?> createCategory(@Valid @RequestBody
                                            CategoryDTO categoryDTO,
                                            BindingResult result
    ){
        if (result.hasErrors()){
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return  ResponseEntity.badRequest().body(errorMessages);
        }
        categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok("ínert category succsessfully");
    }
    @GetMapping("")
    public ResponseEntity<CategoryListResponses> getAllCategory(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        PageRequest pageRequest = PageRequest.of(page-1,limit,Sort.by("id").ascending());
        Page<CategoryResponses> categoryResponses = categoryService.getAllCategory(pageRequest);
        int totalPages = categoryResponses.getTotalPages();
        int totalContent = categoryResponses.getContent().size();
        List<CategoryResponses> categories = categoryResponses.getContent();
        return ResponseEntity.ok( CategoryListResponses.builder()
                        .categories(categories)
                        .pageNumber(page)
                        .totalPage(totalPages)
                        .totalContent(totalContent)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryDTO categoryDTO
    ){
        categoryService.updateCategory(id,categoryDTO);
        return ResponseEntity.ok("update category succsessfully");
    }
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(
            @Valid @PathVariable Long id
    ){
        Category categorie = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categorie);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(
            @Valid @PathVariable Long id
    ){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("delete sucssufully ");
    }


}
