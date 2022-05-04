package com.example.nc_spring_2022.controller;

import com.example.nc_spring_2022.dto.model.CategoryDto;
import com.example.nc_spring_2022.dto.model.Response;
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

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public Response<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return new Response<>(categoryService.save(categoryDto));
    }

    @GetMapping("/{parentId}")
    public Response<List<CategoryDto>> getChildCategories(@PathVariable Long parentId) {
        return new Response<>(categoryService.getAllDtosByParentId(parentId));
    }
}
