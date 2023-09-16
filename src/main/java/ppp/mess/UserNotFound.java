package ppp.mess;

class UserNotFound extends  RuntimeException {

    UserNotFound(Long id) {
        super("no se encuentra usuario" + id);
    }
}
