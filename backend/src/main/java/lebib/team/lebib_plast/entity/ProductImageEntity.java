package lebib.team.lebib_plast.entity;

import jakarta.persistence.*;

@Entity
public class ProductImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, name = "imageUrl", nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ProductEntity product;

}
