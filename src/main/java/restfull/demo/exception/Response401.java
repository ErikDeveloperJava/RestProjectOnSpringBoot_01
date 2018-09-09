package restfull.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class Response401 extends RuntimeException {

    public Response401(String message) {
        super(message);
    }
}
