package wolox.training.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad format date. Format required: yyyy-MM-dd")
public class BadDateException extends RuntimeException {

    public BadDateException() {
        super();
    }

}