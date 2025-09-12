package lebib.team.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Table(name = "cart")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @OneToOne(mappedBy = "cart")
    private UserEntity user;

    @OneToMany(mappedBy = "cart",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemEntity> cartItems;
}
