package lebib.team.controller;

import lebib.team.dto.AddressDto;
import lebib.team.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/users/{userId}/addresses")
@CrossOrigin("*")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<AddressDto>> getAddresses(@PathVariable Long userId) {
        return ResponseEntity.ok(addressService.getAddressesByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<AddressDto> createAddress(@PathVariable Long userId, @RequestBody AddressDto addressDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.createAddress(userId, addressDto));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable Long userId, @PathVariable Long addressId, @RequestBody AddressDto addressDto) {
        return ResponseEntity.ok(addressService.updateAddress(userId, addressId, addressDto));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long userId, @PathVariable Long addressId) {
        addressService.deleteAddress(userId, addressId);
        return ResponseEntity.noContent().build();
    }
}
