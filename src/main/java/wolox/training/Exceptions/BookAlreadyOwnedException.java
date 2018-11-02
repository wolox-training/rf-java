package wolox.training.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Book already owned")
public class BookAlreadyOwnedException extends RuntimeException {

    public BookAlreadyOwnedException()  {
    }
}
