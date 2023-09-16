package ppp.mess;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// El siguiente bloque define un controlador Spring Boot para manejar las solicitudes relacionadas con usuarios y mesas.
@RestController
class Controller {

    // Se inicializan repositorios para usuarios y mesas en el constructor del controlador.
    private final UsersRepo usersRepo;
    private final MesasRepo mesasRepo;

    Controller(UsersRepo usersRepo, MesasRepo mesasRepo){
        this.usersRepo = usersRepo;
        this.mesasRepo = mesasRepo;
    }

    // Métodos para operaciones relacionadas con usuarios.
    /// Usuarios ///

    // Este método maneja las solicitudes GET a "/users" y devuelve una colección de usuarios.
    @GetMapping("/users")
    CollectionModel<EntityModel<Users>> all() {
        // Consulta todos los usuarios, los convierte en EntityModel y los agrega a una lista.
        List<EntityModel<Users>> userEntities = usersRepo.findAll().stream()
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(Controller.class).one(user.getId())).withSelfRel(),
                        linkTo(methodOn(Controller.class).all()).withRel("users")))
                .collect(Collectors.toList());
        // Devuelve la colección de usuarios con enlaces relacionados.
        return CollectionModel.of(userEntities, linkTo(methodOn(Controller.class).all()).withSelfRel());
    }

    // Este método maneja las solicitudes POST a "/users" y crea un nuevo usuario.
    @PostMapping("/users")
    Users newUsers(@RequestBody Users newUsers) {
        return usersRepo.save(newUsers);
    }

    // Este método maneja las solicitudes GET a "/users/{id}" y devuelve un usuario específico.
    @GetMapping("/users/{id}")
    EntityModel<Users> one(@PathVariable Long id){
        // Busca un usuario por su ID, lanza una excepción si no se encuentra.
        Users user = usersRepo.findById(id)
                .orElseThrow(() -> new UserNotFound(id));
        // Devuelve el usuario como EntityModel con enlaces relacionados.
        return EntityModel.of(user,
                linkTo(methodOn(Controller.class).one(id)).withSelfRel(),
                linkTo(methodOn(Controller.class).all()).withRel("users"));
    }

    // Este método maneja las solicitudes PUT a "/users/{id}" y actualiza un usuario existente.
    @PutMapping("/users/{id}")
    Users replaceUsers(@RequestBody Users newUsers,@PathVariable Long id) {
        return usersRepo.findById(id)
                .map(users -> {
                    // Actualiza el nombre y el correo electrónico del usuario y lo guarda.
                    users.setName(newUsers.getName());
                    users.setEmail(newUsers.getEmail());
                    return usersRepo.save(users);
                })
                .orElseGet(() -> {
                    newUsers.setId(id);
                    return usersRepo.save(newUsers);
                });
    }

    // Este método maneja las solicitudes DELETE a "/users/{id}" y elimina un usuario por su ID.
    @DeleteMapping("/users/{id}")
    void deleteUsers(@PathVariable Long id) {
        usersRepo.deleteById(id);
    }

//    @GetMapping("/mesas/{id}")
//    Mesas oneMesa(@PathVariable Long id) {
//        return mesasRepo.findById(id)
//                .orElseThrow(() -> new MesaNotFound(id));
//    }
    //Falta crear los mensajes de not found
    //Refactorizar como una funcion general para no crear una funcion por tabla.

    @PutMapping("/mesas/{id}")
    Mesas replaceMesa(@RequestBody Mesas newMesa, @PathVariable Long id) {
        return mesasRepo.findById(id)
                .map(mesa -> {
                    mesa.setName(newMesa.getName());
                    mesa.setContenido(newMesa.getContenido());
                    return mesasRepo.save(mesa);
                })
                .orElseGet(() -> {
                    newMesa.setId(id);
                    return mesasRepo.save(newMesa);
                });
    }

    @DeleteMapping("/mesas/{id}")
    void deleteMesa(@PathVariable Long id) {
        mesasRepo.deleteById(id);
    }

}