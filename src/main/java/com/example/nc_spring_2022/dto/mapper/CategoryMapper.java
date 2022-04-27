package com.example.nc_spring_2022.dto.mapper;

import com.example.nc_spring_2022.dto.model.CategoryDto;
import com.example.nc_spring_2022.model.Category;
import com.example.nc_spring_2022.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryMapper {
    @Lazy
    private final CategoryService categoryService;

    public CategoryDto createFrom(Category category) {
        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setParentId(getParentCategoryId(category));
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());

        return categoryDto;
    }

    private Long getParentCategoryId(Category category) {
        if (category.getParent() != null) {
            return category.getParent().getId();
        } else {
            return 0L;
        }
    }

    public List<CategoryDto> createFrom(List<Category> categories) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            categoryDtos.add(createFrom(category));
        }
        return categoryDtos;
    }

    public Category createFrom(CategoryDto categoryDto) {
        Category category = getCategoryFromDbOrNew(categoryDto);

        Category parentCategory = getParentCategory(categoryDto.getParentId());
        category.setParent(parentCategory);
        category.setName(categoryDto.getName());

        return category;
    }

    private Category getCategoryFromDbOrNew(CategoryDto categoryDto) {
        if (categoryDto.getId() != null) {
            return categoryService.findById(categoryDto.getId());
        } else {
            return new Category();
        }
    }

    private Category getParentCategory(Long parentId) {
        if (parentId != 0) {
            return categoryService.findById(parentId);
        } else {
            return null;
        }
    }
}
