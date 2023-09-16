package ppp.mess;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
class Controller {

    private final UsersRepo usersRepo;
    private final MesasRepo mesasRepo;

    Controller(UsersRepo usersRepo, MesasRepo mesasRepo){
        this.usersRepo = usersRepo;
        this.mesasRepo = mesasRepo;
    }
/// Usuarios ///

    @GetMapping("/users")
    CollectionModel<EntityModel<Users>> all() {
        List<EntityModel<Users>> userEntities = usersRepo.findAll().stream()
            .map(user -> EntityModel.of(user,
                    linkTo(methodOn(Controller.class).one(user.getId())).withSelfRel(),
                    linkTo(methodOn(Controller.class).all()).withRel("users")))
            .collect(Collectors.toList());
        return CollectionModel.of(userEntities, linkTo(methodOn(Controller.class).all()).withSelfRel());
}

    @PostMapping("/users")
    Users newUsers(@RequestBody Users newUsers) {
        return usersRepo.save(newUsers);
    }
    @GetMapping("/users/{id}")
    EntityModel<Users> one(@PathVariable Long id){
        Users user = usersRepo.findById(id)
                .orElseThrow(() -> new UserNotFound(id));
        return EntityModel.of(user,
                linkTo(methodOn(Controller.class).one(id)).withSelfRel(),
                linkTo(methodOn(Controller.class).all()).withRel("users"));
    }
    @PutMapping("/users/{id}")
    Users replaceUsers(@RequestBody Users newUsers,@PathVariable Long id) {
        return usersRepo.findById(id)
                .map(users -> {
            users.setName(newUsers.getName());
            users.setEmail(newUsers.getEmail());
            return usersRepo.save(users);
        })
                .orElseGet(() -> {
                    newUsers.setId(id);
                    return usersRepo.save(newUsers);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUsers(@PathVariable Long id) {
        usersRepo.deleteById(id);
    }


    /// Mesas ///
    @GetMapping("/mesas")
    List<Mesas> allMesas() {
        return mesasRepo.findAll();
    }

    @PostMapping("/mesas")
    Mesas newMesa(@RequestBody Mesas newMesa) {
        return mesasRepo.save(newMesa);
    }

//    @GetMapping("/mesas/{id}")
//    Mesas oneMesa(@PathVariable Long id) {
//        return mesasRepo.findById(id)
//                .orElseThrow(() -> new MesaNotFound(id));
//    }

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