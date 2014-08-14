package com.dragonphase.kits.api;

public class KitException extends Exception {
    private static final long serialVersionUID = 4634639924183356239L;
    private final Throwable cause;

    public KitException(Throwable throwable) {
        cause = throwable;
    }

    public KitException() {
        cause = null;
    }

    public KitException(Throwable cause, String message) {
        super(message);
        this.cause = cause;
    }

    public KitException(String message) {
        super(message);
        cause = null;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
