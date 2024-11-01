package ru.practicum.categories.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.NewCategoryDto;
import ru.practicum.categories.model.Category;
import ru.practicum.categories.model.CategoryMapper;
import ru.practicum.categories.repository.CategoryRepository;
import ru.practicum.exeptions.NotFoundException;
import ru.practicum.exeptions.NotUniqueException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        checkName(newCategoryDto.getName());
        Category category = categoryRepository.save(CategoryMapper.toCategory(newCategoryDto));
        CategoryDto categoryDto = CategoryMapper.toCategoryDto(category);
        log.info("Category created: {}", categoryDto);
        return categoryDto;
    }

    @Override
    public void deleteCategory(int catId) {
        if (!categoryRepository.existsById(catId)) {
            log.info("Category with id {} does not exist", catId);
            throw new NotUniqueException(String.format("Category with ID %d is not found", catId));
        }
        categoryRepository.deleteById(catId);
        log.info("Category with id {} deleted", catId);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, int catId) {
        checkName(categoryDto.getName());
        Optional<Category> category = categoryRepository.findById(catId);
        if (category.isEmpty()) {
            throw new NotFoundException(String.format("Category with ID %d is not found", catId));
        }
        category.get().setName(categoryDto.getName());
        CategoryDto categoryUpdated = CategoryMapper.toCategoryDto(categoryRepository.save(category.get()));
        log.info("Category updated: {}", categoryUpdated);
        return categoryUpdated;
    }

    private void checkName(String name) {
        if (categoryRepository.existsByName(name)) {
            log.info("Category with name {} already exists", name);
            throw new NotUniqueException("Name of category must be unique.");
        }
    }
}
