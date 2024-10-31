package ru.practicum.categories.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.model.Category;
import ru.practicum.categories.model.CategoryMapper;
import ru.practicum.categories.repository.CategoryRepository;
import ru.practicum.exeptions.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PublicCategoryServiceImpl implements PublicCategoryService {

    private static final Logger log = LoggerFactory.getLogger(PublicCategoryServiceImpl.class);
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAllCategories(int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);

        List<Category> categories = categoryRepository.findAll(pageable).getContent();

        log.info("Found {} categories", categories);
        return categories.stream().map(CategoryMapper::toCategoryDto).toList();
    }

    @Override
    public CategoryDto getCategoryById(int catId) {
        Optional<Category> category = categoryRepository.findById(catId);

        if (category.isEmpty()) {
            log.info("Category with id {} not found", catId);
            throw new NotFoundException("Category with id " + catId + " not found");
        }
        log.info("Category with id {} found: {}", catId, category);
        return CategoryMapper.toCategoryDto(category.get());
    }
}
