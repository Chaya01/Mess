package ppp.mess;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
class DishController {
    private final DishesRepo dishesRepo;
    public DishController (DishesRepo dishesRepo){
        this.dishesRepo = dishesRepo;
    }

    @GetMapping("/dishes")
    public CollectionModel<EntityModel<Dishes>> all() {
        List<EntityModel<Dishes>> dishEntities = dishesRepo.findAll().stream()
                .map(dish -> EntityModel.of(dish,
                        linkTo(methodOn(DishController.class).one(dish.getId())).withSelfRel(),
                        linkTo(methodOn(DishController.class).all()).withRel("dishes")))
                .collect(Collectors.toList());
        return CollectionModel.of(dishEntities, linkTo(methodOn(DishController.class).all()).withSelfRel());
    }

    @PostMapping("/dishes")
    public Dishes newDish(@RequestBody Dishes newDish) {
        System.out.println();
        return dishesRepo.save(newDish);
    }
    @GetMapping("dishes/{id}")
    public EntityModel<Dishes> one(@PathVariable Long id) {
        Dishes dish = dishesRepo.findById(id)
                .orElseThrow(() -> new DishNotFound(id));
        return EntityModel.of(dish,
                linkTo(methodOn(DishController.class).one(id)).withSelfRel(),
                linkTo(methodOn(DishController.class).all()).withRel("dishes"));
    }

    @PutMapping("dishes/{id}")
    public Dishes replaceDish(@RequestBody Dishes newDish, @PathVariable Long id) {
        return dishesRepo.findById(id)
                .map(dish -> {
                    dish.setId(newDish.getId());
                    dish.setName(newDish.getName());
                    dish.setDescription(newDish.getDescription());
                    dish.setIngredients(newDish.getIngredients());
                    dish.setAvariable(newDish.getAvariable());
                    // falta set restaurant
                    // También puedes manejar la actualización de relaciones con branch y restaurant aquí si es necesario
                    return dishesRepo.save(dish);
                })
                .orElseGet(() -> {
                    newDish.setId(id);
                    return dishesRepo.save(newDish);
                });
    }
}
