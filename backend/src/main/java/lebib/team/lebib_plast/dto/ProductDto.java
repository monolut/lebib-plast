package lebib.team.lebib_plast.dto;

import java.util.List;

public record ProductDto (
    String name,
    String description,
    double price,
    List<ProductImageDto> imageUrls
) {

}
