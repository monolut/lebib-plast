package lebib.team.exception;

public class InventoryNotFoundException extends RuntimeException {
    public InventoryNotFoundException(String message) {
        super(message);
    }

    public static InventoryNotFoundException byId(Long id) {
        return new InventoryNotFoundException("Inventory not found with id " + id);
    }

    public static InventoryNotFoundException byProductId(Long productId) {
        return new InventoryNotFoundException("Inventory not found by product id " + productId);
    }
}
