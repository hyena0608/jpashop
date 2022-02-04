package jpabook.jpashop.exception;

public class NotEnouhStockException extends RuntimeException {

    public NotEnouhStockException() {
        super();
    }

    public NotEnouhStockException(String message) {
        super(message);
    }

    public NotEnouhStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnouhStockException(Throwable cause) {
        super(cause);
    }

}
