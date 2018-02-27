package com.example.tomek.simplenotes;

/**
 * Created by Tomek on 27.02.2018.
 */

public class SecurityException extends Exception {
    public SecurityException() {
        super();
    }

    public SecurityException(String message) {
        super(message);
    }

    public SecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityException(Throwable cause) {
        super(cause);
    }


}
