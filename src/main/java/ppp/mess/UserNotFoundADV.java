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

class MesaNotFoundADV {

    @ResponseBody
    @ExceptionHandler(MesaNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String MesaNotFoundHandler(MesaNotFound ex) {
        return ex.getMessage();
    }
}

class BranchNotFoundAdv {

    @ResponseBody
    @ExceptionHandler(BranchNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String BranchNotFoundHandler(BranchNotFound ex) {
        return ex.getMessage();
    }
}

class DishNotFoundADV {

    @ResponseBody
    @ExceptionHandler(DishNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String DishNotFoundHandler(DishNotFound ex) {
        return ex.getMessage();
    }
}
/*
public class RestaurantNotFoundException extends EntityNotFoundException {

    public RestaurantNotFoundException(Long id) {
        super("Restaurante", id);
    }
}
 */
// Extender funciones con este formato. **************To Do****************