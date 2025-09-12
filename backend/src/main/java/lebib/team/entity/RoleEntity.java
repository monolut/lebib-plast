package lebib.team.entity;

import jakarta.persistence.*;
import lebib.team.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
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
