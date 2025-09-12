package lebib.team.entity;

import jakarta.persistence.*;
import lebib.team.enums.Gender;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
@Getter
@Setter
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "surname", length = 30, nullable = false)
    private String surname;

    @Column(name = "birthDate", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 30, nullable = false)
    private Gender gender;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private ProfileEntity profile;

    @OneToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name = "cart_id",  referencedColumnName = "id", nullable = false)
    private CartEntity cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AddressEntity> address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private RoleEntity role;
}
