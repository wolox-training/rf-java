package wolox.training.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserIdMismatchException extends RuntimeException {

    public UserIdMismatchException(String exception) {
        super(exception);
    }
}
