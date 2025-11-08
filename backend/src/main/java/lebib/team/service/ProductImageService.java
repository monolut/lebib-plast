package lebib.team.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lebib.team.dto.ProductImageDto;
import lebib.team.entity.ProductImageEntity;
import lebib.team.exception.ProductImageNotFoundException;
import lebib.team.exception.ProductNotFoundException;
import lebib.team.mapper.ProductImageMapper;
import lebib.team.repository.ProductImageRepository;
import lebib.team.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductImageService {

    @Value("${aws.bucket-name}")
    private String bucketName;
    private final AmazonS3 s3Client;

    private final ProductImageRepository productImageRepository;
    private final ProductImageMapper productImageMapper;
    private final ProductRepository productRepository;

    public ProductImageService(
            ProductImageRepository productImageRepository,
            ProductImageMapper productImageMapper,
            ProductRepository productRepository,
            AmazonS3 s3Client
    ) {
        this.productImageRepository = productImageRepository;
        this.productImageMapper = productImageMapper;
        this.productRepository = productRepository;
        this.s3Client = s3Client;
    }

    public List<ProductImageDto> findByProductId(Long productId) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        return product.getImages().stream()
                .map(productImageMapper::toDto)
                .toList();
    }

    @Transactional
    public ProductImageDto uploadAnImage(Long productId, MultipartFile file) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        String key = "products/" + productId + "/" + file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        try {
            s3Client.putObject(bucketName, key, file.getInputStream(), metadata);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        String imageUrl = s3Client.getUrl(bucketName, key).toString();

        ProductImageEntity productImage = new ProductImageEntity();
        productImage.setImageUrl(imageUrl);
        productImage.setProduct(product);

        productImageRepository.save(productImage);

        return productImageMapper.toDto(productImage);
    }

    @Transactional
    public void deleteAnImage(Long productId, Long productImageId) {
        var productImage = productImageRepository.findById(productImageId)
                .orElseThrow(() -> new ProductImageNotFoundException(productImageId));

        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (!productImage.getProduct().getId().equals(productId))
            throw new IllegalArgumentException("This image does not correspond to this product");

        String imageUrl = productImage.getImageUrl();
        String key = imageUrl.substring(imageUrl.indexOf("products/"));

        s3Client.deleteObject(bucketName, key);

        productImageRepository.delete(productImage);
    }
}