package lebib.team.exception;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(Long addressId) {
        super("Address not found with id " + addressId);
    }
}
