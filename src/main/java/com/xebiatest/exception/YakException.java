package com.xebiatest.exception;

public class YakException extends Exception {
    public YakException() { super(); }
    public YakException(String message) { super(message); }
    public YakException(String message, Throwable cause) { super(message, cause); }
    public YakException(Throwable cause) { super(cause); }

}
