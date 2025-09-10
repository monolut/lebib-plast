package lebib.team.dto;

import lebib.team.entity.ProductEntity;

import java.util.List;

public record CategoryDto(
        String categoryName,
        List<ProductEntity> products
) {
}