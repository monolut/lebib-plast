package lebib.team.service;

import lebib.team.dto.UserDto;
import lebib.team.entity.CartEntity;
import lebib.team.entity.ProfileEntity;
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

import java.util.List;

@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final CartService cartService;
    private final ProfileService profileService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, CartService cartService, ProfileService profileService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.cartService = cartService;
        this.roleRepository = roleRepository;
        this.profileService = profileService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDto> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto findByEmail(String email) {
        return userMapper.toDto(userRepository.findByEmail(email)
                .orElseThrow(() -> UserNotFoundException.byEmail(email)));
    }

    public UserDto findById(Long id) {
        return userMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> UserNotFoundException.byId(id)));
    }

    @Transactional
    public UserDto createUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent())
            throw UserNotFoundException.emailExists(userDto.getEmail());

        UserEntity user = userMapper.toEntity(userDto);

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        ProfileEntity profile = profileService.createProfileForUser(user);
        user.setProfile(profile);

        CartEntity cart = cartService.createCartForUser(user);
        user.setCart(cart);

        RoleEntity role = roleRepository.findByRoleName(Role.USER)
                .orElseThrow(() -> new RoleNotFoundException(Role.USER));

        user.setRole(role);

        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional
    public UserDto updateUserById(Long id, UserDto userDto) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> UserNotFoundException.byId(id));

        if (userDto.getEmail() != null && !userDto.getEmail().isBlank())
            userEntity.setEmail(userDto.getEmail());
        if (userDto.getName() != null && !userDto.getName().isBlank())
            userEntity.setName(userDto.getName());
        if (userDto.getSurname() != null && !userDto.getSurname().isBlank())
            userEntity.setSurname(userDto.getSurname());
        if (userDto.getBirthDate() != null)
            userEntity.setBirthDate(userDto.getBirthDate());

        userRepository.save(userEntity);
        return userMapper.toDto(userEntity);
    }

    @Transactional
    public void deleteUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> UserNotFoundException.byId(id));

        userRepository.delete(userEntity);
    }
}
