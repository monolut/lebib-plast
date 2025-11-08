package lebib.team.service;

import lebib.team.dto.AddressDto;
import lebib.team.entity.AddressEntity;
import lebib.team.entity.UserEntity;
import lebib.team.exception.AddressNotFoundException;
import lebib.team.exception.UserNotFoundException;
import lebib.team.mapper.AddressMapper;
import lebib.team.repository.AddressRepository;
import lebib.team.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;

    @Autowired
    public AddressService(
            AddressRepository addressRepository,
            AddressMapper addressMapper,
            UserRepository userRepository
    ) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    public AddressDto createAddress(Long userId, AddressDto addressDto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.byId(userId));

        AddressEntity addressEntity = addressMapper.toEntity(addressDto);

        addressEntity.setUser(user);

        addressRepository.save(addressEntity);

        return addressMapper.toDto(addressEntity);
    }

    public List<AddressDto> getAddressesByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.byId(userId));

        return user.getAddresses().stream()
                .map(addressMapper::toDto)
                .toList();
    }

    @Transactional
    public AddressDto updateAddress(Long userId, Long addressId, AddressDto addressDto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.byId(userId));

        AddressEntity addressToUpdate = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException(addressId));

        boolean belongsToUser = user.getAddresses().stream()
                .anyMatch(a -> a.getId().equals(addressId));

        if (!belongsToUser) {
            throw new IllegalArgumentException(
                    "Address " + addressId + " does not belong to user " + userId);
        }

        if(addressDto.getCity() != null && !addressDto.getCity().isBlank())
            addressToUpdate.setCity(addressDto.getCity());
        if(addressDto.getCountry() != null && !addressDto.getCountry().isBlank())
            addressToUpdate.setCountry(addressDto.getCountry());
        if(addressDto.getStreet() != null && !addressDto.getStreet().isBlank())
            addressToUpdate.setStreet(addressDto.getStreet());
        if(addressDto.getPostalCode() != null && !addressDto.getPostalCode().isBlank())
            addressToUpdate.setPostalCode(addressDto.getPostalCode());

        addressRepository.save(addressToUpdate);

        return addressMapper.toDto(addressToUpdate);
    }

    @Transactional
    public void deleteAddress(Long userId, Long addressId) {
        AddressEntity address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException(addressId));

        if (!address.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException(
                    "Address " + addressId + " does not belong to user " + userId);
        }

        addressRepository.deleteById(addressId);
    }
    }
