package lebib.team.entity;

import jakarta.persistence.*;
import lebib.team.enums.Role;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", length = 50, nullable = false)
    private Role roleName;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    private List<UserEntity> users;
}
