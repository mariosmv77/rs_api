package gr.codehub.rsapi.exception;

public abstract class BusinessException extends Exception {
    public BusinessException(String message) {
        super(message);
    }
}
