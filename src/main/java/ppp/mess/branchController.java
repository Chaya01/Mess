package ppp.mess;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
class branchController {

    private final BranchesRepo branchesRepo;
    private final RestaurantRepo RestaurantRepo;

    public branchController(BranchesRepo branchesRepo, RestaurantRepo RestaurantRepo) {
        this.branchesRepo = branchesRepo;
        this.RestaurantRepo = RestaurantRepo;
    }
    @GetMapping("/branches")
    public CollectionModel<EntityModel<Branches>> all() {
        List<EntityModel<Branches>> branchEntities = branchesRepo.findAll().stream()
                .map(branch -> EntityModel.of(branch,
                        linkTo(methodOn(branchController.class).one(branch.getId())).withSelfRel(),
                        linkTo(methodOn(branchController.class).all()).withRel("branches")))
                .collect(Collectors.toList());
        return CollectionModel.of(branchEntities, linkTo(methodOn(branchController.class).all()).withSelfRel());
    }
    @PostMapping("/branches")
    public Branches newBranch(@RequestBody Branches newBranch) {
        return branchesRepo.save(newBranch);
    }
    @GetMapping("branches/{id}")
    public EntityModel<Branches> one(@PathVariable Long id) {
        Branches branch = branchesRepo.findById(id)
                .orElseThrow(() -> new BranchNotFound(id));
        return EntityModel.of(branch,
                linkTo(methodOn(branchController.class).one(id)).withSelfRel(),
                linkTo(methodOn(branchController.class).all()).withRel("branches"));
    }
    @PutMapping("branches/{id}")
    public Branches replaceBranch(@RequestBody Branches newBranch, @PathVariable Long id, @RequestParam Long restaurantId) {
        return branchesRepo.findById(id)
                .map(branch -> {
                    branch.setName(newBranch.getName());
                    branch.setAdress(newBranch.getAdress());

                    // Obtener el restaurante por su ID
                    Optional<Restaurant> restaurant = RestaurantRepo.findById(restaurantId);
                    if (restaurant.isPresent()) {
                        branch.setRestaurant(restaurant.get());
                    } else {
                        // Manejo de error si no se encuentra el restaurante
                        // Puedes lanzar una excepción o manejarlo de otra manera
                    }

                    // También puedes manejar la actualización de otras relaciones si es necesario

                    return branchesRepo.save(branch);
                })
                .orElseGet(() -> {
                    newBranch.setId(id); // Configurar la ID en caso de crear una nueva rama

                    // Obtener el restaurante por su ID
                    Optional<Restaurant> restaurant = RestaurantRepo.findById(restaurantId);
                    if (restaurant.isPresent()) {
                        newBranch.setRestaurant(restaurant.get());
                    } else {
                        // Manejo de error si no se encuentra el restaurante
                        // Puedes lanzar una excepción o manejarlo de otra manera
                    }

                    return branchesRepo.save(newBranch);
                });
    }
    @DeleteMapping("branches/{id}")
    public void deleteBranch(@PathVariable Long id) {
        branchesRepo.deleteById(id);
    }
}
