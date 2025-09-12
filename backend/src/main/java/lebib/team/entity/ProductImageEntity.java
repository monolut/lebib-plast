package lebib.team.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "product_image")
public class ProductImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, name = "imageUrl", nullable = false, unique = true)
    private String imageUrl;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private ProductEntity product;

}
