package com.example.styczen.marcin.earthquakeapp.exceptions;

/**
 * Created by Marcin on 2016-10-13.
 */

public class DataBaseException extends AppBaseException {
    private static final String CONTENTS = "Problem with database: ";

    public DataBaseException(String message) {
        super(CONTENTS + message);
    }

    public DataBaseException(String message, Throwable cause) {
        super(CONTENTS + message, cause);
    }
}
