package com.example.styczen.marcin.earthquakeapp.exceptions;

/**
 * Created by Marcin on 2016-10-13.
 */

public abstract class AppBaseException extends Exception {

    public AppBaseException() {
    }

    public AppBaseException(String message) {
        super(message);
    }

    public AppBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppBaseException(Throwable cause) {
        super(cause);
    }
}
