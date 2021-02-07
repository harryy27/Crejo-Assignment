package enums;

public enum ReviewError {

    MOVIE_NOT_RELEASED("Movie yet to be released"),
    REVIEW_ALREADY_EXISTS("Multiple reviews not allowed");

    private final String message;

    public String getMessage() {
        return message;
    }

    ReviewError(String message) {
        this.message = message;
    }
}
