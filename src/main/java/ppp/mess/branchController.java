package ppp.mess;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Branches> replaceBranch(@RequestBody Branches newBranch, @PathVariable Long id, @RequestParam Long restaurantId) {
        Optional<Branches> existingBranch = branchesRepo.findById(id);

        if (existingBranch.isPresent()) {
            Branches branch = existingBranch.get();

            // Actualiza los campos de la sucursal
            branch.setName(newBranch.getName());
            branch.setAdress(newBranch.getAdress());

            // Verifica si el restaurante especificado existe
            Optional<Restaurant> restaurant = RestaurantRepo.findById(restaurantId);

            if (restaurant.isPresent()) {
                branch.setRestaurant(restaurant.get());
                branchesRepo.save(branch);
                return ResponseEntity.ok(branch);
            } else {
                return ResponseEntity.badRequest().body(newBranch); // Cambiado a devolver la sucursal actualizada
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("branches/{id}")
    public void deleteBranch(@PathVariable Long id) {
        branchesRepo.deleteById(id);
    }
}
