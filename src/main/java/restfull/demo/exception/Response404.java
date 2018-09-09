package restfull.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class Response404 extends RuntimeException {

    public Response404() {
        super();
    }

    public Response404(String message) {
        super(message);
    }
}