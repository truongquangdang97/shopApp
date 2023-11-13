package com.project.shopapp.service;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;
import com.project.shopapp.responses.category.CategoryResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO categoryDTO);
    Category getCategoryById(long id);
    Page<CategoryResponses> getAllCategory(PageRequest pageRequest);
    Category updateCategory(long id, CategoryDTO categoryDTO);
    void deleteCategory(Long id);

}
