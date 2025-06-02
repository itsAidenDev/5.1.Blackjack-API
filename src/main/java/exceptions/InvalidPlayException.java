package exceptions;

public class InvalidPlayException extends RuntimeException {
    public InvalidPlayException(String message) {
        super(message);
    }
}
