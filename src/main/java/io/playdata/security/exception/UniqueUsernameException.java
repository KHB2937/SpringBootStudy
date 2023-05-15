package io.playdata.security.exception;

// Custom Error
public class UniqueUsernameException extends Exception {
    public UniqueUsernameException(String msg) {
        super(msg);
    }

    public UniqueUsernameException() {
        super("고유하지 않은 Username 입니다");
    }
}
