package ppp.mess;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
class Controller {

    private final UsersRepo usersRepo;
    private final MesasRepo mesasRepo;

    Controller(UsersRepo usersRepo, MesasRepo mesasRepo){
        this.usersRepo = usersRepo;
        this.mesasRepo = mesasRepo;
    }

    @GetMapping("/users")
    List<Users> all(){
        return usersRepo.findAll();
    }
    @PostMapping("/users")
    Users newUsers(@RequestBody Users newUsers) {
        return usersRepo.save(newUsers);
    }
    @GetMapping("/users/{id}")
    Users one(@PathVariable Long id) {
        return usersRepo.findById(id)
                .orElseThrow(() -> new UserNotFound(id));
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