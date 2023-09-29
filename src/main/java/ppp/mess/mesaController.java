package ppp.mess;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
class mesaController {


    private final MesasRepo mesasRepo;

    public mesaController(MesasRepo mesasRepo) {
        this.mesasRepo = mesasRepo;
    }

    @GetMapping("/mesas")
    public CollectionModel<EntityModel<Mesas>> all() {
        List<EntityModel<Mesas>> mesaEntities = mesasRepo.findAll().stream()
                .map(mesa -> EntityModel.of(mesa,
                        linkTo(methodOn(mesaController.class).one(mesa.getId())).withSelfRel(),
                        linkTo(methodOn(mesaController.class).all()).withRel("mesas")))
                .collect(Collectors.toList());
        return CollectionModel.of(mesaEntities, linkTo(methodOn(mesaController.class).all()).withSelfRel());
    }

    @PostMapping("/mesas")
    public Mesas newMesa(@RequestBody Mesas newMesa) {
        return mesasRepo.save(newMesa);
    }

    @GetMapping("mesas/{id}")
    public EntityModel<Mesas> one(@PathVariable Long id) {
        Mesas mesa = mesasRepo.findById(id)
                .orElseThrow(() -> new MesaNotFound(id));
        return EntityModel.of(mesa,
                linkTo(methodOn(mesaController.class).one(id)).withSelfRel(),
                linkTo(methodOn(mesaController.class).all()).withRel("mesas"));
    }

    @PutMapping("mesas/{id}")
    public Mesas replaceMesa(@RequestBody Mesas newMesa, @PathVariable Long id) {
        return mesasRepo.findById(id)
                .map(mesa -> {
                    mesa.setNumber(newMesa.getNumber());
                    mesa.setCapacity(newMesa.getCapacity());
                    mesa.setSplit(newMesa.getSplit());
                    mesa.setAvariable(newMesa.getAvariable());
                    // También puedes manejar la actualización de relaciones con branch y restaurant aquí si es necesario
                    return mesasRepo.save(mesa);
                })
                .orElseGet(() -> {
                    newMesa.setId(id);
                    return mesasRepo.save(newMesa);
                });
    }

    @DeleteMapping("mesas/{id}")
    public void deleteMesa(@PathVariable Long id) {
        mesasRepo.deleteById(id);
    }
}
