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
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 50, nullable = false)
    private Role role;

    @OneToMany()
    private List<UserEntity> users;
}
