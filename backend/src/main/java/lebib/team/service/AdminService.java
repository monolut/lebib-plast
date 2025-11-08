package lebib.team.service;

import com.amazonaws.services.memorydb.model.UserAlreadyExistsException;
import lebib.team.dto.UserDto;
import lebib.team.entity.RoleEntity;
import lebib.team.entity.UserEntity;
import lebib.team.enums.Role;
import lebib.team.exception.RoleNotFoundException;
import lebib.team.exception.UserNotFoundException;
import lebib.team.mapper.UserMapper;
import lebib.team.repository.RoleRepository;
import lebib.team.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDto createAdmin(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw UserNotFoundException.emailExists(userDto.getEmail());
        }
        RoleEntity adminRole = roleRepository.findByRoleName(Role.ADMIN)
                .orElseThrow(() -> new RoleNotFoundException(Role.ADMIN));

        UserEntity admin = new UserEntity();
        admin.setEmail(userDto.getEmail());
        admin.setPassword(passwordEncoder.encode(userDto.getPassword()));
        admin.setName(userDto.getName());
        admin.setSurname(userDto.getSurname());
        admin.setBirthDate(userDto.getBirthDate());
        admin.setGender(userDto.getGender());
        admin.setRole(adminRole);

        admin.setCart(null);
        admin.setProfile(null);

        userRepository.save(admin);
        return userMapper.toDto(admin);
    }
}
