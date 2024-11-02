package ru.practicum.categories.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.NewCategoryDto;
import ru.practicum.categories.model.Category;
import ru.practicum.categories.model.CategoryMapper;
import ru.practicum.categories.repository.CategoryRepository;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.exeptions.ConflictException;
import ru.practicum.exeptions.NotFoundException;
import ru.practicum.exeptions.NotUniqueException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        checkName(newCategoryDto.getName());
        Category category = categoryRepository.save(CategoryMapper.toCategory(newCategoryDto));
        CategoryDto result = CategoryMapper.toCategoryDto(category);
        log.info("Category created: {}", result);
        return result;
    }

    @Override
    public void deleteCategory(int catId) {
        if (!categoryRepository.existsById(catId)) {
            log.info("Category with id {} does not exist", catId);
            throw new NotUniqueException(String.format("Category with ID %d is not found", catId));
        }
        if (eventRepository.existsByCategoryId(catId)) {
            throw new ConflictException(String.format("Category with ID %d cannot to delete", catId));
        }
        categoryRepository.deleteById(catId);
        log.info("Category with id {} deleted", catId);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, int catId) {
        Optional<Category> category = categoryRepository.findById(catId);
        if (category.isEmpty()) {
            throw new NotFoundException(String.format("Category with ID %d is not found", catId));
        }
        checkName(categoryDto.getName());
        category.get().setName(categoryDto.getName());
        CategoryDto result = CategoryMapper.toCategoryDto(categoryRepository.save(category.get()));
        log.info("Category updated: {}", result);
        return result;
    }

    private void checkName(String name) {
        if (categoryRepository.existsByName(name)) {
            log.info("Category with name {} already exists", name);
            throw new NotUniqueException("Name of category must be unique.");
        }
    }
}
