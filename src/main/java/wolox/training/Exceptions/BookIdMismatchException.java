package wolox.training.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BookIdMismatchException extends RuntimeException {

    public BookIdMismatchException(String exception) {
        super(exception);
    }

}