package ppp.mess;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

class UserNotFoundADV {

    @ResponseBody
    @ExceptionHandler(RestaurantNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String RestaurantNotFoundHandler(RestaurantNotFound ex) {
        return ex.getMessage();
    }
}
