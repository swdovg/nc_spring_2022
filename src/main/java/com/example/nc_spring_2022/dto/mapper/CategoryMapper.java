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

        Long parentId = 0L;
        if (category.getParent() != null) {
            parentId = category.getParent().getId();
        }
        categoryDto.setParentId(parentId);
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());

        return categoryDto;
    }

    public List<CategoryDto> createFrom(List<Category> categories) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
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

        Category parentCategory = getParentCategory(categoryDto.getParentId());
        category.setParent(parentCategory);
        category.setName(categoryDto.getName());

        return category;
    }

    private Category getParentCategory(Long parentId) {
        if (parentId != 0) {
            return categoryRepository.getById(parentId);
        } else {
            return null;
        }
    }

}
