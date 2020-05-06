package com.loyal.rx.error;

public class FlatMapException extends Exception {
    /**
     * Constructs a {@code ParseException} with no detail message.
     */
    public FlatMapException() {
        super();
    }

    /**
     * Constructs a {@code ParseException} with the specified
     * detail message.
     *
     * @param s the detail message.
     */
    public FlatMapException(String s) {
        super(s);
    }
}
