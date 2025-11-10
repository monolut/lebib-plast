package lebib.team.controller;

import jakarta.validation.Valid;
import lebib.team.dto.ReviewDto;
import lebib.team.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/product/{productId}/reviews")
    public ResponseEntity<List<ReviewDto>> getReviewsByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getReviewsByProduct(productId));
    }

    @GetMapping("/{userId}/reviews")
    public ResponseEntity<List<ReviewDto>> getReviewsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }

    @PostMapping("/users/{userId}/products/{productId}/reviews")
    public ResponseEntity<ReviewDto> createReview(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @Valid @RequestBody ReviewDto reviewDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(userId, productId, reviewDto));
    }

    @DeleteMapping("/users/{userId}/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long userId,
            @PathVariable Long reviewId
    ) {
        reviewService.deleteReview(userId, reviewId);
        return ResponseEntity.noContent().build();
    }
}
