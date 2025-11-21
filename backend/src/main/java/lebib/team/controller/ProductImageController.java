package lebib.team.controller;

import lebib.team.dto.ProductImageDto;
import lebib.team.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user/products/{productId}/images")
@CrossOrigin("*")
public class ProductImageController {

    private final ProductImageService productImageService;

    @Autowired
    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @GetMapping
    public ResponseEntity<List<ProductImageDto>> findByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(productImageService.findByProductId(productId));
    }
}
