package lebib.team.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "surname", length = 30, nullable = false)
    private String surname;

    @Column(name = "birthDate", length = 30, nullable = false)
    private LocalDate birthDate;

    @Column(name = "gender", length = 30, nullable = false)
    private String gender;

    private ProfileEntity profile;
}
