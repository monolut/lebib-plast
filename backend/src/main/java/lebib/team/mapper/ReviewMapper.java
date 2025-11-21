package lebib.team.mapper;

import lebib.team.dto.ReviewDto;
import lebib.team.entity.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mappings({
            @Mapping(source = "userId", target = "user.id"),
            @Mapping(source = "productId", target = "product.id")
    })
    ReviewEntity toEntity(ReviewDto reviewDto);

    @Mappings({
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(source = "product.id", target = "productId")
    })
    ReviewDto toDto(ReviewEntity reviewEntity);
}
