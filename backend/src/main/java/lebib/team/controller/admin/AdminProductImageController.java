package lebib.team.controller.admin;

import lebib.team.dto.ProductImageDto;
import lebib.team.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/admin/products/{productId}/images")
@CrossOrigin("*")
public class AdminProductImageController {

    private final ProductImageService productImageService;

    @Autowired
    public AdminProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @PostMapping
    public ResponseEntity<ProductImageDto> uploadAnImage(
            @PathVariable Long productId,
            @RequestParam("file") MultipartFile file
    ) {
        return ResponseEntity.ok(productImageService.uploadAnImage(productId, file));
    }

    @DeleteMapping("/{productImageId}")
    public ResponseEntity<Void> deleteAnImage(
            @PathVariable Long productId,
            @PathVariable Long productImageId
    ) {
        productImageService.deleteAnImage(productId, productImageId);
        return ResponseEntity.noContent().build();
    }
}
