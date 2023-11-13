package com.project.shopapp.service;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;
import com.project.shopapp.repositories.CategoryRepository;
import com.project.shopapp.responses.category.CategoryResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;
    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        Category newCategory = Category
                .builder()
                .name(categoryDTO.getName())
                .build();
        return categoryRepository.save(newCategory);
    }



    @Override
    public Page<CategoryResponses> getAllCategory(PageRequest pageRequest) {
        return categoryRepository.findAll(pageRequest).map(category -> {
            CategoryResponses categoryResponses = CategoryResponses
                    .builder()
                    .id(category.getId())
                    .name(category.getName())
                    .build();
//            categoryResponses.setCreatedAt(category.getCreatedAt());
            return  categoryResponses;
        });
    }

    @Override
    public Category updateCategory(long id, CategoryDTO categoryDTO) {
        Category existingCategory = getCategoryById(id);
        existingCategory.setName(categoryDTO.getName());
        categoryRepository.save(existingCategory);
        return existingCategory;
    }
    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()->new RuntimeException("CATEGORY not found"));
    }

    @Override
    public void deleteCategory(Long id) {
        // xoa cung
        categoryRepository.deleteById(id);
    }
}
