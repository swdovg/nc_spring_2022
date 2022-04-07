package com.example.nc_spring_2022.dto.mapper;

import com.example.nc_spring_2022.dto.model.CategoryDto;
import com.example.nc_spring_2022.model.Category;
import com.example.nc_spring_2022.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryMapper {
    private final CategoryRepository categoryRepository;

    public CategoryDto createFrom(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        Long parentId = 0L;
        if (category.getParent() != null) {
            parentId = category.getParent().getId();
        }
        categoryDto.setParentId(parentId);
        return categoryDto;
    }

    public List<CategoryDto> createFrom(List<Category> categorys) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categorys) {
            categoryDtos.add(createFrom(category));
        }
        return categoryDtos;
    }

    public Category createFrom(CategoryDto categoryDto) {
        Category category;
        if (categoryDto.getId() != null) {
            category = categoryRepository.getById(categoryDto.getId());
        } else {
            category = new Category();
        }
        category.setName(categoryDto.getName());
        Category parentCategory;
        if (categoryDto.getParentId() != 0) {
            parentCategory = categoryRepository.getById(categoryDto.getParentId());
        } else {
            parentCategory = null;
        }
        category.setParent(parentCategory);
        return category;
    }
}
