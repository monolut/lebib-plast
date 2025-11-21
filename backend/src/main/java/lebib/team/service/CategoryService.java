package lebib.team.service;

import lebib.team.dto.CategoryDto;
import lebib.team.entity.CategoryEntity;
import lebib.team.exception.CategoryAlreadyExistsException;
import lebib.team.exception.CategoryNotFoundException;
import lebib.team.mapper.CategoryMapper;
import lebib.team.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(
            CategoryMapper categoryMapper,
            CategoryRepository categoryRepository
    ) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    public CategoryDto findCategoryById(Long id) {
        return categoryMapper.toDto(categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id)));
    }

    public List<CategoryDto> findAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    public CategoryDto findCategoryByName(String name) {
        return categoryMapper.toDto(categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException(name)));
    }

    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        if(categoryRepository.findByName(categoryDto.getName()).isPresent()) {
            throw new CategoryAlreadyExistsException(categoryDto.getName());
        }

        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(categoryDto)));
    }

    @Transactional
    public void deleteCategoryById(Long id) {
        CategoryEntity categoryEntityToDelete = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        categoryRepository.deleteById(id);
    }

    @Transactional
    public CategoryDto updateCategoryById(Long id, CategoryDto categoryDto) {
        CategoryEntity categoryToUpdateEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        categoryToUpdateEntity.setName(categoryDto.getName());

        return categoryMapper.toDto(categoryRepository.save(categoryToUpdateEntity));
    }
}
