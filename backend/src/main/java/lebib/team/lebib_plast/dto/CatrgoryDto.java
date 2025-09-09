package lebib.team.lebib_plast.dto;

import lebib.team.lebib_plast.entity.ProductEntity;

import java.util.List;

public record CatrgoryDto(
        String categoryName,
        List<ProductEntity> products
) {
}
