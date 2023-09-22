package ppp.mess;

class RestaurantNotFound extends  RuntimeException {

    RestaurantNotFound(Long id) {
        super("no se encuentra restaurante" + id);
    }
}
