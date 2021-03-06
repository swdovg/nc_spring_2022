package com.example.nc_spring_2022.service;

import com.example.nc_spring_2022.dto.mapper.CategoryMapper;
import com.example.nc_spring_2022.dto.model.CategoryDto;
import com.example.nc_spring_2022.exception.EntityAlreadyExistsException;
import com.example.nc_spring_2022.model.Category;
import com.example.nc_spring_2022.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Category with id: %d was not found", id))
        );
    }

    public List<Category> findAllByParentId(Long id) {
        if (id == 0) {
            return categoryRepository.findAllByParentId(null);
        } else {
            return categoryRepository.findAllByParentId(id);
        }
    }

    public List<CategoryDto> getAllDtosByParentId(Long id) {
        List<Category> categories = findAllByParentId(id);
        return categoryMapper.createFrom(categories);
    }

    public boolean isCategoryExists(Category category) {
        return categoryRepository.findByName(category.getName()).isPresent();
    }

    public Category save(Category category) {
        checkIsCategoryExists(category);
        return categoryRepository.save(category);
    }

    private void checkIsCategoryExists(Category category) {
        if (isCategoryExists(category)) {
            throw new EntityAlreadyExistsException(
                    String.format("Category with name: %s already exists", category.getName())
            );
        }
    }

    public CategoryDto save(CategoryDto categoryDto) {
        Category category = categoryMapper.createFrom(categoryDto);
        category = save(category);
        return categoryMapper.createFrom(category);
    }
}
