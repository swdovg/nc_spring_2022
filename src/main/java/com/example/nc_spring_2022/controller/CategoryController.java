package com.example.nc_spring_2022.controller;

import com.example.nc_spring_2022.dto.mapper.CategoryMapper;
import com.example.nc_spring_2022.dto.model.CategoryDto;
import com.example.nc_spring_2022.dto.model.Response;
import com.example.nc_spring_2022.model.Category;
import com.example.nc_spring_2022.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping
    public Response<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        Category category = categoryService.save(categoryDto);
        return new Response<>(categoryMapper.createFrom(category));
    }

    @GetMapping("/{parentId}")
    public Response<List<CategoryDto>> getChildCategories(@PathVariable Long parentId) {
        List<Category> categories = categoryService.findAllByParentId(parentId);
        return new Response<>(categoryMapper.createFrom(categories));
    }
}
