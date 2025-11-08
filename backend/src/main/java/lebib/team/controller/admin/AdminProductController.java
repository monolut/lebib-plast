package lebib.team.controller.admin;

import lebib.team.dto.ProductDto;
import lebib.team.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.annotations.NotNull;

@RestController
@RequestMapping("/api/v1/admin/products")
@CrossOrigin("*")
public class AdminProductController {

    private final ProductService productService;

    @Autowired
    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto productDto,
            @RequestParam @NotNull Integer quantity
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDto, quantity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProductById(
            @PathVariable Long id,
            @RequestBody ProductDto productDto
    ) {
        return ResponseEntity.ok(productService.updateProductById(id, productDto));
    }

    @PutMapping("{productId}/category/{categoryId}")
    public ResponseEntity<ProductDto> updateProductCategory(
            @PathVariable Long categoryId,
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(productService.updateProductCategory(categoryId, productId));
    }
}
