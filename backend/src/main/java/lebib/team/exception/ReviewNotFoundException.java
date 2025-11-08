package lebib.team.exception;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException(Long reviewId)
    {
        super("Review not found with id " + reviewId);
    }
}
