package ee.taltech.varadehaldamine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDescriptionException extends RuntimeException {

    public InvalidDescriptionException() {

    }

    public InvalidDescriptionException(String message) {
        super(message);
    }
}
