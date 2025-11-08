package lebib.team.service;

import lebib.team.dto.OrderItemDto;
import lebib.team.dto.ReviewDto;
import lebib.team.entity.*;
import lebib.team.enums.OrderStatus;
import lebib.team.exception.ProductNotFoundException;
import lebib.team.exception.ReviewNotFoundException;
import lebib.team.exception.UserNotFoundException;
import lebib.team.mapper.ReviewMapper;
import lebib.team.repository.OrderRepository;
import lebib.team.repository.ProductRepository;
import lebib.team.repository.ReviewRepository;
import lebib.team.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public ReviewService(
            ReviewRepository reviewRepository,
            ReviewMapper reviewMapper,
            ProductRepository productRepository,
            UserRepository userRepository,
            OrderRepository orderRepository
    ) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public List<ReviewDto> getReviewsByProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException(productId);
        }
        return reviewRepository.findByProductId(productId)
                .stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    public List<ReviewDto> getReviewsByUser(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.byId(userId));

        return user.getReviews().stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    @Transactional
    public ReviewDto createReview(Long userId, Long productId, ReviewDto reviewDto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.byId(userId));

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (reviewRepository.existsByUserIdAndProductId(userId, productId)) {
            throw new IllegalStateException("User already left a review for this product");
        }

        if (!orderRepository.existsDeliveredOrderForProduct(userId, productId, OrderStatus.DELIVERED)) {
            throw new IllegalArgumentException(
                    "User with id " + userId + " did not ever buy product with id " + productId);
        }

        ReviewEntity review = new ReviewEntity();
        review.setProduct(product);
        review.setUser(user);
        review.setText(reviewDto.getText());
        review.setReviewDate(LocalDateTime.now());

        reviewRepository.save(review);
        return reviewMapper.toDto(review);
    }

    @Transactional
    public void deleteReview(Long userId, Long reviewId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.byId(userId));

        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));

        if (!review.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("User " + userId + " did not have wrote review " + reviewId);
        }
        reviewRepository.delete(review);
    }
}
