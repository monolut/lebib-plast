package lebib.team.mapper;

import lebib.team.dto.ReviewDto;
import lebib.team.entity.ReviewEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewEntity toEntity(ReviewDto reviewDto);
    ReviewDto toDto(ReviewEntity reviewEntity);
}
