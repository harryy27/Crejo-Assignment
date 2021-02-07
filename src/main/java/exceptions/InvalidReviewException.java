package exceptions;

import enums.ReviewError;

public class InvalidReviewException extends RuntimeException {

    private final ReviewError code;
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

    public ReviewError getCode() {
        return code;
    }

    public InvalidReviewException(ReviewError code, String message) {
        this.code = code;
        this.message = message;
    }
}
