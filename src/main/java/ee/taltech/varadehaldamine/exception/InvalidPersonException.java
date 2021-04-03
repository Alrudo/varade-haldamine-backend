package ee.taltech.varadehaldamine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPersonException  extends RuntimeException{
    public InvalidPersonException() {

    }

    public InvalidPersonException(String message) {
        super(message);
    }
}
