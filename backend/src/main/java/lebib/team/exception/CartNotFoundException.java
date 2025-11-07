package lebib.team.exception;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException(String message) {
        super(message);
    }

    public static CartNotFoundException byId(Long id) {
        return new CartNotFoundException("Cart not found with id " + id);
    }

    public static CartNotFoundException byUserId(Long userId) {
        return new CartNotFoundException("Cart of user with id " + userId + " not found");
    }
}
