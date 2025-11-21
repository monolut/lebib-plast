package lebib.team.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public static UserNotFoundException byId(Long id) {
        return new UserNotFoundException("User not found with id " + id);
    }

    public static UserNotFoundException byEmail(String email) {
        return new UserNotFoundException("User not found with email " + email);
    }

    public static UserNotFoundException byName(String name) {
        return new UserNotFoundException("User not found with name " + name);
    }

    public static UserNotFoundException emailExists(String email) {
        return new UserNotFoundException("User exist with email " + email);
    }
}
