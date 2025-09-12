package lebib.team.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, name = "name", nullable = false)
    private String name;

    @Column(length = 2000, name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImageEntity> images;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private CategoryEntity category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemEntity> cartItem;
}
