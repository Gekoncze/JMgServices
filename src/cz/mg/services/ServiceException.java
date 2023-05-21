package cz.mg.services;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;

public @Utility class ServiceException extends RuntimeException {
    public ServiceException() {
    }

    public ServiceException(@Mandatory String message) {
        super(message);
    }

    public ServiceException(@Mandatory Throwable cause) {
        super(cause);
    }

    public ServiceException(@Mandatory String message, @Mandatory Throwable cause) {
        super(message, cause);
    }
}
