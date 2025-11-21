package lebib.team.exception;

public class ProductImageNotFoundException extends RuntimeException {
    public ProductImageNotFoundException(Long id) {
        super("Category not found with id " + id);
    }
}
