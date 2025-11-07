package lebib.team.controller;

import lebib.team.dto.CategoryDto;
import lebib.team.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/categories")
@CrossOrigin("*")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories() {
        return ResponseEntity.ok(categoryService.findAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findCategoryById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryDto> getCategoryByName(@PathVariable String name) {
        return ResponseEntity.ok(categoryService.findCategoryByName(name));
    }
}
