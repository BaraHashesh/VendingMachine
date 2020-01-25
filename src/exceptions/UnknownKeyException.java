package exceptions;

public class UnknownKeyException extends RuntimeException {

    public UnknownKeyException(String msg) {
        super(msg);
    }
}
