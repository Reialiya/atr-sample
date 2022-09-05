package hu.icellmobilsoft.atr.sample.util;

public class BaseException extends Exception {
    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Exception e) {
        super(message, e);
    }
    public BaseException(Exception e) {
        super(e);
    }
}