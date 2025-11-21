package lebib.team.seeder;

import lebib.team.entity.RoleEntity;
import lebib.team.entity.UserEntity;
import lebib.team.enums.Gender;
import lebib.team.enums.Role;
import lebib.team.exception.RoleNotFoundException;
import lebib.team.repository.RoleRepository;
import lebib.team.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private String adminEmail = "admin@shop.com";
    private String adminPassword = "asdhiu23h87rfh23finsdf";
    private String adminName = "Admin";
    private String adminSurname = "Admin";
    private LocalDate adminBirthDate = LocalDate.now();
    private Gender adminGender = Gender.MALE;

    @Autowired
    public AdminSeeder(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            RoleEntity adminRole = roleRepository.findByRoleName(Role.ADMIN)
                    .orElseThrow(() -> new RoleNotFoundException(Role.ADMIN));

            UserEntity admin = new UserEntity();
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setName(adminName);
            admin.setSurname(adminSurname);
            admin.setBirthDate(adminBirthDate);
            admin.setGender(adminGender);
            admin.setRole(adminRole);

            userRepository.save(admin);
        }
    }
}
