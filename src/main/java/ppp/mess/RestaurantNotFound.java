package ppp.mess;

class RestaurantNotFound extends  RuntimeException {

    RestaurantNotFound(Long id) {
        super("no se encuentra restaurante" + id);
    }
}

class MesaNotFound extends  RuntimeException {

    MesaNotFound(Long id) {
        super("no se encuentra Mesa" + id);
    }
}
class BranchNotFound extends  RuntimeException {

    BranchNotFound(Long id) {
        super("no se encuentra Branch" + id);
    }
}
class DishNotFound extends  RuntimeException {

    DishNotFound(Long id) {
        super("no se encuentra Dish" + id);
    }
}


/*
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityName, Long id) {
        super("No se encuentra " + entityName + " con ID: " + id);
    }
}
 */ // Aplicar como funcion general para no replicar codigo