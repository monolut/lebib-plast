package lebib.team.exception;

import lebib.team.enums.Role;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(Role role) {
        super("Role " + role + " not found");
    }
}
